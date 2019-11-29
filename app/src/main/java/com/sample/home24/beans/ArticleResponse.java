package com.sample.home24.beans;
/**
 * Awesome Pojo Generator
 * */
public class ArticleResponse{
  private _embedded _embedded;
  private Integer articlesCount;
  public void set_embedded(_embedded _embedded){
   this._embedded=_embedded;
  }
  public _embedded get_embedded(){
   return _embedded;
  }
  public void setArticlesCount(Integer articlesCount){
   this.articlesCount=articlesCount;
  }
  public Integer getArticlesCount(){
   return articlesCount;
  }
}