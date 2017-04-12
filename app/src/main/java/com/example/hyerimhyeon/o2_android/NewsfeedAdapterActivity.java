package com.example.hyerimhyeon.o2_android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.DB.DBConnector;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class NewsfeedAdapterActivity extends ArrayAdapter<NewsfeedItem> implements YouTubePlayer.OnInitializedListener, YouTubePlayer {

    LayoutInflater layoutInflater;
    NewsFeed newsFeed;
    MainActivity mainActivity;
    Context context;
    NewsfeedItem newsfeedItem;
    NewsfeedAdapterActivity newsfeedAdapterActivity;
    ImageThreadLoader imageLoader;

    String token, post_id;
    String select_pods_id;
    String youtube_link,id;
    String update_postId, update_content, update_postType, update_youtubeTitle, update_youtubeLink, update_imageUrl;
    Boolean is_like;
    Bitmap imgView;

    int count = 10;

    private static final int REQUEST_EXTERNAL_STORAGE = 2;
    private PopupWindow popWindow;
    private static final int REQUEST_INTERNET = 1;


    public NewsfeedAdapterActivity(Context context, NewsFeed newsFeed, MainActivity mainActivity) {

        super(context, R.layout.activity_main_newsfeed, newsFeed.newsfeedItem);

        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.newsFeed = newsFeed;
        this.mainActivity = mainActivity;
        this.context = context;
        imageLoader = new ImageThreadLoader(context);
        this.newsfeedItem = newsfeedItem;

    }


    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player,
                                        boolean wasRestored) {
        if (!wasRestored) {
            player.cueVideo("nCgQDjiotG0");
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }

    @Override
    public void release() {

    }

    @Override
    public void cueVideo(String s) {

    }

    @Override
    public void cueVideo(String s, int i) {

    }

    @Override
    public void loadVideo(String s) {

    }

    @Override
    public void loadVideo(String s, int i) {

    }

    @Override
    public void cuePlaylist(String s) {

    }

    @Override
    public void cuePlaylist(String s, int i, int i1) {

    }

    @Override
    public void loadPlaylist(String s) {

    }

    @Override
    public void loadPlaylist(String s, int i, int i1) {

    }

    @Override
    public void cueVideos(List<String> list) {

    }

    @Override
    public void cueVideos(List<String> list, int i, int i1) {

    }

    @Override
    public void loadVideos(List<String> list) {

    }

    @Override
    public void loadVideos(List<String> list, int i, int i1) {

    }

    @Override
    public void play() {

    }

    @Override
    public void pause() {

    }

    @Override
    public boolean isPlaying() {
        return false;
    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public boolean hasPrevious() {
        return false;
    }

    @Override
    public void next() {

    }

    @Override
    public void previous() {

    }

    @Override
    public int getCurrentTimeMillis() {
        return 0;
    }

    @Override
    public int getDurationMillis() {
        return 0;
    }

    @Override
    public void seekToMillis(int i) {

    }

    @Override
    public void seekRelativeMillis(int i) {

    }

    @Override
    public void setFullscreen(boolean b) {

    }

    @Override
    public void setOnFullscreenListener(OnFullscreenListener onFullscreenListener) {

    }

    @Override
    public void setFullscreenControlFlags(int i) {

    }

    @Override
    public int getFullscreenControlFlags() {
        return 0;
    }

    @Override
    public void addFullscreenControlFlag(int i) {

    }

    @Override
    public void setPlayerStyle(PlayerStyle playerStyle) {

    }

    @Override
    public void setShowFullscreenButton(boolean b) {

    }

    @Override
    public void setManageAudioFocus(boolean b) {

    }

    @Override
    public void setPlaylistEventListener(PlaylistEventListener playlistEventListener) {

    }

    @Override
    public void setPlayerStateChangeListener(PlayerStateChangeListener playerStateChangeListener) {

    }

    @Override
    public void setPlaybackEventListener(PlaybackEventListener playbackEventListener) {

    }



    static class ViewHolder{
        RelativeLayout content_layout;
        ImageView profile_img = null;
        ImageView image = null;
        TextView like_btn = null;
        TextView like = null;
        TextView name = null;
        TextView type = null;
        TextView belong = null;
        TextView regist_date = null;
        TextView content = null;
        TextView comment_btn = null;
        LinearLayout youtube_layout = null;
        ImageView youtube_img = null;
        TextView youtube_title = null;
        ImageView delete_img;
        Spinner delete_spi;
        LinearLayout spinner_box;
        YouTubePlayerView youTubePlayerView = null;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View itemView;
        token = mainActivity.token;
        id = mainActivity.id;
        final ViewHolder viewHolder;
       // RecyclerView.ViewHolder viewHolder;

        if (convertView == null) {

            viewHolder = new ViewHolder();
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
            viewHolder.delete_spi = (Spinner) itemView.findViewById(R.id.main_delete_spinner);
            viewHolder.spinner_box = (LinearLayout) itemView.findViewById(R.id.main_spinner_box);

            final NewsfeedItem newfeedItemPosition = newsFeed.newsfeedItem.get(position);


            if(newfeedItemPosition.email.equals(mainActivity.email)){
                //Log.d("response" , "eamil : " + newfeedItemPosition.email + " id : " + mainActivity.email);

                viewHolder.spinner_box.setVisibility(LinearLayout.VISIBLE);
                viewHolder.delete_img.setVisibility(LinearLayout.VISIBLE);
                viewHolder.delete_spi.setVisibility(LinearLayout.VISIBLE);
            }else if(! newfeedItemPosition.email.equals(mainActivity.email)){
                viewHolder.spinner_box.setVisibility(LinearLayout.GONE);
              //  viewHolder.delete_img.setVisibility(LinearLayout.GONE);
            }


            viewHolder.delete_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    select_pods_id = newfeedItemPosition.post_id;
                    update_postId = newfeedItemPosition.post_id;
                    update_content = newfeedItemPosition.content;
                    update_youtubeTitle = newfeedItemPosition.youtube_tite;
                    update_youtubeLink = newfeedItemPosition.youtube_link;
                    update_imageUrl = newfeedItemPosition.post_image_url;
                    onShowPopup(v);
                }
            });



            viewHolder.delete_spi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
            {
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                {
                    String selectedItem = parent.getItemAtPosition(position).toString();
                    if(selectedItem.equals("글 삭제"))
                    {

                        select_pods_id = newfeedItemPosition.post_id;
                        new DeleteContent().execute(new DBConnector());
                    }else if(selectedItem.equals("글 수정")){

                        update_postId = newfeedItemPosition.post_id;
                        update_content = newfeedItemPosition.content;
                        update_youtubeTitle = newfeedItemPosition.youtube_tite;
                        update_youtubeLink = newfeedItemPosition.youtube_link;
                        update_imageUrl = newfeedItemPosition.post_image_url;


                        Intent intent = new Intent(context, NewsfeedUpdateActivity.class);
                        intent.putExtra("post_type","sport_knowledge_feed");
                        intent.putExtra("post_id",update_postId);
                        intent.putExtra("content", update_content);
                        intent.putExtra("youtube_title", update_youtubeTitle);
                        intent.putExtra("youtube_link" , update_youtubeLink);
                        intent.putExtra("post_image_url" , update_imageUrl);
                        ((Activity) getContext()).startActivityForResult(intent,200);
                        //new PutContent().execute(new DBConnector());
                    }
                } // to close the onItemSelected
                public void onNothingSelected(AdapterView<?> parent)
                {

                }
            });

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
                   // Log.d("response", "youtube ㅅㅂ : " + newfeedItemPosition.youtube_id);
                  //  youtube_link = newfeedItemPosition.youtube_link;
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
                viewHolder.type.setText(" ");
                viewHolder.belong.setText(" ");
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
                    Intent intent = new Intent( context.getApplicationContext(), OtherPageActivity.class);
                    intent.putExtra("email",newfeedItemPosition.email);
                    intent.putExtra("name",newfeedItemPosition.name);
                    intent.putExtra("company",newfeedItemPosition.company);
                    intent.putExtra("member_type",newfeedItemPosition.member_type);
                    intent.putExtra("expert_type",newfeedItemPosition.expert_type);
                    intent.putExtra("sport_type",newfeedItemPosition.sport_type);
                    intent.putExtra("member_id",newfeedItemPosition.member_id);
                    intent.putExtra("mentor_type",newfeedItemPosition.mentor_type);

                    intent.putExtra("profile_url",newfeedItemPosition.profile_url);
                    intent.putExtra("phone_number",newfeedItemPosition.phone_number);
                    intent.putExtra("birthday",newfeedItemPosition.birthday);
                    intent.putExtra("is_phone_number_public",newfeedItemPosition.is_phone_number_public);
                    intent.putExtra("is_birthday_public",newfeedItemPosition.is_birthday_public);
                    intent.putExtra("region",newfeedItemPosition.region);
                    intent.putExtra("school_level",newfeedItemPosition.school_level);
                    intent.putExtra("school_name",newfeedItemPosition.school_name);
                    intent.putExtra("experience_1",newfeedItemPosition.experience_1);
                    intent.putExtra("experience_2",newfeedItemPosition.experience_2);
                    intent.putExtra("experience_3",newfeedItemPosition.experience_3);

                    ((Activity) getContext()).startActivity(intent);
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

                        intent.putExtra("email",newfeedItemPosition.email);
                        intent.putExtra("company",newfeedItemPosition.company);
                        intent.putExtra("member_id",newfeedItemPosition.member_id);
                        intent.putExtra("mentor_type",newfeedItemPosition.mentor_type);
                        intent.putExtra("phone_number",newfeedItemPosition.phone_number);
                        intent.putExtra("birthday",newfeedItemPosition.birthday);
                        intent.putExtra("is_phone_number_public",newfeedItemPosition.is_phone_number_public);
                        intent.putExtra("is_birthday_public",newfeedItemPosition.is_birthday_public);
                        intent.putExtra("region",newfeedItemPosition.region);
                        intent.putExtra("school_level",newfeedItemPosition.school_level);
                        intent.putExtra("school_name",newfeedItemPosition.school_name);
                        intent.putExtra("experience_1",newfeedItemPosition.experience_1);
                        intent.putExtra("experience_2",newfeedItemPosition.experience_2);
                        intent.putExtra("experience_3",newfeedItemPosition.experience_3);


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
                        intent.putExtra("youtube_title", newfeedItemPosition2.youtube_tite);
                        intent.putExtra("youtube_id", newfeedItemPosition2.youtube_id);


                        intent.putExtra("email",newfeedItemPosition.email);
                        intent.putExtra("company",newfeedItemPosition.company);
                        intent.putExtra("member_id",newfeedItemPosition.member_id);
                        intent.putExtra("mentor_type",newfeedItemPosition.mentor_type);
                        intent.putExtra("phone_number",newfeedItemPosition.phone_number);
                        intent.putExtra("birthday",newfeedItemPosition.birthday);
                        intent.putExtra("is_phone_number_public",newfeedItemPosition.is_phone_number_public);
                        intent.putExtra("is_birthday_public",newfeedItemPosition.is_birthday_public);
                        intent.putExtra("region",newfeedItemPosition.region);
                        intent.putExtra("school_level",newfeedItemPosition.school_level);
                        intent.putExtra("school_name",newfeedItemPosition.school_name);
                        intent.putExtra("experience_1",newfeedItemPosition.experience_1);
                        intent.putExtra("experience_2",newfeedItemPosition.experience_2);
                        intent.putExtra("experience_3",newfeedItemPosition.experience_3);

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
                        intent.putExtra("youtube_title", newfeedItemPosition2.youtube_tite);
                        intent.putExtra("youtube_id", newfeedItemPosition2.youtube_id);


                        intent.putExtra("email",newfeedItemPosition.email);
                        intent.putExtra("company",newfeedItemPosition.company);
                        intent.putExtra("member_id",newfeedItemPosition.member_id);
                        intent.putExtra("mentor_type",newfeedItemPosition.mentor_type);
                        intent.putExtra("phone_number",newfeedItemPosition.phone_number);
                        intent.putExtra("birthday",newfeedItemPosition.birthday);
                        intent.putExtra("is_phone_number_public",newfeedItemPosition.is_phone_number_public);
                        intent.putExtra("is_birthday_public",newfeedItemPosition.is_birthday_public);
                        intent.putExtra("region",newfeedItemPosition.region);
                        intent.putExtra("school_level",newfeedItemPosition.school_level);
                        intent.putExtra("school_name",newfeedItemPosition.school_name);
                        intent.putExtra("experience_1",newfeedItemPosition.experience_1);
                        intent.putExtra("experience_2",newfeedItemPosition.experience_2);
                        intent.putExtra("experience_3",newfeedItemPosition.experience_3);

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
                        intent.putExtra("youtube_title", newfeedItemPosition2.youtube_tite);
                        intent.putExtra("youtube_id", newfeedItemPosition2.youtube_id);


                        intent.putExtra("email",newfeedItemPosition.email);
                        intent.putExtra("company",newfeedItemPosition.company);
                        intent.putExtra("member_id",newfeedItemPosition.member_id);
                        intent.putExtra("mentor_type",newfeedItemPosition.mentor_type);
                        intent.putExtra("phone_number",newfeedItemPosition.phone_number);
                        intent.putExtra("birthday",newfeedItemPosition.birthday);
                        intent.putExtra("is_phone_number_public",newfeedItemPosition.is_phone_number_public);
                        intent.putExtra("is_birthday_public",newfeedItemPosition.is_birthday_public);
                        intent.putExtra("region",newfeedItemPosition.region);
                        intent.putExtra("school_level",newfeedItemPosition.school_level);
                        intent.putExtra("school_name",newfeedItemPosition.school_name);
                        intent.putExtra("experience_1",newfeedItemPosition.experience_1);
                        intent.putExtra("experience_2",newfeedItemPosition.experience_2);
                        intent.putExtra("experience_3",newfeedItemPosition.experience_3);

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

            viewHolder = new ViewHolder();
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
            viewHolder.delete_spi = (Spinner) itemView.findViewById(R.id.main_delete_spinner);
            viewHolder.spinner_box = (LinearLayout) itemView.findViewById(R.id.main_spinner_box);

//            YouTubePlayerView youTubeView = (YouTubePlayerView) itemView.findViewById(R.id.youtube_view);
//            youTubeView.initialize("AIzaSyBTzYl1ZiZSMPSBSC-9Wdw_b4km0dRzI38", this);

            if(newsFeed.newsfeedItem.size() != 0){

                final NewsfeedItem newfeedItemPosition = newsFeed.newsfeedItem.get(position);



                if(newfeedItemPosition.email.equals(mainActivity.email)){
                    Log.d("response" , "eamil : " + newfeedItemPosition.email + " id : " + mainActivity.email);

                    viewHolder.spinner_box.setVisibility(LinearLayout.VISIBLE);
                    viewHolder.delete_img.setVisibility(LinearLayout.VISIBLE);
                    viewHolder.delete_spi.setVisibility(LinearLayout.VISIBLE);
                }else if(! newfeedItemPosition.email.equals(mainActivity.email)){
                    viewHolder.spinner_box.setVisibility(LinearLayout.GONE);
                    //  viewHolder.delete_img.setVisibility(LinearLayout.GONE);
                }

                viewHolder.delete_img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        select_pods_id = newfeedItemPosition.post_id;
                        update_postId = newfeedItemPosition.post_id;
                        update_content = newfeedItemPosition.content;
                        update_youtubeTitle = newfeedItemPosition.youtube_tite;
                        update_youtubeLink = newfeedItemPosition.youtube_link;
                        update_imageUrl = newfeedItemPosition.post_image_url;

                        onShowPopup(v);
                    }
                });


                viewHolder.delete_spi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
                {
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                    {
                        String selectedItem = parent.getItemAtPosition(position).toString();
                        if(selectedItem.equals("글 삭제"))
                        {
                            select_pods_id = newfeedItemPosition.post_id;
                            new DeleteContent().execute(new DBConnector());
                        }else if(selectedItem.equals("글 수정")){

                            update_postId = newfeedItemPosition.post_id;
                            update_content = newfeedItemPosition.content;
                            update_youtubeTitle = newfeedItemPosition.youtube_tite;
                            update_youtubeLink = newfeedItemPosition.youtube_link;
                            update_imageUrl = newfeedItemPosition.post_image_url;


                            Intent intent = new Intent(context, NewsfeedUpdateActivity.class);
                            intent.putExtra("post_type","sport_knowledge_feed");
                            intent.putExtra("post_id",update_postId);
                            intent.putExtra("content", update_content);
                            intent.putExtra("youtube_title", update_youtubeTitle);
                            intent.putExtra("youtube_link" , update_youtubeLink);
                            intent.putExtra("post_image_url" , update_imageUrl);
                            ((Activity) getContext()).startActivityForResult(intent,200);
                           // new PutContent().execute(new DBConnector());
                        }
                    } // to close the onItemSelected
                    public void onNothingSelected(AdapterView<?> parent)
                    {

                    }
                });

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
                    Log.d("response", "member_type : " + newfeedItemPosition.member_type);
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
                    viewHolder.type.setText(" ");
                    viewHolder.belong.setText(" ");
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

                viewHolder.profile_img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        Intent intent = new Intent( context.getApplicationContext(), OtherPageActivity.class);
                        intent.putExtra("email",newfeedItemPosition.email);
                        intent.putExtra("name",newfeedItemPosition.name);
                        intent.putExtra("company",newfeedItemPosition.company);
                        intent.putExtra("member_type",newfeedItemPosition.member_type);
                        intent.putExtra("expert_type",newfeedItemPosition.expert_type);
                        intent.putExtra("sport_type",newfeedItemPosition.sport_type);
                        intent.putExtra("member_id",newfeedItemPosition.member_id);
                        intent.putExtra("profile_url",newfeedItemPosition.profile_url);
                        intent.putExtra("phone_number",newfeedItemPosition.phone_number);
                        intent.putExtra("birthday",newfeedItemPosition.birthday);
                        intent.putExtra("mentor_type",newfeedItemPosition.mentor_type);
                        intent.putExtra("is_phone_number_public",newfeedItemPosition.is_phone_number_public);
                        intent.putExtra("is_birthday_public",newfeedItemPosition.is_birthday_public);
                        intent.putExtra("region",newfeedItemPosition.region);
                        intent.putExtra("school_level",newfeedItemPosition.school_level);
                        intent.putExtra("school_name",newfeedItemPosition.school_name);
                        intent.putExtra("experience_1",newfeedItemPosition.experience_1);
                        intent.putExtra("experience_2",newfeedItemPosition.experience_2);
                        intent.putExtra("experience_3",newfeedItemPosition.experience_3);

                        ((Activity) getContext()).startActivity(intent);
                    }
                });
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
                        intent.putExtra("youtube_title", newfeedItemPosition2.youtube_tite);
                        intent.putExtra("youtube_id", newfeedItemPosition2.youtube_id);
                        intent.putExtra("token", token);


                        intent.putExtra("email",newfeedItemPosition2.email);
                        intent.putExtra("company",newfeedItemPosition2.company);
                        intent.putExtra("member_id",newfeedItemPosition2.member_id);
                        intent.putExtra("mentor_type",newfeedItemPosition2.mentor_type);
                        intent.putExtra("phone_number",newfeedItemPosition2.phone_number);
                        intent.putExtra("birthday",newfeedItemPosition2.birthday);
                        intent.putExtra("is_phone_number_public",newfeedItemPosition2.is_phone_number_public);
                        intent.putExtra("is_birthday_public",newfeedItemPosition2.is_birthday_public);
                        intent.putExtra("region",newfeedItemPosition2.region);
                        intent.putExtra("school_level",newfeedItemPosition2.school_level);
                        intent.putExtra("school_name",newfeedItemPosition2.school_name);
                        intent.putExtra("experience_1",newfeedItemPosition2.experience_1);
                        intent.putExtra("experience_2",newfeedItemPosition2.experience_2);
                        intent.putExtra("experience_3",newfeedItemPosition2.experience_3);

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
                        intent.putExtra("youtube_title", newfeedItemPosition2.youtube_tite);
                        intent.putExtra("youtube_id", newfeedItemPosition2.youtube_id);
                        intent.putExtra("token", token);
                        intent.putExtra("email",newfeedItemPosition2.email);
                        intent.putExtra("company",newfeedItemPosition2.company);
                        intent.putExtra("member_id",newfeedItemPosition2.member_id);
                        intent.putExtra("mentor_type",newfeedItemPosition2.mentor_type);
                        intent.putExtra("phone_number",newfeedItemPosition2.phone_number);
                        intent.putExtra("birthday",newfeedItemPosition2.birthday);
                        intent.putExtra("is_phone_number_public",newfeedItemPosition2.is_phone_number_public);
                        intent.putExtra("is_birthday_public",newfeedItemPosition2.is_birthday_public);
                        intent.putExtra("region",newfeedItemPosition2.region);
                        intent.putExtra("school_level",newfeedItemPosition2.school_level);
                        intent.putExtra("school_name",newfeedItemPosition2.school_name);
                        intent.putExtra("experience_1",newfeedItemPosition2.experience_1);
                        intent.putExtra("experience_2",newfeedItemPosition2.experience_2);
                        intent.putExtra("experience_3",newfeedItemPosition2.experience_3);

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
                        intent.putExtra("youtube_title", newfeedItemPosition2.youtube_tite);
                        intent.putExtra("youtube_id", newfeedItemPosition2.youtube_id);
                        intent.putExtra("token", token);
                        intent.putExtra("email",newfeedItemPosition2.email);
                        intent.putExtra("company",newfeedItemPosition2.company);
                        intent.putExtra("member_id",newfeedItemPosition2.member_id);
                        intent.putExtra("mentor_type",newfeedItemPosition2.mentor_type);
                        intent.putExtra("phone_number",newfeedItemPosition2.phone_number);
                        intent.putExtra("birthday",newfeedItemPosition2.birthday);
                        intent.putExtra("is_phone_number_public",newfeedItemPosition2.is_phone_number_public);
                        intent.putExtra("is_birthday_public",newfeedItemPosition2.is_birthday_public);
                        intent.putExtra("region",newfeedItemPosition2.region);
                        intent.putExtra("school_level",newfeedItemPosition2.school_level);
                        intent.putExtra("school_name",newfeedItemPosition2.school_name);
                        intent.putExtra("experience_1",newfeedItemPosition2.experience_1);
                        intent.putExtra("experience_2",newfeedItemPosition2.experience_2);
                        intent.putExtra("experience_3",newfeedItemPosition2.experience_3);

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
                        intent.putExtra("youtube_title", newfeedItemPosition2.youtube_tite);
                        intent.putExtra("youtube_id", newfeedItemPosition2.youtube_id);
                        intent.putExtra("token", token);
                        intent.putExtra("email",newfeedItemPosition2.email);
                        intent.putExtra("company",newfeedItemPosition2.company);
                        intent.putExtra("member_id",newfeedItemPosition2.member_id);
                        intent.putExtra("mentor_type",newfeedItemPosition2.mentor_type);
                        intent.putExtra("phone_number",newfeedItemPosition2.phone_number);
                        intent.putExtra("birthday",newfeedItemPosition2.birthday);
                        intent.putExtra("is_phone_number_public",newfeedItemPosition2.is_phone_number_public);
                        intent.putExtra("is_birthday_public",newfeedItemPosition2.is_birthday_public);
                        intent.putExtra("region",newfeedItemPosition2.region);
                        intent.putExtra("school_level",newfeedItemPosition2.school_level);
                        intent.putExtra("school_name",newfeedItemPosition2.school_name);
                        intent.putExtra("experience_1",newfeedItemPosition2.experience_1);
                        intent.putExtra("experience_2",newfeedItemPosition2.experience_2);
                        intent.putExtra("experience_3",newfeedItemPosition2.experience_3);

                       // Log.d("response", "03200 : " + newfeedItemPosition2.member_type);
                        ((Activity) getContext()).startActivityForResult(intent,200);

                    }


                }
            });

            viewHolder.like_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
               //     Log.d("response" , "like btn : " + position);

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

    private class DeleteContent extends AsyncTask<DBConnector, Long, Integer> {


        @Override
        protected Integer doInBackground(DBConnector... params) {

            //it is executed on Background thread
       //     Log.d("response22 " , "delete" + token + "  " + select_pods_id);
            return params[0].DeleteContent(token, select_pods_id);

        }

        @Override
        protected void onPostExecute(final Integer jsonObject) {

            settextToAdapter_deleteContent(jsonObject);

        }
    }

    public void settextToAdapter_deleteContent(Integer jsonObject) {

        mainActivity.onStart();


        if(jsonObject == null){

        }else{

        }

    }



    private class PutContent extends AsyncTask<DBConnector, Long, JSONObject> {


        @Override
        protected JSONObject doInBackground(DBConnector... params) {

            //it is executed on Background thread
            //     Log.d("response22 " , "delete" + token + "  " + select_pods_id);
            return params[0].PutContent(token, update_postId, update_postType, update_content, update_youtubeTitle, update_youtubeLink, update_imageUrl);

        }

        @Override
        protected void onPostExecute(final JSONObject jsonObject) {

            settextToAdapter_putContent(jsonObject);

        }
    }

    public void settextToAdapter_putContent(JSONObject jsonObject) {


        Intent intent = new Intent( context.getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        ((Activity) getContext()).startActivity(intent);


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
        final View inflatedView = layoutInflater.inflate(R.layout.editcontent_popup, null,false);
        // find the ListView in the popup layout
        TextView edit_content = (TextView)inflatedView.findViewById(R.id.edit_content_btn);
        TextView delete_content = (TextView)inflatedView.findViewById(R.id.delete_content_btn);

        edit_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, NewsfeedUpdateActivity.class);
                intent.putExtra("post_type","sport_knowledge_feed");
                intent.putExtra("post_id",update_postId);
                intent.putExtra("content", update_content);
                intent.putExtra("youtube_title", update_youtubeTitle);
                intent.putExtra("youtube_link" , update_youtubeLink);
                intent.putExtra("post_image_url" , update_imageUrl);
                ((Activity) getContext()).startActivityForResult(intent,200);
                popWindow.dismiss();
            }
        });

        delete_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DeleteContent().execute(new DBConnector());
                popWindow.dismiss();
            }
        });
        // get device size
        Display display =  ((Activity)context).getWindowManager().getDefaultDisplay();
        final Point size = new Point();
        display.getSize(size);
        // mDeviceHeight = size.y;


        // fill the data to the list items
      //  setSimpleList(listView);


        // set height depends on the device size
        popWindow = new PopupWindow(inflatedView, size.x - 100,size.y/4, true );
        // set a background drawable with rounders corners
        popWindow.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.comment_rounded));
        // make it focusable to show the keyboard to enter in `EditText`
        popWindow.setFocusable(true);
        // make it outside touchable to dismiss the popup window
        popWindow.setOutsideTouchable(true);

        // show the popup at bottom of the screen and set some margin at bottom ie,
        popWindow.showAtLocation(v, Gravity.CENTER_VERTICAL, 0,100);
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


}


