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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.DB.DBConnector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SearchFeedActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ListView newsfeedLv;
    TextView actionbar_title;

    SearchfeedAdapterActivity searchfeedAdapterActivity;
    NewsFeed newsFeed;
    String token;
    Boolean buttonStateOpen;
    Handler handler;
    DrawerLayout drawer;
    private boolean mLockListView;
    String post_type="";
    String mentor_type = "";
    String expert_type = "";
    String content_query = "";
    String sport_type = "";
    String school_level = "";

    public SharedPreferences loginPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.inflateHeaderView(R.layout.nav_header_main);
        ImageView logo = (ImageView) headerView.findViewById(R.id.nav_header_logo);

        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SearchFeedActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();

            }
        });

        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        token = loginPreferences.getString("token", "");


        newsfeedLv = (ListView)findViewById(R.id.main_newsfeed_lv);



//        View header = getLayoutInflater().inflate(R.layout.main_header, null, false);
//        this.newsfeedLv.addHeaderView(header);

        this.newsFeed = NewsFeed.getNewsFeed();
        this.searchfeedAdapterActivity = new SearchfeedAdapterActivity(this, newsFeed, this);
        this.newsfeedLv.setAdapter(searchfeedAdapterActivity);

//        newsfeedLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
////                Intent intent = new Intent(ExpertFeedActivity.this, FeedDetailActivity.class);
////                startActivity(intent);
//            }
//        });



    }


    @Override
    public void onStart(){
        super.onStart();


        Intent intent = getIntent();
        post_type = intent.getStringExtra("post_type");
        mentor_type = intent.getStringExtra("mentor_type");
        expert_type = intent.getStringExtra("expert_type");
        content_query = intent.getStringExtra("content_query");
        school_level = intent.getStringExtra("school_level");
        sport_type = intent.getStringExtra("sport_type");

        if(post_type.equals("") && mentor_type.equals("") && expert_type.equals("") && content_query.equals("") && school_level.equals("") && sport_type.equals("종목선택")){
            Toast.makeText(getApplicationContext(), "검색결과가 없습니다.",
                    Toast.LENGTH_LONG).show();
            finish();
        }else{
            new GetSearch().execute(new DBConnector());
        }


    }


    private class GetSearch extends AsyncTask<DBConnector, Long, JSONArray> {


        @Override
        protected JSONArray doInBackground(DBConnector... params) {

            //it is executed on Background thread

            String url = "http://o-two-sport.com/api/posts/?";

            if(!sport_type.equals("종목선택")){
                if(!sport_type.equals("")){
                    url = url + "&sport_type="+sport_type;
                }

            }
            if(!mentor_type.equals("")){
                url = url + "&mentor_type="+mentor_type;
            }
            if(!school_level.equals("")){
                url = url + "&school_level="+school_level;
            }
            if(!expert_type.equals("")){
                url = url + "&expert_type="+expert_type;
            }
            if(!content_query.equals("")){
                url = url + "&content_query="+content_query;
            }


            return params[0].GetSearch(token, url);

//            return params[0].GetSearch(token, "" ,sport_type,mentor_type,school_level,expert_type,content_query);

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

                        searchfeedAdapterActivity.notifyDataSetChanged();

                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                }
            }, 100);


        }
    }

    public void settextToAdapter(final JSONArray jsonArray) {

       // Log.d("response", "search arr 3 : " + jsonArray);
        // 아이템을 추가하는 동안 중복 요청을 방지하기 위해 락을 걸어둡니다.
        mLockListView = true;

        if(jsonArray == null){
            Toast.makeText(getApplicationContext(), "검색결과가 없습니다.",
                    Toast.LENGTH_LONG).show();
            finish();
        }else {

        }

        Runnable run = new Runnable()
        {
            @Override
            public void run()
            {

                if(jsonArray == null){
                    Toast.makeText(getApplicationContext(), "검색결과가 없습니다.",
                            Toast.LENGTH_LONG).show();
                    finish();
                }else {

                    NewsfeedItem newsfeedItem;

                    searchfeedAdapterActivity.clear();

                    for(int i = 0 ; i<jsonArray.length(); i++){

                        newsfeedItem = new NewsfeedItem();

                        try {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            JSONObject userJsonObj = jsonObject.getJSONObject("user");

                            newsfeedItem.name = userJsonObj.getString("name");
                            newsfeedItem.email = userJsonObj.getString("email");
                            newsfeedItem.profile_url = userJsonObj.getString("profile_url");

                            newsfeedItem.content = jsonObject.getString("content");
                            newsfeedItem.post_image_url = jsonObject.getString("post_image_url");
                            newsfeedItem.youtube_link = jsonObject.getString("youtube_link");
                            newsfeedItem.like_num = jsonObject.getString("like_num");
                            newsfeedItem.comment_num = jsonObject.getString("comment_num");
                            newsfeedItem.regist_date = jsonObject.getString("timestamp");
                            newsfeedItem.is_like = jsonObject.getBoolean("is_like");
                            newsfeedItem.post_id = jsonObject.getString("id");
                            newsfeedItem.member_type = userJsonObj.getString("member_type");
                            newsfeedItem.company = userJsonObj.getString("company");
                            newsfeedItem.sport_type = userJsonObj.getString("sport_type");
                            newsfeedItem.mentor_type = userJsonObj.getString("mentor_type");
                            newsfeedItem.expert_type = userJsonObj.getString("expert_type");


                            searchfeedAdapterActivity.add(newsfeedItem);
                            searchfeedAdapterActivity.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }


                // 모든 데이터를 로드하여 적용하였다면 어댑터에 알리고 리스트뷰의 락을 해제합니다.
                searchfeedAdapterActivity.notifyDataSetChanged();
                mLockListView = false;
            }
        };

        // 속도의 딜레이를 구현하기 위한 꼼수
        Handler handler = new Handler();
        handler.postDelayed(run, 500);


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
        actionbar_title.setText("검색결과");

        buttonStateOpen = false;

        ImageButton menu_icon = (ImageButton) findViewById(R.id.actionbar_menu);
        menu_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
        search_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchFeedActivity.this, SearchActivity.class);
                intent.putExtra("type","sport");
                startActivityForResult(intent,1);
            }
        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

//        BottomNavigationView bottomNavigationView = (BottomNavigationView)
//                findViewById(R.id.bottom_navigation);
//
//        bottomNavigationView.setOnNavigationItemSelectedListener(
//                new BottomNavigationView.OnNavigationItemSelectedListener() {
//                    @Override
//                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                        switch (item.getItemId()) {
//                            case R.id.home_item:
//                                Intent intent3 = new Intent(SearchFeedActivity.this, MainActivity.class);
//                                startActivity(intent3);
//                                finish();
//                                return true;
//                            case R.id.noty_item:
//                                Intent intent = new Intent(SearchFeedActivity.this, MypageActivity.class);
//                                startActivity(intent);
//                                finish();
//                                return true;
//                            case R.id.write_item:
//
//                                Intent intent2 = new Intent(SearchFeedActivity.this, NewsfeedWriteActivity.class);
//                                intent2.putExtra("post_type", "sport_expert_knowledge_feed");
//
//                                startActivityForResult(intent2, 300);
//
//                                return true;
////                            case R.id.setting_item:
////                                Intent intent1 = new Intent(ExpertFeedActivity.this, MypageActivity.class);
////                                startActivity(intent1);
////                                return true;
//                        }
//                        return true;
//                    }
//                });


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

            Intent intent = new Intent(this, SearchFeedActivity.class);
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
        }else if(id == R.id.nav_header_logo){

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
