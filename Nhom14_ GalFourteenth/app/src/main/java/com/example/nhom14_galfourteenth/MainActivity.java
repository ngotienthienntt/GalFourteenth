package com.example.nhom14_galfourteenth;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    FragmentTransaction ft;
    FragmentTop fragmentTop;
    FragmentBottom fragmentBottom;
    FragmentMiddle fragmentMiddle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(
                    new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 1
            );
        }

        getSupportActionBar().hide();

        ft = getSupportFragmentManager().beginTransaction();
        fragmentTop = FragmentTop.newInstance("menus");
        fragmentBottom = FragmentBottom.newInstance("menus");
        fragmentMiddle = FragmentMiddle.newInstance("images");
        ft.replace(R.id.main_top, fragmentTop);
        ft.replace(R.id.main_middle, fragmentMiddle);
        ft.replace(R.id.main_bottom, fragmentBottom);
        ft.commit();
    }
}