package com.o2_company.hyerimhyeon.o2_android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.o2_company.DB.DBConnector;

import org.json.JSONArray;

public class SearchActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ImageButton back_icon, sport_btn, life_btn, common_btn, back_btn;

    TextView life01, life02, life03, life04, life05, life06;
    TextView sport01, sport02, sport03, sport04, sport05, sport06;
    TextView mentee01, mentee02, mentee03;
    TextView mentor01, mentor02, mentor03, mentor04;

    EditText search_box;


    Spinner type2;
    Handler handler;

    String token = null;
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

        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        token = loginPreferences.getString("token", "");


        Intent intent = getIntent();
        String type = "";
        type = intent.getStringExtra("type");

        if(intent.getStringExtra("sport_type") != null){
          sport_type = intent.getStringExtra("sport_type");
        }else{
            sport_type = "";
        }

        if (type.equals("common")) {
            setContentView(R.layout.activity_search);

            sport_btn = (ImageButton) findViewById(R.id.search_common);
            search_box = (EditText) findViewById(R.id.search_edit);
            back_btn = (ImageButton) findViewById(R.id.search_back_icon);

            back_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

            sport_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    content_query = search_box.getText().toString();
                    post_type = "sport_knowledge_feed";
                    Intent intent1 = new Intent(SearchActivity.this, SearchFeedActivity.class);
                    intent1.putExtra("content_query",content_query);
                    intent1.putExtra("post_type",post_type);
                    intent1.putExtra("sport_type",sport_type);
                    startActivityForResult(intent1,10);
                }
            });

        } else if (type.equals("sport")) {
            setContentView(R.layout.activity_search_sport);
            sport_btn = (ImageButton) findViewById(R.id.search_sport_btn);
            search_box = (EditText) findViewById(R.id.search_edit);
            back_btn = (ImageButton) findViewById(R.id.search_back_icon);

            back_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

            sport_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    content_query = search_box.getText().toString();
                    post_type = "sport_expert_knowledge_feed";
                    Intent intent1 = new Intent(SearchActivity.this, SearchFeedActivity.class);
                    intent1.putExtra("content_query",content_query);
                    intent1.putExtra("member_type","expert");
                    intent1.putExtra("post_type",post_type);
                    intent1.putExtra("sport_type",sport_type);


                    startActivityForResult(intent1,10);
                }
            });


        } else if (type.equals("life")) {
            setContentView(R.layout.activity_search_life);
            sport_btn = (ImageButton) findViewById(R.id.search_life_btn);
            search_box = (EditText) findViewById(R.id.search_edit);
            back_btn = (ImageButton) findViewById(R.id.search_back_icon);

            back_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            sport_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    content_query = search_box.getText().toString();
                    post_type = "life_expert_knowledge_feed";
                    Intent intent1 = new Intent(SearchActivity.this, SearchFeedActivity.class);
                    intent1.putExtra("content_query",content_query);
                    intent1.putExtra("post_type",post_type);
                    intent1.putExtra("sport_type",sport_type);
                    startActivityForResult(intent1,10);
                }
            });

        } else {
            setContentView(R.layout.activity_search);

            Log.d("response" , "sport search : ");
            sport_btn = (ImageButton) findViewById(R.id.search_common);
            search_box = (EditText) findViewById(R.id.search_edit);
            back_btn = (ImageButton) findViewById(R.id.search_back_icon);

            back_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

            sport_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    content_query = search_box.getText().toString();
                    Intent intent1 = new Intent(SearchActivity.this, SearchFeedActivity.class);
                    intent1.putExtra("content_query",content_query);
                    intent1.putExtra("post_type","");
                    intent1.putExtra("sport_type",sport_type);
                    startActivityForResult(intent1,10);
                }
            });

        }


        back_icon = (ImageButton) findViewById(R.id.search_back_icon);

        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


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
//
//            handler = new Handler();
//
//            handler.postDelayed(new Runnable() {
//
//                @Override
//                public void run() {
//                    // TODO Auto-generated method stub
//                    try {
//
//                        newsfeedAdapterActivity.notifyDataSetChanged();
//
//                    } catch (Exception e) {
//                        // TODO: handle exception
//                    }
//                }
//            }, 100);


        }
    }

    public void settextToAdapter(final JSONArray jsonArray) {


        if(jsonArray == null){

        }else{
            Log.d("response" , "search arr : " + jsonArray.toString());
        }

//        // 아이템을 추가하는 동안 중복 요청을 방지하기 위해 락을 걸어둡니다.
//        mLockListView = true;
//
//
//        Runnable run = new Runnable()
//        {
//            @Override
//            public void run()
//            {
//
//                if(jsonArray == null){
//                    Toast.makeText(getApplicationContext(), "뉴스피드가 없습니다.",
//                            Toast.LENGTH_LONG).show();
//                }else {
//
//                    NewsfeedItem newsfeedItem;
//
//                    newsfeedAdapterActivity.clear();
//
//                    for(int i = 0 ; i<jsonArray.length(); i++){
//
//                        newsfeedItem = new NewsfeedItem();
//
//                        try {
//                            JSONObject jsonObject = jsonArray.getJSONObject(i);
//                            JSONObject userJsonObj = jsonObject.getJSONObject("user");
//
//                            newsfeedItem.name = userJsonObj.getString("name");
//                            newsfeedItem.email = userJsonObj.getString("email");
//                            newsfeedItem.profile_url = userJsonObj.getString("profile_url");
//
//                            newsfeedItem.content = jsonObject.getString("content");
//                            newsfeedItem.post_image_url = jsonObject.getString("post_image_url");
//                            newsfeedItem.youtube_link = jsonObject.getString("youtube_link");
//                            newsfeedItem.like_num = jsonObject.getString("like_num");
//                            newsfeedItem.comment_num = jsonObject.getString("comment_num");
//                            newsfeedItem.regist_date = jsonObject.getString("timestamp");
//                            newsfeedItem.is_like = jsonObject.getBoolean("is_like");
//                            newsfeedItem.post_id = jsonObject.getString("id");
//                            newsfeedItem.member_type = userJsonObj.getString("member_type");
//                            newsfeedItem.company = userJsonObj.getString("company");
//                            newsfeedItem.sport_type = userJsonObj.getString("sport_type");
//                            newsfeedItem.mentor_type = userJsonObj.getString("mentor_type");
//                            newsfeedItem.expert_type = userJsonObj.getString("expert_type");
//
//
//                            newsfeedAdapterActivity.add(newsfeedItem);
//                            newsfeedAdapterActivity.notifyDataSetChanged();
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//
//                    }
//                }
//
//
//                // 모든 데이터를 로드하여 적용하였다면 어댑터에 알리고 리스트뷰의 락을 해제합니다.
//                newsfeedAdapterActivity.notifyDataSetChanged();
//                mLockListView = false;
//            }
//        };
//
//        // 속도의 딜레이를 구현하기 위한 꼼수
//        Handler handler = new Handler();
//        handler.postDelayed(run, 500);
//

    }


    @Override
    public void onBackPressed() {
      finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.main, menu);


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



        } else if (id == R.id.nav_gallery) {

            Intent intent = new Intent(SearchActivity.this, SubjectFeedActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_slideshow) {

            Intent intent = new Intent(SearchActivity.this, ExpertFeedActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_manage) {

            Intent intent = new Intent(SearchActivity.this, NewsfeedWriteActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_share) {

            Intent intent = new Intent(SearchActivity.this, NoticeActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_send) {

            Intent intent = new Intent(SearchActivity.this, MypageActivity.class);
            startActivity(intent);

        } else if (id == R.id.people) {

            Intent intent = new Intent(SearchActivity.this, MemberActivity.class);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



}
