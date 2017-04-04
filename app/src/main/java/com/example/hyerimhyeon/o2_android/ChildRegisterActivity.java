package com.example.hyerimhyeon.o2_android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.DB.DBConnector;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Locale;

public class ChildRegisterActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    Button child_btn, mento_btn, expert_btn, register_btn;
    RadioButton one, two, three, four;
    Spinner type, location, school;
    ImageView profile_img;
    EditText mail, pw, pw_ck, name, phone, birth, type_name, location_name, school_name, shcool_name, school_type;
    String email, password, name_str, password_ck, profile_img_url, phone_str, birth_str, sport_type, region, school_level, schcool_name_str, invite_code;
    String phone_open, birth_open;
    private File file = null;

    public SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private String current = "";
    private String yyyymmdd = "YYYYMMDD";
    private Calendar cal = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_child);

        register_btn = (Button) findViewById(R.id.c_register_btn);
        mail = (EditText) findViewById(R.id.c_register_mail);
        pw = (EditText) findViewById(R.id.c_register_pw);
        pw_ck = (EditText) findViewById(R.id.c_register_pw2);
        name = (EditText) findViewById(R.id.c_register_name);
        phone = (EditText) findViewById(R.id.c_register_phone);
        birth = (EditText) findViewById(R.id.c_register_birth);
        school_name = (EditText) findViewById(R.id.c_register_belong);

        String[] str=getResources().getStringArray(R.array.mSpinnerArr);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, str);
        type = (Spinner) findViewById(R.id.c_register_type);
        type.setAdapter(adapter);

        String[] str1=getResources().getStringArray(R.array.locationArrr);
        ArrayAdapter adapter1 = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, str1);
        location = (Spinner) findViewById(R.id.c_register_location);
        location.setAdapter(adapter1);

        String[] str2=getResources().getStringArray(R.array.schoolArr);
        ArrayAdapter adapter2 = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, str2);
        school = (Spinner) findViewById(R.id.c_register_school);
        school.setAdapter(adapter2);


       // phone.addTextChangedListener(onTextChangedListener());



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

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = mail.getText().toString();
                password = pw.getText().toString();
                password_ck = pw_ck.getText().toString();
                name_str = name.getText().toString();
                phone_str = phone.getText().toString();
                birth_str = birth.getText().toString();
                schcool_name_str = school_name.getText().toString();
                sport_type = type.getSelectedItem().toString();
                region = location.getSelectedItem().toString();
                school_level = school.getSelectedItem().toString();

                loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
                invite_code = loginPreferences.getString("invite_code","");

                birth_open = "true";
                phone_open = "true";

                if(file != null) {

                    if (email != null) {

                        if (password != null) {

                            if (name_str != null) {

                                if (phone_str != null) {

                                    if (birth_str != null) {

                                        if (schcool_name_str != null) {

                                            if (!sport_type.equals("종목선택")) {

                                                if (!region.equals("지역선택")) {

                                                    if (!school_level.equals("학력선택")) {

                                                        if (password.equals(password_ck)) {

                                                            new UploadImage().execute(new DBConnector());


                                                        } else {
                                                            Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다.",
                                                                    Toast.LENGTH_LONG).show();
                                                        }


                                                    } else {
                                                        Toast.makeText(getApplicationContext(), "학력을 선택해주세요.",
                                                                Toast.LENGTH_LONG).show();
                                                    }

                                                } else {
                                                    Toast.makeText(getApplicationContext(), "지역을 선택해주세요.",
                                                            Toast.LENGTH_LONG).show();
                                                }

                                            } else {
                                                Toast.makeText(getApplicationContext(), "종목을 선택해주세요.",
                                                        Toast.LENGTH_LONG).show();
                                            }

                                        } else {
                                            Toast.makeText(getApplicationContext(), "학교이름을 입력해주세요.",
                                                    Toast.LENGTH_LONG).show();
                                        }

                                    } else {
                                        Toast.makeText(getApplicationContext(), "생일을 입력해주세요.",
                                                Toast.LENGTH_LONG).show();
                                    }

                                } else {
                                    Toast.makeText(getApplicationContext(), "핸드폰번호를 입력해주세요.",
                                            Toast.LENGTH_LONG).show();
                                }

                            } else {
                                Toast.makeText(getApplicationContext(), "이름을 입력해주세요.",
                                        Toast.LENGTH_LONG).show();
                            }

                        } else {
                            Toast.makeText(getApplicationContext(), "비밀번호를 입력해주세요.",
                                    Toast.LENGTH_LONG).show();
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), "이메일을 입력해주세요.",
                                Toast.LENGTH_LONG).show();
                    }

                }else {
                    Toast.makeText(getApplicationContext(), "프로필 사진을 올려주세요.",
                            Toast.LENGTH_LONG).show();
                }

//                Intent intent = new Intent(ChildRegisterActivity.this, MainActivity.class);
//                startActivity(intent);
            }
        });

        profile_img = (ImageView) findViewById(R.id.child_profile_img);

        profile_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CameraCropActivity().setListener(new CameraCropActivity.PicturePickListener() {
                    @Override
                    public void isSuccess(File files) {
                        file = files;

                      //  Log.d("response " , "file : " + file.toString());
                        String fname = new File(getFilesDir(), file.toString()).getAbsolutePath();
                        //Bitmap bitmap = BitmapFactory.decodeFile(fname);
                        Uri uri = Uri.fromFile(file);
                        //Log.d("response " , "bitmap : " + uri.toString());
                        profile_img.setImageURI(uri);


                    }

                    @Override
                    public void isFail() {
                        Log.d("camera" , "fail");
                    }
                }).show(getSupportFragmentManager(), null);

            }
        });


    }

    private class UploadImage extends AsyncTask<DBConnector, Long, JSONObject> {


        @Override
        protected JSONObject doInBackground(DBConnector... params) {

            //it is executed on Background thread
            String token = "db4fdaf8d9cb71b454a47c114422e29c4165e203";

            return params[0].UploadImage(file, token);

        }

        @Override
        protected void onPostExecute(final JSONObject jsonObject) {

            settextToAdapter(jsonObject);

        }
    }

    public void settextToAdapter(JSONObject jsonObject) {

        if(jsonObject == null){

        }else{

            profile_img_url = "";
            try {
                profile_img_url = jsonObject.getString("profile_image_url").toString();

                Log.d("response" , "profile_img L " + profile_img_url);
                if(profile_img_url != ""){
                    new RegistMentee().execute(new DBConnector());
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }



        }

    }
    private class RegistMentee extends AsyncTask<DBConnector, Long, JSONObject> {


        @Override
        protected JSONObject doInBackground(DBConnector... params) {

            //it is executed on Background thread

            return params[0].MenteeResgister(email, name_str, password,password_ck, profile_img_url, phone_str, phone_open, birth_str, birth_open, sport_type, region, school_level, schcool_name_str, invite_code);

        }

        @Override
        protected void onPostExecute(final JSONObject jsonObject) {

            settextToAdapter2(jsonObject);

        }
    }

    public void settextToAdapter2(JSONObject jsonObject) {

        Log.d("response ", "user : " + jsonObject.toString());

        if(jsonObject == null){

        }else{

            loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
            loginPrefsEditor = loginPreferences.edit();

            try {
             //   Log.d("response" , "mentee name : " + jsonObject.getString("name"));

//                loginPrefsEditor.putString("email",jsonObject.getString("email").toString());
//                loginPrefsEditor.putString("name",jsonObject.getString("name").toString());
//                loginPrefsEditor.putString("profile_url",jsonObject.getString("profile_url").toString());
//                loginPrefsEditor.putString("phone_number",jsonObject.getString("phone_number").toString());
//                loginPrefsEditor.putString("birthday",jsonObject.getString("birthday").toString());
//                loginPrefsEditor.putString("sport_type",jsonObject.getString("sport_type").toString());
//                loginPrefsEditor.putString("region",jsonObject.getString("phone_number").toString());
//                loginPrefsEditor.putString("school_level",jsonObject.getString("birthday").toString());
//                loginPrefsEditor.putString("school_name",jsonObject.getString("sport_type").toString());
//                loginPrefsEditor.putString("token",jsonObject.getString("token").toString());
                loginPrefsEditor.putString("email",email);
                loginPrefsEditor.putString("id","me");
                loginPrefsEditor.putString("name",name_str);
                loginPrefsEditor.putString("password",password);
                loginPrefsEditor.putString("profile_url",profile_img_url);
                loginPrefsEditor.putString("phone_number",phone_str);
                loginPrefsEditor.putString("birthday",birth_str);
                loginPrefsEditor.putString("sport_type",sport_type);
                loginPrefsEditor.putString("region",region);
                loginPrefsEditor.putString("school_level",school_level);
                loginPrefsEditor.putString("school_name",schcool_name_str);
                loginPrefsEditor.putString("token",jsonObject.getString("token"));
                loginPrefsEditor.putString("member_type","mentee");
                loginPrefsEditor.putString("is_receive_push","true");
                loginPrefsEditor.putString("is_admin", jsonObject.getString("is_admin").toString());
                loginPrefsEditor.putBoolean("saveLogin", true);

                loginPrefsEditor.commit();


                Intent intent = new Intent(ChildRegisterActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

                Toast.makeText(getApplicationContext(), name_str+"님 환영합니다.",
                        Toast.LENGTH_LONG).show();

            } catch (JSONException e) {
                e.printStackTrace();
                if(jsonObject.toString().indexOf("{\"birthday\":[\"Date has wrong format. Use one of these formats instead: YYYY[-MM[-DD]].\"]}") != -1){
                    Toast.makeText(getApplicationContext(), "생년월일 형식을 맞춰서 작성해주세요!",
                            Toast.LENGTH_LONG).show();
                }else if(jsonObject.toString().indexOf("{\"email\":[\"This user has already registered.\"]}") != -1) {
                    Toast.makeText(getApplicationContext(), "이미 가입된 이메일입니다!",
                            Toast.LENGTH_LONG).show();
                }else if(jsonObject.toString().indexOf("{\"email\":[\"Enter a valid email address.\"]}") != -1) {
                    Toast.makeText(getApplicationContext(), "이메일 형식을 맞춰서 작성해주세요!",
                            Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(), jsonObject.toString(),
                            Toast.LENGTH_LONG).show();
                }
            }


        }
    }


        @Override
    public void onBackPressed() {
     finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private TextWatcher onTextChangedListener() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                birth.removeTextChangedListener(this);

                try {
                    String originalString = s.toString();

                    Long longval;
                    if (originalString.contains("-")) {
                        originalString = originalString.replaceAll("-", "");
                    }
                    longval = Long.parseLong(originalString);

                    DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.KOREAN);
                    formatter.applyPattern("####-##-##");
                    String formattedString = formatter.format(longval);

                    //setting text after format to EditText
                    birth.setText(formattedString);
                    birth.setSelection(birth.getText().length());
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }

                birth.addTextChangedListener(this);
            }
        };
    }


    private TextWatcher mDateEntryWatcher = new TextWatcher() {

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
                    int day  = Integer.parseInt(clean.substring(0,2));
                    int mon  = Integer.parseInt(clean.substring(2,4));
                    int year = Integer.parseInt(clean.substring(4,8));

                    if(mon > 12) mon = 12;
                    cal.set(Calendar.MONTH, mon-1);
                    year = (year<1900)?1900:(year>2100)?2100:year;
                    cal.set(Calendar.YEAR, year);
                    // ^ first set year for the line below to work correctly
                    //with leap years - otherwise, date e.g. 29/02/2012
                    //would be automatically corrected to 28/02/2012

                    day = (day > cal.getActualMaximum(Calendar.DATE))? cal.getActualMaximum(Calendar.DATE):day;
                    clean = String.format("%02d%02d%02d",day, mon, year);
                }

                clean = String.format("%s/%s/%s", clean.substring(0, 2),
                        clean.substring(2, 4),
                        clean.substring(4, 8));

                sel = sel < 0 ? 0 : sel;
                current = clean;
                birth.setText(current);
                birth.setSelection(sel < current.length() ? sel : current.length());
            }
        }

        @Override
        public void afterTextChanged(Editable s) {}

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    };

}
