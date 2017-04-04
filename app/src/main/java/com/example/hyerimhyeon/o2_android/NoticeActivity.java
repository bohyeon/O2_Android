package com.example.hyerimhyeon.o2_android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
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
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.DB.DBConnector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NoticeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ListView newsfeedLv;
    TextView actionbar_title;
    NoticeActivity noticeActivity;
    NoticeAdapterActivity noticeAdapterActivity;
    NewsFeed newsFeed;
    String token, id, is_admin;
    Handler handler;
    DrawerLayout drawer;
    Boolean buttonStateOpen;
    public SharedPreferences loginPreferences;
    private static final int REQUEST_EXTERNAL_STORAGE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.inflateHeaderView(R.layout.nav_header_main);
        ImageView logo = (ImageView) headerView.findViewById(R.id.nav_header_logo);

        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(NoticeActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();

            }
        });


        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        token = loginPreferences.getString("token", "");
        id = loginPreferences.getString("id", "");
        is_admin = loginPreferences.getString("is_admin","");


        newsfeedLv = (ListView)findViewById(R.id.main_newsfeed_lv);

        this.newsFeed = NewsFeed.getNewsFeed();
        this.noticeAdapterActivity = new NoticeAdapterActivity(this, newsFeed, this);
        this.newsfeedLv.setAdapter(noticeAdapterActivity);

        newsfeedLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(NoticeActivity.this, FeedDetailActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onStart(){
        super.onStart();


//        int permissionReadStorage = ContextCompat.checkSelfPermission(noticeActivity, android.Manifest.permission.READ_EXTERNAL_STORAGE);
//        int permissionWriteStorage = ContextCompat.checkSelfPermission(noticeActivity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
//        if(permissionReadStorage == PackageManager.PERMISSION_DENIED || permissionWriteStorage == PackageManager.PERMISSION_DENIED) {
//            ActivityCompat.requestPermissions(noticeActivity, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_EXTERNAL_STORAGE);
//        } else {

            new GetPosts().execute(new DBConnector());
            Log.d("response", "read/write storage  permission authorized");
    //    }

    }

    private class GetPosts extends AsyncTask<DBConnector, Long, JSONArray> {


        @Override
        protected JSONArray doInBackground(DBConnector... params) {

            //it is executed on Background thread

            return params[0].GetPost(token, "notice","");

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

                        noticeAdapterActivity.notifyDataSetChanged();

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
            noticeAdapterActivity.clear();
            noticeAdapterActivity.notifyDataSetChanged();
            Toast.makeText(getApplicationContext(), "공지사항이 없습니다.",
                    Toast.LENGTH_LONG).show();
        }else{

            noticeAdapterActivity.clear();

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
                            Log.d("response", "youtube_id :  "+ video_id);
                        }

                        newsfeedItem.youtube_id = video_id;

                    }
                    noticeAdapterActivity.add(newsfeedItem);
                    noticeAdapterActivity.notifyDataSetChanged();

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

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

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
        actionbar_title.setText("공지사항");

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

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.home_item:
                                //home is here
                                return true;
                            case R.id.noty_item:
                                Intent intent = new Intent(NoticeActivity.this, MypageActivity.class);
                                startActivity(intent);
                                return true;
                            case R.id.write_item:

                                if(is_admin.equals("true")){
                                    Intent intent2 = new Intent(NoticeActivity.this, NewsfeedWriteActivity.class);
                                    intent2.putExtra("post_type", "notice");

                                    startActivityForResult(intent2, 300);
                                }else{
                                    Toast.makeText(getApplicationContext(), "관리자 전용게시판 입니다.",
                                            Toast.LENGTH_LONG).show();
                                }


                                return true;
//                            case R.id.setting_item:
//                                Intent intent1 = new Intent(ExpertFeedActivity.this, MypageActivity.class);
//                                startActivity(intent1);
//                                return true;
                        }
                        return true;
                    }
                });


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
