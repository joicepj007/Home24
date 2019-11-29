package com.sample.home24.repository;

import android.content.Context;
import android.os.AsyncTask;

import com.sample.home24.beans.ActionProcessor;
import com.sample.home24.beans.ArticleResponse;
import com.sample.home24.beans.Articles;
import com.sample.home24.beans.ArticlesProcessor;
import com.sample.home24.network.ApiClient;
import com.sample.home24.network.services.ArticleServices;
import com.sample.home24.room.AppDatabase;
import com.sample.home24.room.Article;
import com.sample.home24.utils.AppConstants;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class ArticleRepository {
    private ArticleTask articleTask;
    private ArticlesFetchTask articlesFetchTask;
    private ActionTask actionTask;
    private AppDatabase database;
    private static final int LIKE = 1;
    private static final int DIS_LIKE = 2;
    private static final int FETCH_ARTICLE = 3;

    public ArticleRepository(Context context) {
        database = DatabaseClient.getDatabase(context);
        this.articleTask = new ArticleTask(database);
        this.articlesFetchTask = new ArticlesFetchTask(database);
    }

    public void getArticles(int articleCount, final ArticleCallback callback) {
        articleTask.setArticleCallback(callback);
        articleTask.execute(articleCount);
    }
    public void getArticlesFromDB(ArticleCallback callback){
        articlesFetchTask.setArticleCallback(callback);
        articlesFetchTask.execute();
    }

    public void cancelTasks() {
        articleTask.cancel(true);
        actionTask.cancel(true);
    }

    public void updateLike(int id, ActionCallback actionCallback) {
        this.actionTask = new ActionTask(database);
        actionTask.setActionCallback(actionCallback);
        actionTask.execute(LIKE, id);
    }

    public void updateDisLike(int id, ActionCallback actionCallback) {
        this.actionTask = new ActionTask(database);
        actionTask.setActionCallback(actionCallback);
        actionTask.execute(DIS_LIKE, id);
    }

    public void getArticle(int id, ActionCallback actionCallback) {
        this.actionTask = new ActionTask(database);
        actionTask.setActionCallback(actionCallback);
        actionTask.execute(FETCH_ARTICLE, id);
    }

    public interface ArticleCallback {
        void onResponse(ArticlesProcessor articlesProcessor);
    }

    public interface ActionCallback {
        void onResponse(ActionProcessor actionProcessor);
    }

    private static class ArticleTask extends AsyncTask<Integer, Void, ArticlesProcessor> {
        private ArticleCallback articleCallback;
        private AppDatabase database;

        ArticleTask(AppDatabase database) {
            this.database = database;
        }

        public void setArticleCallback(ArticleCallback articleCallback) {
            this.articleCallback = articleCallback;
        }

        @Override
        protected ArticlesProcessor doInBackground(Integer... articleCounts) {
            ArticleServices articleServices = ApiClient.getArticleService();
            ArticlesProcessor articlesProcessor = new ArticlesProcessor();
            Call<ArticleResponse> articleResponseCall = articleServices
                    .getArticles("2", "de_DE", articleCounts[0]);
            try {
                Response<ArticleResponse> response = articleResponseCall.execute();
                if (response.body() != null) {
                    ArticleResponse articleResponse = response.body();
                    if (articleResponse.get_embedded().getArticles().size() > 0) {
                        for (Articles articles :
                                articleResponse.get_embedded().getArticles()) {
                            Article article = new Article();
                            article.sku = articles.getSku();
                            article.articleImageUrl = articles.getMedia().get(0).getUri();
                            article.isDisliked = false;
                            article.isLiked = false;
                            article.tittle=articles.getTitle();
                            int update =
                                    database.articleDao().updateArticle(article.articleImageUrl,
                                            article.tittle,
                                            article.sku);
                            if (update <= 0) {
                                database.articleDao().insertArticle(article);
                            }
                        }
                        List<Article> articlesList = database.articleDao().getAllArticles();
                        articlesProcessor.setArticleList(articlesList);
                        int likeCount = database.articleDao().getLikedCount();
                        int disLikeCount = database.articleDao().getDisLikeCount();
                        articlesProcessor.setTotalCount(likeCount);
                        articlesProcessor.setTotalLikeDisLIkeCount(likeCount + disLikeCount);
                    } else {
                        articlesProcessor.setError("Empty Response");
                    }
                } else {
                    if (response.errorBody() != null)
                        articlesProcessor.setError(response.errorBody().string());
                    else
                        articlesProcessor.setError("Server Issue");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return articlesProcessor;
        }

        @Override
        protected void onPostExecute(ArticlesProcessor articlesProcessor) {
            super.onPostExecute(articlesProcessor);
            articleCallback.onResponse(articlesProcessor);
        }
    }

    private static class ActionTask extends AsyncTask<Integer, Void, ActionProcessor> {
        private AppDatabase database;
        private ActionCallback actionCallback;

        public void setActionCallback(ActionCallback actionCallback) {
            this.actionCallback = actionCallback;
        }

        ActionTask(AppDatabase database) {
            this.database = database;
        }

        @Override
        protected ActionProcessor doInBackground(Integer... integers) {
            int action = integers[0];
            int id = integers[1];
            ActionProcessor actionProcessor = null;
            switch (action) {
                case LIKE:
                    database.articleDao().doLike(id);
                    if (id + 1 <= AppConstants.ARTICLE_TOTAL_COUNT) {
                        id = id + 1;
                    }
                    actionProcessor = new ActionProcessor();
                    actionProcessor.setArticle(database.articleDao().getArticle(id));
                    break;
                case DIS_LIKE:
                    database.articleDao().doDisLike(id);
                    if (id + 1 <= AppConstants.ARTICLE_TOTAL_COUNT) {
                        id = id + 1;
                    }
                    actionProcessor = new ActionProcessor();
                    actionProcessor.setArticle(database.articleDao().getArticle(id));
                    break;
                case FETCH_ARTICLE:
                    actionProcessor = new ActionProcessor();
                    actionProcessor.setArticle(database.articleDao().getArticle(id));
                    break;

            }
            if (actionProcessor != null) {
                int likeCount = database.articleDao().getLikedCount();
                int disLikeCount = database.articleDao().getDisLikeCount();
                actionProcessor.setTotalLikeCount(likeCount);
                actionProcessor.setTotalLikeDisLIkeCount(likeCount + disLikeCount);
            }
            return actionProcessor;
        }

        @Override
        protected void onPostExecute(ActionProcessor actionProcessor) {
            super.onPostExecute(actionProcessor);
            actionCallback.onResponse(actionProcessor);
        }
    }

    private static class ArticlesFetchTask extends AsyncTask<Void, Void, ArticlesProcessor> {
        private ArticleCallback articleCallback;
        private AppDatabase database;

        ArticlesFetchTask(AppDatabase database) {
            this.database = database;
        }

        public void setArticleCallback(ArticleCallback articleCallback) {
            this.articleCallback = articleCallback;
        }

        @Override
        protected ArticlesProcessor doInBackground(Void... voids) {
            ArticlesProcessor articlesProcessor = new ArticlesProcessor();
            List<Article> articlesList = database.articleDao().getAllArticles();
            articlesProcessor.setArticleList(articlesList);
            int likeCount = database.articleDao().getLikedCount();
            int disLikeCount = database.articleDao().getDisLikeCount();
            articlesProcessor.setTotalCount(likeCount);
            articlesProcessor.setTotalLikeDisLIkeCount(likeCount + disLikeCount);
            return articlesProcessor;
        }

        @Override
        protected void onPostExecute(ArticlesProcessor articlesProcessor) {
            super.onPostExecute(articlesProcessor);
            articleCallback.onResponse(articlesProcessor);
        }
    }
}
