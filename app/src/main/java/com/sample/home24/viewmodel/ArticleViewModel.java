package com.sample.home24.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.sample.home24.beans.ActionProcessor;
import com.sample.home24.beans.ArticlesProcessor;
import com.sample.home24.repository.ArticleRepository;
import com.sample.home24.room.Article;

import java.util.List;

public class ArticleViewModel extends AndroidViewModel {
    MutableLiveData<ActionProcessor> actionProcessorMutableLiveData=new MediatorLiveData<>();
    ArticleRepository articleRepository;
    public ArticleViewModel(@NonNull Application application) {
        super(application);
        articleRepository=new ArticleRepository(application);
    }
    public LiveData<ArticlesProcessor> getArticles(int articleCount){
        final MutableLiveData<ArticlesProcessor> articlesProcessorLiveData=new MutableLiveData<>();
        articleRepository.getArticles(articleCount, new ArticleRepository.ArticleCallback() {
            @Override
            public void onResponse(ArticlesProcessor articlesProcessor) {
                articlesProcessorLiveData.setValue(articlesProcessor);
            }
        });
        return articlesProcessorLiveData;
    }
    public LiveData<ArticlesProcessor> getArticlesFromDB(){
        final MutableLiveData<ArticlesProcessor> articlesProcessorLiveData=new MutableLiveData<>();
        articleRepository.getArticlesFromDB( new ArticleRepository.ArticleCallback() {
            @Override
            public void onResponse(ArticlesProcessor articlesProcessor) {
                articlesProcessorLiveData.setValue(articlesProcessor);
            }
        });
        return articlesProcessorLiveData;
    }
    public void updateLike(Article article){
        articleRepository.updateLike(article.id, new ArticleRepository.ActionCallback() {
            @Override
            public void onResponse(ActionProcessor actionProcessor) {
                actionProcessorMutableLiveData.setValue(actionProcessor);
            }
        });
    }
    public void updateDisLike(Article article){
        articleRepository.updateDisLike(article.id, new ArticleRepository.ActionCallback() {
            @Override
            public void onResponse(ActionProcessor actionProcessor) {
                actionProcessorMutableLiveData.setValue(actionProcessor);
            }
        });
    }

    public MutableLiveData<ActionProcessor> getActionProcessorMutableLiveData() {
        return actionProcessorMutableLiveData;
    }

    public void getArticle(int id) {
        articleRepository.getArticle(id, new ArticleRepository.ActionCallback() {
            @Override
            public void onResponse(ActionProcessor actionProcessor) {
                actionProcessorMutableLiveData.setValue(actionProcessor);
            }
        });
    }
}
