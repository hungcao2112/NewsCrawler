package com.example.legen.readnews.library;

import java.io.Serializable;

/**
 * Created by legen on 3/23/2017.
 */

public class News implements Serializable {
    String id;
    String title, link;
    String image;
    public News(){
    }
    public News(String id, String title, String link, String image){
        this.id = id;
        this.title = title;
        this.link = link;
        this.image = image;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {this.id = id;}
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
