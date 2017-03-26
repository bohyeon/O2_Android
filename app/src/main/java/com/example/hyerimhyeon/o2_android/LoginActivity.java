package com.example.hyerimhyeon.o2_android;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.DB.DBConnector;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    EditText id_Edit, pw_Edit;
    Button loginBtn;
    CheckBox login_ck;
    String id_str, pw_str;
    String token = "";

    public SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        id_Edit = (EditText) findViewById(R.id.login_id_edit);
        pw_Edit = (EditText) findViewById(R.id.login_pw_edit);
        loginBtn = (Button) findViewById(R.id.login_btn);
        login_ck = (CheckBox) findViewById(R.id.login_cb);


        id_Edit.setNextFocusDownId(R.id.login_id_edit);

        //로그인 유지하기
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();
        saveLogin = loginPreferences.getBoolean("saveLogin", false);


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                id_str = id_Edit.getText().toString().trim();
                pw_str = pw_Edit.getText().toString();

//                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                startActivity(intent);
//                finish();


                // 이메일과 비밀번호 확인하기
                new GetLoginInfoTask().execute(new DBConnector());


            }
        });

    }

    private class GetLoginInfoTask extends AsyncTask<DBConnector, Long, JSONObject> {


        @Override
        protected JSONObject doInBackground(DBConnector... params) {

            //it is executed on Background thread

            return params[0].LoginDBConnector(id_str,pw_str);

        }

        @Override
        protected void onPostExecute(final JSONObject jsonObject) {

            settextToAdapter(jsonObject);

        }
    }

    public void settextToAdapter(JSONObject jsonObject) {

       if(jsonObject == null){
          //로그인 실패
           Toast.makeText(getApplicationContext(), "아이디와 비밀번호를 다시 입력해주세요.",
                   Toast.LENGTH_LONG).show();
       }else{
          //로그인 성공

           try {

               token = ""+jsonObject.getString("token").toString();

           } catch (JSONException e) {
               e.printStackTrace();

           }

           InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
           imm.hideSoftInputFromWindow(id_Edit.getWindowToken(), 0);
           imm.hideSoftInputFromWindow(pw_Edit.getWindowToken(), 1);

           loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
           loginPrefsEditor = loginPreferences.edit();

           loginPrefsEditor.putString("id", id_str);
           loginPrefsEditor.putString("pw", pw_str);
         //  loginPrefsEditor.putString("token", token);

           try {
               Log.d("response" , "token : " + jsonObject.getString("token"));
               loginPrefsEditor.putString("email", jsonObject.getString("email").toString());
               loginPrefsEditor.putString("name", jsonObject.getString("name").toString());
               loginPrefsEditor.putString("id", jsonObject.getString("id").toString());
               loginPrefsEditor.putString("password",pw_str);
               loginPrefsEditor.putString("token", jsonObject.getString("token").toString());
               loginPrefsEditor.putString("phone_number", jsonObject.getString("phone_number").toString());
               loginPrefsEditor.putString("birthday", jsonObject.getString("birthday").toString());
               loginPrefsEditor.putString("profile_url", jsonObject.getString("profile_url").toString());
               loginPrefsEditor.putString("member_type", jsonObject.getString("member_type").toString());
               loginPrefsEditor.putString("sport_type", jsonObject.getString("sport_type").toString());
               loginPrefsEditor.putString("region", jsonObject.getString("region").toString());
               loginPrefsEditor.putString("school_level", jsonObject.getString("school_level").toString());
               loginPrefsEditor.putString("mentor_type", jsonObject.getString("mentor_type").toString());
               loginPrefsEditor.putString("company", jsonObject.getString("company").toString());
               loginPrefsEditor.putString("expert_type", jsonObject.getString("expert_type").toString());
               loginPrefsEditor.putString("experience_1", jsonObject.getString("experience_1").toString());
               loginPrefsEditor.putString("experience_2", jsonObject.getString("experience_2").toString());
               loginPrefsEditor.putString("experience_3", jsonObject.getString("experience_3").toString());

               if(login_ck.isChecked()){
                   // 로그인 유지하기

                   loginPrefsEditor.putBoolean("saveLogin", true);

               }else{

               }

               loginPrefsEditor.commit();


           } catch (JSONException e) {
               e.printStackTrace();
                Log.d("response", "prefs error : " + e.toString());
           }

           Intent intent = new Intent(LoginActivity.this, MainActivity.class);
           intent.putExtra("token",token);
           intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
           startActivityForResult(intent,100);
           finish();

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
