package com.example.nhom14_galfourteenth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    FragmentTransaction ft;
    FragmentTop fragmentTop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ft = getSupportFragmentManager().beginTransaction();
        fragmentTop = FragmentTop.newInstance("menus");
        ft.replace(R.id.main_top, fragmentTop);
        ft.commit();
    }
}