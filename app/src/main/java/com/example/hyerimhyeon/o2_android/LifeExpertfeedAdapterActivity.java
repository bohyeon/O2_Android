package com.example.hyerimhyeon.o2_android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class LifeExpertfeedAdapterActivity extends ArrayAdapter<NewsfeedItem> {

    LayoutInflater layoutInflater;
    NewsFeed newsFeed;
    LifeExpertFeedActivity lifeExpertFeedActivity;
    Context context;
    NewsfeedItem newsfeedItem;

    LifeExpertfeedAdapterActivity newsfeedAdapterActivity;

    TextView name, type, belong, regist_date, content;

    public LifeExpertfeedAdapterActivity(Context context, NewsFeed newsFeed, LifeExpertFeedActivity lifeExpertFeedActivity) {

        super(context, R.layout.activity_main_newsfeed, newsFeed.newsfeedItem);

        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.newsFeed = newsFeed;
        this.lifeExpertFeedActivity = lifeExpertFeedActivity;
        this.context = context;
        this.newsfeedItem = newsfeedItem;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View itemView;

        if (convertView == null) {
            itemView = layoutInflater.inflate(R.layout.activity_main_newsfeed, parent, false);

            name = (TextView) itemView.findViewById(R.id.main_newsfeed_lv_name);
            type = (TextView) itemView.findViewById(R.id.main_newsfeed_lv_type);
            belong = (TextView) itemView.findViewById(R.id.main_newsfeed_lv_belong);
            regist_date = (TextView) itemView.findViewById(R.id.main_newsfeed_lv_registDate);
            content = (TextView) itemView.findViewById(R.id.main_newsfeed_lv_content);

            final NewsfeedItem newfeedItemPosition = newsFeed.newsfeedItem.get(position);
            name.setText(newfeedItemPosition.name);
            type.setText(newfeedItemPosition.type);
            belong.setText(newfeedItemPosition.belong);
            regist_date.setText(newfeedItemPosition.regist_date);
            content.setText(newfeedItemPosition.content);

            return itemView;

        } else {

            itemView = convertView;

            name = (TextView) itemView.findViewById(R.id.main_newsfeed_lv_name);
            type = (TextView) itemView.findViewById(R.id.main_newsfeed_lv_type);
            belong = (TextView) itemView.findViewById(R.id.main_newsfeed_lv_belong);
            regist_date = (TextView) itemView.findViewById(R.id.main_newsfeed_lv_registDate);
            content = (TextView) itemView.findViewById(R.id.main_newsfeed_lv_content);

            if(newsFeed.newsfeedItem.size() != 0){

                final NewsfeedItem newfeedItemPosition = newsFeed.newsfeedItem.get(position);
                name.setText(newfeedItemPosition.name);
                type.setText(newfeedItemPosition.type);
                belong.setText(newfeedItemPosition.belong);
                regist_date.setText(newfeedItemPosition.regist_date);
                content.setText(newfeedItemPosition.content);

            }


            return  convertView;

        }


        }


    }


