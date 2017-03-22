package com.example.hyerimhyeon.o2_android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.DB.DBConnector;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

public class LifeExpertfeedAdapterActivity extends ArrayAdapter<NewsfeedItem> {

    LayoutInflater layoutInflater;
    NewsFeed newsFeed;
    LifeExpertFeedActivity lifeExpertFeedActivity;
    Context context;
    NewsfeedItem newsfeedItem;
    LifeExpertfeedAdapterActivity newsfeedAdapterActivity;

    TextView name, type, belong, regist_date, content, like, like_btn, comment_btn;
    LinearLayout content_layout;
    String token, post_id;
    String select_pods_id;
    Boolean is_like;
    ImageView image, profile_img;
    private static final int REQUEST_INTERNET = 1;

    public LifeExpertfeedAdapterActivity(Context context, NewsFeed newsFeed, LifeExpertFeedActivity lifeExpertFeedActivity) {

        super(context, R.layout.activity_main_newsfeed, newsFeed.newsfeedItem);

        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.newsFeed = newsFeed;
        this.lifeExpertFeedActivity = lifeExpertFeedActivity;
        this.context = context;
        this.newsfeedItem = newsfeedItem;

    }

    static class ViewHolder{
        LinearLayout content_layout;
        ImageView image, profile_img;
        TextView like_btn, like;
        TextView name, type, belong, regist_date, content, comment_btn;
    }



    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View itemView;
        token = lifeExpertFeedActivity.token;
        final NewsfeedAdapterActivity.ViewHolder viewHolder;
        // RecyclerView.ViewHolder viewHolder;

        if (convertView == null) {

            viewHolder = new NewsfeedAdapterActivity.ViewHolder();
            itemView = layoutInflater.inflate(R.layout.activity_main_newsfeed, parent, false);

            viewHolder.name = (TextView) itemView.findViewById(R.id.main_newsfeed_lv_name);
            viewHolder.type = (TextView) itemView.findViewById(R.id.main_newsfeed_lv_type);
            viewHolder.belong = (TextView) itemView.findViewById(R.id.main_newsfeed_lv_belong);
            viewHolder.regist_date = (TextView) itemView.findViewById(R.id.main_newsfeed_lv_registDate);
            viewHolder.content = (TextView) itemView.findViewById(R.id.main_newsfeed_lv_content);
            viewHolder.like = (TextView) itemView.findViewById(R.id.main_newsfeed_like);
            viewHolder.content_layout = (LinearLayout) itemView.findViewById(R.id.main_feed_layout);
            viewHolder.like_btn = (TextView) itemView.findViewById(R.id.main_like_btn);
            viewHolder.image = (ImageView) itemView.findViewById(R.id.main_newsfeed_lv_img);
            viewHolder.profile_img = (ImageView) itemView.findViewById(R.id.main_profile_img);
            viewHolder.comment_btn = (TextView) itemView.findViewById(R.id.main_comment_btn);


            final NewsfeedItem newfeedItemPosition = newsFeed.newsfeedItem.get(position);

            Log.d( "response", "is url ? " +newfeedItemPosition.post_image_url );

            if(newfeedItemPosition.post_image_url.equals("")|| newfeedItemPosition.post_image_url == null||newfeedItemPosition.post_image_url == " "||newfeedItemPosition.post_image_url == "null"|| newfeedItemPosition.post_image_url == "" || newfeedItemPosition.post_image_url=="http://" || newfeedItemPosition.post_image_url=="http://null" || newfeedItemPosition.post_image_url.equals("http://") || newfeedItemPosition.post_image_url.equals("http:/null/")){

                int width = 0;
                int height = 0;
                Log.d("response" , "screen : " + width + " " + height);
                LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width,height);
                viewHolder.image.setLayoutParams(parms);


            }else{
                int permissionCamera = ContextCompat.checkSelfPermission(lifeExpertFeedActivity, android.Manifest.permission.ACCESS_NETWORK_STATE);
                if(permissionCamera == PackageManager.PERMISSION_DENIED) {
                    ActivityCompat.requestPermissions(lifeExpertFeedActivity, new String[]{android.Manifest.permission.ACCESS_NETWORK_STATE}, REQUEST_INTERNET);
                } else {
                    new DownLoadImageTask(viewHolder.image).execute("http://"+newfeedItemPosition.post_image_url);
                    Log.d( "response", "internet permission authorized" );

                }
                // Log.d("response2", "response img: " + uri);
                // image.setImageURI(uri);
            }

            if(newfeedItemPosition.profile_url.equals("")|| newfeedItemPosition.profile_url == null||newfeedItemPosition.profile_url == " "||newfeedItemPosition.profile_url == "null"|| newfeedItemPosition.profile_url == "" || newfeedItemPosition.profile_url=="http://" || newfeedItemPosition.profile_url=="http://null" || newfeedItemPosition.profile_url.equals("http://") || newfeedItemPosition.profile_url.equals("http:/null/")){


            }else{
                int permissionCamera = ContextCompat.checkSelfPermission(lifeExpertFeedActivity, android.Manifest.permission.ACCESS_NETWORK_STATE);
                if(permissionCamera == PackageManager.PERMISSION_DENIED) {
                    ActivityCompat.requestPermissions(lifeExpertFeedActivity, new String[]{android.Manifest.permission.ACCESS_NETWORK_STATE}, REQUEST_INTERNET);
                } else {
                    new DownLoadImageTask_profile(viewHolder.profile_img).execute("http://"+newfeedItemPosition.profile_url);
                    Log.d( "response", "internet permission authorized" );

                }
                // Log.d("response2", "response img: " + uri);
                // image.setImageURI(uri);
            }

            viewHolder.name.setText(newfeedItemPosition.name);

            if(newfeedItemPosition.member_type != null){
                String type_str = "";
                if(newfeedItemPosition.member_type.equals("mentor")){
                    type_str = "멘토";
                }else if(newfeedItemPosition.member_type.equals("mentee")){
                    type_str = "꿈나무";
                }else if(newfeedItemPosition.member_type.equals("expert")){
                    type_str = "전문가";
                }

                viewHolder.type.setText(type_str);
            }

            if(!newfeedItemPosition.member_type.equals("mentee")){
                viewHolder.belong.setText(newfeedItemPosition.company);
            }else{
                viewHolder.belong.setText("");
            }

            viewHolder.regist_date.setText(newfeedItemPosition.regist_date);
            viewHolder.content.setText(newfeedItemPosition.content);
            viewHolder.like.setText("좋아요 " +newfeedItemPosition.like_num+"개  댓글 " + newfeedItemPosition.comment_num+"개");

            is_like = newfeedItemPosition.is_like;
            post_id = newfeedItemPosition.post_id;

            if(is_like == true){
                viewHolder.like_btn.setTextColor(Color.parseColor("#3F51B5"));
            }else{
                viewHolder.like_btn.setTextColor(Color.parseColor("#555555"));
            }

            viewHolder.content_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if(newsFeed.newsfeedItem.size() != 0) {

                        final NewsfeedItem newfeedItemPosition2 = newsFeed.newsfeedItem.get(position);

                        Intent intent = new Intent( context.getApplicationContext(), FeedDetailActivity.class);
                        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("post_id",newfeedItemPosition2.post_id);
                        intent.putExtra("name",newfeedItemPosition2.name);
                        intent.putExtra("type",newfeedItemPosition2.member_type);
                        intent.putExtra("belong",newfeedItemPosition2.company);
                        intent.putExtra("regist_date",newfeedItemPosition2.regist_date);
                        intent.putExtra("content",newfeedItemPosition2.content);
                        intent.putExtra("like_num",newfeedItemPosition2.like_num);
                        intent.putExtra("comment_num",newfeedItemPosition2.comment_num);
                        intent.putExtra("is_like", newfeedItemPosition2.is_like);
                        intent.putExtra("post_image_url",newfeedItemPosition2.post_image_url);
                        intent.putExtra("profile_url", newfeedItemPosition2.profile_url);
                        intent.putExtra("token", token);
                        Log.d("response","0320 : " + newfeedItemPosition2.member_type);
                        Log.d("response", "name2 : " + newfeedItemPosition2.member_type);
                        ((Activity) getContext()).startActivityForResult(intent,200);

                    }
                }
            });


            viewHolder.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(newsFeed.newsfeedItem.size() != 0) {
                        final NewsfeedItem newfeedItemPosition2 = newsFeed.newsfeedItem.get(position);

                        Intent intent = new Intent( context.getApplicationContext(), FeedDetailActivity.class);
                        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("post_id",newfeedItemPosition2.post_id);
                        intent.putExtra("name",newfeedItemPosition2.name);
                        intent.putExtra("type",newfeedItemPosition2.member_type);
                        intent.putExtra("belong",newfeedItemPosition2.company);
                        intent.putExtra("regist_date",newfeedItemPosition2.regist_date);
                        intent.putExtra("content",newfeedItemPosition2.content);
                        intent.putExtra("like_num",newfeedItemPosition2.like_num);
                        intent.putExtra("comment_num",newfeedItemPosition2.comment_num);
                        intent.putExtra("is_like", newfeedItemPosition2.is_like);
                        intent.putExtra("post_image_url",newfeedItemPosition2.post_image_url);
                        intent.putExtra("profile_url", newfeedItemPosition2.profile_url);
                        intent.putExtra("token", token);
                        //Log.d("response", "name2 : " + newfeedItemPosition2.name);
                        ((Activity) getContext()).startActivityForResult(intent,200);

                    }
                }
            });

            viewHolder.content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(newsFeed.newsfeedItem.size() != 0) {
                        final NewsfeedItem newfeedItemPosition2 = newsFeed.newsfeedItem.get(position);

                        Intent intent = new Intent( context.getApplicationContext(), FeedDetailActivity.class);
                        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("post_id",newfeedItemPosition2.post_id);
                        intent.putExtra("name",newfeedItemPosition2.name);
                        intent.putExtra("type",newfeedItemPosition2.member_type);
                        intent.putExtra("belong",newfeedItemPosition2.company);
                        intent.putExtra("regist_date",newfeedItemPosition2.regist_date);
                        intent.putExtra("content",newfeedItemPosition2.content);
                        intent.putExtra("like_num",newfeedItemPosition2.like_num);
                        intent.putExtra("comment_num",newfeedItemPosition2.comment_num);
                        intent.putExtra("is_like", newfeedItemPosition2.is_like);
                        intent.putExtra("post_image_url",newfeedItemPosition2.post_image_url);
                        intent.putExtra("profile_url", newfeedItemPosition2.profile_url);
                        intent.putExtra("token", token);
                        //Log.d("response", "name2 : " + newfeedItemPosition2.name);
                        ((Activity) getContext()).startActivityForResult(intent,200);

                    }
                }
            });


            viewHolder.comment_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(newsFeed.newsfeedItem.size() != 0) {
                        final NewsfeedItem newfeedItemPosition2 = newsFeed.newsfeedItem.get(position);

                        Intent intent = new Intent( context.getApplicationContext(), FeedDetailActivity.class);
                        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("post_id",newfeedItemPosition2.post_id);
                        intent.putExtra("name",newfeedItemPosition2.name);
                        intent.putExtra("type",newfeedItemPosition2.member_type);
                        intent.putExtra("belong",newfeedItemPosition2.company);
                        intent.putExtra("regist_date",newfeedItemPosition2.regist_date);
                        intent.putExtra("content",newfeedItemPosition2.content);
                        intent.putExtra("like_num",newfeedItemPosition2.like_num);
                        intent.putExtra("comment_num",newfeedItemPosition2.comment_num);
                        intent.putExtra("is_like", newfeedItemPosition2.is_like);
                        intent.putExtra("post_image_url",newfeedItemPosition2.post_image_url);
                        intent.putExtra("profile_url", newfeedItemPosition2.profile_url);
                        intent.putExtra("token", token);
                        //Log.d("response", "name2 : " + newfeedItemPosition2.name);
                        ((Activity) getContext()).startActivityForResult(intent,200);

                    }

                }
            });



            viewHolder.like_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // onShowPopup(v);

                    Log.d("response" , "like btn : " + position);

                    int position_like = position;
                    final NewsfeedItem newfeedItemPosition2 = newsFeed.newsfeedItem.get(position);
                    select_pods_id = newfeedItemPosition2.post_id;

                    if(newfeedItemPosition2.is_like == true){
                        if(position == position_like){
                            viewHolder.like_btn.setTextColor(Color.parseColor("#555555"));
                            new DeleteLike().execute(new DBConnector());
                            int like_int = Integer.parseInt(newfeedItemPosition2.like_num) - 1;
                            viewHolder.like.setText("좋아요 " +like_int+"개  댓글 " + newfeedItemPosition2.comment_num+"개");
                            newfeedItemPosition2.is_like = false;
                        }
                    }else{
                        if(position == position_like) {
                            viewHolder.like_btn.setTextColor(Color.parseColor("#3F51B5"));
                            new Like().execute(new DBConnector());
                            int like_int = Integer.parseInt(newfeedItemPosition2.like_num) + 1;
                            viewHolder.like.setText("좋아요 " +like_int+"개  댓글 " + newfeedItemPosition2.comment_num+"개");
                            newfeedItemPosition2.is_like = true;
                        }
                    }

                }
            });



            return itemView;

        } else {

            viewHolder = new NewsfeedAdapterActivity.ViewHolder();
            itemView = convertView;

            viewHolder.name = (TextView) itemView.findViewById(R.id.main_newsfeed_lv_name);
            viewHolder.type = (TextView) itemView.findViewById(R.id.main_newsfeed_lv_type);
            viewHolder.belong = (TextView) itemView.findViewById(R.id.main_newsfeed_lv_belong);
            viewHolder.regist_date = (TextView) itemView.findViewById(R.id.main_newsfeed_lv_registDate);
            viewHolder. content = (TextView) itemView.findViewById(R.id.main_newsfeed_lv_content);
            viewHolder. like = (TextView) itemView.findViewById(R.id.main_newsfeed_like);
            viewHolder.content_layout = (LinearLayout) itemView.findViewById(R.id.main_feed_layout);
            viewHolder.like_btn = (TextView) itemView.findViewById(R.id.main_like_btn);
            viewHolder.image = (ImageView) itemView.findViewById(R.id.main_newsfeed_lv_img);
            viewHolder.profile_img = (ImageView) itemView.findViewById(R.id.main_profile_img);
            viewHolder.comment_btn = (TextView) itemView.findViewById(R.id.main_comment_btn);


            if(newsFeed.newsfeedItem.size() != 0){

                final NewsfeedItem newfeedItemPosition = newsFeed.newsfeedItem.get(position);

                Log.d( "response", "is url ? " +newfeedItemPosition.post_image_url );

                if(newfeedItemPosition.post_image_url.equals("")|| newfeedItemPosition.post_image_url == null||newfeedItemPosition.post_image_url == " "||newfeedItemPosition.post_image_url == "null"|| newfeedItemPosition.post_image_url == "" || newfeedItemPosition.post_image_url=="http://" || newfeedItemPosition.post_image_url=="http://null" || newfeedItemPosition.post_image_url.equals("http://") || newfeedItemPosition.post_image_url.equals("http:/null/")){

                    int width = 0;
                    int height = 0;
                    Log.d("response" , "screen : " + width + " " + height);
                    LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width,height);
                    viewHolder.image.setLayoutParams(parms);


                }else{
                    int permissionCamera = ContextCompat.checkSelfPermission(lifeExpertFeedActivity, android.Manifest.permission.ACCESS_NETWORK_STATE);
                    if(permissionCamera == PackageManager.PERMISSION_DENIED) {
                        ActivityCompat.requestPermissions(lifeExpertFeedActivity, new String[]{android.Manifest.permission.ACCESS_NETWORK_STATE}, REQUEST_INTERNET);
                    } else {
                        new DownLoadImageTask(viewHolder.image).execute("http://"+newfeedItemPosition.post_image_url);

                    }

                }


                if(newfeedItemPosition.profile_url.equals("")|| newfeedItemPosition.profile_url == null||newfeedItemPosition.profile_url == " "||newfeedItemPosition.profile_url == "null"|| newfeedItemPosition.profile_url == "" || newfeedItemPosition.profile_url=="http://" || newfeedItemPosition.profile_url=="http://null" || newfeedItemPosition.profile_url.equals("http://") || newfeedItemPosition.profile_url.equals("http:/null/")){


                }else{
                    int permissionCamera = ContextCompat.checkSelfPermission(lifeExpertFeedActivity, android.Manifest.permission.ACCESS_NETWORK_STATE);
                    if(permissionCamera == PackageManager.PERMISSION_DENIED) {
                        ActivityCompat.requestPermissions(lifeExpertFeedActivity, new String[]{android.Manifest.permission.ACCESS_NETWORK_STATE}, REQUEST_INTERNET);
                    } else {
                        new DownLoadImageTask_profile(viewHolder.profile_img).execute("http://"+newfeedItemPosition.profile_url);

                    }
                    // Log.d("response2", "response img: " + uri);
                    // image.setImageURI(uri);
                }


                viewHolder.name.setText(newfeedItemPosition.name);

                String type_str = "";
                if(newfeedItemPosition.member_type.equals("mentor")){
                    type_str = "멘토";
                }else if(newfeedItemPosition.member_type.equals("mentee")){
                    type_str = "꿈나무";
                }else if(newfeedItemPosition.member_type.equals("expert")){
                    type_str = "전문가";
                }else{
                    type_str = " ";
                }

                viewHolder.type.setText(type_str);

                if(!newfeedItemPosition.member_type.equals("mentee")){
                    viewHolder.belong.setText(newfeedItemPosition.company);
                }else{
                    viewHolder.belong.setText("");
                }

                viewHolder.regist_date.setText(newfeedItemPosition.regist_date);
                viewHolder.content.setText(newfeedItemPosition.content);
                viewHolder.like.setText("좋아요 " +newfeedItemPosition.like_num+"개  댓글 " + newfeedItemPosition.comment_num+"개");

                post_id = newfeedItemPosition.post_id;
                is_like = newfeedItemPosition.is_like;

                if(is_like == true){
                    viewHolder.like_btn.setTextColor(Color.parseColor("#3F51B5"));
                }else{
                    viewHolder.like_btn.setTextColor(Color.parseColor("#555555"));
                }
            }


            viewHolder.content_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(newsFeed.newsfeedItem.size() != 0) {

                        final NewsfeedItem newfeedItemPosition2 = newsFeed.newsfeedItem.get(position);

                        Intent intent = new Intent( context.getApplicationContext(), FeedDetailActivity.class);
                        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("post_id",newfeedItemPosition2.post_id);
                        intent.putExtra("name",newfeedItemPosition2.name);
                        intent.putExtra("type",newfeedItemPosition2.member_type);
                        intent.putExtra("belong",newfeedItemPosition2.company);
                        intent.putExtra("regist_date",newfeedItemPosition2.regist_date);
                        intent.putExtra("content",newfeedItemPosition2.content);
                        intent.putExtra("like_num",newfeedItemPosition2.like_num);
                        intent.putExtra("comment_num",newfeedItemPosition2.comment_num);
                        intent.putExtra("is_like", newfeedItemPosition2.is_like);
                        intent.putExtra("post_image_url",newfeedItemPosition2.post_image_url);
                        intent.putExtra("profile_url", newfeedItemPosition2.profile_url);
                        intent.putExtra("token", token);


                        Log.d("response", "03200 : " + newfeedItemPosition2.member_type);
                        ((Activity) getContext()).startActivityForResult(intent,200);

                    }

                }
            });

            viewHolder.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(newsFeed.newsfeedItem.size() != 0) {

                        final NewsfeedItem newfeedItemPosition2 = newsFeed.newsfeedItem.get(position);

                        Intent intent = new Intent( context.getApplicationContext(), FeedDetailActivity.class);
                        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("post_id",newfeedItemPosition2.post_id);
                        intent.putExtra("name",newfeedItemPosition2.name);
                        intent.putExtra("type",newfeedItemPosition2.member_type);
                        intent.putExtra("belong",newfeedItemPosition2.company);
                        intent.putExtra("regist_date",newfeedItemPosition2.regist_date);
                        intent.putExtra("content",newfeedItemPosition2.content);
                        intent.putExtra("like_num",newfeedItemPosition2.like_num);
                        intent.putExtra("comment_num",newfeedItemPosition2.comment_num);
                        intent.putExtra("is_like", newfeedItemPosition2.is_like);
                        intent.putExtra("post_image_url",newfeedItemPosition2.post_image_url);
                        intent.putExtra("profile_url", newfeedItemPosition2.profile_url);
                        intent.putExtra("token", token);


                        Log.d("response", "03200 : " + newfeedItemPosition2.member_type);
                        ((Activity) getContext()).startActivityForResult(intent,200);

                    }

                }
            });

            viewHolder.content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(newsFeed.newsfeedItem.size() != 0) {

                        final NewsfeedItem newfeedItemPosition2 = newsFeed.newsfeedItem.get(position);

                        Intent intent = new Intent( context.getApplicationContext(), FeedDetailActivity.class);
                        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("post_id",newfeedItemPosition2.post_id);
                        intent.putExtra("name",newfeedItemPosition2.name);
                        intent.putExtra("type",newfeedItemPosition2.member_type);
                        intent.putExtra("belong",newfeedItemPosition2.company);
                        intent.putExtra("regist_date",newfeedItemPosition2.regist_date);
                        intent.putExtra("content",newfeedItemPosition2.content);
                        intent.putExtra("like_num",newfeedItemPosition2.like_num);
                        intent.putExtra("comment_num",newfeedItemPosition2.comment_num);
                        intent.putExtra("is_like", newfeedItemPosition2.is_like);
                        intent.putExtra("post_image_url",newfeedItemPosition2.post_image_url);
                        intent.putExtra("profile_url", newfeedItemPosition2.profile_url);
                        intent.putExtra("token", token);


                        Log.d("response", "03200 : " + newfeedItemPosition2.member_type);
                        ((Activity) getContext()).startActivityForResult(intent,200);

                    }

                }
            });


            viewHolder.comment_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(newsFeed.newsfeedItem.size() != 0) {

                        final NewsfeedItem newfeedItemPosition2 = newsFeed.newsfeedItem.get(position);

                        Intent intent = new Intent( context.getApplicationContext(), FeedDetailActivity.class);
                        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("post_id",newfeedItemPosition2.post_id);
                        intent.putExtra("name",newfeedItemPosition2.name);
                        intent.putExtra("type",newfeedItemPosition2.member_type);
                        intent.putExtra("belong",newfeedItemPosition2.company);
                        intent.putExtra("regist_date",newfeedItemPosition2.regist_date);
                        intent.putExtra("content",newfeedItemPosition2.content);
                        intent.putExtra("like_num",newfeedItemPosition2.like_num);
                        intent.putExtra("comment_num",newfeedItemPosition2.comment_num);
                        intent.putExtra("is_like", newfeedItemPosition2.is_like);
                        intent.putExtra("post_image_url",newfeedItemPosition2.post_image_url);
                        intent.putExtra("profile_url", newfeedItemPosition2.profile_url);
                        intent.putExtra("token", token);


                        Log.d("response", "03200 : " + newfeedItemPosition2.member_type);
                        ((Activity) getContext()).startActivityForResult(intent,200);

                    }


                }
            });

            viewHolder.like_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("response" , "like btn : " + position);

                    int position_like = position;
                    final NewsfeedItem newfeedItemPosition2 = newsFeed.newsfeedItem.get(position);
                    select_pods_id = newfeedItemPosition2.post_id;

                    if(newfeedItemPosition2.is_like == true){
                        if(position == position_like){
                            viewHolder.like_btn.setTextColor(Color.parseColor("#555555"));
                            new DeleteLike().execute(new DBConnector());
                            int like_int = Integer.parseInt(newfeedItemPosition2.like_num) - 1;
                            newfeedItemPosition2.like_num = like_int+"";
                            viewHolder.like.setText("좋아요 " +like_int+"개  댓글 " + newfeedItemPosition2.comment_num+"개");
                            newfeedItemPosition2.is_like = false;
                        }
                    }else{
                        if(position == position_like) {
                            viewHolder.like_btn.setTextColor(Color.parseColor("#3F51B5"));
                            new Like().execute(new DBConnector());
                            int like_int = Integer.parseInt(newfeedItemPosition2.like_num) + 1;
                            newfeedItemPosition2.like_num = like_int+"";
                            viewHolder.like.setText("좋아요 " +like_int+"개  댓글 " + newfeedItemPosition2.comment_num+"개");
                            newfeedItemPosition2.is_like = true;
                        }
                    }
                }
            });

            return  convertView;

        }


    }

    public boolean isInternetAvailable(String url) {
        try {
            InetAddress ipAddr = InetAddress.getByName(url); //You can replace it with your name

            if (ipAddr.equals("")) {
                return false;
            } else {
                //        Picasso.with(context).load(url).into(image);
                return true;
            }

        } catch (Exception e) {
            Log.d("response" , "set image : " + e.toString());
            return false;
        }

    }

    private class Like extends AsyncTask<DBConnector, Long, JSONObject> {


        @Override
        protected JSONObject doInBackground(DBConnector... params) {

            //it is executed on Background thread
            //  Log.d("response22 " , "hihi" + token + "  " + select_pods_id);
            return params[0].Like(token, select_pods_id);

        }

        @Override
        protected void onPostExecute(final JSONObject jsonObject) {

            settextToAdapter(jsonObject);

        }
    }

    public void settextToAdapter(JSONObject jsonObject) {

        if(jsonObject == null){

        }else{

        }

    }

    private class DeleteLike extends AsyncTask<DBConnector, Long, JSONObject> {


        @Override
        protected JSONObject doInBackground(DBConnector... params) {

            //it is executed on Background thread
            Log.d("response22 " , "delete" + token + "  " + select_pods_id);
            return params[0].DeleteLike(token, select_pods_id);

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

    // call this method when required to show popup
    public void onShowPopup(View v){

        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // inflate the custom popup layout
        final View inflatedView = layoutInflater.inflate(R.layout.fb_popup_layout, null,false);
        // find the ListView in the popup layout
        ListView listView = (ListView)inflatedView.findViewById(R.id.commentsListView);

        // get device size
        Display display =  ((Activity)context).getWindowManager().getDefaultDisplay();
        final Point size = new Point();
        display.getSize(size);
        // mDeviceHeight = size.y;


        // fill the data to the list items
        setSimpleList(listView);


//        // set height depends on the device size
//        popWindow = new PopupWindow(inflatedView, size.x - 50,size.y - 250, true );
//        // set a background drawable with rounders corners
//        popWindow.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.comment_rounded));
//        // make it focusable to show the keyboard to enter in `EditText`
//        popWindow.setFocusable(true);
//        // make it outside touchable to dismiss the popup window
//        popWindow.setOutsideTouchable(true);
//
//        // show the popup at bottom of the screen and set some margin at bottom ie,
//        popWindow.showAtLocation(v, Gravity.BOTTOM, 0,100);
    }


    void setSimpleList(ListView listView){

        ArrayList<String> contactsList = new ArrayList<String>();

        for (int index = 0; index < 10; index++) {
            contactsList.add("I am @ index " + index + " today " + Calendar.getInstance().getTime().toString());
        }

        listView.setAdapter(new ArrayAdapter<String>(getContext(),
                R.layout.fb_comments_list_item, android.R.id.text1,contactsList));

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

            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
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
