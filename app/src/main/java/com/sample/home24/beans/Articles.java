package com.sample.home24.beans;
import java.util.List;
/**
 * Awesome Pojo Generator
 * */
public class Articles{
  private List<Media> media;
  private String sku;
  private String title;
  public void setMedia(List<Media> media){
   this.media=media;
  }
  public List<Media> getMedia(){
   return media;
  }
  public void setSku(String sku){
   this.sku=sku;
  }
  public String getSku(){
   return sku;
  }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}