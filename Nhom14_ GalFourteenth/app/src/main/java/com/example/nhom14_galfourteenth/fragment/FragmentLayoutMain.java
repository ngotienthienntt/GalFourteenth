package com.example.nhom14_galfourteenth.fragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.loader.content.CursorLoader;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.nhom14_galfourteenth.MainActivity;
import com.example.nhom14_galfourteenth.R;
import com.google.android.material.appbar.MaterialToolbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class FragmentLayoutMain extends Fragment implements FragmentCallbacks {
    MainActivity main;
    Context context = null;
    FragmentTransaction ft;
    FragmentBottom fragmentBottom;
    FragmentImage fragmentImage;
    static ArrayList<String> listImages = new ArrayList<String>();
    ArrayList<String> listAlbums = new ArrayList<>();
    MaterialToolbar topAppbar;

    public static FragmentLayoutMain newInstance(String strArg, ArrayList<String> lsImages) {
        FragmentLayoutMain fragment = new FragmentLayoutMain();
        Bundle args = new Bundle();
        args.putString("strArg1", strArg);
        fragment.setArguments(args);

        listImages = lsImages;

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
//      fragmentBottom = FragmentBottom.newInstance("menus");
        fragmentImage = FragmentImage.newInstance("images", this.listImages);
        ft.replace(R.id.main_middle, fragmentImage);
//      ft.replace(R.id.main_bottom, fragmentBottom);
        ft.commit();
        return layout_main;
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
//        topAppbar = (MaterialToolbar) findViewById(R.id.topAppBar);
//
//        topAppbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                switch (item.getItemId()) {
//                    case (R.id.more):
//                        ShowDialog();
//                        break;
//
//                }
//                return true;
//            }
//        });
//
//    }
//
//
//    private void ShowDialog(){
//        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//        builder.setTitle("Tạo Album mới");
//        EditText myEditText = new EditText(MainActivity.this);
//        myEditText.setHint("Nhập tên album");
//        builder.setView(myEditText);
//        builder.setPositiveButton("Tạo", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                finish();
//            }
//        });
//        builder.setNegativeButton("Thoát", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.cancel();
//            }
//        });
//        AlertDialog alertDialog = builder.create();
//
//        alertDialog.show();
//
//    }
//

}