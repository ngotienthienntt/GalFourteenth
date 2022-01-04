package com.example.nhom14_galfourteenth;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.loader.content.CursorLoader;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FragmentMiddle extends Fragment implements FragmentCallbacks {
    MainActivity main;
    Context context = null;
    GridView gridListImage;
    static ArrayList<String> listImages = new ArrayList<>();
    Bundle currentOriginalMemoryBundle;

    public static FragmentMiddle newInstance(String strArg, ArrayList<String> listImagesFromMain) {
        FragmentMiddle fragment = new FragmentMiddle();
        Bundle args = new Bundle();
        args.putString("strArg1", strArg);
        listImages = listImagesFromMain;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentOriginalMemoryBundle = savedInstanceState;
        try {
            context = getActivity();
            main = (MainActivity) getActivity();
        } catch (IllegalStateException e) {
            throw new IllegalStateException("MainActivity must implement callbacks");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout layout_middle = (LinearLayout) inflater.inflate(R.layout.layout_middle, null);
        gridListImage = (GridView) layout_middle.findViewById(R.id.GridImages);

        gridListImage.setAdapter(new MyImageAdapter(context, listImages));
        gridListImage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showBigScreen(position);
            }
        });

        return layout_middle;
    }

    private void showBigScreen(int position) {
        main.setContentView(R.layout.layout_image_detail);
        String imageSelected = listImages.get(position);
        String [] splitImageSelected = imageSelected.split("/");
        TextView title = (TextView) main.findViewById(R.id.txtTitleDetailPicture);
        title.setText(splitImageSelected[splitImageSelected.length - 1]);
        ImageView imgDetail = (ImageView) main.findViewById(R.id.imgDetailPicture);
        imgDetail.setImageBitmap(BitmapFactory.decodeFile(imageSelected));
        Button btnBack = (Button) main.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(context,MainActivity.class);
                startActivity(mIntent);
            }
        });
    }

}
