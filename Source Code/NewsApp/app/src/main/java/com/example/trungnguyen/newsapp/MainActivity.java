package com.example.trungnguyen.newsapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.trungnguyen.newsapp.adapter.ViewPagerAdapter;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;
    TabLayout tab;
    ViewPager viewPager;
    ActionBarDrawerToggle drawerToggle;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Button btnsearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        setTitle(getResources().getString(R.string.app_name));
        setSupportActionBar(toolbar); // vì ta đang sử dụng gói AppCompatActivity tức gói thư viện hổ trợ
        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        //tab.setupWithViewPager(viewPager);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close); //drawerToggle phải đc khỏi tạo sau toolbar
        drawerLayout.addDrawerListener(drawerToggle);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_homepage, menu);
        return true;
    }
// Bắt sự kiện item on Action bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item))
            return true;
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.menuLogin:
            {
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            }
            case R.id.menuBrowser:
            {

            }
            case R.id.menuAbout:
            {

            }
        }
        return true;
    }

    private void addControls() {
        toolbar = (Toolbar) findViewById(R.id.toolbarID);
        //tab = (TabLayout) findViewById(R.id.tab);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(MainActivity.this);
        btnsearch = (Button) findViewById(R.id.ButtonSearch);
        btnsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent2);

            }
        });
    }

    private void changeViewPagerPage(final int position) {
        viewPager.postDelayed(new Runnable() {
            @Override
            public void run() {
                viewPager.setCurrentItem(position, true);
            }
        }, 100);
    }

    // Bắt sự kiện khi click vào mỗi item trong NavigationView
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Log.d("KIEMTRA", "onNavigationItemSelected");
        int id = item.getItemId();
        if(id == R.id.drawMenuTheGioi)
            changeViewPagerPage(0);
        else if (id == R.id.drawMenuTheThao)
            changeViewPagerPage(1);
        else if (id == R.id.drawMenuCongNghe)
            changeViewPagerPage(2);
        else if (id == R.id.drawMenuGiaiTri)
            changeViewPagerPage(3);
        else if (id == R.id.drawMenuThoiTrang)
            changeViewPagerPage(4);
        else if (id == R.id.drawMenuSucKhoe)
            changeViewPagerPage(5);
        else
            changeViewPagerPage(6);
        return true;
    }



        @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
        else {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("Thoát ứng dụng");
            dialog.setMessage("Bạn có muốn thoát ứng dụng?");
            dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            dialog.show();
        }
    }
}
