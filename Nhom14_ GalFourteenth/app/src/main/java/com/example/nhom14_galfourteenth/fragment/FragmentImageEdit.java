package com.example.nhom14_galfourteenth.fragment;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.nhom14_galfourteenth.MainActivity;
import com.example.nhom14_galfourteenth.R;
import com.example.nhom14_galfourteenth.adapter.MyImageAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class FragmentImageEdit extends Fragment implements FragmentCallbacks{
    MainActivity main;
    Context context = null;
    BottomNavigationView bottomMenu;
    static String url;
    ImageView imgEdit;
    TextView imgTitle;

    @Override
    public void onMsgFromMainToFragment(String strValue) {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            context = getActivity();
            main = (MainActivity) getActivity();
        } catch (IllegalStateException e) {
            throw new IllegalStateException("MainActivity must implement callbacks");
        }
    }

    public static FragmentImageEdit newInstance(String strArg, String _url) {
        FragmentImageEdit fragment = new FragmentImageEdit();
        Bundle args = new Bundle();
        args.putString("strArg1", strArg);
        url = _url;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.layout_image_edit, null);
        imgEdit = layout.findViewById(R.id.imgEdit);
        imgTitle = layout.findViewById(R.id.txtTitleEditImage);
        bottomMenu = layout.findViewById(R.id.bottom_navigation_edit);
        bottomMenu.getMenu().setGroupCheckable(0, false, false);
        setImageView(url);

        return layout;
    }

    public void setImageView(String url) {
        String imageSelected = url;
        String[] splitImageSelected = imageSelected.split("/");
        imgTitle.setText(splitImageSelected[splitImageSelected.length - 1]);
        imgEdit.setImageBitmap(BitmapFactory.decodeFile(imageSelected));
    }
}
