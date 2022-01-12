package com.example.nhom14_galfourteenth.fragment;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.loader.content.CursorLoader;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toolbar;

import com.example.nhom14_galfourteenth.Album;
import com.example.nhom14_galfourteenth.MainActivity;
import com.example.nhom14_galfourteenth.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class FragmentLayoutMain extends Fragment implements FragmentCallbacks {
    MainActivity main;
    Context context = null;
    FragmentTransaction ft;
    FragmentBottom fragmentBottom;
    FragmentImage fragmentImage;
    FragmentAlbum fragmentAlbum;
    static ArrayList<String> listImages = new ArrayList<String>();
    static ArrayList<Album> listAlbums = new ArrayList<>();
    String rootAppFolder = Environment.getExternalStorageDirectory() + "/DCIM/";
//    ArrayList<String> listAlbums = new ArrayList<>();
    MaterialToolbar topAppbar;
    BottomNavigationView bottomNavigationView;

    public static FragmentLayoutMain newInstance(String strArg, ArrayList<String> lsImages, ArrayList<Album> lsAlbums) {
        FragmentLayoutMain fragment = new FragmentLayoutMain();
        Bundle args = new Bundle();
        args.putString("strArg1", strArg);
        fragment.setArguments(args);

        listImages = lsImages;
        listAlbums = lsAlbums;


        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            context = getActivity();
            main = (MainActivity) getActivity();
        } catch (IllegalStateException e) {
            throw new IllegalStateException("MainActivity must implement callbacks");
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout layout_main = (LinearLayout) inflater.inflate(R.layout.layout_main, null);

        ft = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentImage = FragmentImage.newInstance("images", this.listImages);
        fragmentAlbum = FragmentAlbum.newInstance("albums", this.listAlbums);
        ft.replace(R.id.main_middle, fragmentImage);
        ft.commit();

        bottomNavigationView = (BottomNavigationView) layout_main.findViewById(R.id.bottom_navigation);
        bottomNavigationView.setLabelVisibilityMode(NavigationBarView.LABEL_VISIBILITY_LABELED);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                switch (item.getItemId()) {
                    case R.id.hinh_anh:
                        FragmentImage.listImages = listImages;
                        replaceFragment(fragmentImage);
                        topAppbar.setTitle("Hình ảnh");
//                        topAppbar.setM
                        break;
                    case R.id.album:
                        replaceFragment(fragmentAlbum);
                        topAppbar.setTitle("Album");
                        break;
                    case R.id.video:
                        topAppbar.setTitle("Video");
                        break;
                    case R.id.chia_se:
                        topAppbar.setTitle("Chia sẻ");
                        break;
                }
                return true;
            }
        });

        topAppbar = (MaterialToolbar) layout_main.findViewById(R.id.topAppBar);
        topAppbar.setOnMenuItemClickListener(new androidx.appcompat.widget.Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case (R.id.more):
                        ShowDialog();
                        break;
                }
                return false;
            }
        });

        return layout_main;
    }

    @SuppressLint("ResourceType")
    public void replaceFragment(Fragment someFragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_middle, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
//
//    FragmentTransaction ft;
//    FragmentTop fragmentTop;
//    FragmentBottom fragmentBottom;
//    FragmentMiddle fragmentMiddle;
//    ArrayList<String> listImages = new ArrayList<String>();
//    ArrayList<String> listAlbums = new ArrayList<>();
//    MaterialToolbar topAppbar;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//            requestPermissions(
//                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1
//            );
//        }
//        getSupportActionBar().hide();
//        this.listImages = getListImages();
//        this.listAlbums = getListAlBums();
//
//        ft = getSupportFragmentManager().beginTransaction();
////        fragmentTop = FragmentTop.newInstance("menus");
//        fragmentBottom = FragmentBottom.newInstance("menus");
//        fragmentMiddle = FragmentMiddle.newInstance("images", this.listImages);
////        ft.replace(R.id.main_top, fragmentTop);
//        ft.replace(R.id.main_middle, fragmentMiddle);
//        ft.replace(R.id.main_bottom, fragmentBottom);
//        ft.commit();
//


//    }
//
//
    private void ShowDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(main);
        builder.setTitle("Tạo Album mới");
        EditText edtNewAlbum = new EditText(main);
        edtNewAlbum.setHint("Nhập tên album");
        builder.setView(edtNewAlbum);
        builder.setPositiveButton("Tạo", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Album newAlbum = new Album(edtNewAlbum.getText().toString());
                listAlbums.add(newAlbum);
                createAblum(newAlbum.getName());
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

        File album = new File(rootAppFolder + name);
        try {
            if (!album.exists()) {
                Files.createDirectory(album.toPath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return album.toPath().toString();
    }


}