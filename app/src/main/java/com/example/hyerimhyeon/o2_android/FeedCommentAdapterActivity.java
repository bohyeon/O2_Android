package com.example.hyerimhyeon.o2_android;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
    String comment_id, token , comment_id_change, content_change;

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

                comment_change.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onShowPopup(v,newfeedItemPosition.content, newfeedItemPosition.id);
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

                    comment_change.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onShowPopup(v, newfeedItemPosition.content, newfeedItemPosition.id);
                        }
                    });

                }else{
                    comment_box.setVisibility(LinearLayout.GONE);
                }


            }

            return  convertView;

        }


        }


    private class PutComment extends AsyncTask<DBConnector, Long, JSONObject> {


        @Override
        protected JSONObject doInBackground(DBConnector... params) {

            //it is executed on Background thread

//            Log.d("response" , "comment id  : " + comment_id_change + content_change);
            return params[0].PutComment(token, comment_id_change, content_change);

        }

        @Override
        protected void onPostExecute(final JSONObject jsonArray) {

            settextToAdapter_change(jsonArray);


        }
    }

    public void settextToAdapter_change(JSONObject jsonArray) {

        //Log.d("response" , "초 : " + jsonArray);

        // ArrayList<NewsfeedItem> newsfeedItems = newsFeed.newsfeedItem;
        NewsfeedItem newsfeedItem;
        feedDetailActivity.GetCommentToAdapter();

        if(jsonArray == null){

        }else{


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

    // call this method when required to show popup
    public void onShowPopup(View v, final String content, final String id){

        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // inflate the custom popup layout
        final View inflatedView = layoutInflater.inflate(R.layout.fb_popup_layout, null,false);
        // find the ListView in the popup layout
        ListView listView = (ListView)inflatedView.findViewById(R.id.commentsListView);
        final EditText editText = (EditText)inflatedView.findViewById(R.id.changeComment);
        TextView complete_btn = (TextView)inflatedView.findViewById(R.id.change_writeComment_completeBtn);

        // get device size
        Display display =  ((Activity)context).getWindowManager().getDefaultDisplay();
        final Point size = new Point();
        display.getSize(size);
        // mDeviceHeight = size.y;
        editText.setText(content);
        complete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comment_id_change = id;
                content_change = editText.getText().toString().trim();
                new PutComment().execute(new DBConnector());
                popWindow.dismiss();
            }
        });

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
        popWindow.showAtLocation(v, Gravity.CENTER, 0,100);
    }
}


