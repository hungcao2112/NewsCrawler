package com.example.legen.readnews.library;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by legen on 3/23/2017.
 */

public class News implements Parcelable {
    String id, title, link, type, image;
    public News(){
    }
    public News(String id, String title, String link, String type, String image){
        this.id = id;
        this.title = title;
        this.link = link;
        this.type = type;
        this.image = image;
    }

    protected News(Parcel in) {
        id = in.readString();
        title = in.readString();
        link = in.readString();
        type = in.readString();
        image = in.readString();
    }

    public static final Creator<News> CREATOR = new Creator<News>() {
        @Override
        public News createFromParcel(Parcel in) {
            return new News(in);
        }

        @Override
        public News[] newArray(int size) {
            return new News[size];
        }
    };

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
    public String getType(){
        return type;
    }
    public void setType(String link){
        this.type = type;}
    public String getImage(){return image;
    }
    public void setImage(String image){
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(link);
        dest.writeString(type);
        dest.writeString(image);
    }
}
