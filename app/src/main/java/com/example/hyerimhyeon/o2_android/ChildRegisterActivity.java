package com.example.hyerimhyeon.o2_android;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
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

import java.io.File;

public class ChildRegisterActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    Button child_btn, mento_btn, expert_btn, register_btn;
    RadioButton one, two, three, four;
    Spinner type, location, school;
    ImageView profile_img;
    EditText mail, pw, pw_ck, name, phone, birth, type_name, location_name, school_name, shcool_name, school_type;

    private File file = null;


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

        location = (Spinner) findViewById(R.id.c_register_location);
        location.setAdapter(adapter);

        String[] str2=getResources().getStringArray(R.array.schoolArr);
        ArrayAdapter adapter2 = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, str2);
        school = (Spinner) findViewById(R.id.c_register_school);
        school.setAdapter(adapter2);


        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChildRegisterActivity.this, MainActivity.class);
                startActivity(intent);
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
                        Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
                        profile_img.setImageBitmap(bitmap);
                    }

                    @Override
                    public void isFail() {
                        Log.d("camera" , "fail");
                    }
                }).show(getSupportFragmentManager(), null);

            }
        });


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
