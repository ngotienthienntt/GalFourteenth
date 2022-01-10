package com.example.nhom14_galfourteenth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.loader.content.CursorLoader;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import com.example.nhom14_galfourteenth.fragment.FragmentLayoutMain;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    FragmentTransaction ft;
    FragmentLayoutMain fragmentLayoutMain;
    ArrayList<String> listImagePaths;
    ArrayList<Album> listAlbums;
//    FragmentTop fragmentTop;
//    FragmentBottom fragmentBottom;
//    FragmentMiddle fragmentMiddle;
//    ArrayList<String> listImages = new ArrayList<String>();
//    ArrayList<String> listAlbums = new ArrayList<>();
//    MaterialToolbar topAppbar;
    String rootAppFolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1
            );
        }
        this.listImagePaths = getListImages();
        this.listAlbums = getListAlbums(this.listImagePaths);


//                }
//                return true;
//            }
//        });
//
//        File dir = new File(Environment.getExternalStorageDirectory() + "/DCIM/GalFourteenth");
//        try {
//            if (!dir.exists()) {
//                Files.createDirectory(dir.toPath());
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            rootAppFolder = dir.toPath().toString();
//        }
//    }
//
//    private void ShowDialog() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//        builder.setTitle("Tạo Album mới");
//        EditText myEditText = new EditText(MainActivity.this);
//        myEditText.setHint("Nhập tên album");
//        builder.setView(myEditText);
//        builder.setPositiveButton("Tạo", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                String album = createAblum(myEditText.getText().toString());
//                listAlbums.add(album);
////                finish();
//            }
//        });
//        builder.setNegativeButton("Thoát", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.cancel();
//            }
//        });
//        AlertDialog alertDialog = builder.create();

        ft = this.getSupportFragmentManager().beginTransaction();
        fragmentLayoutMain = FragmentLayoutMain.newInstance("menus", this.listImagePaths, this.listAlbums);

        ft.replace(R.id.layout_main, fragmentLayoutMain);
        ft.commit();
    }
//


    private ArrayList<String> getListImages() {
        ArrayList<String> listImages = new ArrayList<>();
        int column_index_data, column_index_date;
        String pathImage;
        String[] projection = {
                MediaStore.Files.FileColumns._ID,
                MediaStore.Files.FileColumns.DATA,
                MediaStore.Files.FileColumns.DATE_ADDED,
                MediaStore.Files.FileColumns.MEDIA_TYPE,
                MediaStore.Files.FileColumns.MIME_TYPE,
                MediaStore.Files.FileColumns.TITLE
        };

        String selection = MediaStore.Files.FileColumns.MEDIA_TYPE + "="
                + MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE
                + " OR "
                + MediaStore.Files.FileColumns.MEDIA_TYPE + "="
                + MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;

        Uri queryUri = MediaStore.Files.getContentUri("external");
        CursorLoader cursorLoader = new CursorLoader(getApplicationContext(),
                queryUri,
                projection,
                selection,
                null, // Selection args (none).
                MediaStore.Files.FileColumns.DATE_ADDED + " DESC" // Sort order.
        );

        Cursor cursor = cursorLoader.loadInBackground();
        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_index_date = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATE_ADDED);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        while (cursor.moveToNext()) {
            pathImage = cursor.getString(column_index_data);
            Log.e("getListImages: ", pathImage);
            listImages.add(pathImage);
        }

        return listImages;
    }

    private ArrayList<String> getListFilesOfAlbum(String dir) throws IOException {
        ArrayList<String> fileList = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(dir))) {
            for (Path path : stream) {
                if (!Files.isDirectory(path) && isImageFile(path.toString())) {
                    fileList.add(path.toString());
                }
            }
        }
        return fileList;
    }

    private boolean isImageFile(String path){

        String regex = "([^\\.]+(\\.(?i)(jpg|jpeg|png|gif|bmp|webp))$)";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(path);

        return m.matches();
    }

    public ArrayList<Album> getListAlbums(ArrayList<String> lsImagePaths) {
        ArrayList<Album> listAlbums = new ArrayList<>();
        ArrayList<String> strListAlbums = new ArrayList<>();
        for (int i = 0; i < lsImagePaths.size(); i++) {
            String path = lsImagePaths.get(i);
            String[] str = path.split("/");

            List<String> list = new ArrayList<String>(Arrays.asList(str));
            list.remove(list.size() - 1);
            String[] strArrayPath = list.toArray(new String[0]);
            String albumPath = String.join("/", strArrayPath);

            String title = str[str.length - 2];

            if (!strListAlbums.contains(title)) {
                strListAlbums.add(title);
                Album newAlbum = new Album(title, albumPath);
                try {
                    newAlbum.setListImage(getListFilesOfAlbum(albumPath));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                listAlbums.add(newAlbum);
            }
        }
        return listAlbums;
    }


}