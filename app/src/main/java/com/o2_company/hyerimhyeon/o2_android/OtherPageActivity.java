package com.o2_company.hyerimhyeon.o2_android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.o2_company.DB.DBConnector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;

public class OtherPageActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ListView mypage_mypost, comment_lv, alarm_lv;
    FrameLayout container;
    ImageButton backBtn;
    Boolean buttonStateOpen;

    OtherpagePostAdapterActivity mypageMypostAdapterActivity;

    NewsFeed newsFeed;

    ImageView profile_img;
    Handler handler;
    TextView nametv, typetv, companytv;
    String token, id, member_id;
    DrawerLayout drawer;
    private static final int REQUEST_INTERNET = 1;
    public SharedPreferences loginPreferences;
    private static final int REQUEST_EXTERNAL_STORAGE = 2;
    ImageButton menu_icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage_other);



        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.inflateHeaderView(R.layout.nav_header_main);
        ImageView logo = (ImageView) headerView.findViewById(R.id.nav_header_logo);

        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(OtherPageActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();

            }
        });

        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);



        nametv = (TextView) findViewById(R.id.mypage_name);
        typetv = (TextView) findViewById(R.id.mypage_type);
        companytv = (TextView) findViewById(R.id.mypage_company);
        profile_img = (ImageView) findViewById(R.id.mypage_profile_img);

        final Intent intent = getIntent();
        //   Log.d("response" , "myname: " + loginPreferences.getString("name" , ""));
        nametv.setText(intent.getStringExtra("name"));
        if(intent.getStringExtra("member_type").equals("expert")){
            typetv.setText("전문가");
            companytv.setText(intent.getStringExtra("expert_type"));
        }else if(intent.getStringExtra("member_type").equals("mentor")){
            typetv.setText("멘토");
            companytv.setText(intent.getStringExtra("sport_type")+" " +intent.getStringExtra("company"));
        }else{
            typetv.setText("꿈나무");
            companytv.setText(intent.getStringExtra("sport_type"));
        }

//        if(loginPreferences.getString("company","")!=null){
//            companytv.setText(loginPreferences.getString("company",""));
//        }else{
//            companytv.setText("");
//        }
        member_id = intent.getStringExtra("member_id");

        if(  intent.getStringExtra("profile_url").equals("")||   intent.getStringExtra("profile_url") == null||   intent.getStringExtra("profile_url") == " "||   intent.getStringExtra("profile_url") == "null"||   intent.getStringExtra("profile_url") == "" ||   intent.getStringExtra("profile_url")=="http://" ||   intent.getStringExtra("profile_url")=="http://null" ||   intent.getStringExtra("profile_url").equals("http://") ||   intent.getStringExtra("profile_url").equals("http:/null/")){

        }else{
            int permissionCamera = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_NETWORK_STATE);
            if(permissionCamera == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_NETWORK_STATE}, REQUEST_INTERNET);
            } else {
                new DownLoadImageTask_profile(profile_img).execute("http://" + intent.getStringExtra("profile_url"));

            }
            // Log.d("response2", "response img: " + uri);
            // image.setImageURI(uri);
        }
        token = loginPreferences.getString("token","");
        id = loginPreferences.getString("id","");

        backBtn = (ImageButton) findViewById(R.id.mypage_backBtn);


        mypage_mypost = (ListView)findViewById(R.id.mypage_mypost);


        this.newsFeed = NewsFeed.getNewsFeed();
        this.mypageMypostAdapterActivity = new OtherpagePostAdapterActivity(this, newsFeed, this);
        this.mypage_mypost.setAdapter(mypageMypostAdapterActivity);


        // open or close when click a menu button
        buttonStateOpen = false;
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        menu_icon = (ImageButton) findViewById(R.id.other_moreBtn);

        menu_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent2 = new Intent( OtherPageActivity.this, OtherPageMoreActivity.class);
                Log.d("response" , "member_type :  " + intent.getStringExtra("experience_1"));
                intent2.putExtra("email",intent.getStringExtra("email"));

                intent2.putExtra("name",intent.getStringExtra("name"));
                intent2.putExtra("company",intent.getStringExtra("company"));
                intent2.putExtra("mentor_type",intent.getStringExtra("mentor_type"));
                intent2.putExtra("member_type",intent.getStringExtra("member_type"));
                intent2.putExtra("expert_type",intent.getStringExtra("expert_type"));
                intent2.putExtra("sport_type",intent.getStringExtra("sport_type"));
                intent2.putExtra("member_id",intent.getStringExtra("member_id"));
                intent2.putExtra("profile_url",intent.getStringExtra("profile_url"));

                intent2.putExtra("phone_number",intent.getStringExtra("phone_number"));
                intent2.putExtra("birthday",intent.getStringExtra("birthday"));
                intent2.putExtra("is_phone_number_public",intent.getStringExtra("is_phone_number_public"));
                intent2.putExtra("is_birthday_public",intent.getStringExtra("is_birthday_public"));
                intent2.putExtra("region",intent.getStringExtra("region"));
                intent2.putExtra("school_level",intent.getStringExtra("school_level"));
                intent2.putExtra("school_name",intent.getStringExtra("school_name"));
                intent2.putExtra("experience_1",intent.getStringExtra("experience_1"));
                intent2.putExtra("experience_2",intent.getStringExtra("experience_2"));
                intent2.putExtra("experience_3",intent.getStringExtra("experience_3"));

                startActivityForResult(intent2,300);
            }
        });

    }

    private class DownLoadImageTask_profile extends AsyncTask<String,Void,Bitmap> {
        ImageView imageView;

        public DownLoadImageTask_profile(ImageView imageView) {
            this.imageView = imageView;
        }

        /*
            doInBackground(Params... params)
                Override this method to perform a computation on a background thread.
         */
        protected Bitmap doInBackground(String... urls) {
            //String urlOfImage = urls[0];
            String urlOfImage = urls[0];
            Log.d("response", "image url : " + urlOfImage);
            Bitmap logo = null;
            try {
                InputStream is = new URL(urlOfImage).openStream();
                /*
                    decodeStream(InputStream is)
                        Decode an input stream into a bitmap.
                 */
                logo = BitmapFactory.decodeStream(is);
            } catch (Exception e) { // Catch the download exception
                e.printStackTrace();
                Log.d("response", "image back : " + e.toString());
            }
            return logo;
        }

        /*
            onPostExecute(Result result)
                Runs on the UI thread after doInBackground(Params...).
         */
        protected void onPostExecute(Bitmap result) {


            imageView.setImageBitmap(result);
        }
    }

    @Override
    public void onStart(){
        super.onStart();


        int permissionReadStorage = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_NETWORK_STATE);
        //  int permissionWriteStorage = ContextCompat.checkSelfPermission(mainActivity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(permissionReadStorage == PackageManager.PERMISSION_DENIED ) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_NETWORK_STATE}, REQUEST_EXTERNAL_STORAGE);
        } else {
            new GetPost_User().execute(new DBConnector());
        }

    }


    private class GetPost_User extends AsyncTask<DBConnector, Long, JSONArray> {


        @Override
        protected JSONArray doInBackground(DBConnector... params) {

            //it is executed on Background thread

            return params[0].GetPost_User(token, member_id);

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

                        mypageMypostAdapterActivity.notifyDataSetChanged();

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
            mypageMypostAdapterActivity.clear();
            mypageMypostAdapterActivity.notifyDataSetChanged();
            Toast.makeText(getApplicationContext(), "포스트가 없습니다.",
                    Toast.LENGTH_LONG).show();
        }else{

            mypageMypostAdapterActivity.clear();

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
                    newsfeedItem.experience_1 = userJsonObj.getString("experience_1");
                    newsfeedItem.experience_2 = userJsonObj.getString("experience_2");
                    newsfeedItem.experience_3 = userJsonObj.getString("experience_3");

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
                            Log.d("response", "youtube_id :  "+ video_id);
                        }

                        newsfeedItem.youtube_id = video_id;

                    }


                    mypageMypostAdapterActivity.add(newsfeedItem);
                    mypageMypostAdapterActivity.notifyDataSetChanged();

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

            Intent intent = new Intent(this, OtherPageActivity.class);
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


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {

            case REQUEST_EXTERNAL_STORAGE:
                for (int i = 0; i < permissions.length; i++) {
                    String permission = permissions[i];
                    int grantResult = grantResults[i];
                    if (permission.equals(android.Manifest.permission.ACCESS_NETWORK_STATE)) {
                        if(grantResult == PackageManager.PERMISSION_GRANTED) {
                            Log.d("response", "read/write storage  permission authorized2");

                        } else {
                            Log.d("response", "read/write storage  permission denied2");

                        }
                        break;
                    }
                }
                break;
        }
    }

}
