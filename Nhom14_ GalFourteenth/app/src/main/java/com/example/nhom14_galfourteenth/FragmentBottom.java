package com.example.nhom14_galfourteenth;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class FragmentBottom extends Fragment implements FragmentCallbacks {
    MainActivity main;
    Context context = null;
    ListView listViewTopMenu;
    BottomNavigationView bottomNavigationView;

    public static FragmentBottom newInstance(String strArg) {
        FragmentBottom fragment = new FragmentBottom();
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
        LinearLayout layout_bottom = (LinearLayout) inflater.inflate(R.layout.layout_bottom, null);
        bottomNavigationView = (BottomNavigationView) layout_bottom.findViewById(R.id.bottom_navigation);
        bottomNavigationView.setLabelVisibilityMode(NavigationBarView.LABEL_VISIBILITY_LABELED);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                switch (item.getItemId()) {
                    case R.id.hinh_anh:
                        selectedFragment = new FragmentMiddle();
                        break;
                    case R.id.album:
                        selectedFragment = new FragmentAlbum();
                        break;
                }
                main.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_middle, selectedFragment)
                        .commit();
                return true;
            }
        });

        return layout_bottom;
    }
}
