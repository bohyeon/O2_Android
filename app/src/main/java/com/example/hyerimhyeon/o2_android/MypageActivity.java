package com.example.hyerimhyeon.o2_android;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TabHost;

public class MypageActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ListView mypage_mypost, comment_lv, alarm_lv;
    FrameLayout container;
    ImageButton settingBtn, backBtn;
    Boolean buttonStateOpen;

    MypageMypostAdapterActivity mypageMypostAdapterActivity;
    MypageAlarmAdapterActivity mypageAlarmAdapterActivity;
    NewsFeed newsFeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

//        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//        getSupportActionBar().setCustomView(R.layout.actionbar);


//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.setDrawerListener(toggle);
//        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        settingBtn = (ImageButton) findViewById(R.id.mypage_settingBtn);
        backBtn = (ImageButton) findViewById(R.id.mypage_backBtn);

        settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MypageActivity.this, MypageSettingActivity.class);
                startActivity(intent);
            }
        });

        buttonStateOpen = false;

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (buttonStateOpen == false) {
                    drawer.openDrawer(Gravity.LEFT);
                    buttonStateOpen = true;
                } else if (buttonStateOpen == true) {
                    drawer.closeDrawer(Gravity.LEFT);
                    buttonStateOpen = false;
                }

            }
        });

        TabHost host1 = (TabHost) findViewById(R.id.mypage_tabHost);
        host1.setup();

        TabHost.TabSpec spec = host1.newTabSpec("Tab One");
        spec.setContent(R.id.mypage_tab1);
        spec.setIndicator("내가 쓴 글");
        host1.addTab(spec);

        spec = host1.newTabSpec("Tab Two");
        spec.setContent(R.id.mypage_tab2);
        spec.setIndicator("알림");
        host1.addTab(spec);

        spec = host1.newTabSpec("Tab Three");
        spec.setContent(R.id.mypage_tab3);
        spec.setIndicator("내가 쓴 댓글");
        host1.addTab(spec);


        mypage_mypost = (ListView)findViewById(R.id.mypage_mypost);
        alarm_lv = (ListView)findViewById(R.id.mypage_alarm);
        comment_lv = (ListView)findViewById(R.id.mypage_comment);


        this.newsFeed = NewsFeed.getNewsFeed();
        this.mypageMypostAdapterActivity = new MypageMypostAdapterActivity(this, newsFeed, this);
        this.mypage_mypost.setAdapter(mypageMypostAdapterActivity);

        this.newsFeed = NewsFeed.getNewsFeed();
        this.mypageAlarmAdapterActivity = new MypageAlarmAdapterActivity(this, newsFeed, this);
        this.alarm_lv.setAdapter(mypageAlarmAdapterActivity);

        this.newsFeed = NewsFeed.getNewsFeed();
        this.mypageMypostAdapterActivity = new MypageMypostAdapterActivity(this, newsFeed, this);
        this.comment_lv.setAdapter(mypageMypostAdapterActivity);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

            Intent intent = new Intent(MypageActivity.this, MainActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_gallery) {

            Intent intent = new Intent(MypageActivity.this, SubjectFeedActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_slideshow) {

            Intent intent = new Intent(MypageActivity.this, ExpertFeedActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_manage) {

            Intent intent = new Intent(MypageActivity.this, InviteActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_share) {

            Intent intent = new Intent(MypageActivity.this, NoticeActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_send) {

            Intent intent = new Intent(MypageActivity.this, MypageActivity.class);
            startActivity(intent);

        } else if (id == R.id.people) {

            Intent intent = new Intent(MypageActivity.this, MemberActivity.class);
            startActivity(intent);

        }else if (id == R.id.nav_lifesport) {

            Intent intent = new Intent(MypageActivity.this, LifeExpertFeedActivity.class);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
