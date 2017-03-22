package com.example.hyerimhyeon.o2_android;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.DB.DBConnector;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class MypageSettingActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    Button child_btn, mento_btn, expert_btn, register_btn, logout_btn;
    RadioButton one, two, three, four;
    Spinner type, location, sport_type_sp, school_level_sp, expert_type_sp;
    TextView actionbar_title;
    ImageView profile_img;
    DrawerLayout drawer;

    Boolean buttonStateOpen;
    EditText email, pw, pw_ck, name, phone, birth, sport_type, region, school_level, school_name, company, experience_1, experience_2, experience_3;
    RadioButton radio01, radio02, radio03, radio04;
    String token_str, name_str, password_str, profile_image_url, phone_number_str, is_phone_number_public, birthday, is_birthday_public, sport_type_str, expert_type_str, region_str, school_level_str2, school_name_str, company_str, experience_1_str, experience_2_str, experience_3_str, mentor_type_str;

    private PopupWindow popWindow;
    private File file = null;
    private static final int REQUEST_INTERNET = 1;
    public SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();
        Log.d("response" , "token : " + token_str);
        profile_image_url = loginPreferences.getString("profile_image_url","");

        if(loginPreferences.getString("member_type","").equals("mentee")){
            setContentView(R.layout.activity_mypage_setting_child);


            email = (EditText) findViewById(R.id.c_setting_mail);
            name = (EditText) findViewById(R.id.c_setting_name);
            phone = (EditText) findViewById(R.id.c_setting_phone);
            school_name = (EditText) findViewById(R.id.c_setting_belong);
            birth = (EditText) findViewById(R.id.c_setting_birth);
            child_btn = (Button) findViewById(R.id.c_setting_btn);
            pw = (EditText) findViewById(R.id.c_setting_pw);
            pw_ck = (EditText) findViewById(R.id.c_setting_pw2);
            logout_btn = (Button) findViewById(R.id.c_setting_logout);
            //email = (EditText) findViewById(R.id.c_setting_mail);


            String[] str=getResources().getStringArray(R.array.mSpinnerArr);
            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, str);
            sport_type_sp = (Spinner) findViewById(R.id.c_setting_type);
            sport_type_sp.setAdapter(adapter);

            String[] str1=getResources().getStringArray(R.array.locationArrr);
            ArrayAdapter adapter1 = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, str1);
            location = (Spinner) findViewById(R.id.c_setting_location);
            location.setAdapter(adapter1);

            String[] str2=getResources().getStringArray(R.array.schoolArr);
            ArrayAdapter adapter2 = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, str2);
            school_level_sp = (Spinner) findViewById(R.id.c_setting_school);
            school_level_sp.setAdapter(adapter2);

            List<String> strings = Arrays.asList("축구","농구","배구","탁구","테니스","역도","유도","태권도","복싱","레슬링","승마","체조","육상","펜싱","요트","조정","사격","하키","핸드볼","근대3종","철인3종","배드민턴","골프","싸이클","수영","럭비","씨름","보디빌딩","봅슬레이","스키,스노우보드","하키","컬링","빙상","공수도","양궁","볼링","검도","롤러","세팍타크로","소프트볼","스쿼시");

            final String sport_type = loginPreferences.getString("sport_type","");
            int sport_type_int = 0;
            for(int i=0; i<strings.size(); i++){
                if(strings.get(i).equals(sport_type)){
                    sport_type_int = i;
                }
            }

            List<String> strings_school = Arrays.asList("종목선택","초등학교","중학교","고등학교");
            String school_level_str = loginPreferences.getString("school_level","");
            int school_level_int = 0;
            for(int i=0; i<strings_school.size(); i++){
                if(strings_school.get(i).equals(school_level_str)){
                    school_level_int = i;
                }
            }

            List<String> strings_location = Arrays.asList("종목선택","서울","인천","경기도");
            String location_str = loginPreferences.getString("region","");
            int location_int = 0;
            for(int i=0; i<strings_location.size(); i++){
                if(strings_location.get(i).equals(location_str)){
                    location_int = i;
                }
            }


            email.setText(loginPreferences.getString("email",""));
            name.setText(loginPreferences.getString("name",""));
            phone.setText(loginPreferences.getString("phone_number",""));
            birth.setText(loginPreferences.getString("birthday",""));
            school_name.setText(loginPreferences.getString("school_name",""));

            sport_type_sp.setSelection(sport_type_int);
            school_level_sp.setSelection(school_level_int);
            location.setSelection(location_int);
            //  phone.setText(loginPreferences.getString("phone_number",""));




            profile_img = (ImageView) findViewById(R.id.setting_profile_img);

            if(loginPreferences.getString("profile_url","").equals("")|| loginPreferences.getString("profile_url","") == null|| loginPreferences.getString("profile_url","") == " "|| loginPreferences.getString("profile_url","") == "null"|| loginPreferences.getString("profile_url","") == "" || loginPreferences.getString("profile_url","")=="http://" || loginPreferences.getString("profile_url","")=="http://null" || loginPreferences.getString("profile_url","").equals("http://") || loginPreferences.getString("profile_url","").equals("http:/null/")){

            }else{

                new DownLoadImageTask_profile(profile_img).execute("http://" + loginPreferences.getString("profile_url",""));

            }

            child_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
                    loginPrefsEditor = loginPreferences.edit();

                            name_str = name.getText().toString();
                            if(pw.getText().toString().equals("")){
                                password_str = loginPreferences.getString("password","");
                            }else{
                                password_str = pw.getText().toString();
                            }

                            profile_image_url = profile_image_url;
                            phone_number_str = phone.getText().toString();
                            is_phone_number_public = "true";
                            birthday = birth.getText().toString();
                            is_birthday_public = "true";
                            sport_type_str = sport_type_sp.getSelectedItem().toString();
                            expert_type_str = "";
                            region_str = location.getSelectedItem().toString();
                            school_level_str2 = school_level_sp.getSelectedItem().toString();
                            school_name_str = school_name.getText().toString();
                            company_str = "";
                            experience_1_str = "";
                            experience_2_str = "";
                            experience_3_str = "";


                    token_str = loginPreferences.getString("token","");
                    new PutUserInfo().execute(new DBConnector());
               //     new UploadImage().execute(new DBConnector());

                }
            });

            logout_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new DeleteLogout().execute(new DBConnector());
                }
            });

        }else if(loginPreferences.getString("member_type","").equals("mentor")){

            setContentView(R.layout.activity_mypage_setting_mentor);

            email = (EditText) findViewById(R.id.mentor_setting_mail);
            name = (EditText) findViewById(R.id.mentor_setting_name);
            phone = (EditText) findViewById(R.id.mentor_setting_phone);
            birth = (EditText) findViewById(R.id.mentor_setting_birth);
            company = (EditText) findViewById(R.id.mentor_setting_company);
            radio01 = (RadioButton) findViewById(R.id.mentor_setting_radio1);
            radio02 = (RadioButton) findViewById(R.id.mentor_setting_radio2);
            radio03 = (RadioButton) findViewById(R.id.mentor_setting_radio3);
            radio04 = (RadioButton) findViewById(R.id.mentor_setting_radio4);
            pw = (EditText) findViewById(R.id.mentor_setting_pw);
            pw_ck = (EditText) findViewById(R.id.mentor_setting_pw2);
            mento_btn = (Button) findViewById(R.id.mentor_setting_btn);
            logout_btn = (Button) findViewById(R.id.mentor_setting_logout);


            String[] str=getResources().getStringArray(R.array.mSpinnerArr);
            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, str);
            sport_type_sp = (Spinner) findViewById(R.id.mentor_setting_type);
            sport_type_sp.setAdapter(adapter);

            String[] str1=getResources().getStringArray(R.array.locationArrr);
            ArrayAdapter adapter1 = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, str1);
            location = (Spinner) findViewById(R.id.mentor_setting_location);
            location.setAdapter(adapter1);


            List<String> strings = Arrays.asList("축구","농구","배구","탁구","테니스","역도","유도","태권도","복싱","레슬링","승마","체조","육상","펜싱","요트","조정","사격","하키","핸드볼","근대3종","철인3종","배드민턴","골프","싸이클","수영","럭비","씨름","보디빌딩","봅슬레이","스키,스노우보드","하키","컬링","빙상","공수도","양궁","볼링","검도","롤러","세팍타크로","소프트볼","스쿼시");

            String sport_type = loginPreferences.getString("sport_type","");
            int sport_type_int = 0;
            for(int i=0; i<strings.size(); i++){
                if(strings.get(i).equals(sport_type)){
                    sport_type_int = i;
                }
            }


            List<String> strings_location = Arrays.asList("종목선택","서울","인천","경기도");
            String location_str = loginPreferences.getString("region","");
            int location_int = 0;
            for(int i=0; i<strings_location.size(); i++){
                if(strings_location.get(i).equals(location_str)){
                    location_int = i;
                }
            }

            String mento_type = loginPreferences.getString("mentor_type","");
            mentor_type_str = loginPreferences.getString("mentor_type","");

            radio01.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    radio01.setChecked(true);
                    radio02.setChecked(false);
                    radio03.setChecked(false);
                    radio04.setChecked(false);
                    mentor_type_str = "현역선수";
                }
            });

            radio02.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    radio02.setChecked(true);
                    radio01.setChecked(false);
                    radio03.setChecked(false);
                    radio04.setChecked(false);
                    mentor_type_str = "은퇴선수";
                }
            });

            radio03.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    radio03.setChecked(true);
                    radio02.setChecked(false);
                    radio01.setChecked(false);
                    radio04.setChecked(false);
                    mentor_type_str = "감독";
                }
            });

            radio04.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    radio04.setChecked(true);
                    radio02.setChecked(false);
                    radio03.setChecked(false);
                    radio01.setChecked(false);
                    mentor_type_str = "코치";
                }
            });


            if(mento_type.equals("현역선수")){
                radio01.setChecked(true);
                radio02.setChecked(false);
                radio03.setChecked(false);
                radio04.setChecked(false);
            }else if(mento_type.equals("은퇴선수")){
                radio02.setChecked(true);
                radio01.setChecked(false);
                radio03.setChecked(false);
                radio04.setChecked(false);
            }else if(mento_type.equals("감독")){
                radio03.setChecked(true);
                radio02.setChecked(false);
                radio01.setChecked(false);
                radio04.setChecked(false);
            }else if(mento_type.equals("코치")){
                radio04.setChecked(true);
                radio02.setChecked(false);
                radio03.setChecked(false);
                radio01.setChecked(false);
            }

            email.setText(loginPreferences.getString("email",""));
            name.setText(loginPreferences.getString("name",""));
            phone.setText(loginPreferences.getString("phone_number",""));
            birth.setText(loginPreferences.getString("birthday",""));
            company.setText(loginPreferences.getString("company",""));
            sport_type_sp.setSelection(sport_type_int);
            location.setSelection(location_int);
            //  phone.setText(loginPreferences.getString("phone_number",""));




            profile_img = (ImageView) findViewById(R.id.setting_profile_img);

            if(loginPreferences.getString("profile_url","").equals("")|| loginPreferences.getString("profile_url","") == null|| loginPreferences.getString("profile_url","") == " "|| loginPreferences.getString("profile_url","") == "null"|| loginPreferences.getString("profile_url","") == "" || loginPreferences.getString("profile_url","")=="http://" || loginPreferences.getString("profile_url","")=="http://null" || loginPreferences.getString("profile_url","").equals("http://") || loginPreferences.getString("profile_url","").equals("http:/null/")){

            }else{

                new DownLoadImageTask_profile(profile_img).execute("http://" + loginPreferences.getString("profile_url",""));

            }

            mento_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
                    loginPrefsEditor = loginPreferences.edit();

                    name_str = name.getText().toString();
                    if(pw.getText().toString().equals("")){
                        password_str = loginPreferences.getString("password","");
                    }else{
                        password_str = pw.getText().toString();
                    }

                    profile_image_url = profile_image_url;
                    phone_number_str = phone.getText().toString();
                    is_phone_number_public = "true";
                    birthday = birth.getText().toString();
                    is_birthday_public = "true";
                    sport_type_str = sport_type_sp.getSelectedItem().toString();
                    expert_type_str = "";
                    region_str = location.getSelectedItem().toString();
                    school_level_str2 = "";
                    school_name_str = "";

                    company_str = company.getText().toString();
                    experience_1_str = "";
                    experience_2_str = "";
                    experience_3_str = "";


                    token_str = loginPreferences.getString("token","");
                    new PutUserInfo().execute(new DBConnector());
                    //     new UploadImage().execute(new DBConnector());

                }
            });


            logout_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new DeleteLogout().execute(new DBConnector());
                }
            });


        }else if(loginPreferences.getString("member_type","").equals("expert")){
            setContentView(R.layout.activity_mypage_setting_expert);


            email = (EditText) findViewById(R.id.e_setting_mail);
            name = (EditText) findViewById(R.id.e_setting_name);
            phone = (EditText) findViewById(R.id.e_setting_phone);
            birth = (EditText) findViewById(R.id.e_setting_birth);
            company = (EditText) findViewById(R.id.e_setting_company);

            pw = (EditText) findViewById(R.id.e_setting_pw);
            pw_ck = (EditText) findViewById(R.id.e_setting_pw2);
            mento_btn = (Button) findViewById(R.id.e_setting_btn);
            logout_btn = (Button) findViewById(R.id.e_setting_logout);


            logout_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new DeleteLogout().execute(new DBConnector());
                }
            });

            String[] str=getResources().getStringArray(R.array.SportTypeArrr);
            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, str);
            expert_type_sp = (Spinner) findViewById(R.id.e_setting_type);
            expert_type_sp.setAdapter(adapter);



            List<String> strings_expertType = Arrays.asList("종목선택","스포츠 의학","스포츠 심리","스포츠 트레이닝","스포츠 영양","스포츠 재활","스포츠 진로","기타");
            String expert_type = loginPreferences.getString("region","");
            int expert_type_int = 0;
            for(int i=0; i<strings_expertType.size(); i++){
                if(strings_expertType.get(i).equals(expert_type)){
                    expert_type_int = i;
                }
            }



            email.setText(loginPreferences.getString("email",""));
            name.setText(loginPreferences.getString("name",""));
            phone.setText(loginPreferences.getString("phone_number",""));
            birth.setText(loginPreferences.getString("birthday",""));
            company.setText(loginPreferences.getString("company",""));
            expert_type_sp.setSelection(expert_type_int);
            experience_1.setText(loginPreferences.getString("experience_1",""));
            experience_2.setText(loginPreferences.getString("experience_2",""));
            experience_3.setText(loginPreferences.getString("experience_3",""));
          //  location.setSelection(location_int);
            //  phone.setText(loginPreferences.getString("phone_number",""));




            profile_img = (ImageView) findViewById(R.id.setting_profile_img);

            if(loginPreferences.getString("profile_url","").equals("")|| loginPreferences.getString("profile_url","") == null|| loginPreferences.getString("profile_url","") == " "|| loginPreferences.getString("profile_url","") == "null"|| loginPreferences.getString("profile_url","") == "" || loginPreferences.getString("profile_url","")=="http://" || loginPreferences.getString("profile_url","")=="http://null" || loginPreferences.getString("profile_url","").equals("http://") || loginPreferences.getString("profile_url","").equals("http:/null/")){

            }else{

                new DownLoadImageTask_profile(profile_img).execute("http://" + loginPreferences.getString("profile_url",""));

            }


            mento_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
                    loginPrefsEditor = loginPreferences.edit();

                    name_str = name.getText().toString();
                    if(pw.getText().toString().equals("")){
                        password_str = loginPreferences.getString("password","");
                    }else{
                        password_str = pw.getText().toString();
                    }

                    profile_image_url = profile_image_url;
                    phone_number_str = phone.getText().toString();
                    is_phone_number_public = "true";
                    birthday = birth.getText().toString();
                    is_birthday_public = "true";
                    sport_type_str = "";
                    expert_type_str = expert_type_sp.getSelectedItem().toString();
                    region_str = "";
                    school_level_str2 = "";
                    school_name_str = "";

                    company_str = company.getText().toString();
                    experience_1_str = experience_1.getText().toString();
                    experience_2_str = experience_2.getText().toString();
                    experience_3_str = experience_3.getText().toString();


                    token_str = loginPreferences.getString("token","");
                    new PutUserInfo().execute(new DBConnector());
                    //     new UploadImage().execute(new DBConnector());

                }
            });

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

               // Log.d("response", "profile_img L " + profile_img_url);
                if (profile_image_url != "") {

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

            Toast.makeText(getApplicationContext(), name_str+"님 로그아웃 완료!",
                    Toast.LENGTH_LONG).show();

            Intent intent = new Intent(MypageSettingActivity.this, LoginActivity.class);
            finish();
            startActivity(intent);

        }


    }



    private class PutUserInfo extends AsyncTask<DBConnector, Long, JSONObject> {


        @Override
        protected JSONObject doInBackground(DBConnector... params) {

            //it is executed on Background thread
           // Log.d("response" , "update user token: " + token_str.toString());
            return params[0].PutUserInfo(token_str, name_str , password_str,profile_image_url,phone_number_str, is_phone_number_public, birthday, is_birthday_public, sport_type_str, mentor_type_str, expert_type_str, region_str, school_level_str2, school_name_str, company_str, experience_1_str, experience_2_str, experience_3_str);

        }

        @Override
        protected void onPostExecute(final JSONObject jsonObject) {

            settextToAdapter(jsonObject);

        }
    }

    public void settextToAdapter(JSONObject jsonObject) {

//        Log.d("response" , "update user : " + jsonObject.toString());

        if(jsonObject == null){

        }else{

            loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
            loginPrefsEditor = loginPreferences.edit();


            try {
                loginPrefsEditor.putString("name",name_str);
                loginPrefsEditor.putString("phone_number",jsonObject.getString("phone_number").toString());
                loginPrefsEditor.putString("birthday",jsonObject.getString("birthday").toString());
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

            } catch (JSONException e) {
                e.printStackTrace();
            }


            loginPrefsEditor.commit();

            Toast.makeText(getApplicationContext(), name_str+"님 회원정보가 수정되었습니다.",
                    Toast.LENGTH_LONG).show();

            Intent intent = getIntent();
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

            Intent intent = new Intent(MypageSettingActivity.this, MainActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_gallery) {

            Intent intent = new Intent(MypageSettingActivity.this, SubjectFeedActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_slideshow) {

            Intent intent = new Intent(MypageSettingActivity.this, ExpertFeedActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_manage) {

            Intent intent = new Intent(MypageSettingActivity.this, InviteActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_share) {

            Intent intent = new Intent(MypageSettingActivity.this, NoticeActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_send) {

            Intent intent = new Intent(MypageSettingActivity.this, MypageActivity.class);
            startActivity(intent);

        } else if (id == R.id.people) {

            Intent intent = new Intent(MypageSettingActivity.this, MemberActivity.class);
            startActivity(intent);

        }else if (id == R.id.nav_lifesport) {

            Intent intent = new Intent(MypageSettingActivity.this, LifeExpertFeedActivity.class);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
