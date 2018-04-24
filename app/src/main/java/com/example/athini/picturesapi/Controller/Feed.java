package com.example.athini.picturesapi.Controller;


/**
 * Created by athini on 2018/04/24.
 */

public class Feed {

    private String image, name, content, date;
    public Feed(String image, String name, String content, String date){
        this.image = image;
        this.name = name;
        this.content = content;
        this.date = date;
    }
    public String getImage(){
        return image;
    }
    public String getName(){
        return name;
    }
    public String getContent(){
        return content;
    }
    public String getDate(){
        return date;
    }
}
