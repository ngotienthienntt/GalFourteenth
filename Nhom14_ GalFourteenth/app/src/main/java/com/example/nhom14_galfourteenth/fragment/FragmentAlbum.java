package com.example.nhom14_galfourteenth.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.nhom14_galfourteenth.Album;
import com.example.nhom14_galfourteenth.MainActivity;
import com.example.nhom14_galfourteenth.R;
import com.example.nhom14_galfourteenth.adapter.MyAlbumAdapter;

import java.io.IOException;
import java.util.ArrayList;

public class FragmentAlbum extends Fragment implements FragmentCallbacks {
    MainActivity main;
    Context context = null;
    GridView gridAlbum;
    ArrayList<String> listAvatarOfAlbums = new ArrayList<>();
    ArrayList<String> listImgOfAlbum = new ArrayList<>();
    static ArrayList<String> listImagePaths;
    static ArrayList<Album> listAlbums = new ArrayList<>();

    public static FragmentAlbum newInstance(String strArg, ArrayList<Album> lsAlbums) {
        FragmentAlbum fragment = new FragmentAlbum();
        Bundle args = new Bundle();
        args.putString("strArg1", strArg);
        fragment.setArguments(args);

        listAlbums = lsAlbums;


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

        gridAlbum.setAdapter(new MyAlbumAdapter(main, context, listAlbums));
        gridAlbum.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (listAlbums.get(position).getListImage().size() == 0) {
                    FragmentAddImg fragmentAdd = FragmentAddImg.newInstance("addImgToAlbum", listAlbums.get(position).getPath());
                    replaceFragment((fragmentAdd));
                } else {
                    FragmentImage fragmentImage = FragmentImage.newInstance("imagesOfAlbum", listAlbums.get(position).getListImage());
                    replaceFragment((fragmentImage));
                }
            }
        });

        return layout_album;
    }

    @SuppressLint("ResourceType")
    public void replaceFragment(Fragment someFragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_middle, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public ArrayList<String> getListAvatarOfAlbums(ArrayList<String> album, ArrayList<String> images) {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < album.size(); i++) {
            for (int j = 0; j < images.size(); j++) {
                String path = images.get(j);
                String[] word = path.split("/");
                String newWord = word[word.length - 2];

                if (album.get(i).equals(newWord)) {
                    list.add(images.get(j));
                    break;
                }
            }
        }
        return list;
    }

    public ArrayList<String> getListImgOfAlbum(String nameAlbum, ArrayList<String> images) {
        ArrayList<String> listImages = new ArrayList<>();

        for (int i = 0; i < images.size(); i++) {
            String path = images.get(i);
            String[] word = path.split("/");
            String newWord = word[word.length - 2];
            if (nameAlbum.equals(newWord)) {
                listImages.add(images.get(i));
            }
        }
        return listImages;
    }

    @Override
    public void onMsgFromMainToFragment(String strValue) {

    }

    public void onAlbumFromMainToFragment(ArrayList<Album> _listAlbums) {
        this.listAlbums = _listAlbums;
    }
}
