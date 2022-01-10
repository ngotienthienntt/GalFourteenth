package com.example.nhom14_galfourteenth;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.nhom14_galfourteenth.common.OnSwipeTouchListener;
import com.example.nhom14_galfourteenth.fragment.FragmentAlbum;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.io.File;
import java.util.ArrayList;

public class ImageDetail extends AppCompatActivity {

    Intent mInten;
    int position;
    ImageView imgDetail;
    TextView title;
    ImageView btnBack;
    BottomNavigationView bottomMenu;
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
        bottomMenu = (BottomNavigationView)findViewById(R.id.bottom_navigation) ;
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

        bottomMenu.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.detail_delete:

                        deleteImage(listImages.get(position));
                        break;
                    case R.id.detail_edit:
                        break;
                }
                return true;
            }
        });
    }

    public void setImageView(int position){
        String imageSelected = listImages.get(position);
        String [] splitImageSelected = listImages.get(position).split("/");
        title.setText(splitImageSelected[splitImageSelected.length - 1]);
        imgDetail.setImageBitmap(BitmapFactory.decodeFile(imageSelected));
    }

    public void deleteImage(String file){
        String where = MediaStore.MediaColumns.DATA + "=?";
        final String[] selectionArgs = new String[] {
                file
        };

        File File = new File(file);
        final ContentResolver contentResolver = getBaseContext().getContentResolver();
        final Uri filesUri = MediaStore.Files.getContentUri("external");

        if (File.exists()) {
            contentResolver.delete(filesUri, where, selectionArgs);
            int delPos = position;
            if (position == listImages.size() - 1 && listImages.size() > 1){
                position--;
            }
            else if (position == 0 && listImages.size() > 1){
                position++;
            }
            setImageView(position);
            Toast toast= Toast.makeText(getApplicationContext(),
                    "Xóa ảnh thành công", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER|Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();
            listImages.remove(delPos);
        }
    }
}
