package com.sample.home24.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ArticleDao {
    @Query("SELECT * FROM article")
    List<Article> getAllArticles();

    @Query("SELECT COUNT(*) FROM article WHERE isLiked=1")
    int getLikedCount();

    @Query("UPDATE article SET isLiked=1,isDisliked=0 WHERE id=:id")
    int doLike(int id);

    @Query("UPDATE article SET isLiked=0,isDisliked=1 WHERE id=:id")
    int doDisLike(int id);

    @Insert
    void insertArticle(Article article);

    @Query("UPDATE article SET articleImageUrl=:url,tittle=:tittle WHERE sku=:sku")
    int updateArticle(String url, String tittle, String sku);

    @Query("SELECT * FROM article WHERE id=:id")
    Article getArticle(int id);

    @Query("SELECT COUNT(*) FROM article WHERE isDisliked=1")
    int getDisLikeCount();

}
