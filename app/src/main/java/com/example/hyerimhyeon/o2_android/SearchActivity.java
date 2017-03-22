package com.example.hyerimhyeon.o2_android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.DB.DBConnector;

import org.json.JSONArray;

public class SearchActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ImageButton back_icon, sport_btn, life_btn, common_btn;

    TextView life01, life02, life03, life04, life05, life06;
    TextView sport01, sport02, sport03, sport04, sport05, sport06;
    TextView mentee01, mentee02, mentee03;
    TextView mentor01, mentor02, mentor03, mentor04;

    EditText search_box;

    Boolean life_b01 = false;
    Boolean life_b02 = false;
    Boolean life_b03 = false;
    Boolean life_b04 = false;
    Boolean life_b05 = false;
    Boolean life_b06 = false;

    Boolean sport_b01 = false;
    Boolean sport_b02 = false;
    Boolean sport_b03 = false;
    Boolean sport_b04 = false;
    Boolean sport_b05 = false;
    Boolean sport_b06 = false;

    Boolean mentee_01 = false;
    Boolean mentee_02 = false;
    Boolean mentee_03 = false;

    Boolean mentor_01 = false;
    Boolean mentor_02 = false;
    Boolean mentor_03 = false;
    Boolean mentor_04 = false;


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



        if (type.equals("common")) {
            setContentView(R.layout.activity_search);
            String[] str = getResources().getStringArray(R.array.mSpinnerArr);
            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, str);
            type2 = (Spinner) findViewById(R.id.search_type);
            type2.setAdapter(adapter);

            sport_btn = (ImageButton) findViewById(R.id.search_common);
            search_box = (EditText) findViewById(R.id.search_edit);

            sport_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    content_query = search_box.getText().toString();
                    sport_type = type2.getSelectedItem().toString();

                    Intent intent1 = new Intent(SearchActivity.this, SearchFeedActivity.class);
                    intent1.putExtra("post_type",post_type);
                    intent1.putExtra("mentor_type",mentor_type);
                    intent1.putExtra("expert_type",expert_type);
                    intent1.putExtra("content_query",content_query);
                    intent1.putExtra("sport_type",sport_type);
                    intent1.putExtra("school_level",school_level);

                    startActivityForResult(intent1,10);
                }
            });

        } else if (type.equals("sport")) {
            setContentView(R.layout.activity_search_sport);
            sport_btn = (ImageButton) findViewById(R.id.search_sport_btn);
            search_box = (EditText) findViewById(R.id.search_edit);

            sport_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    content_query = search_box.getText().toString();
                    sport_type = "";

                    Intent intent1 = new Intent(SearchActivity.this, SearchFeedActivity.class);
                    intent1.putExtra("post_type",post_type);
                    intent1.putExtra("mentor_type",mentor_type);
                    intent1.putExtra("expert_type",expert_type);
                    intent1.putExtra("content_query",content_query);
                    intent1.putExtra("sport_type",sport_type);
                    intent1.putExtra("school_level",school_level);

                    startActivityForResult(intent1,10);
                }
            });


        } else if (type.equals("life")) {
            setContentView(R.layout.activity_search_life);
            sport_btn = (ImageButton) findViewById(R.id.search_life_btn);
            search_box = (EditText) findViewById(R.id.search_edit);

            sport_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    content_query = search_box.getText().toString();
                    sport_type = "";

                    Intent intent1 = new Intent(SearchActivity.this, SearchFeedActivity.class);
                    intent1.putExtra("post_type",post_type);
                    intent1.putExtra("mentor_type",mentor_type);
                    intent1.putExtra("expert_type",expert_type);
                    intent1.putExtra("content_query",content_query);
                    intent1.putExtra("sport_type",sport_type);
                    intent1.putExtra("school_level",school_level);

                    startActivityForResult(intent1,10);
                }
            });

        } else {
            setContentView(R.layout.activity_search);
        }


        back_icon = (ImageButton) findViewById(R.id.search_back_icon);

        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mentee01 = (TextView) findViewById(R.id.main_box_01);
        mentee02 = (TextView) findViewById(R.id.main_box_02);
        mentee03 = (TextView) findViewById(R.id.main_box_03);

        mentor01 = (TextView) findViewById(R.id.main_box_04);
        mentor02 = (TextView) findViewById(R.id.main_box_05);
        mentor03 = (TextView) findViewById(R.id.main_box_06);
        mentor04 = (TextView) findViewById(R.id.main_box_07);


        life01 = (TextView) findViewById(R.id.life_box_01);
        life02 = (TextView) findViewById(R.id.life_box_02);
        life03 = (TextView) findViewById(R.id.life_box_03);
        life04 = (TextView) findViewById(R.id.life_box_04);
        life05 = (TextView) findViewById(R.id.life_box_05);
        life06 = (TextView) findViewById(R.id.life_box_06);

        sport01 = (TextView) findViewById(R.id.sport_box_01);
        sport02 = (TextView) findViewById(R.id.sport_box_02);
        sport03 = (TextView) findViewById(R.id.sport_box_03);
        sport04 = (TextView) findViewById(R.id.sport_box_04);
        sport05 = (TextView) findViewById(R.id.sport_box_05);
        sport06 = (TextView) findViewById(R.id.sport_box_06);

        if(type.equals("common")){

            mentee01.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(mentee_01==false){
                        mentee01.setBackground(getResources().getDrawable(R.drawable.filter_border));
                        mentee01.setTextColor(getResources().getColor(R.color.mainBlue));
                        mentee02.setTextColor(getResources().getColor(R.color.black));
                        mentee03.setTextColor(getResources().getColor(R.color.black));
                        mentee_01 = true;
                        school_level = "초등학교";
                    }else{
                        mentee01.setBackgroundColor(Color.rgb(255,255,255));
                        mentee01.setTextColor(getResources().getColor(R.color.black));
                        mentee_01 = false;
                        school_level = "";
                    }
                }
            });

            mentee02.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(mentee_02==false){
                        mentee02.setBackground(getResources().getDrawable(R.drawable.filter_border));
                        mentee02.setTextColor(getResources().getColor(R.color.mainBlue));
                        mentee01.setBackgroundColor(Color.rgb(255,255,255));
                        mentee03.setBackgroundColor(Color.rgb(255,255,255));
                        mentee_02 = true;
                        school_level = "중학교";
                    }else{
                        mentee02.setBackgroundColor(Color.rgb(255,255,255));
                        mentee02.setTextColor(getResources().getColor(R.color.black));
                        mentee_02 = false;
                        school_level = "";
                    }
                }
            });

            mentee03.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(mentee_03==false){
                        mentee03.setBackground(getResources().getDrawable(R.drawable.filter_border));
                        mentee03.setTextColor(getResources().getColor(R.color.mainBlue));
                        mentee01.setBackgroundColor(Color.rgb(255,255,255));
                        mentee02.setBackgroundColor(Color.rgb(255,255,255));
                        mentee_03 = true;
                        school_level = "초등학교";
                    }else{
                        mentee03.setBackgroundColor(Color.rgb(255,255,255));
                        mentee03.setTextColor(getResources().getColor(R.color.black));
                        mentee_03 = false;
                        school_level = "";
                    }
                }
            });

            mentor01.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(mentor_01==false){
                        mentor01.setBackground(getResources().getDrawable(R.drawable.filter_border));
                        mentor01.setTextColor(getResources().getColor(R.color.mainBlue));
                        mentor02.setBackgroundColor(Color.rgb(255,255,255));
                        mentor03.setBackgroundColor(Color.rgb(255,255,255));;
                        mentor04.setBackgroundColor(Color.rgb(255,255,255));;
                        mentor_01 = true;
                        mentor_type = "감독";
                    }else{
                        mentor01.setBackgroundColor(Color.rgb(255,255,255));
                        mentor01.setTextColor(getResources().getColor(R.color.black));
                        mentor_01 = false;
                        mentor_type = " ";
                    }
                }
            });

            mentor02.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(mentor_02==false){
                        mentor02.setBackground(getResources().getDrawable(R.drawable.filter_border));
                        mentor02.setTextColor(getResources().getColor(R.color.mainBlue));
                        mentor01.setBackgroundColor(Color.rgb(255,255,255));
                        mentor03.setBackgroundColor(Color.rgb(255,255,255));;
                        mentor04.setBackgroundColor(Color.rgb(255,255,255));;
                        mentor_02 = true;
                        mentor_type = "코치";
                    }else{
                        mentor02.setBackgroundColor(Color.rgb(255,255,255));
                        mentor02.setTextColor(getResources().getColor(R.color.black));
                        mentor_02 = false;
                        mentor_type = " ";
                    }
                }
            });

            mentor03.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(mentor_03==false){
                        mentor03.setBackground(getResources().getDrawable(R.drawable.filter_border));
                        mentor03.setTextColor(getResources().getColor(R.color.mainBlue));
                        mentor01.setBackgroundColor(Color.rgb(255,255,255));
                        mentor02.setBackgroundColor(Color.rgb(255,255,255));;
                        mentor04.setBackgroundColor(Color.rgb(255,255,255));;
                        mentor_03 = true;
                        mentor_type = "현역선수";
                    }else{
                        mentor03.setBackgroundColor(Color.rgb(255,255,255));
                        mentor03.setTextColor(getResources().getColor(R.color.black));
                        mentor_03 = false;
                        mentor_type = " ";
                    }
                }
            });

            mentor04.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(mentor_04==false){
                        mentor04.setBackground(getResources().getDrawable(R.drawable.filter_border));
                        mentor04.setTextColor(getResources().getColor(R.color.mainBlue));
                        mentor01.setBackgroundColor(Color.rgb(255,255,255));
                        mentor02.setBackgroundColor(Color.rgb(255,255,255));;
                        mentor03.setBackgroundColor(Color.rgb(255,255,255));;
                        mentor_04 = true;
                        mentor_type = "은퇴선수";
                    }else{
                        mentor04.setBackgroundColor(Color.rgb(255,255,255));
                        mentor04.setTextColor(getResources().getColor(R.color.black));
                        mentor_04 = false;
                        mentor_type = " ";
                    }
                }
            });

        }else if(type.equals("life")){

            post_type = "life_expert_knowledge_feed";

            life01.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(life_b01 == false){
                        life01.setBackground(getResources().getDrawable(R.drawable.filter_border));
                        life01.setTextColor(getResources().getColor(R.color.mainBlue));
                        life_b01 = true;
                        expert_type = "법률 전문가";
                    }else{
                        life01.setBackgroundColor(Color.rgb(255,255,255));
                        life01.setTextColor(getResources().getColor(R.color.black));
                        life_b01 = false;
                        expert_type = "";
                    }


                }
            });
            life02.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(life_b02 == false){

                        life02.setBackground(getResources().getDrawable(R.drawable.filter_border));
                        life02.setTextColor(getResources().getColor(R.color.mainBlue));
                        life_b02 = true;
                        expert_type = "세무,회계 전문가";
                    }else{
                        life02.setBackgroundColor(Color.rgb(255,255,255));
                        life02.setTextColor(getResources().getColor(R.color.black));
                        life_b02 = false;
                        expert_type = "";
                    }


                }
            });
            life03.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(life_b03 == false){

                        life03.setBackground(getResources().getDrawable(R.drawable.filter_border));
                        life03.setTextColor(getResources().getColor(R.color.mainBlue));
                        life_b03 = true;
                        expert_type = "부동산 전문가";
                    }else{
                        life03.setBackgroundColor(Color.rgb(255,255,255));
                        life03.setTextColor(getResources().getColor(R.color.black));
                        life_b03 = false;
                        expert_type = "";
                    }


                }
            });
            life04.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(life_b04 == false){

                        life04.setBackground(getResources().getDrawable(R.drawable.filter_border));
                        life04.setTextColor(getResources().getColor(R.color.mainBlue));
                        life_b04 = true;
                        expert_type = "손해 사정인";
                    }else{
                        life04.setBackgroundColor(Color.rgb(255,255,255));
                        life04.setTextColor(getResources().getColor(R.color.black));
                        life_b04 = false;
                        expert_type = "";
                    }



                }
            });

            life05.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if(life_b05 == false){

                        life05.setBackground(getResources().getDrawable(R.drawable.filter_border));
                        life05.setTextColor(getResources().getColor(R.color.mainBlue));
                        life_b05 = true;
                        expert_type = "증권,투자 전문가";
                    }else{
                        life05.setBackgroundColor(Color.rgb(255,255,255));
                        life05.setTextColor(getResources().getColor(R.color.black));
                        life_b05 = false;
                        expert_type = "";
                    }


                }
            });
            life06.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(life_b06 == false){

                        life06.setBackground(getResources().getDrawable(R.drawable.filter_border));
                        life06.setTextColor(getResources().getColor(R.color.mainBlue));
                        life_b06 = true;
                        expert_type = "보험 전문가";
                    }else{
                        life06.setBackgroundColor(Color.rgb(255,255,255));
                        life06.setTextColor(getResources().getColor(R.color.black));
                        life_b06 = false;
                        expert_type = "";
                    }



                }
            });
        }else if(type.equals("sport")){

            post_type = "sport_expert_knowledge_feed";

            sport01.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(sport_b01 == false){

                        sport01.setBackground(getResources().getDrawable(R.drawable.filter_border));
                        sport01.setTextColor(getResources().getColor(R.color.mainBlue));
                        sport_b01 = true;
                        expert_type = "스포츠 의학";

                    }else{
                        sport01.setBackgroundColor(Color.rgb(255,255,255));
                        sport01.setTextColor(getResources().getColor(R.color.black));
                        sport_b01 = false;
                        expert_type = "";
                    }



                }
            });
            sport02.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(sport_b02 == false){

                        sport02.setBackground(getResources().getDrawable(R.drawable.filter_border));
                        sport02.setTextColor(getResources().getColor(R.color.mainBlue));
                        sport_b02 = true;
                        expert_type = "스포츠 영양";
                    }else{
                        sport02.setBackgroundColor(Color.rgb(255,255,255));
                        sport02.setTextColor(getResources().getColor(R.color.black));
                        sport_b02 = false;
                        expert_type = "";
                    }

                }
            });

            sport03.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(sport_b03 == false){

                        sport03.setBackground(getResources().getDrawable(R.drawable.filter_border));
                        sport03.setTextColor(getResources().getColor(R.color.mainBlue));
                        sport_b03 = true;
                        expert_type = "스포츠 의학";
                    }else{
                        sport03.setBackgroundColor(Color.rgb(255,255,255));
                        sport03.setTextColor(getResources().getColor(R.color.black));
                        sport_b03 = false;
                        expert_type = "";
                    }
                }
            });
            sport04.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(sport_b04 == false){

                        sport04.setBackground(getResources().getDrawable(R.drawable.filter_border));
                        sport04.setTextColor(getResources().getColor(R.color.mainBlue));
                        sport_b04 = true;
                        expert_type = "스포츠 영양";
                    }else{
                        sport04.setBackgroundColor(Color.rgb(255,255,255));
                        sport04.setTextColor(getResources().getColor(R.color.black));
                        sport_b04 = false;
                        expert_type = "";
                    }

                }
            });

            sport05.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(sport_b05 == false){

                        sport05.setBackground(getResources().getDrawable(R.drawable.filter_border));
                        sport05.setTextColor(getResources().getColor(R.color.mainBlue));
                        sport_b05 = true;
                        expert_type = "스포츠 재활";
                    }else{
                        sport05.setBackgroundColor(Color.rgb(255,255,255));
                        sport05.setTextColor(getResources().getColor(R.color.black));
                        sport_b05 = false;
                        expert_type = "";
                    }

                }
            });
            sport06.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(sport_b06 == false){

                        sport06.setBackground(getResources().getDrawable(R.drawable.filter_border));
                        sport06.setTextColor(getResources().getColor(R.color.mainBlue));
                        sport_b06 = true;
                        expert_type = "스포츠 진로";
                    }else{
                        sport06.setBackgroundColor(Color.rgb(255,255,255));
                        sport06.setTextColor(getResources().getColor(R.color.black));
                        sport_b06 = false;
                        expert_type = "";
                    }
                }
            });

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
