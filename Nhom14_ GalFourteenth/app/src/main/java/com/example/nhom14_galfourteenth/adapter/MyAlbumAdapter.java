package com.example.nhom14_galfourteenth.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.nhom14_galfourteenth.Album;
import com.example.nhom14_galfourteenth.MainActivity;
import com.example.nhom14_galfourteenth.R;

import java.util.ArrayList;

public class MyAlbumAdapter extends BaseAdapter {
    private MainActivity main;
    private Context context; // main activity’s context
    ArrayList<String> listImages = new ArrayList<>(); // thumbnail data set
    ArrayList<Album> listAlbums = new ArrayList<>();
    LinearLayout layout_album;

    public MyAlbumAdapter(MainActivity main, Context mainActivityContext, ArrayList<Album> listAlbums) {
        this.main = main;
        this.context = mainActivityContext;
        this.listAlbums = listAlbums;

        System.out.println("size" + listAlbums.size());
        for (int i = 0; i < listAlbums.size(); i++){
            listAlbums.get(i).showAlbum();
        }
    }

    public int getCount() {
        return listAlbums.size();
    }

    public Object getItem(int position) {
        return listAlbums.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View album_item = li.inflate(R.layout.layout_album_item, null);
        album_item.setId(position);
        ImageView icon = (ImageView) album_item.findViewById(R.id.icon_album);
        TextView caption = (TextView) album_item.findViewById(R.id.caption_album);
//        if (convertView == null) {
//            int gridsize = context.getResources().getDimensionPixelOffset(R.dimen.gridview_size);
//            icon.setLayoutParams(new GridView.LayoutParams(gridsize, gridsize));
//            icon.setScaleType(ImageView.ScaleType.FIT_XY);
//            icon.setPadding(5, 5, 5, 5);
//
//        } else {
//            icon = (ImageView) convertView;
//        }
        ArrayList<String> listImage = this.listAlbums.get(position).getListImage();

        if(listImage.size() > 0){
            icon.setImageBitmap(BitmapFactory.decodeFile(this.listAlbums.get(position).getListImage().get(0)));
        }else
        {
            icon.setImageResource(R.drawable.icon_add);
        }
        icon.setId(position);
//
//        for (String item : this.listImages) {
//            Log.e("path", item);
//        }

        caption.setText(this.listAlbums.get(position).getName());
        //caption.setBackgroundColor(Color.WHITE);

//        ImageView imageView;
//        if (convertView == null) {
//            imageView = new ImageView(context);
//            int gridsize = context.getResources().getDimensionPixelOffset(R.dimen.gridview_size);
//            imageView.setLayoutParams(new GridView.LayoutParams(gridsize, gridsize));
//            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//            imageView.setPadding(5, 5, 5, 5);
//
//        } else {
//            imageView = (ImageView) convertView;
//        }
//        imageView.setImageBitmap(BitmapFactory.decodeFile(this.listImages.get(position)));
//        imageView.setId(position);
        return album_item;
    }//getView
}
