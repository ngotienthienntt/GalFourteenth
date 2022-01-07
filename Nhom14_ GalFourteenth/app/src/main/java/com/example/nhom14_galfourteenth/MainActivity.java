package com.example.nhom14_galfourteenth;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.loader.content.CursorLoader;

import com.google.android.material.appbar.MaterialToolbar;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    FragmentTransaction ft;
    FragmentTop fragmentTop;
    FragmentBottom fragmentBottom;
    FragmentMiddle fragmentMiddle;
    ArrayList<String> listImages = new ArrayList<String>();
    ArrayList<String> listAlbums = new ArrayList<>();
    MaterialToolbar topAppbar;
    String rootAppFolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1
            );
        }
        getSupportActionBar().hide();
        this.listImages = getListImages();
        this.listAlbums = getListAlBums();

        ft = getSupportFragmentManager().beginTransaction();
//        fragmentTop = FragmentTop.newInstance("menus");
        fragmentBottom = FragmentBottom.newInstance("menus");
        fragmentMiddle = FragmentMiddle.newInstance("images", this.listImages);
//        ft.replace(R.id.main_top, fragmentTop);
        ft.replace(R.id.main_middle, fragmentMiddle);
        ft.replace(R.id.main_bottom, fragmentBottom);
        ft.commit();

        topAppbar = (MaterialToolbar) findViewById(R.id.topAppBar);

        topAppbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case (R.id.more):
                        ShowDialog();
                        break;

                }
                return true;
            }
        });

        File dir = new File(Environment.getExternalStorageDirectory() + "/DCIM/GalFourteenth");
        try {
            if (!dir.exists()) {
                Files.createDirectory(dir.toPath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            rootAppFolder = dir.toPath().toString();
        }
    }

    private void ShowDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Tạo Album mới");
        EditText myEditText = new EditText(MainActivity.this);
        myEditText.setHint("Nhập tên album");
        builder.setView(myEditText);
        builder.setPositiveButton("Tạo", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String album = createAblum(myEditText.getText().toString());
                listAlbums.add(album);
//                finish();
            }
        });
        builder.setNegativeButton("Thoát", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();

        alertDialog.show();

    }

    private String createAblum(String name) {
        File album = new File(rootAppFolder + "/" + name);
        try {
            if (!album.exists()) {
                Files.createDirectory(album.toPath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return album.toPath().toString();
    }

    private ArrayList<String> getListImages() {
        ArrayList<String> listImages = new ArrayList<>();
        int column_index_data, column_index_date;
        String pathImage;
        String[] projection = {
                MediaStore.Files.FileColumns._ID,
                MediaStore.Files.FileColumns.DATA,
                MediaStore.Files.FileColumns.DATE_ADDED,
                MediaStore.Files.FileColumns.MEDIA_TYPE,
                MediaStore.Files.FileColumns.MIME_TYPE,
                MediaStore.Files.FileColumns.TITLE
        };

        String selection = MediaStore.Files.FileColumns.MEDIA_TYPE + "="
                + MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE
                + " OR "
                + MediaStore.Files.FileColumns.MEDIA_TYPE + "="
                + MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;

        Uri queryUri = MediaStore.Files.getContentUri("external");
        CursorLoader cursorLoader = new CursorLoader(this,
                queryUri,
                projection,
                selection,
                null, // Selection args (none).
                MediaStore.Files.FileColumns.DATE_ADDED + " DESC" // Sort order.
        );

        Cursor cursor = cursorLoader.loadInBackground();
        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_index_date = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATE_ADDED);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        while (cursor.moveToNext()) {
            pathImage = cursor.getString(column_index_data);
            Log.e("getListImages: ", pathImage);
            listImages.add(pathImage);
        }
        return listImages;
    }

    public ArrayList<String> getListAlBums() {
        ArrayList<String> listAlbums = new ArrayList<>();
        for (int i = 0; i < listImages.size(); i++) {
            String path = listImages.get(i);
            String[] str = path.split("/");
            String tittle = str[str.length - 2];
            if (!listAlbums.contains(tittle)) {
                listAlbums.add(tittle);
            }
        }
        return listAlbums;
    }
}