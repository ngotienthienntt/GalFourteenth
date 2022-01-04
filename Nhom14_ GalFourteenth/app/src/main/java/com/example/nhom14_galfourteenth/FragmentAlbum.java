package com.example.nhom14_galfourteenth;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.loader.content.CursorLoader;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class FragmentAlbum extends Fragment implements FragmentCallbacks {
    MainActivity main;
    Context context = null;
    GridView gridAlbum;

    public static FragmentAlbum newInstance(String strArg) {
        FragmentAlbum fragment = new FragmentAlbum();
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
        LinearLayout layout_album = (LinearLayout) inflater.inflate(R.layout.layout_album, null);
        gridAlbum = (GridView) layout_album.findViewById(R.id.GridAlbum);

        gridAlbum.setAdapter(new MyAlbumAdapter(main,context, main.listImages));

        return layout_album;
    }
}
