package com.nguyenven299.vpn_easy_one_click.view;

import static com.nguyenven299.vpn_easy_one_click.helpers.Utils.getImgURL;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.nguyenven299.vpn_easy_one_click.BuildConfig;
import com.nguyenven299.vpn_easy_one_click.helpers.DialogHelper;
import com.nguyenven299.vpn_easy_one_click.R;
import com.nguyenven299.vpn_easy_one_click.model.Server;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout mDrawerLayout;
    private ImageView menuImageView, mIcon;
    private NavigationView mNavigationView;
    private View mHeader, mIncludedLayout;
    private HomeFragment fragmentLayout;
    private ArrayList<Server> serverLists;

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

            Glide.with(this).load(R.mipmap.ic_menu_foreground).into(menuImageView);
            menuImageView.setOnClickListener((v) -> {
                mDrawerLayout.openDrawer(GravityCompat.START);
            });

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, new HomeFragment()).commit();

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
        mDrawerLayout.closeDrawer(GravityCompat.START);
        switch (item.getItemId()) {
            case R.id.policy: {
                Intent intent = new Intent(this, PolicyActivity.class);
                intent.putExtra("Policy", true);
                startActivity(intent);
            }
            return false;
            case R.id.rate:
                DialogHelper.getInstance().showDialog(this, "Rate our app",
                        "If you enjoy using " + this.getString(R.string.app_name) + ", would you mind taking a moment to rate it?",
                        R.mipmap.ic_rate_app_dialog_foreground,
                        "Rate",
                        new DialogHelper.iActionDialog() {
                            @Override
                            public void actionOnClick(DialogHelper dialogHelper) {
                                ReviewManager manager = ReviewManagerFactory.create(MainActivity.this);
                                Task<ReviewInfo> request = manager.requestReviewFlow();
                                request.addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {
                                        // We can get the ReviewInfo object
                                        ReviewInfo reviewInfo = task.getResult();
                                    } else {
                                        // There was some problem, log or handle the error code.
//                                        @ReviewErrorCode int reviewErrorCode = ((Exception) task.getException());
                                    }
                                });
                            }
                        });
                return false;
            case R.id.share:
                DialogHelper.getInstance().showDialog(this, "Share our App",
                        "If you enjoy using " + this.getString(R.string.app_name) + ", would you mind taking a moment to share it?",
                        R.mipmap.ic_share_app_dialog_foreground,
                        "Share",
                        new DialogHelper.iActionDialog() {
                            @Override
                            public void actionOnClick(DialogHelper dialogHelper) {
                                try {
                                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                                    shareIntent.setType("text/plain");
                                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, MainActivity.this.getString(R.string.app_name));
                                    String shareMessage = "\nShare app to your friend\n\n";
                                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
                                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                                    startActivity(Intent.createChooser(shareIntent, "Choose option"));
                                } catch (Exception e) {
                                    //e.toString();
                                }
                            }
                        });
                return false;
            case R.id.info: {
                Intent intent = new Intent(this, PolicyActivity.class);
                intent.putExtra("Policy", false);
                startActivity(intent);
            }
            return false;
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