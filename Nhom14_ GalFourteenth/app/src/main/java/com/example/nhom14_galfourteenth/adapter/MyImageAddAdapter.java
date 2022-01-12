package com.example.nhom14_galfourteenth.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nhom14_galfourteenth.R;

import java.util.ArrayList;

public class MyImageAddAdapter extends BaseAdapter {
    private Context context; // main activity’s context
    ArrayList<String> listImages = new ArrayList<>(); // thumbnail data set
    ArrayList<String> lstImgAdd = new ArrayList<String>();

    public MyImageAddAdapter(Context mainActivityContext, ArrayList<String> listImages, ArrayList<String> lstImgAdd) {
        context = mainActivityContext;
        this.listImages = listImages;
        this.lstImgAdd = lstImgAdd;
    }

    // how many entries are there in the data set?
    public int getCount() {
        return listImages.size();
    }

    // what is in a given 'position' in the data set?
    public Object getItem(int position) {
        return listImages.get(position);
    }

    // what is the ID of data item in given 'position‘?
    public long getItemId(int position) {
        return position;
    }

    // create a view for each thumbnail in the data set, add it to gridview
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View addImg = li.inflate(R.layout.layout_image_check, null);
        addImg.setId(position);

        ImageView imgAdd = addImg.findViewById(R.id.imgAdd);
        ImageView imgCheck = addImg.findViewById(R.id.imgCheck);

        String pathImg = this.listImages.get(position);

        imgAdd.setImageBitmap(BitmapFactory.decodeFile(pathImg));

        if (!lstImgAdd.contains(pathImg)) {
            imgCheck.setImageResource(R.drawable.ic_circle);
            imgAdd.setImageAlpha(255);
        } else {
            imgCheck.setImageResource(R.drawable.ic_checked);
            imgAdd.setImageAlpha(155);
        }
        return addImg;
//        ImageView imageView;
//// if possible, reuse (convertView) image already held in cache
//        if (convertView == null) {
//// no previous version of thumbnail held in the scrapview holder define entry in res/values/dimens.xml for grid height,width in dips <dimen name="gridview_size">100dp</dimen>
//// setLayoutParams will do conversion to physical pixels
//            imageView = new ImageView(context);
//            int gridsize = context.getResources().getDimensionPixelOffset(R.dimen.gridview_size);
//            imageView.setLayoutParams(new GridView.LayoutParams(gridsize, gridsize));
//            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//        } else {
//            imageView = (ImageView) convertView;
//        }
//        imageView.setImageBitmap(BitmapFactory.decodeFile(this.listImages.get(position)));
//        imageView.setId(position);
//        return imageView;
    }//getView
}
