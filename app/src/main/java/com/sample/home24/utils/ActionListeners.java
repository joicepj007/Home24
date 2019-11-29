package com.sample.home24.utils;

import com.sample.home24.room.Article;

public interface ActionListeners {
    void onLikeClick(Article article);

    void onDisLikeClick(Article article);
}