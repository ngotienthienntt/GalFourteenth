package com.example.nhom14_galfourteenth;

import java.util.ArrayList;

public class Album {
    String name;
    String path;
    ArrayList<String> listImage;

    public Album(){

    }

    public Album(String name){
        this.name = name;
        this.path = "";
        this.listImage = new ArrayList<>();
    }

    public Album(String name, String path) {
        this.name = name;
        this.path = path;
        this.listImage = new ArrayList<>();
    }

    public Album(String name, String path, ArrayList<String> listImage) {
        this.name = name;
        this.path = path;
        this.listImage = listImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public ArrayList<String> getListImage() {
        return listImage;
    }

    public void setListImage(ArrayList<String> listImage) {
        this.listImage = listImage;
    }

    public void showAlbum(){
        System.out.println(name);
        System.out.println(path);
        for (int i = 0; i < listImage.size(); i++){
            System.out.println(listImage.get(i));
        }
    }

}
