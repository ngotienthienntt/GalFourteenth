package com.example.nhom14_galfourteenth;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ImageDetail extends AppCompatActivity {

    Intent mInten;
    String imageSelected;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_image_detail);
        mInten = getIntent();
        imageSelected = mInten.getStringExtra("selectedImage");
        String [] splitImageSelected = imageSelected.split("/");
        TextView title = (TextView) findViewById(R.id.txtTitleDetailPicture);
        title.setText(splitImageSelected[splitImageSelected.length - 1]);
        ImageView imgDetail = (ImageView) findViewById(R.id.imgDetailPicture);
        imgDetail.setImageBitmap(BitmapFactory.decodeFile(imageSelected));
        ImageView btnBack = (ImageView) findViewById(R.id.btnBack);

//        btnBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent mIntent = new Intent(context,MainActivity.class);
//                startActivity(mIntent);
//            }
//        });
    }
}
