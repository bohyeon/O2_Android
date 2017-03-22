package com.example.hyerimhyeon.o2_android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.example.DB.DBConnector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MemberActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView actionbar_title, userSearch_btn;
    ListView expertLv, mentoLv, childLv;
    ImageButton searchBtn;
    EditText expertSearch, mentoSearch, childSearch;
    MemberAdapterActivity memberAdapterActivity;
    MenteeMemberAdapterActivity menteeMemberAdapterActivity;
    MentorMemberAdapterActivity mentorMemberAdapterActivity;
    MemberSearchAdapterActivity memberSearchAdapterActivity;
    NewsFeed newsFeed;
    Boolean buttonStateOpen;
    MemberActivity memberActivity;
    DrawerLayout drawer;
    public SharedPreferences loginPreferences;
    String token;
    Handler handler;
    String member_type, search_user_str;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.inflateHeaderView(R.layout.nav_header_main);
        ImageView logo = (ImageView) headerView.findViewById(R.id.nav_header_logo);

        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MemberActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();

            }
        });

        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        token = loginPreferences.getString("token", "");
        member_type = "expert";


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
        userSearch_btn = (TextView) findViewById(R.id.member_search_btn);
        expertSearch = (EditText) findViewById(R.id.member_expertSearch);
        mentoSearch = (EditText) findViewById(R.id.member_mentoSearch);
        childSearch = (EditText) findViewById(R.id.member_childSearch);


        this.newsFeed = NewsFeed.getNewsFeed();
        this.memberAdapterActivity = new MemberAdapterActivity(this, newsFeed, this);
        this.expertLv.setAdapter(memberAdapterActivity);

        expertSearch.getBackground().clearColorFilter();
        mentoSearch.getBackground().clearColorFilter();
        childSearch.getBackground().clearColorFilter();

        host1.getTabWidget().getChildAt(0).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                newsFeed = NewsFeed.getNewsFeed();
                memberAdapterActivity = new MemberAdapterActivity(getApplicationContext(), newsFeed, memberActivity);
                expertLv.setAdapter(memberAdapterActivity);
                member_type = "expert";
                new GetUser().execute(new DBConnector());
            }
        });

        host1.getTabWidget().getChildAt(1).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                newsFeed = NewsFeed.getNewsFeed();
                mentorMemberAdapterActivity = new MentorMemberAdapterActivity(getApplicationContext(), newsFeed, memberActivity);
                expertLv.setAdapter(mentorMemberAdapterActivity);
                member_type = "mentor";
                new GetMentorUser().execute(new DBConnector());
            }
        });

        host1.getTabWidget().getChildAt(2).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                newsFeed = NewsFeed.getNewsFeed();
                menteeMemberAdapterActivity = new MenteeMemberAdapterActivity(getApplicationContext(), newsFeed, memberActivity);
                expertLv.setAdapter(menteeMemberAdapterActivity);
                member_type = "mentee";
                new GetMenteeUser().execute(new DBConnector());
            }
        });


        userSearch_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                search_user_str = expertSearch.getText().toString().trim();
                newsFeed = NewsFeed.getNewsFeed();
                memberSearchAdapterActivity = new MemberSearchAdapterActivity(getApplicationContext(), newsFeed, memberActivity);
                expertLv.setAdapter(memberSearchAdapterActivity);
                new GetSearchUser().execute(new DBConnector());
            }
        });
    }

    @Override
    public void onStart(){
        super.onStart();

            new GetUser().execute(new DBConnector());

    }

    private class GetSearchUser extends AsyncTask<DBConnector, Long, JSONArray> {


        @Override
        protected JSONArray doInBackground(DBConnector... params) {

            //it is executed on Background thread

            return params[0].GetUser(token, search_user_str , member_type);

        }

        @Override
        protected void onPostExecute(final JSONArray jsonArray) {

            settextToAdapter_Search(jsonArray);

            handler = new Handler();

            handler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    try {

                        memberSearchAdapterActivity.notifyDataSetChanged();

                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                }
            }, 100);


        }
    }


    public void settextToAdapter_Search(JSONArray jsonArray) {
        Log.d("response" , "search_user : " + jsonArray);
        NewsfeedItem newsfeedItem;


        if(jsonArray == null){
            Toast.makeText(getApplicationContext(), "검색 결과가 없습니다.",
                    Toast.LENGTH_LONG).show();
        }else{

            memberSearchAdapterActivity.clear();

            for(int i = 0 ; i<jsonArray.length(); i++){

                newsfeedItem = new NewsfeedItem();

                try {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    newsfeedItem.name = jsonObject.getString("name");
                    newsfeedItem.profile_url = jsonObject.getString("profile_url");
                    newsfeedItem.member_type = jsonObject.getString("member_type");
                    newsfeedItem.company = jsonObject.getString("company");

                    memberSearchAdapterActivity.add(newsfeedItem);
                    memberSearchAdapterActivity.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }

    }

    private class GetUser extends AsyncTask<DBConnector, Long, JSONArray> {


        @Override
        protected JSONArray doInBackground(DBConnector... params) {

            //it is executed on Background thread

            return params[0].GetUser(token, "" , "expert");

        }

        @Override
        protected void onPostExecute(final JSONArray jsonArray) {

            settextToAdapter(jsonArray);

            handler = new Handler();

            handler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    try {

                        memberAdapterActivity.notifyDataSetChanged();

                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                }
            }, 100);


        }
    }


    public void settextToAdapter(JSONArray jsonArray) {

//          Log.d("response" , "user : " + jsonArray.toString());

        // ArrayList<NewsfeedItem> newsfeedItems = newsFeed.newsfeedItem;
        NewsfeedItem newsfeedItem;


        if(jsonArray == null){
            Toast.makeText(getApplicationContext(), "전문가회원이 없습니다.",
                    Toast.LENGTH_LONG).show();
        }else{

            memberAdapterActivity.clear();

            for(int i = 0 ; i<jsonArray.length(); i++){

                newsfeedItem = new NewsfeedItem();

                try {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    newsfeedItem.name = jsonObject.getString("name");
                    newsfeedItem.profile_url = jsonObject.getString("profile_url");
                    newsfeedItem.member_type = jsonObject.getString("member_type");
                    newsfeedItem.company = jsonObject.getString("company");

                    memberAdapterActivity.add(newsfeedItem);
                    memberAdapterActivity.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }

    }

    private class GetMentorUser extends AsyncTask<DBConnector, Long, JSONArray> {


        @Override
        protected JSONArray doInBackground(DBConnector... params) {

            //it is executed on Background thread

            return params[0].GetUser(token, "" , "mentor");

        }

        @Override
        protected void onPostExecute(final JSONArray jsonArray) {

            settextToAdapter_mentor(jsonArray);

            handler = new Handler();

            handler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    try {

                        mentorMemberAdapterActivity.notifyDataSetChanged();

                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                }
            }, 100);


        }
    }

    public void settextToAdapter_mentor(JSONArray jsonArray) {

//          Log.d("response" , "user : " + jsonArray.toString());

        // ArrayList<NewsfeedItem> newsfeedItems = newsFeed.newsfeedItem;
        NewsfeedItem newsfeedItem;


        if(jsonArray == null){
            Toast.makeText(getApplicationContext(), "멘토회원이 없습니다.",
                    Toast.LENGTH_LONG).show();
        }else{

       //     memberAdapterActivity.clear();
            mentorMemberAdapterActivity.clear();

            for(int i = 0 ; i<jsonArray.length(); i++){

                newsfeedItem = new NewsfeedItem();

                try {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    newsfeedItem.name = jsonObject.getString("name");
                    newsfeedItem.profile_url = jsonObject.getString("profile_url");
                    newsfeedItem.member_type = jsonObject.getString("member_type");
                    newsfeedItem.company = jsonObject.getString("company");

                    mentorMemberAdapterActivity.add(newsfeedItem);
                    mentorMemberAdapterActivity.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        }



    }

    private class GetMenteeUser extends AsyncTask<DBConnector, Long, JSONArray> {


        @Override
        protected JSONArray doInBackground(DBConnector... params) {

            //it is executed on Background thread

            return params[0].GetUser(token, "" , "mentee");

        }

        @Override
        protected void onPostExecute(final JSONArray jsonArray) {

            settextToAdapter_mentee(jsonArray);

            handler = new Handler();

            handler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    try {

                        menteeMemberAdapterActivity.notifyDataSetChanged();

                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                }
            }, 100);


        }
    }

    public void settextToAdapter_mentee(JSONArray jsonArray) {

//          Log.d("response" , "user : " + jsonArray.toString());

        // ArrayList<NewsfeedItem> newsfeedItems = newsFeed.newsfeedItem;
        NewsfeedItem newsfeedItem;


        if(jsonArray == null){
            Toast.makeText(getApplicationContext(), "꿈나무 회원이 없습니다.",
                    Toast.LENGTH_LONG).show();
        }else{

            menteeMemberAdapterActivity.clear();

            for(int i = 0 ; i<jsonArray.length(); i++){

                newsfeedItem = new NewsfeedItem();

                try {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    newsfeedItem.name = jsonObject.getString("name");
                    newsfeedItem.profile_url = jsonObject.getString("profile_url");
                    newsfeedItem.member_type = jsonObject.getString("member_type");


                    menteeMemberAdapterActivity.add(newsfeedItem);
                    menteeMemberAdapterActivity.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        }



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
        actionbar_title.setText("회원");

        buttonStateOpen = false;
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ImageButton menu_icon = (ImageButton) findViewById(R.id.actionbar_menu);
        menu_icon.setOnClickListener(new View.OnClickListener() {
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

        ImageButton search_icon = (ImageButton) findViewById(R.id.actionbar_search);
        search_icon.setBackgroundResource(0);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


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

            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();

        } else if (id == R.id.nav_gallery) {

            Intent intent = new Intent(this, SubjectFeedActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();

        } else if (id == R.id.nav_slideshow) {

            Intent intent = new Intent(this, ExpertFeedActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();

        } else if (id == R.id.nav_manage) {

            Intent intent = new Intent(this, InviteActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();

        } else if (id == R.id.nav_share) {

            Intent intent = new Intent(this, NoticeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_send) {

            Intent intent = new Intent(this, MypageActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        } else if (id == R.id.people) {

            Intent intent = new Intent(this, MemberActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }else if (id == R.id.nav_lifesport) {

            Intent intent = new Intent(this, LifeExpertFeedActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
