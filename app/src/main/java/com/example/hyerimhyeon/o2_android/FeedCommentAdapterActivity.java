package com.example.hyerimhyeon.o2_android;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.DB.DBConnector;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;

public class FeedCommentAdapterActivity extends ArrayAdapter<NewsfeedItem> {

    LayoutInflater layoutInflater;
    LinearLayout comment_box;
    NewsFeed newsFeed;
    FeedDetailActivity feedDetailActivity;
    Context context;
    NewsfeedItem newsfeedItem;

    FeedCommentAdapterActivity newsfeedAdapterActivity;
    public SharedPreferences loginPreferences;

    TextView name, type, belong, regist_date, content, like, like_btn, comment_btn, comment_change, comment_remove;
    ImageView profile_img;
    String comment_id, token;

    private PopupWindow popWindow;

    public FeedCommentAdapterActivity(Context context, NewsFeed newsFeed, FeedDetailActivity feedDetailActivity) {

        super(context, R.layout.fb_comments_list_item, newsFeed.newsfeedItem);

        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.newsFeed = newsFeed;
        this.feedDetailActivity = feedDetailActivity;
        this.context = context;
        this.newsfeedItem = newsfeedItem;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View itemView;

        token = feedDetailActivity.token;

        if (convertView == null) {
            itemView = layoutInflater.inflate(R.layout.fb_comments_list_item, parent, false);

            name = (TextView) itemView.findViewById(R.id.comment_newsfeed_lv_name);
            type = (TextView) itemView.findViewById(R.id.comment_newsfeed_lv_type);
            belong = (TextView) itemView.findViewById(R.id.comment_newsfeed_lv_belong);
            content = (TextView) itemView.findViewById(R.id.comment_newsfeed_lv_content);
            profile_img = (ImageView) itemView.findViewById(R.id.comment_profile_img);
            comment_change = (TextView) itemView.findViewById(R.id.comment_change);
            comment_remove = (TextView) itemView.findViewById(R.id.comment_remove);
            comment_box = (LinearLayout) itemView.findViewById(R.id.detail_comment_layout);

            final NewsfeedItem newfeedItemPosition = newsFeed.newsfeedItem.get(position);
            name.setText(newfeedItemPosition.name);
            content.setText(newfeedItemPosition.content);


                String type_str = "";
                if(newfeedItemPosition.type.equals("mentor")){
                    type_str = "멘토";
                }else if(newfeedItemPosition.type.equals("mentee")){
                    type_str = "꿈나무";
                }else if(newfeedItemPosition.type.equals("expert")){
                    type_str = "전문가";
                }

               type.setText(type_str);


            if(!newfeedItemPosition.type.equals("mentee")){
                belong.setText(newfeedItemPosition.company);
            }else{
                belong.setText("");
            }

            if(newfeedItemPosition.profile_url.equals("")|| newfeedItemPosition.profile_url == null||newfeedItemPosition.profile_url == " "||newfeedItemPosition.profile_url == "null"|| newfeedItemPosition.profile_url == "" || newfeedItemPosition.profile_url=="http://" || newfeedItemPosition.profile_url=="http://null" || newfeedItemPosition.profile_url.equals("http://") || newfeedItemPosition.profile_url.equals("http:/null/")){

            }else{
                new DownLoadImageTask_profile(profile_img).execute("http://"+newfeedItemPosition.profile_url);

            }
            Log.d("response" , "id : " + feedDetailActivity.email + " id2 : " + newfeedItemPosition.email);
            if(feedDetailActivity.email.equals(newfeedItemPosition.email)){
                comment_box.setVisibility(LinearLayout.VISIBLE);
                comment_remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        comment_id = newfeedItemPosition.id;
                        new DeleteComment().execute(new DBConnector());
                    }
                });

            }else{
                comment_box.setVisibility(LinearLayout.GONE);
            }



            return itemView;

        } else {

            itemView = convertView;

            name = (TextView) itemView.findViewById(R.id.comment_newsfeed_lv_name);
            type = (TextView) itemView.findViewById(R.id.comment_newsfeed_lv_type);
            belong = (TextView) itemView.findViewById(R.id.comment_newsfeed_lv_belong);
       //     regist_date = (TextView) itemView.findViewById(R.id.comment_newsfeed_lv_registDate);
            content = (TextView) itemView.findViewById(R.id.comment_newsfeed_lv_content);
            profile_img = (ImageView) itemView.findViewById(R.id.comment_profile_img);
            comment_change = (TextView) itemView.findViewById(R.id.comment_change);
            comment_remove = (TextView) itemView.findViewById(R.id.comment_remove);
            comment_box = (LinearLayout) itemView.findViewById(R.id.detail_comment_layout);

            if(newsFeed.newsfeedItem.size() != 0){

                final NewsfeedItem newfeedItemPosition = newsFeed.newsfeedItem.get(position);
                name.setText(newfeedItemPosition.name);
                content.setText(newfeedItemPosition.content);


                String type_str = "";
                if(newfeedItemPosition.type.equals("mentor")){
                    type_str = "멘토";
                }else if(newfeedItemPosition.type.equals("mentee")){
                    type_str = "꿈나무";
                }else if(newfeedItemPosition.type.equals("expert")){
                    type_str = "전문가";
                }

                type.setText(type_str);


                if(!newfeedItemPosition.type.equals("mentee")){
                    belong.setText(newfeedItemPosition.company);
                }else{
                    belong.setText("");
                }


                if(newfeedItemPosition.profile_url.equals("")|| newfeedItemPosition.profile_url == null||newfeedItemPosition.profile_url == " "||newfeedItemPosition.profile_url == "null"|| newfeedItemPosition.profile_url == "" || newfeedItemPosition.profile_url=="http://" || newfeedItemPosition.profile_url=="http://null" || newfeedItemPosition.profile_url.equals("http://") || newfeedItemPosition.profile_url.equals("http:/null/")){

                }else{
                    new DownLoadImageTask_profile(profile_img).execute("http://"+newfeedItemPosition.profile_url);

                }

                Log.d("response" , "id : " + feedDetailActivity.email + " id2 : " + newfeedItemPosition.email);
                if(feedDetailActivity.email.equals(newfeedItemPosition.email)){
                    comment_box.setVisibility(LinearLayout.VISIBLE);
                    comment_remove.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            comment_id = newfeedItemPosition.id;
                            new DeleteComment().execute(new DBConnector());
                        }
                    });

                }else{
                    comment_box.setVisibility(LinearLayout.GONE);
                }


            }

            return  convertView;

        }


        }


    private class DeleteComment extends AsyncTask<DBConnector, Long, JSONObject> {


        @Override
        protected JSONObject doInBackground(DBConnector... params) {

            //it is executed on Background thread

            Log.d("response" , "comment id  : " + comment_id + token);
            return params[0].DeleteComment(token, comment_id);

        }

        @Override
        protected void onPostExecute(final JSONObject jsonArray) {

            settextToAdapter(jsonArray);


        }
    }

    public void settextToAdapter(JSONObject jsonArray) {

        //  Log.d("response" , "post : " + jsonArray.toString());

        // ArrayList<NewsfeedItem> newsfeedItems = newsFeed.newsfeedItem;
        NewsfeedItem newsfeedItem;


        if(jsonArray == null){
           feedDetailActivity.GetCommentToAdapter();
        }else{


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


