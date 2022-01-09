package com.example.nhom14_galfourteenth.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.nhom14_galfourteenth.MainActivity;
import com.example.nhom14_galfourteenth.R;

public class FragmentTop extends Fragment implements FragmentCallbacks {
    MainActivity main;
    Context context = null;
    ListView listViewTopMenu;

    public static FragmentTop newInstance(String strArg) {
        FragmentTop fragment = new FragmentTop();
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
        LinearLayout layout_blue = (LinearLayout) inflater.inflate(R.layout.layout_top, null);

        return layout_blue;
    }
}
