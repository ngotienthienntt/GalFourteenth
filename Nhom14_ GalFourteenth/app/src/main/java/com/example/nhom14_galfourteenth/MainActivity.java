package com.example.nhom14_galfourteenth;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.loader.content.CursorLoader;

import com.example.nhom14_galfourteenth.fragment.FragmentAlbum;
import com.example.nhom14_galfourteenth.fragment.FragmentDetail;
import com.example.nhom14_galfourteenth.fragment.FragmentLayoutMain;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements MainCallbacks {

    FragmentTransaction ft;
    FragmentLayoutMain fragmentLayoutMain;
    public ArrayList<String> listImagePaths;
    public ArrayList<Album> listAlbums;
    FragmentDetail fragmentDetail;
    ImageEdit fragmentImageEdit;
    FragmentAlbum fragmentAlbum;
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
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1
            );
        }
        loadData();


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
//        builder.setTitle("T???o Album m???i");
//        EditText myEditText = new EditText(MainActivity.this);
//        myEditText.setHint("Nh???p t??n album");
//        builder.setView(myEditText);
//        builder.setPositiveButton("T???o", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                String album = createAblum(myEditText.getText().toString());
//                listAlbums.add(album);
////                finish();
//            }
//        });
//        builder.setNegativeButton("Tho??t", new DialogInterface.OnClickListener() {
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

    public void  loadData(){
        this.listImagePaths = getListImages();
        try {
            this.listAlbums = getListAlbums(this.listImagePaths);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getListImages() {
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
                + MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE + " OR "
                + MediaStore.Files.FileColumns.MEDIA_TYPE + "="
                + MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;

        Uri queryUri = MediaStore.Files.getContentUri("external");
        CursorLoader cursorLoader = new CursorLoader(getApplicationContext(),
                queryUri, projection, selection, null,
                MediaStore.Files.FileColumns.DATE_ADDED + " DESC" // Sort order.
        );

        Cursor cursor = cursorLoader.loadInBackground();
        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_index_date = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATE_ADDED);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        while (cursor.moveToNext()) {
            pathImage = cursor.getString(column_index_data);
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

    private boolean isImageFile(String path) {

        String regex = "([^\\.]+(\\.(?i)(jpg|jpeg|png|gif|bmp|webp))$)";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(path);

        return m.matches();
    }

//    public ArrayList<Album> getListAlbums(ArrayList<String> lsImagePaths) {
//        ArrayList<Album> listAlbums = new ArrayList<>();
//        ArrayList<String> strListAlbums = new ArrayList<>();
//        for (int i = 0; i < lsImagePaths.size(); i++) {
//            String path = lsImagePaths.get(i);
//            String[] str = path.split("/");
//
//            List<String> list = new ArrayList<String>(Arrays.asList(str));
//            list.remove(list.size() - 1);
//            String[] strArrayPath = list.toArray(new String[0]);
//            String albumPath = String.join("/", strArrayPath);
//
//            String title = str[str.length - 2];
//
//            if (!strListAlbums.contains(title)) {
//                strListAlbums.add(title);
//                Album newAlbum = new Album(title, albumPath);
//                try {
//                    newAlbum.setListImage(getListFilesOfAlbum(albumPath));
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                listAlbums.add(newAlbum);
//            }
//        }
//        return listAlbums;
//    }

    public ArrayList<Album> getListAlbums(ArrayList<String> lstImg) throws IOException {
        String dirDCIM = Environment.getExternalStorageDirectory() + "/DCIM/";
        ArrayList<Album> lst = new ArrayList<Album>();

        for (String item : lstImg) {
            String path = item.substring(0, item.lastIndexOf("/"));
            if (!containsPath(lst, path)) {
                Album alb = new Album();
                alb.setName(path.substring(path.lastIndexOf("/") + 1));
                alb.setPath(path);
                alb.setListImage(getListFilesOfAlbum(path));
                lst.add(alb);
            }
        }

        File[] albums = new File(dirDCIM).listFiles(File::isDirectory);
        for (File file : albums) {
            String path = file.getPath().toString();
            if (!containsPath(lst, path)) {
                Album alb = new Album();
                alb.setName(path.substring(path.lastIndexOf("/") + 1));
                alb.setPath(path);
                alb.setListImage(getListFilesOfAlbum(path));
                lst.add(alb);
            }
        }

        return lst;
    }

    private boolean containsPath(final List<Album> list, final String path) {
        return list.stream().filter(o -> o.getPath().equals(path)).findFirst().isPresent();
    }

    public ArrayList<String> getLstImg() {
        return this.listImagePaths;
    }

    @Override
    public void onMsgFromFragToMain(String sender, String strValue) {
        if(sender == "AddImg-Flag"){
            this.loadData();
            fragmentLayoutMain.onImageFromMainToFragment(listImagePaths, listAlbums);
        }
        else if (sender == "ChangeToDetail"){
            String[] listImageParse = strValue.split(";");
            int positonFocus = Integer.parseInt(listImageParse[listImageParse.length - 1]);
            ArrayList<String> listImageSender = new ArrayList<String>();
            for (int i = 0; i < listImageParse.length - 1; i++){
                listImageSender.add(listImageParse[i]);
            }


            fragmentDetail = FragmentDetail.newInstance("Detail", positonFocus, listImageSender);
            replaceFragment(fragmentDetail);
        }
//        else if (sender == "ChangeToAlbum"){
//            fragmentAlbum = FragmentAlbum.newInstance("Detail", Integer.parseInt(strValue), listImagePaths);
//            replaceFragment(fragmentDetail);
//        }
//        else if (sender == "ChangeToEdit") {
//            fragmentImageEdit = ImageEdit.newInstance("Edit", strValue);
//            replaceFragment(fragmentImageEdit);
//        }
    }


    @SuppressLint("ResourceType")
    public void replaceFragment(Fragment someFragment) {
        FragmentTransaction transaction = this.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.layout_main, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK) {
//            if (requestCode == 1) {
//                if (data.getClipData() != null) {
//                    ClipData mClipData = data.getClipData();
//                    for (int i = 0; i < mClipData.getItemCount(); i++) {
//                        ClipData.Item item = mClipData.getItemAt(i);
//                        Uri uri = item.getUri();
//                        Log.e("LOG",uri.getPath());
//                        // display your images
////                        imageView.setImageURI(uri);
//                    }
//                } else if (data.getData() != null) {
//                    Uri uri = data.getData();
//                    Log.e("LOG",uri.getPath());
//                    // display your image
////                    imageView.setImageURI(uri);
//                }
//            }
//        }
//    }
}