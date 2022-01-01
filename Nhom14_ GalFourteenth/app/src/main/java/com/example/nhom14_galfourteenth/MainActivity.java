package com.example.nhom14_galfourteenth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    FragmentTransaction ft;
    FragmentTop fragmentTop;
    FragmentBottom fragmentBottom;
    FragmentMiddle fragmentMiddle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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