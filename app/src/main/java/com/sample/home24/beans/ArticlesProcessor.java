package com.sample.home24.beans;

import com.sample.home24.room.Article;

import java.util.List;

public class ArticlesProcessor {
    private String error;
    private List<Article> articleList;
    private int totalCount;
    private int totalLikeDisLIkeCount;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<Article> getArticleList() {
        return articleList;
    }

    public void setArticleList(List<Article> articleList) {
        this.articleList = articleList;
    }

    public int getTotalLikeDisLIkeCount() {
        return totalLikeDisLIkeCount;
    }

    public void setTotalLikeDisLIkeCount(int totalLikeDisLIkeCount) {
        this.totalLikeDisLIkeCount = totalLikeDisLIkeCount;
    }
}
