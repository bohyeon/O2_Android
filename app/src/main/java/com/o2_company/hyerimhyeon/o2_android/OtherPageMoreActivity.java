package com.o2_company.hyerimhyeon.o2_android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.o2_company.DB.DBConnector;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Calendar;

public class OtherPageMoreActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    Button child_btn, mento_btn, expert_btn, register_btn, logout_btn;
    RadioButton one, two, three, four;
    Spinner type, location, sport_type_sp, school_level_sp, expert_type_sp;
    TextView actionbar_title;
    ImageView profile_img;
    DrawerLayout drawer;

    Boolean buttonStateOpen;
    TextView email, pw, pw_ck, name, phone, birth, sport_type, region, school_level, school_name, company, experience_1, experience_2, experience_3, mentor_type, expert_type;
    RadioButton radio01, radio02, radio03, radio04;
    CheckBox birth_public, phone_public;
    String token_str, name_str, password_str, profile_image_url, phone_number_str, is_phone_number_public, birthday, is_birthday_public, sport_type_str, expert_type_str, region_str, school_level_str2, school_name_str, company_str, experience_1_str, experience_2_str, experience_3_str, mentor_type_str;
    CheckBox noti_ck;
    String noti_bool = "true";
    String email_str;
    private PopupWindow popWindow;
    private File file = null;
    private static final int REQUEST_INTERNET = 1;
    public SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private String current = "";
    private String yyyymmdd = "YYYYMMDD";
    private Calendar cal = Calendar.getInstance();

    @Override
    public void onStart(){
        super.onStart();

        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();
       // email_str = intent.getStringExtra("email");
        Log.d("response" , "token : " + token_str);
        profile_image_url = loginPreferences.getString("profile_image_url","");

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();

        Intent intent = getIntent();

//        Log.d("response" , "sport type 0 : " + intent.getStringExtra("member_type").toString());

        if(intent.getStringExtra("member_type").equals("mentee")){
            setContentView(R.layout.activity_mentee_page_more);


            email = (TextView) findViewById(R.id.c_setting_mail);
            name = (TextView) findViewById(R.id.c_setting_name);
            phone = (TextView) findViewById(R.id.c_setting_phone);
            school_name = (TextView) findViewById(R.id.c_setting_belong);
            birth = (TextView) findViewById(R.id.c_setting_birth);
            child_btn = (Button) findViewById(R.id.c_setting_btn);
            birth_public = (CheckBox) findViewById(R.id.c_setting_birth_ok);
            phone_public = (CheckBox) findViewById(R.id.c_setting_phone_ok);
            sport_type = (TextView) findViewById(R.id.c_setting_sport);
            region = (TextView) findViewById(R.id.c_setting_region);
            school_level = (TextView) findViewById(R.id.c_setting_school_level);

            sport_type.setText(intent.getStringExtra("sport_type"));
            region.setText(intent.getStringExtra("region"));
            school_level.setText(intent.getStringExtra("school_level"));



            email.setText(intent.getStringExtra("email"));
            name.setText(intent.getStringExtra("name"));

            if(intent.getStringExtra("is_phone_number_public").equals("true")){
                phone.setText(intent.getStringExtra("phone_number"));
            }else{
                phone.setText("비공개");
            }

            if(intent.getStringExtra("is_birthday_public").equals("true")){
                birth.setText(intent.getStringExtra("birthday"));
            }else{
                birth.setText("비공개");
            }

            school_name.setText(intent.getStringExtra("school_name"));


            profile_img = (ImageView) findViewById(R.id.setting_profile_img);

            if(intent.getStringExtra("profile_url").equals("")|| intent.getStringExtra("profile_url") == null|| intent.getStringExtra("profile_url") == " "|| intent.getStringExtra("profile_url") == "null"|| intent.getStringExtra("profile_url") == "" || intent.getStringExtra("profile_url")=="http://" || intent.getStringExtra("profile_url")=="http://null" || intent.getStringExtra("profile_url").equals("http://") || intent.getStringExtra("profile_url").equals("http:/null/")){

            }else{

                new DownLoadImageTask_profile(profile_img).execute("http://" + intent.getStringExtra("profile_url"));

            }

        }else if(intent.getStringExtra("member_type").equals("mentor")){

            setContentView(R.layout.activity_mentor_page_more);

            email = (TextView) findViewById(R.id.mentor_setting_mail);
            name = (TextView) findViewById(R.id.mentor_setting_name);
            phone = (TextView) findViewById(R.id.mentor_setting_phone);
            birth = (TextView) findViewById(R.id.mentor_setting_birth);
            company = (TextView) findViewById(R.id.mentor_setting_company);
            radio01 = (RadioButton) findViewById(R.id.mentor_setting_radio1);
            radio02 = (RadioButton) findViewById(R.id.mentor_setting_radio2);
            radio03 = (RadioButton) findViewById(R.id.mentor_setting_radio3);
            radio04 = (RadioButton) findViewById(R.id.mentor_setting_radio4);
            profile_img = (ImageView) findViewById(R.id.mentor_setting_profile_img);
            sport_type = (TextView) findViewById(R.id.mentor_setting_sport);
            region = (TextView) findViewById(R.id.mentor_setting_region);
            mentor_type = (TextView) findViewById(R.id.mentor_setting_mentor_type);
            
            Log.d("response" , "regio : " + intent.getStringExtra("mentor_type"));
            sport_type.setText(intent.getStringExtra("sport_type"));
            region.setText(intent.getStringExtra("region"));
            mentor_type.setText(intent.getStringExtra("mentor_type"));
            email.setText(intent.getStringExtra("email"));
            name.setText(intent.getStringExtra("name"));
            company.setText(intent.getStringExtra("company"));

            if(intent.getStringExtra("is_phone_number_public").equals("true")){
                phone.setText(intent.getStringExtra("phone_number"));
            }else{
                phone.setText("비공개");
            }

            if(intent.getStringExtra("is_birthday_public").equals("true")){
                birth.setText(intent.getStringExtra("birthday"));
            }else{
                birth.setText("비공개");
            }



            if(intent.getStringExtra("profile_url").equals("")|| intent.getStringExtra("profile_url") == null|| intent.getStringExtra("profile_url") == " "|| intent.getStringExtra("profile_url") == "null"|| intent.getStringExtra("profile_url") == "" || intent.getStringExtra("profile_url")=="http://" || intent.getStringExtra("profile_url")=="http://null" || intent.getStringExtra("profile_url").equals("http://") || intent.getStringExtra("profile_url").equals("http:/null/")){

            }else{

                new DownLoadImageTask_profile(profile_img).execute("http://" + intent.getStringExtra("profile_url"));

            }



        }else {
            setContentView(R.layout.activity_expert_page_more);


            email = (TextView) findViewById(R.id.e_setting_mail);
            name = (TextView) findViewById(R.id.e_setting_name);
            phone = (TextView) findViewById(R.id.e_setting_phone);
            birth = (TextView) findViewById(R.id.e_setting_birth);
          //  company = (TextView) findViewById(R.id.e_setting_company);
            experience_1 = (TextView) findViewById(R.id.e_setting_experience01);
            experience_2 = (TextView) findViewById(R.id.e_setting_experience02);
            experience_3 = (TextView) findViewById(R.id.e_setting_experience03);
            expert_type = (TextView) findViewById(R.id.e_setting_expert_type);

            expert_type.setText(intent.getStringExtra("expert_type"));


            email.setText(intent.getStringExtra("email"));
            name.setText(intent.getStringExtra("name"));
            if(intent.getStringExtra("is_phone_number_public").equals("true")){
                phone.setText(intent.getStringExtra("phone_number"));
            }else{
                phone.setText("비공개");
            }

            if(intent.getStringExtra("is_birthday_public").equals("true")){
                birth.setText(intent.getStringExtra("birthday"));
            }else{
                birth.setText("비공개");
            }


            //company.setText(intent.getStringExtra("expert_type"));
            experience_1.setText(intent.getStringExtra("experience_1"));
            experience_2.setText(intent.getStringExtra("experience_2"));
            experience_3.setText(intent.getStringExtra("experience_3"));


            profile_img = (ImageView) findViewById(R.id.setting_profile_img);

            if(intent.getStringExtra("profile_url").equals("")|| intent.getStringExtra("profile_url") == null|| intent.getStringExtra("profile_url") == " "|| intent.getStringExtra("profile_url") == "null"|| intent.getStringExtra("profile_url") == "" || intent.getStringExtra("profile_url")=="http://" || intent.getStringExtra("profile_url")=="http://null" || intent.getStringExtra("profile_url").equals("http://") || intent.getStringExtra("profile_url").equals("http:/null/")){

            }else{

                new DownLoadImageTask_profile(profile_img).execute("http://" + intent.getStringExtra("profile_url"));

            }


        }

    }

    private class UploadImage extends AsyncTask<DBConnector, Long, JSONObject> {


        @Override
        protected JSONObject doInBackground(DBConnector... params) {

            //it is executed on Background thread
         //   String token = "db4fdaf8d9cb71b454a47c114422e29c4165e203";

            return params[0].UploadImage(file, token_str);

        }

        @Override
        protected void onPostExecute(final JSONObject jsonObject) {

            settextToAdapter_profile(jsonObject);

        }
    }

    public void settextToAdapter_profile(JSONObject jsonObject) {

        if (jsonObject == null) {

        } else {

            profile_image_url = "";
            try {
                profile_image_url = jsonObject.getString("profile_image_url").toString();

                Log.d("response", "profile_img u " + jsonObject);
                if (profile_image_url != "") {
                    loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
                    loginPrefsEditor = loginPreferences.edit();
                    String img_url = jsonObject.getString("profile_image_url").replace("\\/","\\");
                    Log.d("response", "profile_img u " + img_url);
                    loginPrefsEditor.putString("profile_url",img_url);

                    loginPrefsEditor.commit();
                   new PutUserInfo().execute(new DBConnector());
                 //   new MentoRegisterActivity.MentoResgister().execute(new DBConnector());
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


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
         //   Log.d("response", "image url : " + urlOfImage);
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
         //       Log.d("response", "image back : " + e.toString());
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

    private class DeleteLogout extends AsyncTask<DBConnector, Long, JSONObject> {


        @Override
        protected JSONObject doInBackground(DBConnector... params) {

            //it is executed on Background thread
            //   String token = "db4fdaf8d9cb71b454a47c114422e29c4165e203";

            return params[0].DeleteLogout(token_str);

        }

        @Override
        protected void onPostExecute(final JSONObject jsonObject) {

            settextToAdapter_logout(jsonObject);

        }
    }

    public void settextToAdapter_logout(JSONObject jsonObject) {

        if (jsonObject == null) {

        } else {


            loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
            loginPrefsEditor = loginPreferences.edit();

            loginPrefsEditor.clear();
            loginPrefsEditor.commit();


            Intent intent = new Intent(OtherPageMoreActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            finish();
            startActivity(intent);

        }


    }



    private class PutUserInfo extends AsyncTask<DBConnector, Long, JSONObject> {


        @Override
        protected JSONObject doInBackground(DBConnector... params) {

            //it is executed on Background thread
           // Log.d("response" , "update user token: " + token_str.toString());
            return params[0].PutUserInfo(token_str, name_str , password_str,profile_image_url,phone_number_str, is_phone_number_public, birthday, is_birthday_public, sport_type_str, mentor_type_str, expert_type_str, region_str, school_level_str2, school_name_str, company_str, experience_1_str, experience_2_str, experience_3_str, noti_bool);

        }

        @Override
        protected void onPostExecute(final JSONObject jsonObject) {

            settextToAdapter(jsonObject);

        }
    }

    public void settextToAdapter(JSONObject jsonObject) {

        //Log.d("response" , "update user : " + jsonObject.toString());
       // Log.d("response" , "check img : " +jsonObject);
        if(jsonObject == null){

        }else{

            loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
            loginPrefsEditor = loginPreferences.edit();


            try {
                loginPrefsEditor.putString("name",name_str);

           //     loginPrefsEditor.putString("profile_image_url",jsonObject.getString("profile_url").toString());
                loginPrefsEditor.putString("phone_number",jsonObject.getString("phone_number").toString());
                loginPrefsEditor.putString("birthday",jsonObject.getString("birthday").toString());
                loginPrefsEditor.putString("is_phone_number_public", jsonObject.getString("is_phone_number_public").toString());
                loginPrefsEditor.putString("is_birthday_public", jsonObject.getString("is_birthday_public").toString());
                loginPrefsEditor.putString("sport_type",jsonObject.getString("sport_type").toString());
                loginPrefsEditor.putString("region",jsonObject.getString("region").toString());
                loginPrefsEditor.putString("school_level",jsonObject.getString("school_level").toString());
                loginPrefsEditor.putString("school_name",jsonObject.getString("school_name").toString());
                loginPrefsEditor.putString("mentor_type",jsonObject.getString("mentor_type").toString());
                loginPrefsEditor.putString("company",jsonObject.getString("company").toString());
                loginPrefsEditor.putString("expert_type",jsonObject.getString("expert_type").toString());
                loginPrefsEditor.putString("experience_1",jsonObject.getString("experience_1").toString());
                loginPrefsEditor.putString("experience_2",jsonObject.getString("experience_2").toString());
                loginPrefsEditor.putString("experience_3",jsonObject.getString("experience_3").toString());
                loginPrefsEditor.putString("is_receive_push",jsonObject.getString("is_receive_push").toString());

      //          Log.d("response" , "sport type 3 : " + jsonObject.getString("sport_type").toString());

            } catch (JSONException e) {
                e.printStackTrace();
            }


            loginPrefsEditor.commit();

            Toast.makeText(getApplicationContext(), name_str+"님 회원정보가 수정되었습니다.",
                    Toast.LENGTH_LONG).show();

            Intent intent = new Intent(this, OtherPageMoreActivity.class);
            finish();
            startActivity(intent);
        }

    }


        @Override
    public void onBackPressed() {
     finish();
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
        actionbar_title.setText("설정");


        buttonStateOpen = false;

        ImageButton menu_icon = (ImageButton) findViewById(R.id.actionbar_menu);
        menu_icon.setBackgroundResource(R.drawable.arrow_icon);
        menu_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ImageButton search_icon = (ImageButton) findViewById(R.id.actionbar_search);
        search_icon.setBackgroundResource(0);


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

            Intent intent = new Intent(OtherPageMoreActivity.this, MainActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_gallery) {

            Intent intent = new Intent(OtherPageMoreActivity.this, SubjectFeedActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_slideshow) {

            Intent intent = new Intent(OtherPageMoreActivity.this, ExpertFeedActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_manage) {

            Intent intent = new Intent(OtherPageMoreActivity.this, InviteActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_share) {

            Intent intent = new Intent(OtherPageMoreActivity.this, NoticeActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_send) {

            Intent intent = new Intent(OtherPageMoreActivity.this, MypageActivity.class);
            startActivity(intent);

        } else if (id == R.id.people) {

            Intent intent = new Intent(OtherPageMoreActivity.this, MemberActivity.class);
            startActivity(intent);

        }else if (id == R.id.nav_lifesport) {

            Intent intent = new Intent(OtherPageMoreActivity.this, LifeExpertFeedActivity.class);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
