package com.o2_company.hyerimhyeon.o2_android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.o2_company.DB.DBConnector;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.Calendar;

public class ExpertRegisterActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    Button child_btn, mento_btn, expert_btn, register_btn;
    RadioButton one, two, three, four;
    Spinner type, location, school;
    private File file = null;
    ScrollView agree01_scroll, top_scroll, agree02_scroll;
    ImageView profile_img;
    EditText mail, pw, pw_ck, name, phone, birth, type_name, location_name, belong_name, shcool_name, school_type, experience_1Et, experience_2Et, experience_3Et, company_et;
    String email, password, name_str, password_ck, profile_img_url, phone_str, birth_str, sport_type, region,  expert_type, company, experience_1, experience_2, experience_3, invite_code;
    String phone_open, birth_open, id;
    TextView agree_tv, userAgree_tv;
    CheckBox agree01_ck, agree02_ck;
    ExpertRegisterActivity expertRegisterActivity;


    public SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private String current = "";
    private String yyyymmdd = "YYYYMMDD";
    private Calendar cal = Calendar.getInstance();
    private static final int REQUEST_EXTERNAL_STORAGE = 2;
    private static final int REQUEST_CAMERA = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_expert);

        register_btn = (Button) findViewById(R.id.e_register_btn);
        mail = (EditText) findViewById(R.id.e_register_mail);
        pw = (EditText) findViewById(R.id.e_register_pw);
        pw_ck = (EditText) findViewById(R.id.e_register_pw2);
        name = (EditText) findViewById(R.id.e_register_name);
        phone = (EditText) findViewById(R.id.e_register_phone);
        birth = (EditText) findViewById(R.id.e_register_birth);
        company_et = (EditText) findViewById(R.id.e_register_belong);
        experience_1Et = (EditText) findViewById(R.id.e_register_experience01);
        experience_2Et = (EditText) findViewById(R.id.e_register_experience02);
        experience_3Et = (EditText) findViewById(R.id.e_register_experience03);
        agree_tv = (TextView) findViewById(R.id.e_register_agree);
        agree01_scroll = (ScrollView) findViewById(R.id.e_register_scroll01);
        userAgree_tv = (TextView) findViewById(R.id.e_register_agree02);
        agree02_scroll = (ScrollView) findViewById(R.id.e_register_scroll02);
        top_scroll = (ScrollView) findViewById(R.id.e_register_top_scroll);
        agree01_ck = (CheckBox) findViewById(R.id.e_agree_ck);
        agree02_ck = (CheckBox) findViewById(R.id.e_agree_ck02);

        //  agree_tv.setMovementMethod(new ScrollingMovementMethod());

        expertRegisterActivity = new ExpertRegisterActivity();

        top_scroll.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                //  Log.v(TAG,”PARENT TOUCH”);
                findViewById(R.id.e_register_scroll01).getParent().requestDisallowInterceptTouchEvent(false);
                return false;
            }
        });

        agree01_scroll.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event)
            {
                // Log.v(TAG,”CHILD TOUCH”);
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        agree02_scroll.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event)
            {
                // Log.v(TAG,”CHILD TOUCH”);
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });


        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();


        String[] str=getResources().getStringArray(R.array.SportTypeArrr);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, str);
        type = (Spinner) findViewById(R.id.e_register_type);
        type.setAdapter(adapter);


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
                expert_type = type.getSelectedItem().toString();
                company = company_et.getText().toString();
               // region = location.getSelectedItem().toString();
                experience_1 = experience_1Et.getText().toString();
                experience_2 = experience_2Et.getText().toString();
                experience_3 = experience_3Et.getText().toString();


                invite_code = loginPreferences.getString("invite_code","");
                birth_open = "true";
                phone_open = "true";

                if(file != null) {

                    if (email != null) {

                        if (password != null) {

                            if (name_str != null) {

                                if (phone_str != null) {

                                    if (birth_str != null) {

                                            if (!expert_type.equals("분야선택")) {

                                                if (experience_1 != null) {


                                                    if (experience_2 != null) {

                                                        if (experience_3 != null) {

                                                            if (company != null) {

                                                                if (password.equals(password_ck)) {

                                                                    if (agree01_ck.isChecked() == true) {

                                                                        if (agree02_ck.isChecked() == true) {

                                                                            new UploadImage().execute(new DBConnector());


                                                                        } else {
                                                                            Toast.makeText(getApplicationContext(), "개인정보취급방침에 동의해주세요.",
                                                                                    Toast.LENGTH_LONG).show();
                                                                        }



                                                                    } else {
                                                                        Toast.makeText(getApplicationContext(), "이용약관에 동의해주세요.",
                                                                                Toast.LENGTH_LONG).show();
                                                                    }




                                                                }else{
                                                                    Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다.",
                                                                            Toast.LENGTH_LONG).show();
                                                                }




                                                            }else{
                                                                Toast.makeText(getApplicationContext(), "소속을 입력해주세요.",
                                                                        Toast.LENGTH_LONG).show();
                                                            }


                                                        }else{
                                                            Toast.makeText(getApplicationContext(), "이력을 선택해주세요.",
                                                                    Toast.LENGTH_LONG).show();
                                                        }

                                                    }else{
                                                        Toast.makeText(getApplicationContext(), "이력을 선택해주세요.",
                                                                Toast.LENGTH_LONG).show();
                                                    }

                                                }else{
                                                    Toast.makeText(getApplicationContext(), "이력을 선택해주세요.",
                                                            Toast.LENGTH_LONG).show();
                                                }


                                            } else {
                                                Toast.makeText(getApplicationContext(), "종목을 선택해주세요.",
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
            }
        });

        profile_img = (ImageView) findViewById(R.id.expert_profile_img);

        profile_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    new CameraCropActivity().setListener(new CameraCropActivity.PicturePickListener() {
                        @Override
                        public void isSuccess(File files) {
                            file = files;
                            Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
                            profile_img.setImageBitmap(bitmap);
                        }

                        @Override
                        public void isFail() {
                            Log.d("camera", "fail");
                        }
                    }).show(getSupportFragmentManager(), null);



            }
        });

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CAMERA:
                for (int i = 0; i < permissions.length; i++) {
                    String permission = permissions[i];
                    int grantResult = grantResults[i];
                    if (permission.equals(android.Manifest.permission.CAMERA)) {
                        if (grantResult == PackageManager.PERMISSION_GRANTED) {
                            Log.d("response", "camera permission authorized2");

                        } else {
                            Log.d("response", "camera permission denied");
                            //  resultText.setText("camera permission denied");
                        }
                        break;
                    }
                }
                break;
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
                    new ExpertRegister().execute(new DBConnector());
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }



        }

    }
    private class ExpertRegister extends AsyncTask<DBConnector, Long, JSONObject> {


        @Override
        protected JSONObject doInBackground(DBConnector... params) {

            //it is executed on Background thread

            return params[0].ExpertResgister(email, name_str, password,password_ck, profile_img_url, phone_str, phone_open, birth_str, birth_open, expert_type, experience_1,experience_2 ,experience_3, invite_code, company);

        }

        @Override
        protected void onPostExecute(final JSONObject jsonObject) {

            settextToAdapter2(jsonObject);

        }
    }

    public void settextToAdapter2(JSONObject jsonObject) {

    //    Log.d("response ", "user : " + jsonObject.toString());

        Boolean ok = false;
        if(jsonObject == null){

        }else{

            try {
           //     Log.d("response" , "expert name : " + jsonObject.getString("name"));

//                loginPrefsEditor.putString("email",jsonObject.getString("email").toString());
//                loginPrefsEditor.putString("name",jsonObject.getString("name").toString());
//                loginPrefsEditor.putString("profile_url",jsonObject.getString("profile_url").toString());
//                loginPrefsEditor.putString("phone_number",jsonObject.getString("phone_number").toString());
//                loginPrefsEditor.putString("birthday",jsonObject.getString("birthday").toString());
//                loginPrefsEditor.putString("sport_type",jsonObject.getString("sport_type").toString());
//                loginPrefsEditor.putString("member_type",jsonObject.getString("member_type").toString());
//                loginPrefsEditor.putString("region",jsonObject.getString("phone_number").toString());
//                loginPrefsEditor.putString("school_level",jsonObject.getString("birthday").toString());
//                loginPrefsEditor.putString("school_name",jsonObject.getString("sport_type").toString());
//                loginPrefsEditor.putString("token",jsonObject.getString("token").toString());

                      //  Log.d("response","register token2 : " + jsonObject.getString("token"));
                        loginPrefsEditor.putString("email",email);
                       loginPrefsEditor.putString("pw",password);
                        loginPrefsEditor.putString("id",jsonObject.getString("id").toString());
                        loginPrefsEditor.putString("name",name_str);
                        loginPrefsEditor.putString("password",password);
                        loginPrefsEditor.putString("profile_url",profile_img_url);
                        loginPrefsEditor.putString("phone_number",phone_str);
                        loginPrefsEditor.putString("birthday",birth_str);
                        loginPrefsEditor.putString("is_phone_number_public","false");
                        loginPrefsEditor.putString("is_birthday_public", "false");
                        loginPrefsEditor.putString("sport_type",sport_type);
                        loginPrefsEditor.putString("region",region);
                        loginPrefsEditor.putString("expert_type",expert_type);
                        loginPrefsEditor.putString("experience_1",experience_1);
                        loginPrefsEditor.putString("experience_2",experience_2);
                        loginPrefsEditor.putString("experience_3",experience_3);
                        loginPrefsEditor.putString("company",company);
                        loginPrefsEditor.putString("token",jsonObject.getString("token"));
                        loginPrefsEditor.putString("member_type","expert");
                        loginPrefsEditor.putString("is_receive_push","true");
                        loginPrefsEditor.putString("is_admin", jsonObject.getString("is_admin").toString());
                        loginPrefsEditor.putBoolean("saveLogin", true);
                        loginPrefsEditor.commit();

                        Toast.makeText(getApplicationContext(), name_str + "님 환영합니다.",
                                Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(ExpertRegisterActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                         startActivity(intent);


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
                        Toast.makeText(getApplicationContext(), e.toString(),
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
}
