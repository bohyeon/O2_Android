package com.example.hyerimhyeon.o2_android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.PopupWindow;
import android.widget.TextView;

public class FeedCommentAdapterActivity extends ArrayAdapter<NewsfeedItem> {

    LayoutInflater layoutInflater;
    NewsFeed newsFeed;
    FeedDetailActivity feedDetailActivity;
    Context context;
    NewsfeedItem newsfeedItem;

    FeedCommentAdapterActivity newsfeedAdapterActivity;

    TextView name, type, belong, regist_date, content, like;

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

        if (convertView == null) {
            itemView = layoutInflater.inflate(R.layout.fb_comments_list_item, parent, false);

            name = (TextView) itemView.findViewById(R.id.comment_newsfeed_lv_name);
            type = (TextView) itemView.findViewById(R.id.comment_newsfeed_lv_type);
            belong = (TextView) itemView.findViewById(R.id.comment_newsfeed_lv_belong);
           // regist_date = (TextView) itemView.findViewById(R.id.comment_newsfeed_lv_registDate);
            content = (TextView) itemView.findViewById(R.id.comment_newsfeed_lv_content);

            final NewsfeedItem newfeedItemPosition = newsFeed.newsfeedItem.get(position);
            name.setText(newfeedItemPosition.name);
            type.setText(newfeedItemPosition.type);
            belong.setText(newfeedItemPosition.belong);
           // regist_date.setText(newfeedItemPosition.regist_date);
            content.setText(newfeedItemPosition.content);

            return itemView;

        } else {

            itemView = convertView;

            name = (TextView) itemView.findViewById(R.id.comment_newsfeed_lv_name);
            type = (TextView) itemView.findViewById(R.id.comment_newsfeed_lv_type);
            belong = (TextView) itemView.findViewById(R.id.comment_newsfeed_lv_belong);
       //     regist_date = (TextView) itemView.findViewById(R.id.comment_newsfeed_lv_registDate);
            content = (TextView) itemView.findViewById(R.id.comment_newsfeed_lv_content);


            if(newsFeed.newsfeedItem.size() != 0){

                final NewsfeedItem newfeedItemPosition = newsFeed.newsfeedItem.get(position);
                name.setText(newfeedItemPosition.name);
                type.setText(newfeedItemPosition.type);
                belong.setText(newfeedItemPosition.belong);
             //   regist_date.setText(newfeedItemPosition.regist_date);
                content.setText(newfeedItemPosition.content);

            }

            return  convertView;

        }


        }



    }


