package com.example.nhom14_galfourteenth;

import android.app.WallpaperManager;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nhom14_galfourteenth.common.OnSwipeTouchListener;
import com.example.nhom14_galfourteenth.fragment.FragmentLayoutMain;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class ImageDetail extends AppCompatActivity {

    Intent mInten;
    int position;
    ImageView imgDetail;
    TextView title;
    ImageView btnBack;
    BottomNavigationView bottomMenu;
    ArrayList<String> listImages = new ArrayList<>();
    FragmentLayoutMain fragmentLayoutMain;
    MainActivity main;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_image_detail);
        mInten = getIntent();
        listImages = mInten.getStringArrayListExtra("listImage");
        position = Integer.parseInt(mInten.getStringExtra("position"));

        title = (TextView) findViewById(R.id.txtTitleDetailPicture);
        imgDetail = (ImageView) findViewById(R.id.imgDetailPicture);

        bottomMenu = (BottomNavigationView) findViewById(R.id.bottom_navigation);

//        bottomMenu.setDefaultFocusHighlightEnabled(false);
        setImageView(position);
//        btnBack = (ImageView) findViewById(R.id.btnBack);

//        btnBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent mIntent = new Intent(getBaseContext(),MainActivity.class);
//                startActivity(mIntent);
//            }
//        });

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
                        setWallpaperManager(listImages.get(position));
                        break;
                }
                return true;
            }
        });


    }

    public void setImageView(int position) {
        String imageSelected = listImages.get(position);
        String[] splitImageSelected = listImages.get(position).split("/");
        title.setText(splitImageSelected[splitImageSelected.length - 1]);
        imgDetail.setImageBitmap(BitmapFactory.decodeFile(imageSelected));
    }

    public void deleteImage(String file) {
        String where = MediaStore.MediaColumns.DATA + "=?";
        final String[] selectionArgs = new String[]{
                file
        };

        File File = new File(file);
        final ContentResolver contentResolver = getBaseContext().getContentResolver();
        final Uri filesUri = MediaStore.Files.getContentUri("external");

        if (File.exists()) {
            contentResolver.delete(filesUri, where, selectionArgs);
            int delPos = position;
            if (position == 0 && listImages.size() > 1) {
                position++;
            } else {
                position--;
            }
            setImageView(position);
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Xóa ảnh thành công", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();
            listImages.remove(delPos);

        }
    }

    private void setWallpaperManager(String path) {
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeFile(path, bmOptions);
        bitmap = Bitmap.createScaledBitmap(bitmap, 1080, 2220, true);
        try {
            wallpaperManager.setBitmap(bitmap);
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Đặt ảnh nền thành công", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
    }

}
