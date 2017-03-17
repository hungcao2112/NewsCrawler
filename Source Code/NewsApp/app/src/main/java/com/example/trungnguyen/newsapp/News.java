package com.example.trungnguyen.newsapp;

/**
 * Created by caonguyen on 3/17/17.
 */

public class News {
    private String title, link;
    private String image;
    public News(){
    }
    public News(String title, String link, String image){
        this.title = title;
        this.link = link;
        this.image = image;
    }
    public String getTitle(){
        return title;
    }
    public void setTitle(String name){
        this.title = name;
    }
    public String getLink(){
        return link;
    }
    public void setLink(String link){
        this.link = link;
    }
    public String getImage(){
        return image;
    }
    public void setImage(String image){
        this.image = image;
    }
}
