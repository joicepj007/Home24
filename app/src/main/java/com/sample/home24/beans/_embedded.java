package com.sample.home24.beans;
import java.util.List;
/**
 * Awesome Pojo Generator
 * */
public class _embedded{
  private List<Articles> articles;
  public void setArticles(List<Articles> articles){
   this.articles=articles;
  }
  public List<Articles> getArticles(){
   return articles;
  }
}