package com.example.nhom14_galfourteenth.fragment;

import android.app.WallpaperManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.example.nhom14_galfourteenth.BuildConfig;
import com.example.nhom14_galfourteenth.ImageEdit;
import com.example.nhom14_galfourteenth.MainActivity;
import com.example.nhom14_galfourteenth.R;
import com.example.nhom14_galfourteenth.common.OnSwipeTouchListener;
import com.example.nhom14_galfourteenth.common.ZoomInZoomOut;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.util.ArrayList;

public class FragmentDetail extends Fragment implements FragmentCallbacks {

    MainActivity main;
    Context context = null;
    ImageView imgDetail;
    TextView title;
    BottomNavigationView bottomMenu;
    static int position;
    static ArrayList<String> listImages = new ArrayList<>();
    String rootAppFolder = Environment.getExternalStorageDirectory() + "/DCIM/";

    public static FragmentDetail newInstance(String strArg, int _position, ArrayList<String> _listImages) {
        FragmentDetail fragment = new FragmentDetail();
        Bundle args = new Bundle();
        args.putString("strArg1", strArg);
        fragment.setArguments(args);
        position = _position;
        listImages = _listImages;
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
        LinearLayout layoutDetail = (LinearLayout) inflater.inflate(R.layout.layout_image_detail, null);

        title = (TextView) layoutDetail.findViewById(R.id.txtTitleDetailPicture);
        imgDetail = (ImageView) layoutDetail.findViewById(R.id.imgDetailPicture);
        setImageView(position);
        bottomMenu = (BottomNavigationView) layoutDetail.findViewById(R.id.bottom_navigation);
        bottomMenu.getMenu().setGroupCheckable(0, false, false);

        imgDetail.setOnTouchListener(new OnSwipeTouchListener(context) {
            public void onSwipeTop() {
            }

            public void onSwipeRight() {
                if (position > 0) {
                    position -= 1;
                    setImageView(position);
                }
            }

            public void onSwipeLeft() {
                if (position < listImages.size() - 1) {
                    position += 1;
                    setImageView(position);
                }
            }

            public void onSwipeBottom() {
            }
        });

//        imgDetail.setOnTouchListener(new ZoomInZoomOut(){
//            public void onTouch() {
//            }
//        });

        bottomMenu.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.detail_edit:
//                        String pathImageFilter = Objects.requireNonNull(getIntent().getStringExtra("path"));
                        Intent filter_intent = new Intent(getActivity(), ImageEdit.class);
                        filter_intent.putExtra("pathFilter", listImages.get(position));
                        startActivity(filter_intent);
                        break;
                    case R.id.detail_delete:
                        deleteImage(listImages.get(position));
                        break;
                    case R.id.detail_setbackground:
                        setWallpaperManager(listImages.get(position));
                        break;
                    case R.id.detail_share:
                        shareImg(listImages.get(position));
                        break;
                    case R.id.detail_favorite:
                        addImageToFavoriteList(listImages.get(position));
                        break;
                }
                return true;
            }
        });

        return layoutDetail;
    }

    @Override
    public void onMsgFromMainToFragment(String strValue) {

    }

    public void addImageToFavoriteList(String path){
        File album = new File(rootAppFolder + "Favorite");
        try {
            if (!album.exists()) {
                Files.createDirectory(album.toPath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        createFile(path, album.getPath());
        Toast toast = Toast.makeText(context,
                "Đã thêm ảnh vào album yêu thích", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();
    }

    public void createFile(String path, String albumpath) {
        try {
            File sourceFile = new File(path);
            File destFile = new File(albumpath + path.substring(path.lastIndexOf("/")));
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
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        main.onMsgFromFragToMain("AddImg-Flag", "load");
    }

    public void setImageView(int position) {
        String imageSelected = listImages.get(position);
        Log.i("ShowRa", imageSelected);
        String[] splitImageSelected = listImages.get(position).split("/");
        title.setText(splitImageSelected[splitImageSelected.length - 1]);
        imgDetail.setImageBitmap(BitmapFactory.decodeFile(imageSelected));
    }

    public void deleteImage(String file) {
        String where = MediaStore.MediaColumns.DATA + "=?";
        final String[] selectionArgs = new String[]{
                file
        };

        File File = new File(file);
        final ContentResolver contentResolver = context.getContentResolver();
        final Uri filesUri = MediaStore.Files.getContentUri("external");

        if (File.exists()) {
            contentResolver.delete(filesUri, where, selectionArgs);
            int delPos = position;
            if (position == 0 && listImages.size() > 1) {
                position++;
            } else {
                position--;
            }
            setImageView(position);
            Toast toast = Toast.makeText(context,
                    "Xóa ảnh thành công", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();
            listImages.remove(delPos);
        }
    }

    private void setWallpaperManager(String path) {
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(context);
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeFile(path, bmOptions);
        bitmap = Bitmap.createScaledBitmap(bitmap, 1080, 2220, true);
        try {
            wallpaperManager.setBitmap(bitmap);
            Toast toast = Toast.makeText(context,
                    "Đặt ảnh nền thành công", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void shareImg(String path) {
        File file = new File(path);
//        Uri uri = Uri.fromFile(file);
        Uri uri = FileProvider.getUriForFile(main, BuildConfig.APPLICATION_ID + ".provider", file);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        context.startActivity(intent);
    }
}
