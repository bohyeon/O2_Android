package com.example.hyerimhyeon.o2_android;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

public class SearchActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ImageButton back_icon;

    TextView life01, life02, life03, life04, life05, life06;
    TextView sport01, sport02, sport03, sport04, sport05, sport06;

    Boolean life_b01 = false;
    Boolean life_b02 = false;
    Boolean life_b03 = false;
    Boolean life_b04 = false;
    Boolean life_b05 = false;
    Boolean life_b06 = false;

    Boolean sport_b01 = false;
    Boolean sport_b02 = false;
    Boolean sport_b03 = false;
    Boolean sport_b04 = false;
    Boolean sport_b05 = false;
    Boolean sport_b06 = false;

    Spinner type2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String type = "";
        type=intent.getStringExtra("type");

        if(type.equals("common")){
            setContentView(R.layout.activity_search);
            String[] str=getResources().getStringArray(R.array.mSpinnerArr);
            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, str);
            type2 = (Spinner) findViewById(R.id.search_type);
            type2.setAdapter(adapter);

        }else if(type.equals("sport")){
            setContentView(R.layout.activity_search_sport);
        }else if(type.equals("life")){
            setContentView(R.layout.activity_search_life);
        }else{
            setContentView(R.layout.activity_search);
        }


        back_icon = (ImageButton) findViewById(R.id.search_back_icon);

        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        life01 = (TextView) findViewById(R.id.life_box_01);
        life02 = (TextView) findViewById(R.id.life_box_02);
        life03 = (TextView) findViewById(R.id.life_box_03);
        life04 = (TextView) findViewById(R.id.life_box_04);
        life05 = (TextView) findViewById(R.id.life_box_05);
        life06 = (TextView) findViewById(R.id.life_box_06);

        sport01 = (TextView) findViewById(R.id.sport_box_01);
        sport02 = (TextView) findViewById(R.id.sport_box_02);
        sport03 = (TextView) findViewById(R.id.sport_box_03);
        sport04 = (TextView) findViewById(R.id.sport_box_04);
        sport05 = (TextView) findViewById(R.id.sport_box_05);
        sport06 = (TextView) findViewById(R.id.sport_box_06);


        if(type.equals("life")){

            life01.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(life_b01 == false){
                        life01.setBackground(getResources().getDrawable(R.drawable.filter_border));
                        life01.setTextColor(getResources().getColor(R.color.mainBlue));
                        life_b01 = true;
                    }else{
                        life01.setBackgroundColor(Color.rgb(255,255,255));
                        life01.setTextColor(getResources().getColor(R.color.black));
                        life_b01 = false;
                    }


                }
            });
            life02.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(life_b02 == false){

                        life02.setBackground(getResources().getDrawable(R.drawable.filter_border));
                        life02.setTextColor(getResources().getColor(R.color.mainBlue));
                        life_b02 = true;
                    }else{
                        life02.setBackgroundColor(Color.rgb(255,255,255));
                        life02.setTextColor(getResources().getColor(R.color.black));
                        life_b02 = false;
                    }


                }
            });
            life03.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(life_b03 == false){

                        life03.setBackground(getResources().getDrawable(R.drawable.filter_border));
                        life03.setTextColor(getResources().getColor(R.color.mainBlue));
                        life_b03 = true;
                    }else{
                        life03.setBackgroundColor(Color.rgb(255,255,255));
                        life03.setTextColor(getResources().getColor(R.color.black));
                        life_b03 = false;
                    }


                }
            });
            life04.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(life_b04 == false){

                        life04.setBackground(getResources().getDrawable(R.drawable.filter_border));
                        life04.setTextColor(getResources().getColor(R.color.mainBlue));
                        life_b04 = true;
                    }else{
                        life04.setBackgroundColor(Color.rgb(255,255,255));
                        life04.setTextColor(getResources().getColor(R.color.black));
                        life_b04 = false;
                    }



                }
            });

            life05.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if(life_b05 == false){

                        life05.setBackground(getResources().getDrawable(R.drawable.filter_border));
                        life05.setTextColor(getResources().getColor(R.color.mainBlue));

                        life_b05 = true;
                    }else{
                        life05.setBackgroundColor(Color.rgb(255,255,255));
                        life05.setTextColor(getResources().getColor(R.color.black));
                        life_b05 = false;
                    }


                }
            });
            life06.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(life_b06 == false){

                        life06.setBackground(getResources().getDrawable(R.drawable.filter_border));
                        life06.setTextColor(getResources().getColor(R.color.mainBlue));
                        life_b06 = true;
                    }else{
                        life06.setBackgroundColor(Color.rgb(255,255,255));
                        life06.setTextColor(getResources().getColor(R.color.black));
                        life_b06 = false;
                    }



                }
            });
        }else if(type.equals("sport")){

            sport01.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(sport_b01 == false){

                        sport01.setBackground(getResources().getDrawable(R.drawable.filter_border));
                        sport01.setTextColor(getResources().getColor(R.color.mainBlue));
                        sport_b01 = true;

                    }else{
                        sport01.setBackgroundColor(Color.rgb(255,255,255));
                        sport01.setTextColor(getResources().getColor(R.color.black));
                        sport_b01 = false;
                    }



                }
            });
            sport02.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(sport_b02 == false){

                        sport02.setBackground(getResources().getDrawable(R.drawable.filter_border));
                        sport02.setTextColor(getResources().getColor(R.color.mainBlue));
                        sport_b02 = true;
                    }else{
                        sport02.setBackgroundColor(Color.rgb(255,255,255));
                        sport02.setTextColor(getResources().getColor(R.color.black));
                        sport_b02 = false;
                    }

                }
            });

            sport03.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(sport_b03 == false){

                        sport03.setBackground(getResources().getDrawable(R.drawable.filter_border));
                        sport03.setTextColor(getResources().getColor(R.color.mainBlue));
                        sport_b03 = true;
                    }else{
                        sport03.setBackgroundColor(Color.rgb(255,255,255));
                        sport03.setTextColor(getResources().getColor(R.color.black));
                        sport_b03 = false;
                    }
                }
            });
            sport04.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(sport_b04 == false){

                        sport04.setBackground(getResources().getDrawable(R.drawable.filter_border));
                        sport04.setTextColor(getResources().getColor(R.color.mainBlue));
                        sport_b04 = true;
                    }else{
                        sport04.setBackgroundColor(Color.rgb(255,255,255));
                        sport04.setTextColor(getResources().getColor(R.color.black));
                        sport_b04 = false;
                    }

                }
            });

            sport05.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(sport_b05 == false){

                        sport05.setBackground(getResources().getDrawable(R.drawable.filter_border));
                        sport05.setTextColor(getResources().getColor(R.color.mainBlue));
                        sport_b05 = true;
                    }else{
                        sport05.setBackgroundColor(Color.rgb(255,255,255));
                        sport05.setTextColor(getResources().getColor(R.color.black));
                        sport_b05 = false;
                    }

                }
            });
            sport06.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(sport_b06 == false){

                        sport06.setBackground(getResources().getDrawable(R.drawable.filter_border));
                        sport06.setTextColor(getResources().getColor(R.color.mainBlue));
                        sport_b06 = true;
                    }else{
                        sport06.setBackgroundColor(Color.rgb(255,255,255));
                        sport06.setTextColor(getResources().getColor(R.color.black));
                        sport_b06 = false;
                    }
                }
            });

        }


    }

    @Override
    public void onBackPressed() {
      finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.main, menu);


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

            Intent intent = new Intent(SearchActivity.this, SubjectFeedActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_slideshow) {

            Intent intent = new Intent(SearchActivity.this, ExpertFeedActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_manage) {

            Intent intent = new Intent(SearchActivity.this, NewsfeedWriteActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_share) {

            Intent intent = new Intent(SearchActivity.this, NoticeActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_send) {

            Intent intent = new Intent(SearchActivity.this, MypageActivity.class);
            startActivity(intent);

        } else if (id == R.id.people) {

            Intent intent = new Intent(SearchActivity.this, MemberActivity.class);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



}
