package com.example.nhom14_galfourteenth;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.divyanshu.colorseekbar.ColorSeekBar;
import com.example.nhom14_galfourteenth.adapter.EmojisAdapter;
import com.example.nhom14_galfourteenth.fragment.TextEditorDialogFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import ja.burhanrashid52.photoeditor.PhotoEditor;
import ja.burhanrashid52.photoeditor.PhotoEditorView;
import ja.burhanrashid52.photoeditor.PhotoFilter;
import ja.burhanrashid52.photoeditor.TextStyleBuilder;

public class ImageEdit extends AppCompatActivity {
    private PhotoEditorView mPhotoEditorView;
    private PhotoEditor mPhotoEditor;
    BottomNavigationView navBottomFilterImage;
    SeekBar seekBarBrushSize;
    ColorSeekBar colorSeekBarPicker;
    HorizontalScrollView horizontalScrollViewFilter;
    Dialog emoji_dialog;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_image_edit);
        String pathFilter = getIntent().getStringExtra("pathFilter");
        Uri uriFilter = Uri.parse(pathFilter);
        mPhotoEditorView = findViewById(R.id.photoEditorView);
        mPhotoEditorView.getSource().setImageURI(uriFilter);
        Typeface mEmojiTypeFace = Typeface.createFromAsset(getAssets(), "emojione-android.ttf");
        mPhotoEditor = new PhotoEditor.Builder(this, mPhotoEditorView).setPinchTextScalable(true)
                .setDefaultEmojiTypeface(mEmojiTypeFace).build();
        seekBarBrushSize = findViewById(R.id.seekbar_brush_size);
        colorSeekBarPicker = findViewById(R.id.color_picker_seekbar);
        horizontalScrollViewFilter = findViewById(R.id.horizontal_filter);

        navBottomFilterImage = findViewById(R.id.nav_bottom_filter_image);
        navBottomFilterImage.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_brush:
                        horizontalScrollViewFilter.setVisibility(View.INVISIBLE);
                        mPhotoEditor.setBrushDrawingMode(true);
                        seekBarBrushSize.setVisibility(View.VISIBLE);
                        colorSeekBarPicker.setVisibility(View.VISIBLE);
                        seekBarBrushSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                            @Override
                            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                                mPhotoEditor.setBrushSize(progress);
                            }

                            @Override
                            public void onStartTrackingTouch(SeekBar seekBar) {

                            }

                            @Override
                            public void onStopTrackingTouch(SeekBar seekBar) {

                            }
                        });
                        colorSeekBarPicker.setOnColorChangeListener(new ColorSeekBar.OnColorChangeListener() {
                            @Override
                            public void onColorChangeListener(int color) {
                                mPhotoEditor.setBrushColor(color);
                            }
                        });
                        return true;
                    case R.id.nav_text_fields:
                        seekBarBrushSize.setVisibility(View.INVISIBLE);
                        colorSeekBarPicker.setVisibility(View.INVISIBLE);
                        horizontalScrollViewFilter.setVisibility(View.INVISIBLE);
                        /*
                        TextStyleBuilder b = new TextStyleBuilder();
                        b.withTextColor(Color.RED);
                        mPhotoEditor.addText("inputText", b);
                        */
                        TextEditorDialogFragment textEditorDialogFragment = TextEditorDialogFragment.show(ImageEdit.this);
                        textEditorDialogFragment.setOnTextEditorListener(new TextEditorDialogFragment.TextEditor() {
                            @Override
                            public void onDone(String inputText, int colorCode) {
                                final TextStyleBuilder styleBuilder = new TextStyleBuilder();
                                styleBuilder.withTextColor(colorCode);

                                mPhotoEditor.addText(inputText, styleBuilder);
                            }
                        });
                        //Toast.makeText(FilterActivity.this, "Add Text field", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.nav_filter:
                        seekBarBrushSize.setVisibility(View.INVISIBLE);
                        colorSeekBarPicker.setVisibility(View.INVISIBLE);
                        horizontalScrollViewFilter.setVisibility(View.VISIBLE);
                        findViewById(R.id.filter_brightness).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mPhotoEditor.setFilterEffect(PhotoFilter.BRIGHTNESS);
                            }
                        });
                        findViewById(R.id.filter_blackwhite).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mPhotoEditor.setFilterEffect(PhotoFilter.BLACK_WHITE);
                            }
                        });
                        findViewById(R.id.filter_contrast).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mPhotoEditor.setFilterEffect(PhotoFilter.CONTRAST);
                            }
                        });
                        findViewById(R.id.filter_filllight).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mPhotoEditor.setFilterEffect(PhotoFilter.FILL_LIGHT);
                            }
                            });
                        findViewById(R.id.filter_fisheye).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mPhotoEditor.setFilterEffect(PhotoFilter.FISH_EYE);
                            }
                        });
                        return true;
                    case R.id.nav_eraser:
                        seekBarBrushSize.setVisibility(View.INVISIBLE);
                        colorSeekBarPicker.setVisibility(View.INVISIBLE);
                        horizontalScrollViewFilter.setVisibility(View.INVISIBLE);
                        mPhotoEditor.brushEraser();
                        return true;
                    case R.id.nav_emoji:
                        seekBarBrushSize.setVisibility(View.INVISIBLE);
                        colorSeekBarPicker.setVisibility(View.INVISIBLE);
                        horizontalScrollViewFilter.setVisibility(View.INVISIBLE);
                        show_emoji_picker(ImageEdit.this);
                        return true;
                    default:
                        return false;
                }
            }
        });
        findViewById(R.id.cancel_edit_image_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.save_edit_image_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = new File(getBaseContext().getFilesDir()
                        + File.separator + ""
                        + System.currentTimeMillis() + ".png"); //not use .jpg if android >= 10.0
                try{
                    try{
                        file.createNewFile();
                    }
                    catch(Exception e){
                        e.printStackTrace();
                        Toast.makeText(ImageEdit.this, "Create new file fail!!!", Toast.LENGTH_SHORT).show();
                    }
                    mPhotoEditor.saveAsFile(file.getAbsolutePath(), new PhotoEditor.OnSaveListener() {
                        @Override
                        public void onSuccess(@NonNull String imagePath) {
                            try {
                                MediaStore.Images.Media.insertImage(getContentResolver(), imagePath, "", "");
                                startActivity(new Intent(ImageEdit.this, MainActivity.class));
                                Toast.makeText(ImageEdit.this, "Edit image successfully!!!", Toast.LENGTH_SHORT).show();
                            }
                            catch (FileNotFoundException e) {
                                e.printStackTrace();
                                Toast.makeText(ImageEdit.this, "Insert image fail!!!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            exception.printStackTrace();
                            Toast.makeText(ImageEdit.this, "Edit image fail!!!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                catch(SecurityException e){
                    e.printStackTrace();
                    Toast.makeText(ImageEdit.this, "Permission fail", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void show_emoji_picker(Activity activity) {
        ArrayList<String> emoji_list = PhotoEditor.getEmojis(ImageEdit.this);
        //Toast.makeText(FilterActivity.this, emoji_list.get(0), Toast.LENGTH_SHORT).show();

        emoji_dialog = new Dialog(activity);
        emoji_dialog.setCancelable(true);
        emoji_dialog.setContentView(R.layout.emojis_picker);
        TextView tv = (TextView) emoji_dialog.findViewById(R.id.emoji_header);
        tv.setText(R.string.emoji_select);
        Button btndialog = (Button) emoji_dialog.findViewById(R.id.cancel_dialog);
        btndialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emoji_dialog.dismiss();
            }
        });
        GridView gridView = (GridView) emoji_dialog.findViewById(R.id.grid);
        gridView.setAdapter(new EmojisAdapter(this, emoji_list));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPhotoEditor.addEmoji(emoji_list.get(position));
                emoji_dialog.dismiss();
            }
        });
        emoji_dialog.show();
    }
}
