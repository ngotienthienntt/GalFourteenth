package com.example.nhom14_galfourteenth;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nhom14_galfourteenth.common.OnSwipeTouchListener;

import java.util.ArrayList;

public class ImageDetail extends AppCompatActivity {

    Intent mInten;
    int position;
    ImageView imgDetail;
    TextView title;
    ImageView btnBack;
    ArrayList<String> listImages = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_image_detail);
        mInten = getIntent();
        listImages = mInten.getStringArrayListExtra("listImage");
        position = Integer.parseInt(mInten.getStringExtra("position"));

        title = (TextView) findViewById(R.id.txtTitleDetailPicture);
        imgDetail = (ImageView) findViewById(R.id.imgDetailPicture);
        setImageView(position);
        btnBack = (ImageView) findViewById(R.id.btnBack);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(getBaseContext(),MainActivity.class);
                startActivity(mIntent);
            }
        });

        imgDetail.setOnTouchListener(new OnSwipeTouchListener(getBaseContext()) {
            public void onSwipeTop() {

            }

            public void onSwipeRight() {
                if (position > 0) {
                    position -= 1;
                    setImageView(position);
                }


            }

            public void onSwipeLeft() {

                if (position < listImages.size() - 1) {
                    position += 1;
                    setImageView(position);
                }
            }

            public void onSwipeBottom() {
            }

        });
    }

    public void setImageView(int position){
        String imageSelected = listImages.get(position);
        String [] splitImageSelected = listImages.get(position).split("/");
        title.setText(splitImageSelected[splitImageSelected.length - 1]);
        imgDetail.setImageBitmap(BitmapFactory.decodeFile(imageSelected));

    }
}
