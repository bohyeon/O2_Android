package com.o2_company.hyerimhyeon.o2_android;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.o2_company.DB.DBConnector;

import org.json.JSONException;
import org.json.JSONObject;

public class SplashActivity extends Activity
        implements NavigationView.OnNavigationItemSelectedListener {

    Button loginBtn, registBtn;
    String code_str;
    private CustomDialog mCustomDialog;


    public SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();
        //loginPrefsEditor.clear();
        //loginPrefsEditor.commit();

        String id = null;
        id = loginPreferences.getString("id", "");
       // Log.d("response" , "login : " + id);

        if(id== null){

        }else{
            if (loginPreferences.getBoolean("saveLogin",false)){
                String token = loginPreferences.getString("token","");
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                intent.putExtra("token",token);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        }


        loginBtn = (Button) findViewById(R.id.splash_login_btn);
        registBtn = (Button) findViewById(R.id.splah_regist_btn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        registBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mCustomDialog = new CustomDialog(SplashActivity.this,
                        "회원가입 코드", // 제목
                        "", // 내용
                        leftListener, // 왼쪽 버튼 이벤트
                        rightListener); // 오른쪽 버튼 이벤트
                mCustomDialog.show();



            }
        });


    }

    private class PostCode extends AsyncTask<DBConnector, Long, JSONObject> {


        @Override
        protected JSONObject doInBackground(DBConnector... params) {

            //it is executed on Background thread

            return params[0].PostCode(code_str);

        }

        @Override
        protected void onPostExecute(final JSONObject jsonObject) {

            settextToAdapter(jsonObject);

        }
    }

    public void settextToAdapter(JSONObject jsonObject) {

        if(jsonObject == null){
            Intent intent2 = new Intent(SplashActivity.this, RegisterActivity.class);
            intent2.putExtra("invite_code" , code_str);
           // Log.d("response" , "invite_code2" +code_str);
             startActivityForResult(intent2,10);
            finish();
            Toast.makeText(getApplicationContext(), "회원가입을 해주세요!",
                    Toast.LENGTH_LONG).show();

        }else{
            try {
                String str = jsonObject.getString("detail").toString();
                Toast.makeText(getApplicationContext(), "코드를 다시 입력해주세요!",
                        Toast.LENGTH_LONG).show();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    private View.OnClickListener leftListener = new View.OnClickListener() {
        public void onClick(View v) {

            code_str = mCustomDialog.codeEt.getText().toString();
              //  Log.d("response" , "code : " + code_str);

            new PostCode().execute(new DBConnector());
        }
    };

    private View.OnClickListener rightListener = new View.OnClickListener() {
        public void onClick(View v) {
            mCustomDialog.dismiss();
        }
    };

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
