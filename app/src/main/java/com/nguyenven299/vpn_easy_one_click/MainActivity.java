package com.nguyenven299.vpn_easy_one_click;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout mDrawerLayout;
    private ImageView menuImageView, mIcon;
    private NavigationView mNavigationView;
    private View mHeader, mIncludedLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNavigationView = findViewById(R.id.nav_view);
        if (mNavigationView == null)
            return;
        else {
            menuImageView = findViewById(R.id.menuImageView);
            mHeader = mNavigationView.inflateHeaderView(R.layout.nav_header);
            mIcon = mHeader.findViewById(R.id.imageApp);
            Glide.with(this).load(R.mipmap.image_app_foreground).into(mIcon);
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            ActionBar actionbar = getSupportActionBar();
            actionbar.setTitle("");
//        actionbar.setDisplayHomeAsUpEnabled(true);
//        actionbar.setHomeAsUpIndicator(R.drawable.ic_launcher_foreground);
            Glide.with(this).load(R.mipmap.ic_menu_foreground).into(menuImageView);
            menuImageView.setOnClickListener((v) -> {
                mDrawerLayout.openDrawer(GravityCompat.START);
            });
            mDrawerLayout = findViewById(R.id.drawer_layout);
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.black));

            mNavigationView.inflateMenu(R.menu.drawer_view);
            mNavigationView.setNavigationItemSelectedListener(this);
            mNavigationView.setItemTextColor(ColorStateList.valueOf(Color.WHITE));

        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.policy:
//                showPolicyDialog();
                return true;
            case R.id.rate:
//                showRateDialog();
                return true;
            case R.id.share:
//                showShareDialog();
                return true;
            case R.id.info:
//                showInformationDialog();
                return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.drawer_view, menu);

//        for(int i = 0; i < menu.size(); i++){
//            Drawable drawable = menu.getItem(i).getIcon();
//            if(drawable != null) {
//                drawable.mutate();
//                drawable.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
//            }
//        }

        return true;
    }
}