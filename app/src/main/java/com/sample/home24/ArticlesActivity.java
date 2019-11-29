package com.sample.home24;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.sample.home24.beans.ActionProcessor;
import com.sample.home24.beans.ArticlesProcessor;
import com.sample.home24.room.Article;
import com.sample.home24.utils.ActionListeners;
import com.sample.home24.utils.AppConstants;
import com.sample.home24.viewmodel.ArticleViewModel;

public class ArticlesActivity extends AppCompatActivity implements ActionListeners {
    private ProgressBar progressBar;
    private TextView errorView;
    private ArticleViewModel mArticleViewModel;
    private int currentPageId = 0;
    private Article currentArticle;
    private ArticleFragment articleFragment;
    private TextView nextButton, previousButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles);
        init();
        setValues();
    }

    private void setValues() {
        mArticleViewModel.getActionProcessorMutableLiveData().observe(this,
                new Observer<ActionProcessor>() {
                    @Override
                    public void onChanged(ActionProcessor actionProcessor) {
                        if (currentPageId == actionProcessor.getArticle().id) {
                            articleFragment.updateCount(actionProcessor.getArticle(),
                                    actionProcessor.getTotalLikeCount());
                            articleFragment.enableButton(actionProcessor.getTotalLikeDisLIkeCount());
                        } else {
                            currentPageId = actionProcessor.getArticle().id;
                            currentArticle = actionProcessor.getArticle();
                            showArticleToTheUser(actionProcessor.getTotalLikeCount(),
                                    actionProcessor.getTotalLikeDisLIkeCount());
                        }
                    }
                });
        showProgressbar();
        mArticleViewModel.getArticles(AppConstants.ARTICLE_TOTAL_COUNT)
                .observe(this, new Observer<ArticlesProcessor>() {
                    @Override
                    public void onChanged(ArticlesProcessor articlesProcessor) {
                        if (articlesProcessor.getError() == null) {
                            for (Article article : articlesProcessor.getArticleList()) {
                                currentPageId = article.id;
                                currentArticle = article;
                                if (!article.isLiked && !article.isDisliked) {
                                    break;
                                }
                            }
                            showArticleToTheUser(articlesProcessor.getTotalCount(),
                                    articlesProcessor.getTotalLikeDisLIkeCount());
                            articleFragment.updateCount(currentArticle,
                                    articlesProcessor.getTotalCount());
                            articleFragment.enableButton(articlesProcessor.getTotalLikeDisLIkeCount());
                        } else {
                            showError(articlesProcessor.getError());
                        }
                    }
                });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentPageId + 1 <= AppConstants.ARTICLE_TOTAL_COUNT) {
                    mArticleViewModel.getArticle(currentPageId + 1);
                } else {
                    Toast.makeText(ArticlesActivity.this, "Reached Last Article",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentPageId - 1 > 0) {
                    mArticleViewModel.getArticle(currentPageId - 1);
                } else {
                    Toast.makeText(ArticlesActivity.this, "This is the first article",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void showProgressbar() {
        progressBar.setVisibility(View.VISIBLE);
        errorView.setVisibility(View.GONE);
    }

    private void showError(String message) {
        progressBar.setVisibility(View.GONE);
        errorView.setVisibility(View.VISIBLE);
        errorView.setText(message);
    }

    private void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
        errorView.setVisibility(View.GONE);
    }

    private void showArticleToTheUser(int totalCount, int totalLikeDisLikeCount) {
        if (currentPageId != 0 && currentArticle != null) {
            hideProgressBar();
            articleFragment = ArticleFragment.newInstance(currentArticle, totalCount,
                    totalLikeDisLikeCount, this);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.fl_content_pane, articleFragment)
                    .commit();
        } else {
            showError("Something Went Wrong");
        }
    }

    private void init() {
        errorView = findViewById(R.id.tv_error_text);
        progressBar = findViewById(R.id.pb_article_loader);
        nextButton = findViewById(R.id.btn_next);
        previousButton = findViewById(R.id.btn_previous);
        mArticleViewModel = ViewModelProviders.of(this).get(ArticleViewModel.class);
    }


    @Override
    public void onLikeClick(Article article) {
        mArticleViewModel.updateLike(article);
    }

    @Override
    public void onDisLikeClick(Article article) {
        mArticleViewModel.updateDisLike(article);
    }
}
