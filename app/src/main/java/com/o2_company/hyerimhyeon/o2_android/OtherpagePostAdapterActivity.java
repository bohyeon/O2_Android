package com.o2_company.hyerimhyeon.o2_android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.o2_company.DB.DBConnector;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.IOException;
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
    RelativeLayout content_layout;
    String token, post_id;
    String select_pods_id, id, youtube_link;
    Boolean is_like;

    private static final int REQUEST_INTERNET = 1;

    static class ViewHolder{
        TextView like_btn ,like, comment_btn, content;
        ImageView image;
        ImageView delete_img;
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
        token = mypageActivity.token;
        id = mypageActivity.id;
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
            viewHolder.content_layout = (RelativeLayout) itemView.findViewById(R.id.main_feed_layout);
            viewHolder.like_btn = (TextView) itemView.findViewById(R.id.main_like_btn);
            viewHolder.image = (ImageView) itemView.findViewById(R.id.main_newsfeed_lv_img);
            viewHolder.profile_img = (ImageView) itemView.findViewById(R.id.main_profile_img);
            viewHolder.comment_btn = (TextView) itemView.findViewById(R.id.main_comment_btn);
            viewHolder.youtube_layout = (LinearLayout) itemView.findViewById(R.id.youtube_layout);
            viewHolder.youtube_img = (ImageView) itemView.findViewById(R.id.youtube_img);
            viewHolder.youtube_title = (TextView) itemView.findViewById(R.id.main_youtube_title);
            viewHolder.delete_img = (ImageView) itemView.findViewById(R.id.main_arrow_btn);


            final NewsfeedItem newfeedItemPosition = newsFeed.newsfeedItem.get(position);

            viewHolder.delete_img.setVisibility(View.INVISIBLE);

            if(newfeedItemPosition.youtube_id == null || newfeedItemPosition.youtube_id.equals("")){

                // viewHolder.youtube_layout.setBackground(null);
                int width = 0;
                int height = 0;
                LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width,height);
                viewHolder.youtube_img.setLayoutParams(parms);
                //       viewHolder.youtube_title.setLayoutParams(parms);
                viewHolder.youtube_layout.setBackground(null);
                viewHolder.youtube_img.setImageBitmap(null);
                viewHolder.youtube_title.setText(null);

            }else{
                if(!newfeedItemPosition.youtube_id.equals("") ){
                    viewHolder.youtube_layout.setBackground(null);
                    Log.d("response", "youtube ㅅㅂ : " + newfeedItemPosition.youtube_id);
                    youtube_link = newfeedItemPosition.youtube_link;
                    //   new GetYoutube().execute(new DBConnector());
                    // new DownLoadImageTask_youtube(viewHolder.youtube_img).execute("http://img.youtube.com/vi/"+newfeedItemPosition.youtube_id+"/1.jpg");


                    WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
                    Display display = wm.getDefaultDisplay();
                    DisplayMetrics metrics = new DisplayMetrics();
                    display.getMetrics(metrics);
                    int width = metrics.widthPixels/3;
                    int height = 230;

                    LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width,width);
                    //  viewHolder.youtube_title.setLayoutParams(parms);
                    viewHolder.youtube_title.setText(newfeedItemPosition.youtube_tite);
                    viewHolder.youtube_img.setLayoutParams(parms);
                    viewHolder.youtube_layout.setBackgroundResource(R.drawable.table_border);
                    Picasso.with(context)
                            .load("http://img.youtube.com/vi/"+newfeedItemPosition.youtube_id+"/1.jpg")
                            .resize(width,width)
                            .placeholder(R.drawable.blankimg)
                            .error(R.drawable.blankimg)
                            .into(viewHolder.youtube_img);

                }else{
                    int width = 0;
                    int height = 0;
                    LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width,height);
                    viewHolder.youtube_img.setLayoutParams(parms);
                    viewHolder.youtube_title.setLayoutParams(parms);
                    viewHolder.youtube_layout.setBackground(null);
                    viewHolder.youtube_img.setImageBitmap(null);
                    viewHolder.youtube_title.setText(null);
                }

            }


            if(newfeedItemPosition.post_image_url.equals("")|| newfeedItemPosition.post_image_url == null||newfeedItemPosition.post_image_url == " "||newfeedItemPosition.post_image_url == "null"|| newfeedItemPosition.post_image_url == "" || newfeedItemPosition.post_image_url=="http://" || newfeedItemPosition.post_image_url=="http://null" || newfeedItemPosition.post_image_url.equals("http://") || newfeedItemPosition.post_image_url.equals("http:/null/")){

                int width = 0;
                int height = 0;
                // Log.d("response" , "screen : " + width + " " + height);
                LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width,height);
                viewHolder.image.setLayoutParams(parms);
                //viewHolder.image.setBackgroundResource(0);

            }else{

                int width =  ViewGroup.LayoutParams.FILL_PARENT;
                int height = ViewGroup.LayoutParams.MATCH_PARENT;
                LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width,height);
                viewHolder.image.setLayoutParams(parms);


                Picasso.with(context)
                        .load("http://"+newfeedItemPosition.post_image_url)
                        .placeholder(R.drawable.blankimg)
                        .error(R.drawable.blankimg)
                        .into(viewHolder.image);



//                Picasso
//                        .with(context)
//                        .load("http://"+newfeedItemPosition.post_image_url)
//                        .placeholder(R.drawable.blankimg)
//                        .error(R.drawable.blankimg)
//                        .transform(new ExifTransformation(context, Uri.parse("http://"+newfeedItemPosition.post_image_url)))
//                        .into(viewHolder.image);

                // new DownLoadImageTask(viewHolder.image).execute("http://"+newfeedItemPosition.post_image_url);
            }

            if(newfeedItemPosition.profile_url.equals("")|| newfeedItemPosition.profile_url == null||newfeedItemPosition.profile_url == " "||newfeedItemPosition.profile_url == "null"|| newfeedItemPosition.profile_url == "" || newfeedItemPosition.profile_url=="http://" || newfeedItemPosition.profile_url=="http://null" || newfeedItemPosition.profile_url.equals("http://") || newfeedItemPosition.profile_url.equals("http:/null/")){

                viewHolder.profile_img.setBackgroundResource(0);

            }else{
                viewHolder.profile_img.setBackgroundResource(0);


                Picasso.with(context)
                        .load("http://"+newfeedItemPosition.profile_url)
                        .resize(200,200)
                        .placeholder(R.drawable.blankimg)
                        .error(R.drawable.blankimg)
                        .into(viewHolder.profile_img);

                // new DownLoadImageTask_profile(viewHolder.profile_img).execute("http://"+newfeedItemPosition.profile_url);
                // String youtube_title = getTitleQuietly(newfeedItemPosition.youtube_link);
                // Log.d("response" , "title : " + youtube_title);
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

                if(newfeedItemPosition.member_type.equals("mentor")){
                    viewHolder.belong.setText(newfeedItemPosition.sport_type);
                }else if(newfeedItemPosition.member_type.equals("expert")){
                    // Log.d("response" , "belong : " + newfeedItemPosition.name + newfeedItemPosition.expert_type);
                    viewHolder.belong.setText(newfeedItemPosition.expert_type);
                }else{
                    viewHolder.belong.setText(newfeedItemPosition.sport_type);
                }

            }else{
                viewHolder.type.setText("");
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

            viewHolder.youtube_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(newfeedItemPosition.youtube_link.toString()));
                    ((Activity) getContext()).startActivity(browserIntent);
                }
            });

            viewHolder.youtube_title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(newfeedItemPosition.youtube_link.toString()));
                    ((Activity) getContext()).startActivity(browserIntent);
                }
            });

            viewHolder.profile_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent intent = new Intent( context.getApplicationContext(), OtherPageActivity.class);
//                    ((Activity) getContext()).startActivity(intent);
                }
            });

            viewHolder.content_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if(newsFeed.newsfeedItem.size() != 0) {

                        final NewsfeedItem newfeedItemPosition2 = newsFeed.newsfeedItem.get(position);

                        Intent intent = new Intent( context.getApplicationContext(), FeedDetailActivity.class);
                        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("post_id",newfeedItemPosition2.post_id);
                        intent.putExtra("name",newfeedItemPosition2.name);
                        intent.putExtra("member_type",newfeedItemPosition2.member_type);
                        intent.putExtra("expert_type",newfeedItemPosition2.expert_type);
                        intent.putExtra("sport_type",newfeedItemPosition2.sport_type);
                        intent.putExtra("type",newfeedItemPosition2.member_type);
                        intent.putExtra("belong",newfeedItemPosition2.company);
                        intent.putExtra("regist_date",newfeedItemPosition2.regist_date);
                        intent.putExtra("content",newfeedItemPosition2.content);
                        intent.putExtra("like_num",newfeedItemPosition2.like_num);
                        intent.putExtra("comment_num",newfeedItemPosition2.comment_num);
                        intent.putExtra("is_like", newfeedItemPosition2.is_like);
                        intent.putExtra("post_image_url",newfeedItemPosition2.post_image_url);
                        intent.putExtra("profile_url", newfeedItemPosition2.profile_url);
                        intent.putExtra("youtube_link",newfeedItemPosition2.youtube_link);
                        intent.putExtra("youtube_id", newfeedItemPosition2.youtube_id);
                        intent.putExtra("youtube_title", newfeedItemPosition2.youtube_tite);
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
                        intent.putExtra("member_type",newfeedItemPosition2.member_type);
                        intent.putExtra("expert_type",newfeedItemPosition2.expert_type);
                        intent.putExtra("sport_type",newfeedItemPosition2.sport_type);
                        intent.putExtra("type",newfeedItemPosition2.member_type);
                        intent.putExtra("belong",newfeedItemPosition2.company);
                        intent.putExtra("regist_date",newfeedItemPosition2.regist_date);
                        intent.putExtra("content",newfeedItemPosition2.content);
                        intent.putExtra("like_num",newfeedItemPosition2.like_num);
                        intent.putExtra("comment_num",newfeedItemPosition2.comment_num);
                        intent.putExtra("is_like", newfeedItemPosition2.is_like);
                        intent.putExtra("post_image_url",newfeedItemPosition2.post_image_url);
                        intent.putExtra("profile_url", newfeedItemPosition2.profile_url);
                        intent.putExtra("youtube_link",newfeedItemPosition2.youtube_link);
                        intent.putExtra("youtube_id", newfeedItemPosition2.youtube_id);
                        intent.putExtra("youtube_title", newfeedItemPosition2.youtube_tite);
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
                        intent.putExtra("member_type",newfeedItemPosition2.member_type);
                        intent.putExtra("expert_type",newfeedItemPosition2.expert_type);
                        intent.putExtra("sport_type",newfeedItemPosition2.sport_type);
                        intent.putExtra("type",newfeedItemPosition2.member_type);
                        intent.putExtra("belong",newfeedItemPosition2.company);
                        intent.putExtra("regist_date",newfeedItemPosition2.regist_date);
                        intent.putExtra("content",newfeedItemPosition2.content);
                        intent.putExtra("like_num",newfeedItemPosition2.like_num);
                        intent.putExtra("comment_num",newfeedItemPosition2.comment_num);
                        intent.putExtra("is_like", newfeedItemPosition2.is_like);
                        intent.putExtra("post_image_url",newfeedItemPosition2.post_image_url);
                        intent.putExtra("profile_url", newfeedItemPosition2.profile_url);
                        intent.putExtra("youtube_link",newfeedItemPosition2.youtube_link);
                        intent.putExtra("youtube_id", newfeedItemPosition2.youtube_id);
                        intent.putExtra("youtube_title", newfeedItemPosition2.youtube_tite);
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
                        intent.putExtra("member_type",newfeedItemPosition2.member_type);
                        intent.putExtra("expert_type",newfeedItemPosition2.expert_type);
                        intent.putExtra("sport_type",newfeedItemPosition2.sport_type);
                        intent.putExtra("type",newfeedItemPosition2.member_type);
                        intent.putExtra("belong",newfeedItemPosition2.company);
                        intent.putExtra("regist_date",newfeedItemPosition2.regist_date);
                        intent.putExtra("content",newfeedItemPosition2.content);
                        intent.putExtra("like_num",newfeedItemPosition2.like_num);
                        intent.putExtra("comment_num",newfeedItemPosition2.comment_num);
                        intent.putExtra("is_like", newfeedItemPosition2.is_like);
                        intent.putExtra("post_image_url",newfeedItemPosition2.post_image_url);
                        intent.putExtra("profile_url", newfeedItemPosition2.profile_url);
                        intent.putExtra("youtube_link",newfeedItemPosition2.youtube_link);
                        intent.putExtra("youtube_id", newfeedItemPosition2.youtube_id);
                        intent.putExtra("youtube_title", newfeedItemPosition2.youtube_tite);
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
            viewHolder.content_layout = (RelativeLayout) itemView.findViewById(R.id.main_feed_layout);
            viewHolder.like_btn = (TextView) itemView.findViewById(R.id.main_like_btn);
            viewHolder.image = (ImageView) itemView.findViewById(R.id.main_newsfeed_lv_img);
            viewHolder.profile_img = (ImageView) itemView.findViewById(R.id.main_profile_img);
            viewHolder.comment_btn = (TextView) itemView.findViewById(R.id.main_comment_btn);
            viewHolder.youtube_layout = (LinearLayout) itemView.findViewById(R.id.youtube_layout);
            viewHolder.youtube_img = (ImageView) itemView.findViewById(R.id.youtube_img);
            viewHolder.youtube_title = (TextView) itemView.findViewById(R.id.main_youtube_title);
            viewHolder.delete_img = (ImageView) itemView.findViewById(R.id.main_arrow_btn);

//
//            YouTubePlayerView youTubeView = (YouTubePlayerView) itemView.findViewById(R.id.youtube_view);
//            youTubeView.initialize("AIzaSyBTzYl1ZiZSMPSBSC-9Wdw_b4km0dRzI38", this);

            if(newsFeed.newsfeedItem.size() != 0){

                final NewsfeedItem newfeedItemPosition = newsFeed.newsfeedItem.get(position);

                viewHolder.delete_img.setVisibility(View.INVISIBLE);

                if(newfeedItemPosition.youtube_id == null || newfeedItemPosition.youtube_id.equals("")){

                    /// viewHolder.youtube_layout.setBackground(null);
                    int width = 0;
                    int height = 0;
                    LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width,height);
                    viewHolder.youtube_img.setLayoutParams(parms);

                    viewHolder.youtube_layout.setBackground(null);
                    viewHolder.youtube_img.setImageBitmap(null);
                    viewHolder.youtube_title.setText(null);
                    //viewHolder.youtube_title.setLayoutParams(parms);
                }else{
                    if(!newfeedItemPosition.youtube_id.equals("") ){

                        viewHolder.youtube_layout.setBackground(null);
                        youtube_link = newfeedItemPosition.youtube_link;

                        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
                        Display display = wm.getDefaultDisplay();
                        DisplayMetrics metrics = new DisplayMetrics();
                        display.getMetrics(metrics);
                        int width = metrics.widthPixels/3;
                        int width2 = metrics.widthPixels;

                        LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width,width);
                        viewHolder.youtube_img.setLayoutParams(parms);

                        viewHolder.youtube_title.setText(newfeedItemPosition.youtube_tite);
                        viewHolder.youtube_layout.setBackgroundResource(R.drawable.table_border);
                        Picasso.with(context)
                                .load("http://img.youtube.com/vi/"+newfeedItemPosition.youtube_id+"/1.jpg")
                                .resize(width,width)
                                .placeholder(R.drawable.blankimg)
                                .error(R.drawable.blankimg)
                                .into(viewHolder.youtube_img);


                    }else{
                        int width = 0;
                        int height = 0;
                        LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width,height);
                        viewHolder.youtube_img.setLayoutParams(parms);
                        viewHolder.youtube_title.setLayoutParams(parms);
                        viewHolder.youtube_layout.setBackground(null);
                        viewHolder.youtube_img.setImageBitmap(null);
                        viewHolder.youtube_title.setText(null);
                    }

                }

                if(newfeedItemPosition.post_image_url.equals("")|| newfeedItemPosition.post_image_url == null||newfeedItemPosition.post_image_url == " "||newfeedItemPosition.post_image_url == "null"|| newfeedItemPosition.post_image_url == "" || newfeedItemPosition.post_image_url=="http://" || newfeedItemPosition.post_image_url=="http://null" || newfeedItemPosition.post_image_url.equals("http://") || newfeedItemPosition.post_image_url.equals("http:/null/")){

                    int width = 0;
                    int height = 0;
                    // Log.d("response" , "screen : " + width + " " + height);
                    LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width,height);
                    viewHolder.image.setLayoutParams(parms);
                    // viewHolder.image.setBackgroundResource(0);

                }else{

                    int width =  ViewGroup.LayoutParams.FILL_PARENT;
                    int height = ViewGroup.LayoutParams.MATCH_PARENT;
                    LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width,height);
                    viewHolder.image.setLayoutParams(parms);

//                    Glide.with(context).load("http://"+newfeedItemPosition.post_image_url).into(viewHolder.image);

                    Picasso.with(context)
                            .load("http://"+newfeedItemPosition.post_image_url)
                            .placeholder(R.drawable.blankimg)
                            .error(R.drawable.blankimg)
                            .into(viewHolder.image);

//                    Picasso
//                            .with(context)
//                            .load("http://"+newfeedItemPosition.post_image_url)
//                            .placeholder(R.drawable.blankimg)
//                            .error(R.drawable.blankimg)
//                            .transform(new ExifTransformation(context, Uri.parse("http://"+newfeedItemPosition.post_image_url)))
//                            .into(viewHolder.image);
                }


                if(newfeedItemPosition.profile_url.equals("")|| newfeedItemPosition.profile_url == null||newfeedItemPosition.profile_url == " "||newfeedItemPosition.profile_url == "null"|| newfeedItemPosition.profile_url == "" || newfeedItemPosition.profile_url=="http://" || newfeedItemPosition.profile_url=="http://null" || newfeedItemPosition.profile_url.equals("http://") || newfeedItemPosition.profile_url.equals("http:/null/")){
                    viewHolder.profile_img.setBackgroundResource(0);
                }else{
                    viewHolder.profile_img.setBackgroundResource(0);

                    Picasso.with(context)
                            .load("http://"+newfeedItemPosition.profile_url)
                            .resize(250,200)
                            .placeholder(R.drawable.blankimg)
                            .error(R.drawable.blankimg)
                            .into(viewHolder.profile_img);
                    // new DownLoadImageTask_profile(viewHolder.profile_img).execute("http://"+newfeedItemPosition.profile_url);

                }


                viewHolder.name.setText(newfeedItemPosition.name);

                String type_str = "";

                if(newfeedItemPosition.member_type != null){
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

                    if(newfeedItemPosition.member_type.equals("mentor")){
                        viewHolder.belong.setText(newfeedItemPosition.sport_type);
                    }else if(newfeedItemPosition.member_type.equals("expert")){
                        // Log.d("response" , "belong : " + newfeedItemPosition.name + newfeedItemPosition.expert_type);
                        viewHolder.belong.setText(newfeedItemPosition.expert_type);
                    }else{
                        viewHolder.belong.setText(newfeedItemPosition.sport_type);
                    }

                }else{
                    viewHolder.type.setText("");
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
                viewHolder.youtube_img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(newfeedItemPosition.youtube_link.toString()));
                        ((Activity) getContext()).startActivity(browserIntent);
                    }
                });

                viewHolder.youtube_title.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(newfeedItemPosition.youtube_link.toString()));
                        ((Activity) getContext()).startActivity(browserIntent);
                    }
                });

            }

            viewHolder.profile_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent intent = new Intent( context.getApplicationContext(), OtherPageActivity.class);
//                    ((Activity) getContext()).startActivity(intent);
                }
            });



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
                        intent.putExtra("youtube_link",newfeedItemPosition2.youtube_link);
                        intent.putExtra("youtube_id", newfeedItemPosition2.youtube_id);
                        intent.putExtra("youtube_title", newfeedItemPosition2.youtube_tite);
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
                        intent.putExtra("member_type",newfeedItemPosition2.member_type);
                        intent.putExtra("expert_type",newfeedItemPosition2.expert_type);
                        intent.putExtra("sport_type",newfeedItemPosition2.sport_type);
                        intent.putExtra("type",newfeedItemPosition2.member_type);
                        intent.putExtra("belong",newfeedItemPosition2.company);
                        intent.putExtra("regist_date",newfeedItemPosition2.regist_date);
                        intent.putExtra("content",newfeedItemPosition2.content);
                        intent.putExtra("like_num",newfeedItemPosition2.like_num);
                        intent.putExtra("comment_num",newfeedItemPosition2.comment_num);
                        intent.putExtra("is_like", newfeedItemPosition2.is_like);
                        intent.putExtra("post_image_url",newfeedItemPosition2.post_image_url);
                        intent.putExtra("profile_url", newfeedItemPosition2.profile_url);
                        intent.putExtra("youtube_link",newfeedItemPosition2.youtube_link);
                        intent.putExtra("youtube_id", newfeedItemPosition2.youtube_id);
                        intent.putExtra("youtube_title", newfeedItemPosition2.youtube_tite);
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
                        intent.putExtra("member_type",newfeedItemPosition2.member_type);
                        intent.putExtra("expert_type",newfeedItemPosition2.expert_type);
                        intent.putExtra("sport_type",newfeedItemPosition2.sport_type);
                        intent.putExtra("type",newfeedItemPosition2.member_type);
                        intent.putExtra("belong",newfeedItemPosition2.company);
                        intent.putExtra("regist_date",newfeedItemPosition2.regist_date);
                        intent.putExtra("content",newfeedItemPosition2.content);
                        intent.putExtra("like_num",newfeedItemPosition2.like_num);
                        intent.putExtra("comment_num",newfeedItemPosition2.comment_num);
                        intent.putExtra("is_like", newfeedItemPosition2.is_like);
                        intent.putExtra("post_image_url",newfeedItemPosition2.post_image_url);
                        intent.putExtra("profile_url", newfeedItemPosition2.profile_url);
                        intent.putExtra("youtube_link",newfeedItemPosition2.youtube_link);
                        intent.putExtra("youtube_id", newfeedItemPosition2.youtube_id);
                        intent.putExtra("youtube_title", newfeedItemPosition2.youtube_tite);
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
                        intent.putExtra("member_type",newfeedItemPosition2.member_type);
                        intent.putExtra("expert_type",newfeedItemPosition2.expert_type);
                        intent.putExtra("sport_type",newfeedItemPosition2.sport_type);
                        intent.putExtra("type",newfeedItemPosition2.member_type);
                        intent.putExtra("belong",newfeedItemPosition2.company);
                        intent.putExtra("regist_date",newfeedItemPosition2.regist_date);
                        intent.putExtra("content",newfeedItemPosition2.content);
                        intent.putExtra("like_num",newfeedItemPosition2.like_num);
                        intent.putExtra("comment_num",newfeedItemPosition2.comment_num);
                        intent.putExtra("is_like", newfeedItemPosition2.is_like);
                        intent.putExtra("post_image_url",newfeedItemPosition2.post_image_url);
                        intent.putExtra("profile_url", newfeedItemPosition2.profile_url);
                        intent.putExtra("youtube_link",newfeedItemPosition2.youtube_link);
                        intent.putExtra("youtube_id", newfeedItemPosition2.youtube_id);
                        intent.putExtra("youtube_title", newfeedItemPosition2.youtube_tite);
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

    private class Like extends AsyncTask<DBConnector, Long, String> {


        @Override
        protected String doInBackground(DBConnector... params) {

            //it is executed on Background thread
            //  Log.d("response22 " , "hihi" + token + "  " + select_pods_id);
            return params[0].Like(token, select_pods_id);

        }

        @Override
        protected void onPostExecute(final String jsonObject) {

            settextToAdapter(jsonObject);

        }
    }

    public void settextToAdapter(String jsonObject) {

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

    private class GetYoutube extends AsyncTask<DBConnector, Long, JSONObject> {


        @Override
        protected JSONObject doInBackground(DBConnector... params) {

            //it is executed on Background thread
            //Log.d("response22 " , "delete" + token + "  " + select_pods_id);
            return params[0].GetYoutube(youtube_link);

        }

        @Override
        protected void onPostExecute(final JSONObject jsonObject) {

            settextToAdapter_youtube(jsonObject);

        }
    }

    public void settextToAdapter_youtube(JSONObject jsonObject) {

        if(jsonObject == null){

        }else{
            Log.d("response" , "youtube json : " + jsonObject);
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
        //       setSimpleList(listView);


        // set height depends on the device size
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
            int height = metrics.heightPixels/2;
            Log.d("response" , "screen : " + width + " " + height);
            LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width,height);
            imageView.setLayoutParams(parms);

            imageView.setImageBitmap(result);
        }




    }


    private class DownLoadImageTask_youtube extends AsyncTask<String,Void,Bitmap>{
        ImageView imageView;

        public DownLoadImageTask_youtube(ImageView imageView){
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

            int width = 250;
            int height = 200;
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

//    public static String getTitleQuietly(String youtubeUrl) {
//        try {
//            if (youtubeUrl != null) {
//                URL embededURL = new URL("http://www.youtube.com/oembed?url=" +
//                        youtubeUrl + "&format=json"
//                );
//
//                return new JSONObject(IOUtils.toString(embededURL)).getString("title");
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    private static int exifToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) { return 90; }
        else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {  return 180; }
        else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {  return 270; }
        return 0;
    }

    public synchronized static int GetExifOrientation(String filepath)
    {
        int degree = 0;
        ExifInterface exif = null;

        try
        {
            exif = new ExifInterface(filepath);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        if (exif != null)
        {
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);

            if (orientation != -1)
            {
                // We only recognize a subset of orientation tag values.
                switch(orientation)
                {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        degree = 90;
                        break;

                    case ExifInterface.ORIENTATION_ROTATE_180:
                        degree = 180;
                        break;

                    case ExifInterface.ORIENTATION_ROTATE_270:
                        degree = 270;
                        break;
                }

            }
        }

        return degree;
    }
}

