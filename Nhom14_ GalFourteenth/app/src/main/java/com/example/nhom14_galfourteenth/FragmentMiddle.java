package com.example.nhom14_galfourteenth;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class FragmentMiddle extends Fragment implements FragmentCallbacks {
    MainActivity main;
    Context context = null;
    GridView gridListImage;

    Integer[] thumbnails = {R.drawable.button_bg_round, R.drawable.button_bg_round, R.drawable.button_bg_round, R.drawable.button_bg_round};

    public static FragmentMiddle newInstance(String strArg) {
        FragmentMiddle fragment = new FragmentMiddle();
        Bundle args = new Bundle();
        args.putString("strArg1", strArg);
        fragment.setArguments(args);
        return fragment;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout layout_middle = (LinearLayout) inflater.inflate(R.layout.layout_middle, null);
        gridListImage = (GridView) layout_middle.findViewById(R.id.GridImages);
        gridListImage.setAdapter(new MyImageAdapter(context, thumbnails));
//        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) { showBigScreen(position); }
//        });

        return layout_middle;
    }

}
