package com.o2_company.hyerimhyeon.o2_android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.o2_company.DB.DBConnector;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class MypageAlarmAdapterActivity extends ArrayAdapter<NewsfeedItem_Noti> {

    LayoutInflater layoutInflater;
    NewsFeed_Noti newsFeed2;
    MypageActivity mypageActivity;
    ImageView profile_img;
    Context context;
    String post_id;
    NewsfeedItem_Noti newsfeedItem;

    MypageAlarmAdapterActivity newsfeedAdapterActivity;

    TextView content, date;

    public MypageAlarmAdapterActivity(Context context, NewsFeed_Noti newsFeed2, MypageActivity mypageActivity) {

        super(context, R.layout.activity_alarm_item, newsFeed2.newsfeedItem);

        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.newsFeed2 = newsFeed2;
        this.mypageActivity = mypageActivity;
        this.context = context;
        this.newsfeedItem = newsfeedItem;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View itemView;

        if (convertView == null) {
            itemView = layoutInflater.inflate(R.layout.activity_alarm_item, parent, false);


            content = (TextView) itemView.findViewById(R.id.alarm_content);
            date = (TextView) itemView.findViewById(R.id.alarm_date);
            profile_img = (ImageView) itemView.findViewById(R.id.alarm_photo);

            final NewsfeedItem_Noti newfeedItemPosition = newsFeed2.newsfeedItem.get(position);
        //    Log.d("response" , "comment post noti : " + newfeedItemPosition.name);
            content.setText(newfeedItemPosition.name);
            date.setText(newfeedItemPosition.regist_date);
            Picasso.with(context)
                    .load("http://"+newfeedItemPosition.profile_url)
                    .placeholder(R.drawable.blankimg)
                    .error(R.drawable.blankimg)
                    .into(profile_img);


            profile_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    post_id = newfeedItemPosition.post_id;
                    new GetPosts_detail().execute(new DBConnector());
                }
            });

            content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    post_id = newfeedItemPosition.post_id;
                    new GetPosts_detail().execute(new DBConnector());
                }
            });


            return itemView;

        } else {

            itemView = convertView;

            content = (TextView) itemView.findViewById(R.id.alarm_content);
            date = (TextView) itemView.findViewById(R.id.alarm_date);
            profile_img = (ImageView) itemView.findViewById(R.id.alarm_photo);

            if(newsFeed2.newsfeedItem.size() != 0){

                final NewsfeedItem_Noti newfeedItemPosition = newsFeed2.newsfeedItem.get(position);
              //  Log.d("response" , "comment post noti : " + newfeedItemPosition.name);
                content.setText(newfeedItemPosition.name);
                date.setText(newfeedItemPosition.regist_date);
                Picasso.with(context)
                        .load("http://"+newfeedItemPosition.profile_url)
                        .placeholder(R.drawable.blankimg)
                        .error(R.drawable.blankimg)
                        .into(profile_img);


                profile_img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        post_id = newfeedItemPosition.post_id;
                        new GetPosts_detail().execute(new DBConnector());
                    }
                });

                content.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        post_id = newfeedItemPosition.post_id;
                        new GetPosts_detail().execute(new DBConnector());
                    }
                });

            }


            return  convertView;

        }


        }


    private class GetPosts_detail extends AsyncTask<DBConnector, Long, JSONObject> {


        @Override
        protected JSONObject doInBackground(DBConnector... params) {

            //it is executed on Background thread


            return params[0].GetPost_Detail(mypageActivity.token, post_id);

        }

        @Override
        protected void onPostExecute(final JSONObject jsonObject) {

            settextToAdapter(jsonObject);


        }
    }

    public void settextToAdapter(final JSONObject jsonObject) {



        Runnable run = new Runnable()
        {
            @Override
            public void run()
            {

                if(jsonObject == null){
                    Toast.makeText(getContext(), "해당 게시글이 없습니다.",
                            Toast.LENGTH_LONG).show();
                }else {

                    try {

                        Log.d("response", "what?" + post_id);
                        JSONObject userJsonObj = jsonObject.getJSONObject("user");

                        Intent intent = new Intent( context.getApplicationContext(), FeedDetailActivity.class);
                        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("post_id",post_id);
                        String youtube_link = jsonObject.getString("youtube_link");
                        String youtube_id = null;
                        String youtube_title = jsonObject.getString("youtube_title");


                        if(youtube_link == null || youtube_link.equals("") ){
                            youtube_id = "";
                        }else{
                            //  newsfeedItem.youtube_tite = userJsonObj.getString("youtube_title");
                            String str = youtube_link;
                            String video_id = "";

                            if(str.toString().indexOf("youtube.com") != -1) {
                                if(str.indexOf("&") > 0){
                                    video_id = str.substring(str.indexOf("=")+1 , str.indexOf("&"));
                                }else{
                                    video_id = str.substring(str.indexOf("=")+1);
                                }
                            }else if(str.toString().indexOf("youtu.be") != -1 ){
                                //   Log.d("response", "youtube_str :  "+ str);
                                video_id = str.substring(17);
                               // Log.d("response", "youtube_id :  "+ video_id);
                            }

                            youtube_id = video_id;

                        }


                        

                        intent.putExtra("name",userJsonObj.getString("name"));
                        intent.putExtra("type",userJsonObj.getString("member_type"));
                        intent.putExtra("belong",userJsonObj.getString("company"));
                     //   intent.putExtra("regist_date",jsonObject.getString("timestamp").substring(0,10));
                        intent.putExtra("content",jsonObject.getString("content"));
                        intent.putExtra("like_num",jsonObject.getString("like_num"));
                        intent.putExtra("comment_num",jsonObject.getString("comment_num"));
                        intent.putExtra("is_like", jsonObject.getBoolean("is_like"));
                        intent.putExtra("post_image_url",jsonObject.getString("post_image_url"));
                        intent.putExtra("profile_url", userJsonObj.getString("profile_url"));
                        intent.putExtra("youtube_link",youtube_link);
                        intent.putExtra("youtube_id", youtube_id);
                        intent.putExtra("youtube_title", youtube_title);
                        intent.putExtra("token", mypageActivity.token);
//                        Log.d("response","0320 : " + newfeedItemPosition2.member_type);
//                        Log.d("response", "name2 : " + newfeedItemPosition2.member_type);
                        ((Activity) getContext()).startActivityForResult(intent,200);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }


            }
        };

        // 속도의 딜레이를 구현하기 위한 꼼수
        Handler handler = new Handler();
        handler.postDelayed(run, 500);


    }

    }


