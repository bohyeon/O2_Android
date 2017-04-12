package com.example.hyerimhyeon.o2_android;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
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
import java.util.Calendar;
import java.util.List;

public class MypageSettingActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    Button child_btn, mento_btn, expert_btn, register_btn, logout_btn, delete_btn;
    RadioButton one, two, three, four;
    Spinner type, location, sport_type_sp, school_level_sp, expert_type_sp;
    TextView actionbar_title;
    ImageView profile_img;
    DrawerLayout drawer;

    Boolean buttonStateOpen;
    EditText email, pw, pw_ck, name, phone, birth, sport_type, region, school_level, school_name, company, experience_1, experience_2, experience_3;
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
        email_str = loginPreferences.getString("email","");
        token_str = loginPreferences.getString("token","");
       // Log.d("response" , "token : " + token_str);
        profile_image_url = loginPreferences.getString("profile_image_url","");

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();

//        Log.d("response" , "sport type 0 : " + loginPreferences.getString("member_type","").toString());

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
            noti_ck = (CheckBox) findViewById(R.id.c_setting_noti);
            //email = (EditText) findViewById(R.id.c_setting_mail);
            birth_public = (CheckBox) findViewById(R.id.c_setting_birth_ok);
            phone_public = (CheckBox) findViewById(R.id.c_setting_phone_ok);
            delete_btn = (Button) findViewById(R.id.c_setting_delete);

            delete_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(MypageSettingActivity.this)
                            .setMessage("작성하신 글들은 꿈나무에게 큰 도움이 됩니다! 정말 탈퇴를 하시겠습니까?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    new DeleteUser().execute(new DBConnector());
                                }
                            })
                            .setNegativeButton("No", null)
                            .show();
                }
            });



            if(loginPreferences.getString("is_phone_number_public","").equals("true")){
                phone_public.setChecked(true);
            }else{
                phone_public.setChecked(false);
            }

            if(loginPreferences.getString("is_birthday_public","").equals("true")){
                birth_public.setChecked(true);
            }else{
                birth_public.setChecked(false);
            }

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

            List<String> strings = Arrays.asList("종목선택","축구","야구","농구","배구","탁구","테니스","역도","유도","태권도","복싱","레슬링","승마","체조","육상","펜싱","요트","조정","사격","하키","핸드볼","근대3종","철인3종","배드민턴","골프","싸이클","수영","럭비","씨름","보디빌딩","봅슬레이","스키,스노우보드","하키","컬링","빙상","공수도","양궁","볼링","검도","롤러","세팍타크로","소프트볼","스쿼시","기타");

            final String sport_type = loginPreferences.getString("sport_type","");
            int sport_type_int = 0;
            //Log.d("response" , "sport type 1 : " + sport_type);
            for(int i=0; i<strings.size(); i++){
                if(strings.get(i).equals(sport_type)){

                    sport_type_int = i;
                }
            }

            List<String> strings_school = Arrays.asList("학력선택","초등학교","중학교","고등학교");
            String school_level_str = loginPreferences.getString("school_level","");
            int school_level_int = 0;
            for(int i=0; i<strings_school.size(); i++){
                if(strings_school.get(i).equals(school_level_str)){
                    school_level_int = i;
                }
            }

            List<String> strings_location = Arrays.asList("지역선택","서울","부산","인천","대구","광주","울산","경기도","강원도","충청북도","충청남도","전라남도","전라북도","경상북도","경상남도","제주도");
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

            if(loginPreferences.getString("is_receive_push","").equals("true")){
                noti_ck.setChecked(true);
            }else{
                noti_ck.setChecked(false);
            }




            sport_type_sp.setSelection(sport_type_int);
            school_level_sp.setSelection(school_level_int);
            location.setSelection(location_int);
            //  phone.setText(loginPreferences.getString("phone_number",""));

           // Log.d("response" , "sport type 11 : " + sport_type_sp.getSelectedItem());


            profile_img = (ImageView) findViewById(R.id.setting_profile_img);

            if(loginPreferences.getString("profile_url","").equals("")|| loginPreferences.getString("profile_url","") == null|| loginPreferences.getString("profile_url","") == " "|| loginPreferences.getString("profile_url","") == "null"|| loginPreferences.getString("profile_url","") == "" || loginPreferences.getString("profile_url","")=="http://" || loginPreferences.getString("profile_url","")=="http://null" || loginPreferences.getString("profile_url","").equals("http://") || loginPreferences.getString("profile_url","").equals("http:/null/")){

            }else{

                new DownLoadImageTask_profile(profile_img).execute("http://" + loginPreferences.getString("profile_url",""));

            }

            TextWatcher tw = new TextWatcher() {


                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    if (!s.toString().equals(current)) {
                        String clean = s.toString().replaceAll("[^\\d.]", "");
                        String cleanC = current.replaceAll("[^\\d.]", "");

                        int cl = clean.length();
                        int sel = cl;
                        for (int i = 2; i <= cl && i < 6; i += 2) {
                            sel++;
                        }
                        //Fix for pressing delete next to a forward slash
                        if (clean.equals(cleanC)) sel--;

                        if (clean.length() < 8){
                            clean = clean + yyyymmdd.substring(clean.length());
                        }else{
                            //This part makes sure that when we finish entering numbers
                            //the date is correct, fixing it otherwise
                            int day  = Integer.parseInt(clean.substring(0,4));
                            int mon  = Integer.parseInt(clean.substring(4,6));
                            int year = Integer.parseInt(clean.substring(6,8));

                            if(mon > 12) mon = 12;
                            cal.set(Calendar.MONTH, mon-1);
                            year = (year<1900)?1900:(year>2100)?2100:year;
                            cal.set(Calendar.YEAR, year);
                            // ^ first set year for the line below to work correctly
                            //with leap years - otherwise, date e.g. 29/02/2012
                            //would be automatically corrected to 28/02/2012

                            day = (day > cal.getActualMaximum(Calendar.DATE))? cal.getActualMaximum(Calendar.DATE):day;
                            //  clean = String.format("%02d%02d%02d",day, mon, year);
                        }

                        clean = String.format("%s-%s-%s", clean.substring(0, 4),
                                clean.substring(4, 6),
                                clean.substring(6, 8));

                        sel = sel < 0 ? 0 : sel;
                        current = clean;
                        birth.setText(current);
                        birth.setSelection(sel < current.length() ? sel : current.length());
                    }

                }

                @Override
                public void afterTextChanged(Editable s) {

                }

            };


            phone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
            birth.addTextChangedListener(tw);

            child_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
                    loginPrefsEditor = loginPreferences.edit();

                            name_str = name.getText().toString();
                            if(pw_ck.getText().toString().equals("")){
                                password_str = loginPreferences.getString("pw","").trim();
                            }else{
                                password_str = pw_ck.getText().toString().trim();
                            }


                            profile_image_url = profile_image_url;
                            phone_number_str = phone.getText().toString();
                            is_phone_number_public = phone_public.isChecked()+"";
                            if(is_phone_number_public.equals("true")){
                                is_phone_number_public = "True";
                            }else{
                                is_phone_number_public = "False";
                            }

                            birthday = birth.getText().toString();
                            is_birthday_public = birth_public.isChecked()+"";
                            if(is_birthday_public.equals("true")){
                                is_birthday_public = "True";
                            }else{
                                is_birthday_public = "False";
                            }


                            sport_type_str = sport_type_sp.getSelectedItem().toString();
                            expert_type_str = "-";
                            region_str = location.getSelectedItem().toString();
                            school_level_str2 = school_level_sp.getSelectedItem().toString();
                            school_name_str = school_name.getText().toString();
                            company_str = "-";
                            experience_1_str = "-";
                            experience_2_str = "-";
                            experience_3_str = "-";

                            if(noti_ck.isChecked()){
                                noti_bool = "True";
                            }else{
                                noti_bool = "False";
                            }

                    token_str = loginPreferences.getString("token","");
                    if(file == null){
                        new PutUserInfo().execute(new DBConnector());
                    }else if(file != null){
                        Log.d("response" , "file mypage : " + file);
                        new UploadImage().execute(new DBConnector());
                    }

               //     new UploadImage().execute(new DBConnector());

                }
            });

            logout_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  //  Toast.makeText(getApplicationContext(), name_str+"님 로그아웃 완료!",
                   //         Toast.LENGTH_LONG).show();
                    new DeleteLogout().execute(new DBConnector());
                }
            });

            profile_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new CameraCropActivity().setListener(new CameraCropActivity.PicturePickListener() {
                        @Override
                        public void isSuccess(File files) {
                            file = files;
                            String fname = new File(getFilesDir(), file.toString()).getAbsolutePath();
                            Uri uri = Uri.fromFile(file);
                            profile_img.setImageURI(uri);
                        }

                        @Override
                        public void isFail() {
                            Log.d("camera" , "fail");
                        }
                    }).show(getSupportFragmentManager(), null);
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
            profile_img = (ImageView) findViewById(R.id.mentor_setting_profile_img);
            noti_ck = (CheckBox) findViewById(R.id.mentor_setting_noti);
            birth_public = (CheckBox) findViewById(R.id.mentor_setting_birth_ok);
            phone_public = (CheckBox) findViewById(R.id.mentor_setting_phone_ok);
            delete_btn = (Button) findViewById(R.id.mentor_setting_delete);

            delete_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(MypageSettingActivity.this)
                            .setMessage("작성하신 글들은 꿈나무에게 큰 도움이 됩니다! 정말 탈퇴를 하시겠습니까?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    new DeleteUser().execute(new DBConnector());
                                }
                            })
                            .setNegativeButton("No", null)
                            .show();
                }
            });


            if(loginPreferences.getString("is_phone_number_public","").equals("true")){
                phone_public.setChecked(true);
            }else{
                phone_public.setChecked(false);
            }

            if(loginPreferences.getString("is_birthday_public","").equals("true")){
                birth_public.setChecked(true);
            }else{
                birth_public.setChecked(false);
            }

            birth_public = (CheckBox) findViewById(R.id.mentor_setting_birth_ok);
            phone_public = (CheckBox) findViewById(R.id.mentor_setting_phone_ok);

            String[] str=getResources().getStringArray(R.array.mSpinnerArr);
            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, str);
            sport_type_sp = (Spinner) findViewById(R.id.mentor_setting_type);
            sport_type_sp.setAdapter(adapter);

            String[] str1=getResources().getStringArray(R.array.locationArrr);
            ArrayAdapter adapter1 = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, str1);
            location = (Spinner) findViewById(R.id.mentor_setting_location);
            location.setAdapter(adapter1);


            List<String> strings = Arrays.asList("종목선택","축구","야구", "농구","배구","탁구","테니스","역도","유도","태권도","복싱","레슬링","승마","체조","육상","펜싱","요트","조정","사격","하키","핸드볼","근대3종","철인3종","배드민턴","골프","싸이클","수영","럭비","씨름","보디빌딩","봅슬레이","스키,스노우보드","하키","컬링","빙상","공수도","양궁","볼링","검도","롤러","세팍타크로","소프트볼","스쿼시","기타");

            String sport_type = loginPreferences.getString("sport_type","");
            int sport_type_int = 0;
            for(int i=0; i<strings.size(); i++){
                if(strings.get(i).equals(sport_type)){
                    sport_type_int = i;
                }
            }


            List<String> strings_location = Arrays.asList("지역선택","서울","부산","인천","대구","광주","울산","경기도","강원도","충청북도","충청남도","전라남도","전라북도","경상북도","경상남도","제주도");
            String location_str = loginPreferences.getString("region","");
            int location_int = 0;
            for(int i=0; i<strings_location.size(); i++){
                if(strings_location.get(i).equals(location_str)){
                    location_int = i;
                }
            }

            if(loginPreferences.getString("is_receive_push","").equals("true")){
                noti_ck.setChecked(true);
            }else{
                noti_ck.setChecked(false);
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



            if(loginPreferences.getString("profile_url","").equals("")|| loginPreferences.getString("profile_url","") == null|| loginPreferences.getString("profile_url","") == " "|| loginPreferences.getString("profile_url","") == "null"|| loginPreferences.getString("profile_url","") == "" || loginPreferences.getString("profile_url","")=="http://" || loginPreferences.getString("profile_url","")=="http://null" || loginPreferences.getString("profile_url","").equals("http://") || loginPreferences.getString("profile_url","").equals("http:/null/")){

            }else{

                new DownLoadImageTask_profile(profile_img).execute("http://" + loginPreferences.getString("profile_url",""));

            }
            TextWatcher tw = new TextWatcher() {


                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    if (!s.toString().equals(current)) {
                        String clean = s.toString().replaceAll("[^\\d.]", "");
                        String cleanC = current.replaceAll("[^\\d.]", "");

                        int cl = clean.length();
                        int sel = cl;
                        for (int i = 2; i <= cl && i < 6; i += 2) {
                            sel++;
                        }
                        //Fix for pressing delete next to a forward slash
                        if (clean.equals(cleanC)) sel--;

                        if (clean.length() < 8){
                            clean = clean + yyyymmdd.substring(clean.length());
                        }else{
                            //This part makes sure that when we finish entering numbers
                            //the date is correct, fixing it otherwise
                            int day  = Integer.parseInt(clean.substring(0,4));
                            int mon  = Integer.parseInt(clean.substring(4,6));
                            int year = Integer.parseInt(clean.substring(6,8));

                            if(mon > 12) mon = 12;
                            cal.set(Calendar.MONTH, mon-1);
                            year = (year<1900)?1900:(year>2100)?2100:year;
                            cal.set(Calendar.YEAR, year);
                            // ^ first set year for the line below to work correctly
                            //with leap years - otherwise, date e.g. 29/02/2012
                            //would be automatically corrected to 28/02/2012

                            day = (day > cal.getActualMaximum(Calendar.DATE))? cal.getActualMaximum(Calendar.DATE):day;
                            //  clean = String.format("%02d%02d%02d",day, mon, year);
                        }

                        clean = String.format("%s-%s-%s", clean.substring(0, 4),
                                clean.substring(4, 6),
                                clean.substring(6, 8));

                        sel = sel < 0 ? 0 : sel;
                        current = clean;
                        birth.setText(current);
                        birth.setSelection(sel < current.length() ? sel : current.length());
                    }

                }

                @Override
                public void afterTextChanged(Editable s) {

                }

            };


            phone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
            birth.addTextChangedListener(tw);


            mento_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                 //   Log.d("response" , "sport type 2 : " + sport_type_sp.getSelectedItem().toString());

                    loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
                    loginPrefsEditor = loginPreferences.edit();

                    name_str = name.getText().toString();
                    if(pw_ck.getText().toString().equals("")){
                        password_str = loginPreferences.getString("pw","").trim();
                    }else{
                        password_str = pw_ck.getText().toString().trim();
                    }

                  //  Log.d("response" , "password : " + password_str);

                    profile_image_url = profile_image_url;
                    phone_number_str = phone.getText().toString();
                    is_phone_number_public = phone_public.isChecked()+"";
                    if(is_phone_number_public.equals("true")){
                        is_phone_number_public = "True";
                    }else{
                        is_phone_number_public = "False";
                    }

                    birthday = birth.getText().toString();
                    is_birthday_public = birth_public.isChecked()+"";
                    if(is_birthday_public.equals("true")){
                        is_birthday_public = "True";
                    }else{
                        is_birthday_public = "False";
                    }
                    sport_type_str = sport_type_sp.getSelectedItem().toString();

                    expert_type_str = "-";
                    region_str = location.getSelectedItem().toString();
                    school_level_str2 = "-";
                    school_name_str = "-";

                    company_str = company.getText().toString();
                    experience_1_str = "-";
                    experience_2_str = "-";
                    experience_3_str = "-";

                    if(noti_ck.isChecked()){
                        noti_bool = "True";
                    }else{
                        noti_bool = "False";
                    }

                    token_str = loginPreferences.getString("token","");
                   // new PutUserInfo().execute(new DBConnector());
                    if(file == null){
                        new PutUserInfo().execute(new DBConnector());
                    }else if(file != null){
                        Log.d("response" , "file mypage : " + file);
                        new UploadImage().execute(new DBConnector());
                    }

                }
            });


            profile_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("response" , "mypage img :  ");
                    new CameraCropActivity().setListener(new CameraCropActivity.PicturePickListener() {
                        @Override
                        public void isSuccess(File files) {
                            file = files;
                            String fname = new File(getFilesDir(), file.toString()).getAbsolutePath();
                            Uri uri = Uri.fromFile(file);
                            profile_img.setImageURI(uri);
                        }

                        @Override
                        public void isFail() {
                            Log.d("camera" , "fail");
                        }
                    }).show(getSupportFragmentManager(), null);
                }
            });


            logout_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(getApplicationContext(), name_str+"님 로그아웃 완료!",
//                            Toast.LENGTH_LONG).show();
                    new DeleteLogout().execute(new DBConnector());
                }
            });


        }else {
            setContentView(R.layout.activity_mypage_setting_expert);


            email = (EditText) findViewById(R.id.e_setting_mail);
            name = (EditText) findViewById(R.id.e_setting_name);
            phone = (EditText) findViewById(R.id.e_setting_phone);
            birth = (EditText) findViewById(R.id.e_setting_birth);
           // company = (EditText) findViewById(R.id.e_setting_company);

            pw = (EditText) findViewById(R.id.e_setting_pw);
            pw_ck = (EditText) findViewById(R.id.e_setting_pw2);
            mento_btn = (Button) findViewById(R.id.e_setting_btn);
            logout_btn = (Button) findViewById(R.id.e_setting_logout);
            experience_1 = (EditText) findViewById(R.id.e_setting_experience01);
            experience_2 = (EditText) findViewById(R.id.e_setting_experience02);
            experience_3 = (EditText) findViewById(R.id.e_setting_experience03);
            noti_ck = (CheckBox) findViewById(R.id.expert_setting_noti);

            birth_public = (CheckBox) findViewById(R.id.e_setting_birth_ok);
            phone_public = (CheckBox) findViewById(R.id.e_setting_phone_ok);

            delete_btn = (Button) findViewById(R.id.e_setting_delete);


            delete_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(MypageSettingActivity.this)
                            .setMessage("작성하신 글들은 꿈나무에게 큰 도움이 됩니다! 정말 탈퇴를 하시겠습니까?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    new DeleteUser().execute(new DBConnector());
                                }
                            })
                            .setNegativeButton("No", null)
                            .show();
                }
            });


            if(loginPreferences.getString("is_phone_number_public","").equals("true")){
                phone_public.setChecked(true);
            }else{
                phone_public.setChecked(false);
            }

            if(loginPreferences.getString("is_birthday_public","").equals("true")){
                birth_public.setChecked(true);
            }else{
                birth_public.setChecked(false);
            }

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


            List<String> strings_expertType = Arrays.asList("분야선택","스포츠 의학","스포츠 심리","스포츠 트레이닝","스포츠 영양","스포츠 재활","스포츠 진로","기타");
            String expert_type = loginPreferences.getString("expert_type","");

            int expert_type_int = 7;
            for(int i=0; i<strings_expertType.size(); i++){
                if(strings_expertType.get(i).equals(expert_type)){
                    expert_type_int = i;
                }
            }
            //Log.d("response","expert_type : " + expert_type_int);
           // Log.d("response","expert log : " + loginPreferences.getString("company",""));

            email.setText(loginPreferences.getString("email",""));
            name.setText(loginPreferences.getString("name",""));
            phone.setText(loginPreferences.getString("phone_number",""));
            birth.setText(loginPreferences.getString("birthday",""));
         //   company.setText(loginPreferences.getString("company",""));
            expert_type_sp.setSelection(expert_type_int);
            experience_1.setText(loginPreferences.getString("experience_1",""));
            experience_2.setText(loginPreferences.getString("experience_2",""));
            experience_3.setText(loginPreferences.getString("experience_3",""));
          //  location.setSelection(location_int);
            //  phone.setText(loginPreferences.getString("phone_number",""));


            TextWatcher tw = new TextWatcher() {


                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    if (!s.toString().equals(current)) {
                        String clean = s.toString().replaceAll("[^\\d.]", "");
                        String cleanC = current.replaceAll("[^\\d.]", "");

                        int cl = clean.length();
                        int sel = cl;
                        for (int i = 2; i <= cl && i < 6; i += 2) {
                            sel++;
                        }
                        //Fix for pressing delete next to a forward slash
                        if (clean.equals(cleanC)) sel--;

                        if (clean.length() < 8){
                            clean = clean + yyyymmdd.substring(clean.length());
                        }else{
                            //This part makes sure that when we finish entering numbers
                            //the date is correct, fixing it otherwise
                            int day  = Integer.parseInt(clean.substring(0,4));
                            int mon  = Integer.parseInt(clean.substring(4,6));
                            int year = Integer.parseInt(clean.substring(6,8));

                            if(mon > 12) mon = 12;
                            cal.set(Calendar.MONTH, mon-1);
                            year = (year<1900)?1900:(year>2100)?2100:year;
                            cal.set(Calendar.YEAR, year);
                            // ^ first set year for the line below to work correctly
                            //with leap years - otherwise, date e.g. 29/02/2012
                            //would be automatically corrected to 28/02/2012

                            day = (day > cal.getActualMaximum(Calendar.DATE))? cal.getActualMaximum(Calendar.DATE):day;
                            //  clean = String.format("%02d%02d%02d",day, mon, year);
                        }

                        clean = String.format("%s-%s-%s", clean.substring(0, 4),
                                clean.substring(4, 6),
                                clean.substring(6, 8));

                        sel = sel < 0 ? 0 : sel;
                        current = clean;
                        birth.setText(current);
                        birth.setSelection(sel < current.length() ? sel : current.length());
                    }

                }

                @Override
                public void afterTextChanged(Editable s) {

                }

            };

            phone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
            birth.addTextChangedListener(tw);

            if(loginPreferences.getString("is_receive_push","").equals("true")){
                noti_ck.setChecked(true);
            }else{
                noti_ck.setChecked(false);
            }


            profile_img = (ImageView) findViewById(R.id.setting_profile_img);

            if(loginPreferences.getString("profile_url","").equals("")|| loginPreferences.getString("profile_url","") == null|| loginPreferences.getString("profile_url","") == " "|| loginPreferences.getString("profile_url","") == "null"|| loginPreferences.getString("profile_url","") == "" || loginPreferences.getString("profile_url","")=="http://" || loginPreferences.getString("profile_url","")=="http://null" || loginPreferences.getString("profile_url","").equals("http://") || loginPreferences.getString("profile_url","").equals("http:/null/")){

            }else{

                new DownLoadImageTask_profile(profile_img).execute("http://" + loginPreferences.getString("profile_url",""));

            }

            profile_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new CameraCropActivity().setListener(new CameraCropActivity.PicturePickListener() {
                        @Override
                        public void isSuccess(File files) {
                            file = files;
                            String fname = new File(getFilesDir(), file.toString()).getAbsolutePath();
                            Uri uri = Uri.fromFile(file);
                            profile_img.setImageURI(uri);
                        }

                        @Override
                        public void isFail() {
                            Log.d("camera" , "fail");
                        }
                    }).show(getSupportFragmentManager(), null);
                }
            });


            mento_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
                    loginPrefsEditor = loginPreferences.edit();

                    name_str = name.getText().toString();
                    if(pw_ck.getText().toString().equals("")){
                        password_str = loginPreferences.getString("pw","").trim();
                    }else{
                        password_str = pw_ck.getText().toString().trim();
                    }


                    profile_image_url = profile_image_url;
                    phone_number_str = phone.getText().toString();
                    is_phone_number_public = phone_public.isChecked()+"";
                    if(is_phone_number_public.equals("true")){
                        is_phone_number_public = "True";
                    }else{
                        is_phone_number_public = "False";
                    }
                    birthday = birth.getText().toString();
                    is_birthday_public = birth_public.isChecked()+"";
                    if(is_birthday_public.equals("true")){
                        is_birthday_public = "True";
                    }else{
                        is_birthday_public = "False";
                    }
                    sport_type_str = "-";
                    expert_type_str = expert_type_sp.getSelectedItem().toString();
                    region_str = "-";
                    school_level_str2 = "-";
                    school_name_str = "-";

                 //   company_str = company.getText().toString();
                    experience_1_str = experience_1.getText().toString();
                    experience_2_str = experience_2.getText().toString();
                    experience_3_str = experience_3.getText().toString();

                    if(noti_ck.isChecked()){
                        noti_bool = "True";
                    }else{
                        noti_bool = "False";
                    }

                    token_str = loginPreferences.getString("token","");

                    if(file == null){
                        new PutUserInfo().execute(new DBConnector());
                    }else if(file != null){
                      //  Log.d("response" , "file mypage : " + file);
                        new UploadImage().execute(new DBConnector());
                    }


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

                //Log.d("response", "profile_img u " + jsonObject);
                if (profile_image_url != "") {
                    loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
                    loginPrefsEditor = loginPreferences.edit();
                    String img_url = jsonObject.getString("profile_image_url").replace("\\/","\\");
                    //Log.d("response", "profile_img u " + img_url);
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


            Intent intent = new Intent(MypageSettingActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            finish();
            startActivity(intent);

        }


    }

    private class DeleteUser extends AsyncTask<DBConnector, Long, Integer> {


        @Override
        protected Integer doInBackground(DBConnector... params) {

            //it is executed on Background thread
            //   String token = "db4fdaf8d9cb71b454a47c114422e29c4165e203";

            return params[0].DeleteUser(token_str);

        }

        @Override
        protected void onPostExecute(final Integer code) {

            settextToAdapter_logout_deleteuser(code);

        }
    }

    public void settextToAdapter_logout_deleteuser(Integer code) {

        if (code == null) {

        } else {

            if(code == 204){

                loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
                loginPrefsEditor = loginPreferences.edit();

                loginPrefsEditor.clear();
                loginPrefsEditor.commit();

                Toast.makeText(getApplicationContext(), "탈퇴가 완료되었습니다.",
                        Toast.LENGTH_LONG).show();

                Intent intent = new Intent(MypageSettingActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                //finish();
                startActivity(intent);

            }

        }


    }



    private class PutUserInfo extends AsyncTask<DBConnector, Long, JSONObject> {


        @Override
        protected JSONObject doInBackground(DBConnector... params) {

            //it is executed on Background thread
           // Log.d("response" , "update user token: " + token_str.toString());
           // Log.d("response","password1 : "+ password_str);
            return params[0].PutUserInfo(token_str, name_str , password_str,profile_image_url,phone_number_str, is_phone_number_public, birthday, is_birthday_public, sport_type_str, mentor_type_str, expert_type_str, region_str, school_level_str2, school_name_str, company_str, experience_1_str, experience_2_str, experience_3_str, noti_bool);

        }

        @Override
        protected void onPostExecute(final JSONObject jsonObject) {

            settextToAdapter(jsonObject);

        }
    }

    public void settextToAdapter(JSONObject jsonObject) {

//        Log.d("response" , "update user : " + jsonObject.toString());
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
                loginPrefsEditor.putString("pw",password_str);
                loginPrefsEditor.putString("birthday",jsonObject.getString("birthday").toString());
                loginPrefsEditor.putString("is_phone_number_public", jsonObject.getString("is_phone_number_public").toString());
                loginPrefsEditor.putString("is_birthday_public", jsonObject.getString("is_birthday_public").toString());
                loginPrefsEditor.putString("sport_type",jsonObject.getString("sport_type").toString());
                loginPrefsEditor.putString("expert_type",jsonObject.getString("expert_type").toString());
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

            Intent intent = new Intent(this, MypageSettingActivity.class);
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
