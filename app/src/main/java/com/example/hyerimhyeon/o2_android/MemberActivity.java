package com.example.hyerimhyeon.o2_android;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

public class MemberActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView actionbar_title;
    ListView expertLv, mentoLv, childLv;
    ImageButton searchBtn;
    EditText expertSearch, mentoSearch, childSearch;
    MemberAdapterActivity memberAdapterActivity;
    NewsFeed newsFeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
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



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.setDrawerListener(toggle);
//        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        TabHost host1 = (TabHost) findViewById(R.id.member_tabHost);
        host1.setup();

        TabHost.TabSpec spec = host1.newTabSpec("Tab One");
        spec.setContent(R.id.member_tab1);
        spec.setIndicator("전문가");
        host1.addTab(spec);

        spec = host1.newTabSpec("Tab Two");
        spec.setContent(R.id.member_tab2);
        spec.setIndicator("멘토");
        host1.addTab(spec);

        spec = host1.newTabSpec("Tab Three");
        spec.setContent(R.id.member_tab3);
        spec.setIndicator("꿈나무");
        host1.addTab(spec);

        expertLv = (ListView)findViewById(R.id.member_expertLv);
        mentoLv = (ListView)findViewById(R.id.member_mentoLv);
        childLv = (ListView)findViewById(R.id.member_childLv);

        this.newsFeed = NewsFeed.getNewsFeed();
        this.memberAdapterActivity = new MemberAdapterActivity(this, newsFeed, this);
        this.expertLv.setAdapter(memberAdapterActivity);

        this.newsFeed = NewsFeed.getNewsFeed();
        this.memberAdapterActivity = new MemberAdapterActivity(this, newsFeed, this);
        this.mentoLv.setAdapter(memberAdapterActivity);

        this.newsFeed = NewsFeed.getNewsFeed();
        this.memberAdapterActivity = new MemberAdapterActivity(this, newsFeed, this);
        this.childLv.setAdapter(memberAdapterActivity);


        expertSearch = (EditText) findViewById(R.id.member_expertSearch);
        mentoSearch = (EditText) findViewById(R.id.member_mentoSearch);
        childSearch = (EditText) findViewById(R.id.member_childSearch);


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
        ActionBar actionBar = getSupportActionBar();

        // Custom Actionbar를 사용하기 위해 CustomEnabled을 true 시키고 필요 없는 것은 false 시킨다
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);            //액션바 아이콘을 업 네비게이션 형태로 표시합니다.
        actionBar.setDisplayShowTitleEnabled(false);        //액션바에 표시되는 제목의 표시유무를 설정합니다.
        actionBar.setDisplayShowHomeEnabled(false);            //홈 아이콘을 숨김처리합니다.


        //layout을 가지고 와서 actionbar에 포팅을 시킵니다.
        LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
        View actionbar = inflater.inflate(R.layout.custom_action_bar, null);

        actionBar.setCustomView(actionbar);

        //액션바 양쪽 공백 없애기
        Toolbar parent = (Toolbar)actionbar.getParent();
        parent.setPadding(0,0,0,0);
        parent.setContentInsetsAbsolute(0,0);

        actionbar_title = (TextView) findViewById(R.id.actionbar_title);
        searchBtn = (ImageButton) findViewById(R.id.actionbar_search);

        actionbar_title.setText("멤버");
        searchBtn.setImageDrawable(null);


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

            Intent intent = new Intent(MemberActivity.this, MainActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_gallery) {

            Intent intent = new Intent(MemberActivity.this, SubjectFeedActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_slideshow) {

            Intent intent = new Intent(MemberActivity.this, ExpertFeedActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_manage) {

            Intent intent = new Intent(MemberActivity.this, InviteActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_share) {

            Intent intent = new Intent(MemberActivity.this, NoticeActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_send) {

            Intent intent = new Intent(MemberActivity.this, MypageActivity.class);
            startActivity(intent);

        } else if (id == R.id.people) {

            Intent intent = new Intent(MemberActivity.this, MemberActivity.class);
            startActivity(intent);

        }else if (id == R.id.nav_lifesport) {

            Intent intent = new Intent(MemberActivity.this, LifeExpertFeedActivity.class);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
