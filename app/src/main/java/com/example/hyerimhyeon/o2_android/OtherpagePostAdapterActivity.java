package com.example.hyerimhyeon.o2_android;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
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

public class OtherpagePostAdapterActivity extends ArrayAdapter<NewsfeedItem> {

    LayoutInflater layoutInflater;
    NewsFeed newsFeed;
    OtherPageActivity mypageActivity;
    Context context;
    NewsfeedItem newsfeedItem;

    OtherpagePostAdapterActivity newsfeedAdapterActivity;

    TextView name, type, belong, regist_date;
    ImageView profile_img;
    LinearLayout content_layout;
    String token, post_id;
    String select_pods_id;
    Boolean is_like;

    private static final int REQUEST_INTERNET = 1;

    static class ViewHolder{
        TextView like_btn ,like, comment_btn, content;
        ImageView image;
    }



    public OtherpagePostAdapterActivity(Context context, NewsFeed newsFeed, OtherPageActivity mypageActivity) {

        super(context, R.layout.activity_main_newsfeed, newsFeed.newsfeedItem);

        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.newsFeed = newsFeed;
        this.mypageActivity = mypageActivity;
        this.context = context;
        this.newsfeedItem = newsfeedItem;

    }



    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View itemView;
        final ViewHolder viewHolder;
        token = mypageActivity.token;
        // RecyclerView.ViewHolder viewHolder;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            itemView = layoutInflater.inflate(R.layout.activity_main_newsfeed, parent, false);

            name = (TextView) itemView.findViewById(R.id.main_newsfeed_lv_name);
            type = (TextView) itemView.findViewById(R.id.main_newsfeed_lv_type);
            belong = (TextView) itemView.findViewById(R.id.main_newsfeed_lv_belong);
            regist_date = (TextView) itemView.findViewById(R.id.main_newsfeed_lv_registDate);
            viewHolder.content = (TextView) itemView.findViewById(R.id.main_newsfeed_lv_content);
            viewHolder.like = (TextView) itemView.findViewById(R.id.main_newsfeed_like);
            content_layout = (LinearLayout) itemView.findViewById(R.id.main_feed_layout);
            viewHolder.like_btn = (TextView) itemView.findViewById(R.id.main_like_btn);
            viewHolder.image = (ImageView) itemView.findViewById(R.id.main_newsfeed_lv_img);
            profile_img = (ImageView) itemView.findViewById(R.id.main_profile_img);
            viewHolder.comment_btn = (TextView) itemView.findViewById(R.id.main_comment_btn);

            final NewsfeedItem newfeedItemPosition = newsFeed.newsfeedItem.get(position);

            name.setText(newfeedItemPosition.name);

            String type_str = "";
            if(newfeedItemPosition.member_type.equals("mentor")){
                type_str = "멘토";
            }else if(newfeedItemPosition.member_type.equals("mentee")){
                type_str = "꿈나무";
            }else if(newfeedItemPosition.member_type.equals("expert")){
                type_str = "전문가";
            }

            type.setText(type_str);

            if(newfeedItemPosition.company.length()>1){
                belong.setText(newfeedItemPosition.company);
            }else{
                belong.setText("");
            }


            regist_date.setText(newfeedItemPosition.regist_date);
            viewHolder.content.setText(newfeedItemPosition.content);
            viewHolder.like.setText("좋아요 " +newfeedItemPosition.like_num+"개  댓글 " + newfeedItemPosition.comment_num+"개");


            if(newfeedItemPosition.post_image_url.equals("")|| newfeedItemPosition.post_image_url == null||newfeedItemPosition.post_image_url == " "||newfeedItemPosition.post_image_url == "null"|| newfeedItemPosition.post_image_url == "" || newfeedItemPosition.post_image_url=="http://" || newfeedItemPosition.post_image_url=="http://null" || newfeedItemPosition.post_image_url.equals("http://") || newfeedItemPosition.post_image_url.equals("http:/null/")){

                int width = 0;
                int height = 0;
                Log.d("response" , "screen : " + width + " " + height);
                LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width,height);
                viewHolder.image.setLayoutParams(parms);


            }else{
                int permissionCamera = ContextCompat.checkSelfPermission(mypageActivity, Manifest.permission.ACCESS_NETWORK_STATE);
                if(permissionCamera == PackageManager.PERMISSION_DENIED) {
                    ActivityCompat.requestPermissions(mypageActivity, new String[]{Manifest.permission.ACCESS_NETWORK_STATE}, REQUEST_INTERNET);
                } else {
                    new DownLoadImageTask(viewHolder.image).execute("http://"+newfeedItemPosition.post_image_url);
                    Log.d( "response", "internet permission authorized" );

                }

            }

            if(newfeedItemPosition.profile_url.equals("")|| newfeedItemPosition.profile_url == null||newfeedItemPosition.profile_url == " "||newfeedItemPosition.profile_url == "null"|| newfeedItemPosition.profile_url == "" || newfeedItemPosition.profile_url=="http://" || newfeedItemPosition.profile_url=="http://null" || newfeedItemPosition.profile_url.equals("http://") || newfeedItemPosition.profile_url.equals("http:/null/")){


            }else{
                int permissionCamera = ContextCompat.checkSelfPermission(mypageActivity, Manifest.permission.ACCESS_NETWORK_STATE);
                if(permissionCamera == PackageManager.PERMISSION_DENIED) {
                    ActivityCompat.requestPermissions(mypageActivity, new String[]{Manifest.permission.ACCESS_NETWORK_STATE}, REQUEST_INTERNET);
                } else {
                    new DownLoadImageTask_profile(profile_img).execute("http://"+newfeedItemPosition.profile_url);
                    Log.d( "response", "internet permission authorized" );

                }

            }

            is_like = newfeedItemPosition.is_like;
            post_id = newfeedItemPosition.post_id;

            if(is_like == true){
                viewHolder.like_btn.setTextColor(Color.parseColor("#3F51B5"));
            }else{
                viewHolder.like_btn.setTextColor(Color.parseColor("#555555"));
            }
            final NewsfeedItem newfeedItemPosition2 = newsFeed.newsfeedItem.get(position);

            content_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(newsFeed.newsfeedItem.size() != 0) {

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

            viewHolder.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(newsFeed.newsfeedItem.size() != 0) {

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

                    int position_like = position;
                    final NewsfeedItem newfeedItemPosition2 = newsFeed.newsfeedItem.get(position);
                    select_pods_id = newfeedItemPosition2.post_id;

                    if(newfeedItemPosition2.is_like == true){
                        if(position == position_like){
                            viewHolder.like_btn.setTextColor(Color.parseColor("#555555"));
                            new DeleteLike().execute(new DBConnector());
                            int like_int = Integer.parseInt(newfeedItemPosition2.like_num) - 1;
                            viewHolder.like.setText("좋아요 " +like_int+"개  댓글 " + newfeedItemPosition2.comment_num+"개");
                            newfeedItemPosition2.like_num = like_int+"";
                            newfeedItemPosition2.is_like = false;
                        }
                    }else{
                        if(position == position_like) {
                            viewHolder.like_btn.setTextColor(Color.parseColor("#3F51B5"));
                            new Like().execute(new DBConnector());
                            int like_int = Integer.parseInt(newfeedItemPosition2.like_num) + 1;
                            viewHolder.like.setText("좋아요 " +like_int+"개  댓글 " + newfeedItemPosition2.comment_num+"개");
                            newfeedItemPosition2.like_num = like_int+"";
                            newfeedItemPosition2.is_like = true;
                        }
                    }

                }
            });



            return itemView;

        } else {

            itemView = convertView;
            viewHolder = new ViewHolder();

            name = (TextView) itemView.findViewById(R.id.main_newsfeed_lv_name);
            type = (TextView) itemView.findViewById(R.id.main_newsfeed_lv_type);
            belong = (TextView) itemView.findViewById(R.id.main_newsfeed_lv_belong);
            regist_date = (TextView) itemView.findViewById(R.id.main_newsfeed_lv_registDate);
            viewHolder.content = (TextView) itemView.findViewById(R.id.main_newsfeed_lv_content);
            viewHolder.like = (TextView) itemView.findViewById(R.id.main_newsfeed_like);
            content_layout = (LinearLayout) itemView.findViewById(R.id.main_feed_layout);
            viewHolder.like_btn = (TextView) itemView.findViewById(R.id.main_like_btn);
            viewHolder.image = (ImageView) itemView.findViewById(R.id.main_newsfeed_lv_img);
            profile_img = (ImageView) itemView.findViewById(R.id.main_profile_img);
            viewHolder.comment_btn = (TextView) itemView.findViewById(R.id.main_comment_btn);


            if(newsFeed.newsfeedItem.size() != 0){

                final NewsfeedItem newfeedItemPosition = newsFeed.newsfeedItem.get(position);

                name.setText(newfeedItemPosition.name);

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

                type.setText(type_str);

                if(newfeedItemPosition.company.length()>1){
                    belong.setText(newfeedItemPosition.company);
                }else{
                    belong.setText("");
                }

                regist_date.setText(newfeedItemPosition.regist_date);
                viewHolder.content.setText(newfeedItemPosition.content);
                viewHolder.like.setText("좋아요 " +newfeedItemPosition.like_num+"개  댓글 " + newfeedItemPosition.comment_num+"개");



                if(newfeedItemPosition.post_image_url.equals("")|| newfeedItemPosition.post_image_url == null||newfeedItemPosition.post_image_url == " "||newfeedItemPosition.post_image_url == "null"|| newfeedItemPosition.post_image_url == "" || newfeedItemPosition.post_image_url=="http://" || newfeedItemPosition.post_image_url=="http://null" || newfeedItemPosition.post_image_url.equals("http://") || newfeedItemPosition.post_image_url.equals("http:/null/")){

                    int width = 0;
                    int height = 0;
                    Log.d("response" , "screen : " + width + " " + height);
                    LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width,height);
                    viewHolder.image.setLayoutParams(parms);


                }else{
                    int permissionCamera = ContextCompat.checkSelfPermission(mypageActivity, Manifest.permission.ACCESS_NETWORK_STATE);
                    if(permissionCamera == PackageManager.PERMISSION_DENIED) {
                        ActivityCompat.requestPermissions(mypageActivity, new String[]{Manifest.permission.ACCESS_NETWORK_STATE}, REQUEST_INTERNET);
                    } else {
                        new DownLoadImageTask(viewHolder.image).execute("http://"+newfeedItemPosition.post_image_url);
                        Log.d( "response", "internet permission authorized" );

                    }

                }

                if(newfeedItemPosition.profile_url.equals("")|| newfeedItemPosition.profile_url == null||newfeedItemPosition.profile_url == " "||newfeedItemPosition.profile_url == "null"|| newfeedItemPosition.profile_url == "" || newfeedItemPosition.profile_url=="http://" || newfeedItemPosition.profile_url=="http://null" || newfeedItemPosition.profile_url.equals("http://") || newfeedItemPosition.profile_url.equals("http:/null/")){


                }else{
                    int permissionCamera = ContextCompat.checkSelfPermission(mypageActivity, Manifest.permission.ACCESS_NETWORK_STATE);
                    if(permissionCamera == PackageManager.PERMISSION_DENIED) {
                        ActivityCompat.requestPermissions(mypageActivity, new String[]{Manifest.permission.ACCESS_NETWORK_STATE}, REQUEST_INTERNET);
                    } else {
                        new DownLoadImageTask_profile(profile_img).execute("http://"+newfeedItemPosition.profile_url);
                        Log.d( "response", "internet permission authorized" );

                    }

                }


                post_id = newfeedItemPosition.post_id;
                is_like = newfeedItemPosition.is_like;

                if(is_like == true){
                    viewHolder.like_btn.setTextColor(Color.parseColor("#3F51B5"));
                }else{
                    viewHolder.like_btn.setTextColor(Color.parseColor("#555555"));
                }
            }



            content_layout.setOnClickListener(new View.OnClickListener() {
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

                     //   Log.d("response", "name2 : " + newfeedItemPosition2.name);
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

                        //   Log.d("response", "name2 : " + newfeedItemPosition2.name);
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

                        //   Log.d("response", "name2 : " + newfeedItemPosition2.name);
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

                        //   Log.d("response", "name2 : " + newfeedItemPosition2.name);
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
                            newfeedItemPosition2.like_num = like_int+"";
                            newfeedItemPosition2.is_like = false;
                        }
                    }else{
                        if(position == position_like) {
                            viewHolder.like_btn.setTextColor(Color.parseColor("#3F51B5"));
                            new Like().execute(new DBConnector());
                            int like_int = Integer.parseInt(newfeedItemPosition2.like_num) + 1;
                            viewHolder.like.setText("좋아요 " +like_int+"개  댓글 " + newfeedItemPosition2.comment_num+"개");
                            newfeedItemPosition2.like_num = like_int+"";
                            newfeedItemPosition2.is_like = true;
                        }
                    }

                }
            });
            return  convertView;

        }


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

    public boolean isInternetAvailable(String url) {
        try {
            InetAddress ipAddr = InetAddress.getByName(url); //You can replace it with your name

            if (ipAddr.equals("")) {
                return false;
            } else {
                return true;
            }

        } catch (Exception e) {
            return false;
        }

    }

    private class Like extends AsyncTask<DBConnector, Long, JSONObject> {


        @Override
        protected JSONObject doInBackground(DBConnector... params) {

            //it is executed on Background thread
            Log.d("response22 " , "hihi" + token + "  " + select_pods_id);
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


    void setSimpleList(ListView listView){

        ArrayList<String> contactsList = new ArrayList<String>();

        for (int index = 0; index < 10; index++) {
            contactsList.add("I am @ index " + index + " today " + Calendar.getInstance().getTime().toString());
        }

        listView.setAdapter(new ArrayAdapter<String>(getContext(),
                R.layout.fb_comments_list_item, android.R.id.text1,contactsList));

    }



}


