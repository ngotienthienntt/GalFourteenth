package com.example.nhom14_galfourteenth.fragment;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.nhom14_galfourteenth.Album;
import com.example.nhom14_galfourteenth.ImageDetail;
import com.example.nhom14_galfourteenth.MainActivity;
import com.example.nhom14_galfourteenth.R;
import com.example.nhom14_galfourteenth.adapter.MyImageAdapter;
import com.example.nhom14_galfourteenth.adapter.MyImageAddAdapter;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class FragmentAddImg extends Fragment implements FragmentCallbacks {
    MainActivity main;
    Context context = null;
    Button btnAdd;
    Button btnDone;
    GridView gridAdd;
    static String pathAblum;
    ArrayList<String> lstImgMain = new ArrayList<String>();
    ArrayList<String> lstImgAdd = new ArrayList<String>();

    public static FragmentAddImg newInstance(String strArg, String path) {
        FragmentAddImg fragment = new FragmentAddImg();
        Bundle args = new Bundle();
        args.putString("strArg1", strArg);
        fragment.setArguments(args);
        pathAblum = path;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            context = getActivity();
            main = (MainActivity) getActivity();
            lstImgMain = main.getLstImg();
        } catch (IllegalStateException e) {
            throw new IllegalStateException("MainActivity must implement callbacks");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout layout_add_img = (LinearLayout) inflater.inflate(R.layout.layout_add_img, null);
        btnAdd = (Button) layout_add_img.findViewById(R.id.btnAddImg);
        btnDone = (Button) layout_add_img.findViewById(R.id.btnDone);
        gridAdd = (GridView) layout_add_img.findViewById(R.id.GridAddImg);

        btnDone.setVisibility(View.GONE);
        gridAdd.setVisibility(View.GONE);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnDone.setVisibility(View.VISIBLE);
                gridAdd.setVisibility(View.VISIBLE);
                btnAdd.setVisibility(View.GONE);
            }
        });

        gridAdd.setAdapter(new MyImageAddAdapter(context, lstImgMain, lstImgAdd));
        gridAdd.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String pathImg = lstImgMain.get(position);
                ImageView imgAdd = view.findViewById(R.id.imgAdd);
                ImageView imgCheck = view.findViewById(R.id.imgCheck);
                if (lstImgAdd.contains(pathImg)) {
                    lstImgAdd.remove(pathImg);
                    imgCheck.setImageResource(R.drawable.ic_circle);
                    imgAdd.setImageAlpha(255);
                } else {
                    lstImgAdd.add(pathImg);
                    main.listImagePaths.add(pathImg);
                    try {
                        main.listAlbums = main.getListAlbums(main.listImagePaths);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    imgCheck.setImageResource(R.drawable.ic_checked);
                    imgAdd.setImageAlpha(155);
                }
            }
        });

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDialogAddImg();
            }
        });

        return layout_add_img;
    }

    private String copyFile(String srcPath, String desPath) {
        try {
            File sourceFile = new File(srcPath);
            File destFile = new File(desPath + srcPath.substring(srcPath.lastIndexOf("/")));
            if (!destFile.exists()) {
                destFile.createNewFile();

                FileChannel source = null;
                FileChannel destination = null;
                try {
                    source = new FileInputStream(sourceFile).getChannel();
                    destination = new FileOutputStream(destFile).getChannel();
                    destination.transferFrom(source, 0, source.size());
                } finally {
                    if (source != null)
                        source.close();
                    if (destination != null)
                        destination.close();
                    return destFile.getPath();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public void deleteFile(String file) {
        try {
            new File(file).delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMsgFromMainToFragment(String strValue) {

    }

    private void ShowDialogAddImg() {
        AlertDialog.Builder builder = new AlertDialog.Builder(main);
        builder.setTitle("Di chuyển hay sao chép ảnh?");
        builder.setPositiveButton("Di chuyển", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                addImg(true);
            }
        });
        builder.setNegativeButton("Sao chép", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                addImg(false);
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void addImg(boolean move) {
        ArrayList<String> addedPathImgs = new ArrayList<String>();

        for (String item : lstImgAdd) {
            try {
                String added = copyFile(item, pathAblum);
                if (!added.equals("")) {
                    addedPathImgs.add(added);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (move) {
            for (String item : lstImgAdd) {
                try {
                    deleteFile(item);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        main.onMsgFromFragToMain("AddImg-Flag", "load");
        FragmentImage fragmentImage = FragmentImage.newInstance("imagesOfAlbum", addedPathImgs);
        main.getSupportFragmentManager().beginTransaction().replace(R.id.main_middle, fragmentImage).addToBackStack(null).commit();
    }
}
