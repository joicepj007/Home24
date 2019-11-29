package com.sample.home24.beans;
/**
 * Awesome Pojo Generator
 * */
public class Media{
  private Object size;
  private String mimeType;
  private Object type;
  private Integer priority;
  private String uri;
  public void setSize(Object size){
   this.size=size;
  }
  public Object getSize(){
   return size;
  }
  public void setMimeType(String mimeType){
   this.mimeType=mimeType;
  }
  public String getMimeType(){
   return mimeType;
  }
  public void setType(Object type){
   this.type=type;
  }
  public Object getType(){
   return type;
  }
  public void setPriority(Integer priority){
   this.priority=priority;
  }
  public Integer getPriority(){
   return priority;
  }
  public void setUri(String uri){
   this.uri=uri;
  }
  public String getUri(){
   return uri;
  }
}