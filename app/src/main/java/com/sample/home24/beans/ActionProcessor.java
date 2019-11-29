package com.sample.home24.beans;

import com.sample.home24.room.Article;

import java.util.List;

public class ActionProcessor {
    private int totalLikeCount;
    private int totalLikeDisLIkeCount;
    private Article article;

    public int getTotalLikeCount() {
        return totalLikeCount;
    }

    public void setTotalLikeCount(int totalLikeCount) {
        this.totalLikeCount = totalLikeCount;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public int getTotalLikeDisLIkeCount() {
        return totalLikeDisLIkeCount;
    }

    public void setTotalLikeDisLIkeCount(int totalLikeDisLIkeCount) {
        this.totalLikeDisLIkeCount = totalLikeDisLIkeCount;
    }
}
