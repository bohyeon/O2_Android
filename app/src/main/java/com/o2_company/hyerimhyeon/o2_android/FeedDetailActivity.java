package com.o2_company.hyerimhyeon.o2_android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.o2_company.DB.DBConnector;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;

public class FeedDetailActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ListView comment_lv;
    EditText comment_Et;
    TextView actionbar_title, name, type, belong, content, registdate, like, like_btn, comment_btn;
    String comment;
    FeedCommentAdapterActivity feedCommentAdapterActivity;
    FeedDetailActivity feedDetailActivity;
    NewsFeed newsFeed;
    String token, post_id;
    int like_int, comment_int;
    Handler handler;
    ImageView image, profile_img;
    LinearLayout youtube_layout = null;
    ImageView youtube_img = null;
    TextView youtube_title = null;
    String youtube_link , id, email;

    private static final int REQUEST_INTERNET = 1;
    private PopupWindow popWindow;
    public SharedPreferences loginPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsfeed_detail);

        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        id = loginPreferences.getString("id", "");
        email = loginPreferences.getString("email", "");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.setDrawerListener(toggle);
//        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        comment_lv = (ListView) findViewById(R.id.commentsListView);

        View header = getLayoutInflater().inflate(R.layout.activity_newsfeed_detail_header, null, false);
        this.comment_lv.addHeaderView(header);


        this.newsFeed = NewsFeed.getNewsFeed();
        this.feedCommentAdapterActivity = new FeedCommentAdapterActivity(this, newsFeed, this);
        this.comment_lv.setAdapter(feedCommentAdapterActivity);


        name = (TextView) header.findViewById(R.id.detail_newsfeed_lv_name);
        type = (TextView) header.findViewById(R.id.detail_newsfeed_lv_type);
        belong = (TextView) header.findViewById(R.id.detail_newsfeed_lv_belong);
        content = (TextView) header.findViewById(R.id.detail_newsfeed_lv_content);
        registdate = (TextView) header.findViewById(R.id.detail_newsfeed_lv_registDate);
        like = (TextView) header.findViewById(R.id.detail_newsfeed_like);
        like_btn = (TextView) header.findViewById(R.id.detail_like_btn);

        profile_img = (ImageView) header.findViewById(R.id.detail_profile_img);
        image = (ImageView) header.findViewById(R.id.detail_newsfeed_lv_img);

        comment_Et = (EditText) findViewById(R.id.writeComment);
        comment_btn = (TextView) findViewById(R.id.detail_writeComment_completeBtn);

        youtube_layout = (LinearLayout) header.findViewById(R.id.youtube_layout);
        youtube_img = (ImageView) header.findViewById(R.id.youtube_img);
        youtube_title = (TextView) header.findViewById(R.id.main_youtube_title);

        final Intent intent = getIntent();
        //Log.d("response","name : " + name);
        name.setText(intent.getStringExtra("name"));

        String type_str = "";

        if(intent.getStringExtra("type") == null){

        }else{
            if(intent.getStringExtra("type").equals("mentor")){
                type_str = "멘토";
            }else if(intent.getStringExtra("type").equals("mentee")){
                type_str = "꿈나무";
            }else if(intent.getStringExtra("type").equals("expert")){
                type_str = "전문가";
            }

            type.setText(type_str);

      //      Log.d("response" , "member_type : " + intent.getStringExtra("member_type"));

            if(intent.getStringExtra("member_type")!=null){
                if(intent.getStringExtra("member_type").equals("mentor")){
                    belong.setText(intent.getStringExtra("sport_type"));
                }else if(intent.getStringExtra("member_type").equals("expert")){
                    // Log.d("response" , "belong : " + newfeedItemPosition.name + newfeedItemPosition.expert_type);
                    belong.setText(intent.getStringExtra("expert_type"));
                }else{
                    belong.setText(intent.getStringExtra("sport_type"));
                }
            }


           // belong.setText(intent.getStringExtra("belong"));
        }



//        if(!intent.getStringExtra("type").equals("mentee")){
//            belong.setText(intent.getStringExtra("belong"));
//        }else{
//            belong.setText("");
//        }

//        profile_img.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent2 = new Intent( FeedDetailActivity.this, OtherPageActivity.class);
//                intent2.putExtra("name",intent.getStringExtra("name"));
//                intent2.putExtra("company",intent.getStringExtra("company"));
//                intent2.putExtra("mentor_type",intent.getStringExtra("mentor_type"));
//                intent2.putExtra("member_type",intent.getStringExtra("member_type"));
//                intent2.putExtra("expert_type",intent.getStringExtra("expert_type"));
//                intent2.putExtra("sport_type",intent.getStringExtra("sport_type"));
//                intent2.putExtra("member_id",intent.getStringExtra("member_id"));
//                intent2.putExtra("profile_url",intent.getStringExtra("profile_url"));
//
//                intent2.putExtra("phone_number",intent.getStringExtra("phone_number"));
//                intent2.putExtra("birthday",intent.getStringExtra("birthday"));
//                intent2.putExtra("is_phone_number_public",intent.getStringExtra("is_phone_number_public"));
//                intent2.putExtra("is_birthday_public",intent.getStringExtra("is_birthday_public"));
//                intent2.putExtra("region",intent.getStringExtra("region"));
//                intent2.putExtra("school_level",intent.getStringExtra("school_level"));
//                intent2.putExtra("school_name",intent.getStringExtra("school_name"));
//                intent2.putExtra("experience_1",intent.getStringExtra("experience_1"));
//                intent2.putExtra("experience_2",intent.getStringExtra("experience_2"));
//                intent2.putExtra("experience_3",intent.getStringExtra("experience_3"));
//                startActivity(intent);
//            }
//        });


        content.setText(intent.getStringExtra("content"));
        registdate.setText(intent.getStringExtra("regist_date"));
        like.setText("좋아요 " + intent.getStringExtra("like_num") + "개  댓글 " + intent.getStringExtra("comment_num") + "개");

        if(intent.getStringExtra("like_num") == null){

        }else{
            like_int = Integer.parseInt(intent.getStringExtra("like_num"));
        }

        if(intent.getStringExtra("comment_num") == null){

        }else{
            comment_int = Integer.parseInt(intent.getStringExtra("comment_num"));
        }


        token = intent.getStringExtra("token");
        post_id = intent.getStringExtra("post_id");

        if(intent.getBooleanExtra("is_like",false) == true){
            like_btn.setTextColor(Color.parseColor("#3F51B5"));
        }else{
            like_btn.setTextColor(Color.parseColor("#555555"));
        }


        if(intent.getStringExtra("youtube_id") == null ||intent.getStringExtra("youtube_id").equals("")){

            // viewHolder.youtube_layout.setBackground(null);
            int width = 0;
            int height = 0;
            LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width,height);
            youtube_img.setLayoutParams(parms);
            //       viewHolder.youtube_title.setLayoutParams(parms);
            youtube_layout.setBackground(null);
            youtube_img.setImageBitmap(null);
            youtube_title.setText(null);


        }else{
            if(!intent.getStringExtra("youtube_id").equals("") ){
                youtube_layout.setBackground(null);
                Log.d("response", "youtube ㅅㅂ : " + intent.getStringExtra("youtube_id"));
                youtube_link = intent.getStringExtra("youtube_link");
                //   new GetYoutube().execute(new DBConnector());
                // new DownLoadImageTask_youtube(viewHolder.youtube_img).execute("http://img.youtube.com/vi/"+newfeedItemPosition.youtube_id+"/1.jpg");


                WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
                Display display = wm.getDefaultDisplay();
                DisplayMetrics metrics = new DisplayMetrics();
                display.getMetrics(metrics);
                int width = metrics.widthPixels/3;
                int height = 230;

                LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width,width);
                //  viewHolder.youtube_title.setLayoutParams(parms);
                Log.d("response","youtube titllej : " + intent.getStringExtra("youtube_title"));
                youtube_title.setText(intent.getStringExtra("youtube_title"));
                youtube_img.setLayoutParams(parms);
                youtube_layout.setBackgroundResource(R.drawable.table_border);
                Picasso.with(feedDetailActivity)
                        .load("http://img.youtube.com/vi/"+intent.getStringExtra("youtube_id")+"/1.jpg")
                        .resize(width,width)
                        .placeholder(R.drawable.blankimg)
                        .error(R.drawable.blankimg)
                        .into(youtube_img);

            }else{
                int width = 0;
                int height = 0;
                LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width,height);
                youtube_img.setLayoutParams(parms);
                //       viewHolder.youtube_title.setLayoutParams(parms);
                youtube_layout.setBackground(null);
                youtube_img.setImageBitmap(null);
                youtube_title.setText(null);

            }

        }

        youtube_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(youtube_link.toString()));
                startActivity(browserIntent);
            }
        });

        youtube_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(youtube_link.toString()));
                startActivity(browserIntent);
            }
        });

        if(intent.getStringExtra("post_image_url").equals("")|| intent.getStringExtra("post_image_url") == null|| intent.getStringExtra("post_image_url") == " "|| intent.getStringExtra("post_image_url") == "null"|| intent.getStringExtra("post_image_url") == "" || intent.getStringExtra("post_image_url")=="http://" || intent.getStringExtra("post_image_url")=="http://null" || intent.getStringExtra("post_image_url").equals("http://") || intent.getStringExtra("post_image_url").equals("http:/null/")){

            int width = 0;
            int height = 0;
            LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width,height);
            image.setLayoutParams(parms);


        }else{
               // new DownLoadImageTask(image).execute("http://"+intent.getStringExtra("post_image_url"));
            int width =  ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width,height);
            image.setLayoutParams(parms);


            Picasso.with(feedDetailActivity)
                    .load("http://"+intent.getStringExtra("post_image_url"))
                    .placeholder(R.drawable.blankimg)
                    .error(R.drawable.blankimg)
                    .into(image);



        }


        if(intent.getStringExtra("profile_url").equals("")|| intent.getStringExtra("profile_url") == null|| intent.getStringExtra("profile_url") == " "|| intent.getStringExtra("profile_url") == "null"|| intent.getStringExtra("profile_url") == "" || intent.getStringExtra("profile_url")=="http://" || intent.getStringExtra("profile_url")=="http://null" || intent.getStringExtra("profile_url").equals("http://") || intent.getStringExtra("profile_url").equals("http:/null/")){
            profile_img.setBackgroundResource(0);

        }else{
            profile_img.setBackgroundResource(0);

            Picasso.with(feedDetailActivity)
                    .load("http://"+intent.getStringExtra("profile_url"))
                    .resize(250,200)
                    .placeholder(R.drawable.blankimg)
                    .error(R.drawable.blankimg)
                    .into(profile_img);

            // new DownLoadImageTask_profile(profile_img).execute("http://"+intent.getStringExtra("profile_url"));
            }
//



        //get comments by post id
        new GetComments().execute(new DBConnector());



        final Boolean[] is_like = {intent.getBooleanExtra("is_like", false)};

        like_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if( is_like[0] == true ){
                    like_btn.setTextColor(Color.parseColor("#555555"));
                    new DeleteLike().execute(new DBConnector());
                    is_like[0] = false;
                    like_int = like_int -1;
                    like.setText("좋아요 " + like_int + "개  댓글 " + intent.getStringExtra("comment_num") + "개");

                }else{
                    like_btn.setTextColor(Color.parseColor("#3F51B5"));
                    new Like().execute(new DBConnector());
                    is_like[0] = true;
                    like_int = like_int +1;
                    like.setText("좋아요 " + like_int + "개  댓글 " + intent.getStringExtra("comment_num") + "개");

                }
            }
        });

        comment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                comment = comment_Et.getText().toString();

                if(comment == null || comment.equals("")){
                    Toast.makeText(getApplicationContext(), "내용을 입력해주세요!",
                            Toast.LENGTH_LONG).show();
                }else{

                    new Comment().execute(new DBConnector());
                    comment_Et.setText("");

                    InputMethodManager inputManager = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);

                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);


                    //get comments by post id
                    new GetComments().execute(new DBConnector());

                    comment_int++;
                    like.setText("좋아요 " + like_int + "개  댓글 " + comment_int + "개");
                }

            }
        });

    }

    public void GetCommentToAdapter(){
        new GetComments().execute(new DBConnector());
        int comment_num2 = comment_int-1;

        like.setText("좋아요 " + like_int + "개  댓글 " + comment_num2 + "개");
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {

            case REQUEST_INTERNET:
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
    private class Like extends AsyncTask<DBConnector, Long, String> {


        @Override
        protected String doInBackground(DBConnector... params) {

            //it is executed on Background thread
           // Log.d("response22 " , "hihi" + token + "  " + select_pods_id);
            return params[0].Like(token, post_id);

        }

        @Override
        protected void onPostExecute(final String jsonObject) {

            settextToAdapter(jsonObject);

        }
    }

    public void settextToAdapter(String jsonObject) {

        if(jsonObject == null){

        }else{

        }

    }

    private class DeleteLike extends AsyncTask<DBConnector, Long, JSONObject> {


        @Override
        protected JSONObject doInBackground(DBConnector... params) {

            //it is executed on Background thread
          //  Log.d("response22 " , "delete" + token + "  " + select_pods_id);
            return params[0].DeleteLike(token, post_id);

        }

        @Override
        protected void onPostExecute(final JSONObject jsonObject) {

            settextToAdapter_deleteLike(jsonObject);

        }
    }

    public void settextToAdapter_deleteLike(JSONObject jsonObject) {

        if(jsonObject == null){

        }else{

        }

    }

    private class Comment extends AsyncTask<DBConnector, Long, JSONObject> {


        @Override
        protected JSONObject doInBackground(DBConnector... params) {

            //it is executed on Background thread
           // Log.d("response22 " , "hihi" + token + "  " + select_pods_id);
            return params[0].Comment(token, post_id, comment);

        }

        @Override
        protected void onPostExecute(final JSONObject jsonObject) {

            settextToAdapter_comment(jsonObject);

        }
    }

    public void settextToAdapter_comment(JSONObject jsonObject) {

        if(jsonObject == null){

        }else{
           // Log.d("response" , "comment : " + jsonObject.toString());
        }

    }


    private class GetComments extends AsyncTask<DBConnector, Long, JSONArray> {


        @Override
        protected JSONArray doInBackground(DBConnector... params) {

            //it is executed on Background thread
            // Log.d("response22 " , "hihi" + token + "  " + select_pods_id);
            return params[0].GetComment(token, post_id);

        }

        @Override
        protected void onPostExecute(final JSONArray jsonArray) {

            settextToAdapter_Getcomment(jsonArray);

            handler = new Handler();

            handler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    try {

                        feedCommentAdapterActivity.notifyDataSetChanged();

                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                }
            }, 100);

        }
    }

    public void settextToAdapter_Getcomment(JSONArray jsonArray) {

        NewsfeedItem newsfeedItem;

        if(jsonArray == null){

        }else{
            Log.d("response" , "getcomment : " + jsonArray.toString());
            feedCommentAdapterActivity.clear();

            for(int i = 0 ; i<jsonArray.length(); i++){

                newsfeedItem = new NewsfeedItem();

                try {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    JSONObject userJsonObj = jsonObject.getJSONObject("user");

                    newsfeedItem.name = userJsonObj.getString("name");
                    newsfeedItem.email = userJsonObj.getString("email");
                    newsfeedItem.profile_url = userJsonObj.getString("profile_url");
                    newsfeedItem.member_type = userJsonObj.getString("member_type");
                    newsfeedItem.type = userJsonObj.getString("member_type");
                    newsfeedItem.company = userJsonObj.getString("company");
                    newsfeedItem.sport_type = userJsonObj.getString("sport_type");
                    newsfeedItem.mentor_type = userJsonObj.getString("mentor_type");
                    newsfeedItem.expert_type = userJsonObj.getString("expert_type");
                    newsfeedItem.content = jsonObject.getString("content");
                    newsfeedItem.regist_date = jsonObject.getString("timestamp").substring(0,10);
                    newsfeedItem.belong = userJsonObj.getString("sport_type");
                    newsfeedItem.id = jsonObject.getString("id");

                    feedCommentAdapterActivity.add(newsfeedItem);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


        }

    }

    @Override
    public void onBackPressed() {
        finish();
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
       // getMenuInflater().inflate(R.menu.main, menu);

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
        actionbar_title.setText("댓글 달기");
        ImageButton searchBtn = (ImageButton) findViewById(R.id.actionbar_search);
        ImageButton menu_icon = (ImageButton) findViewById(R.id.actionbar_menu);

        searchBtn.setBackgroundResource(0);
        menu_icon.setBackgroundResource(0);


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

            Intent intent = new Intent(FeedDetailActivity.this, SubjectFeedActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_slideshow) {

            Intent intent = new Intent(FeedDetailActivity.this, ExpertFeedActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_manage) {

            Intent intent = new Intent(FeedDetailActivity.this, NewsfeedWriteActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_share) {

            Intent intent = new Intent(FeedDetailActivity.this, NoticeActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_send) {

            Intent intent = new Intent(FeedDetailActivity.this, MypageActivity.class);
            startActivity(intent);

        } else if (id == R.id.people) {

            Intent intent = new Intent(FeedDetailActivity.this, MemberActivity.class);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private class DownLoadImageTask extends AsyncTask<String,Void,Bitmap>{
        ImageView imageView;

        public DownLoadImageTask(ImageView imageView){
            this.imageView = imageView;
        }

        /*
            doInBackground(Params... params)
                Override this method to perform a computation on a background thread.
         */
        protected Bitmap doInBackground(String...urls){
            //String urlOfImage = urls[0];
            String urlOfImage = urls[0];
            Log.d("response" , "image url : " + urlOfImage);
            Bitmap logo = null;
            try{
                InputStream is = new URL(urlOfImage).openStream();
                /*
                    decodeStream(InputStream is)
                        Decode an input stream into a bitmap.
                 */
                logo = BitmapFactory.decodeStream(is);
            }catch(Exception e){ // Catch the download exception
                e.printStackTrace();
                Log.d("response" ,"image back : " + e.toString());
            }
            return logo;
        }

        /*
            onPostExecute(Result result)
                Runs on the UI thread after doInBackground(Params...).
         */
        protected void onPostExecute(Bitmap result){

            WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            DisplayMetrics metrics = new DisplayMetrics();
            display.getMetrics(metrics);
            int width = metrics.widthPixels;
            int height = metrics.heightPixels/2 + 50;
            Log.d("response" , "screen : " + width + " " + height);
            LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width,height);
            imageView.setLayoutParams(parms);

            imageView.setImageBitmap(result);
        }




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


}
