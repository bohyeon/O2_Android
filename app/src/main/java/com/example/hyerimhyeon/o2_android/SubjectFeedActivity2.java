package com.example.hyerimhyeon.o2_android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.BottomNavigationView;
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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.DB.DBConnector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SubjectFeedActivity2 extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ListView newsfeedLv;
    SubjectFeedActivity2 subjectFeedActivity2;
    SubjectfeedAdapterActivity subjectfeedAdapterActivity;
    NewsFeed newsFeed;
    String sport_type;
    TextView actionbar_title;
    String token, id, email;
    Handler handler;
    public SharedPreferences loginPreferences;
    private static final int REQUEST_EXTERNAL_STORAGE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        token = loginPreferences.getString("token", "");
        id = loginPreferences.getString("id", "");
        email = loginPreferences.getString("email","");

        Intent intent = getIntent();
        sport_type = intent.getStringExtra("sport_type");


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        newsfeedLv = (ListView)findViewById(R.id.main_newsfeed_lv);


        this.newsFeed = NewsFeed.getNewsFeed();
        this.subjectfeedAdapterActivity = new SubjectfeedAdapterActivity(this, newsFeed, this);
        this.newsfeedLv.setAdapter(subjectfeedAdapterActivity);



    }

    @Override
    public void onStart(){
        super.onStart();


//        int permissionReadStorage = ContextCompat.checkSelfPermission(subjectFeedActivity2, android.Manifest.permission.ACCESS_NETWORK_STATE);
//        //  int permissionWriteStorage = ContextCompat.checkSelfPermission(mainActivity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
//        if(permissionReadStorage == PackageManager.PERMISSION_DENIED ) {
//            ActivityCompat.requestPermissions(subjectFeedActivity2, new String[]{android.Manifest.permission.ACCESS_NETWORK_STATE}, REQUEST_EXTERNAL_STORAGE);
//        } else {

            new GetPosts().execute(new DBConnector());
//            Log.d("response", "read/write storage  permission authorized");
//        }

    }


    private class GetPosts extends AsyncTask<DBConnector, Long, JSONArray> {


        @Override
        protected JSONArray doInBackground(DBConnector... params) {

            //it is executed on Background thread

            return params[0].GetPost_sport(token, sport_type.trim());

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

                        subjectfeedAdapterActivity.notifyDataSetChanged();

                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                }
            }, 100);


        }
    }

    public void settextToAdapter(JSONArray jsonArray) {

        //  Log.d("response" , "post : " + jsonArray.toString());

        // ArrayList<NewsfeedItem> newsfeedItems = newsFeed.newsfeedItem;
        NewsfeedItem newsfeedItem;


        if(jsonArray == null){

            subjectfeedAdapterActivity.clear();
//            subjectfeedAdapterActivity.add(newsfeedItem);
//            subjectfeedAdapterActivity.notifyDataSetChanged();
            Toast.makeText(getApplicationContext(), "뉴스피드가 없습니다.",
                    Toast.LENGTH_LONG).show();
        }else{


            subjectfeedAdapterActivity.clear();
            for(int i = 0 ; i<jsonArray.length(); i++){

                newsfeedItem = new NewsfeedItem();

                try {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    JSONObject userJsonObj = jsonObject.getJSONObject("user");


                    newsfeedItem.name = userJsonObj.getString("name");
                    newsfeedItem.email = userJsonObj.getString("email");
                    newsfeedItem.profile_url = userJsonObj.getString("profile_url");
                    newsfeedItem.phone_number = userJsonObj.getString("phone_number");
                    newsfeedItem.birthday = userJsonObj.getString("birthday");
                    newsfeedItem.is_phone_number_public = userJsonObj.getString("is_phone_number_public");
                    newsfeedItem.is_birthday_public = userJsonObj.getString("is_birthday_public");
                    newsfeedItem.region = userJsonObj.getString("region");
                    newsfeedItem.school_level = userJsonObj.getString("school_level");
                    newsfeedItem.school_name = userJsonObj.getString("school_name");


                    newsfeedItem.content = jsonObject.getString("content");
                    newsfeedItem.post_image_url = jsonObject.getString("post_image_url");
                    newsfeedItem.youtube_link = jsonObject.getString("youtube_link");
                    newsfeedItem.like_num = jsonObject.getString("like_num");
                    newsfeedItem.comment_num = jsonObject.getString("comment_num");
                    newsfeedItem.regist_date = jsonObject.getString("timestamp").substring(0,10);
                    newsfeedItem.is_like = jsonObject.getBoolean("is_like");
                    newsfeedItem.post_id = jsonObject.getString("id");
                    newsfeedItem.member_type = userJsonObj.getString("member_type");
                    newsfeedItem.company = userJsonObj.getString("company");
                    newsfeedItem.sport_type = userJsonObj.getString("sport_type");
                    newsfeedItem.mentor_type = userJsonObj.getString("mentor_type");
                    newsfeedItem.expert_type = userJsonObj.getString("expert_type");
                    newsfeedItem.member_id = userJsonObj.getString("id");
                    newsfeedItem.youtube_tite = jsonObject.getString("youtube_title");

                    if(newsfeedItem.youtube_link == null || newsfeedItem.youtube_link.equals("") ){
                        newsfeedItem.youtube_id = "";
                    }else{
                        newsfeedItem.youtube_tite = jsonObject.getString("youtube_title");
                        String str = newsfeedItem.youtube_link;
                        String video_id = "";

                        if(str.toString().indexOf("youtube.com") != -1) {
                            if(str.indexOf("&") > 0){
                                video_id = str.substring(str.indexOf("=")+1 , str.indexOf("&"));
                            }else{
                                video_id = str.substring(str.indexOf("=")+1);
                            }
                        }else if(str.toString().indexOf("youtu.be") != -1 ){
                            //   Log.d("response", "youtube_str :  "+ str);
                            video_id = str.substring(17);
                        //    Log.d("response", "youtube_id :  "+ video_id);
                        }

                        newsfeedItem.youtube_id = video_id;

                    }

                    subjectfeedAdapterActivity.add(newsfeedItem);
                    subjectfeedAdapterActivity.notifyDataSetChanged();

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
        actionbar_title.setText(sport_type+" 전용피드");

        ImageButton search_icon = (ImageButton) findViewById(R.id.actionbar_search);
        search_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubjectFeedActivity2.this, SearchActivity.class);
                intent.putExtra("type","OnlySport");
                intent.putExtra("sport_type",sport_type);
                startActivityForResult(intent,1);
            }
        });

        ImageButton menu_icon = (ImageButton) findViewById(R.id.actionbar_menu);
        menu_icon.setBackgroundResource(0);

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);
        bottomNavigationView.removeAllViews();
//        bottomNavigationView.setOnNavigationItemSelectedListener(
//                new BottomNavigationView.OnNavigationItemSelectedListener() {
//                    @Override
//                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                        switch (item.getItemId()) {
//                            case R.id.home_item:
//                                Intent intent3 = new Intent(SubjectFeedActivity2.this, MainActivity.class);
//                                startActivity(intent3);
//                                finish();
//                                return true;
//                            case R.id.noty_item:
//                                Intent intent = new Intent(SubjectFeedActivity2.this, MypageActivity.class);
//                                startActivity(intent);
//                                finish();
//                                return true;
//                            case R.id.write_item:
//
//                                Intent intent2 = new Intent(SubjectFeedActivity2.this, NewsfeedWriteActivity.class);
//                                intent2.putExtra("post_type", "sport_knowledge_feed");
//                                intent2.putExtra("sport_type", sport_type);
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

            Intent intent = new Intent(SubjectFeedActivity2.this, MainActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_gallery) {

            Intent intent = new Intent(SubjectFeedActivity2.this, SubjectFeedActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_slideshow) {

            Intent intent = new Intent(SubjectFeedActivity2.this, ExpertFeedActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_manage) {

            Intent intent = new Intent(SubjectFeedActivity2.this, InviteActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_share) {

            Intent intent = new Intent(SubjectFeedActivity2.this, NoticeActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_send) {

            Intent intent = new Intent(SubjectFeedActivity2.this, MypageActivity.class);
            startActivity(intent);

        } else if (id == R.id.people) {

            Intent intent = new Intent(SubjectFeedActivity2.this, MemberActivity.class);
            startActivity(intent);

        }else if (id == R.id.nav_lifesport) {

            Intent intent = new Intent(SubjectFeedActivity2.this, LifeExpertFeedActivity.class);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
