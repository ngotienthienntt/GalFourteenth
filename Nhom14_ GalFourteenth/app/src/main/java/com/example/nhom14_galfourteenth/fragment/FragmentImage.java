package com.example.nhom14_galfourteenth.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.nhom14_galfourteenth.ImageDetail;
import com.example.nhom14_galfourteenth.MainActivity;
import com.example.nhom14_galfourteenth.adapter.MyImageAdapter;
import com.example.nhom14_galfourteenth.R;

import java.util.ArrayList;

public class FragmentImage extends Fragment implements FragmentCallbacks {
    MainActivity main;
    Context context = null;
    GridView gridListImage;
    static ArrayList<String> listImages = new ArrayList<>();
    Bundle currentOriginalMemoryBundle;

    public static FragmentImage newInstance(String strArg, ArrayList<String> listImagesFromMain) {
        FragmentImage fragment = new FragmentImage();
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
                Intent mIntent = new Intent(getContext(), ImageDetail.class);
                mIntent.putExtra("selectedImage", listImages.get(position));
                startActivity(mIntent);
            }
        });

        return layout_middle;
    }
}
