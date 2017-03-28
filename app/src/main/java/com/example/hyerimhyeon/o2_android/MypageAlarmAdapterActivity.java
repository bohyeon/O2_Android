package com.example.hyerimhyeon.o2_android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MypageAlarmAdapterActivity extends ArrayAdapter<NewsfeedItem_Noti> {

    LayoutInflater layoutInflater;
    NewsFeed_Noti newsFeed;
    MypageActivity mypageActivity;
    Context context;
    NewsfeedItem_Noti newsfeedItem;

    MypageAlarmAdapterActivity newsfeedAdapterActivity;

    TextView content, date;

    public MypageAlarmAdapterActivity(Context context, NewsFeed_Noti newsFeed, MypageActivity mypageActivity) {

        super(context, R.layout.activity_alarm_item, newsFeed.newsfeedItem);

        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.newsFeed = newsFeed;
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

            final NewsfeedItem_Noti newfeedItemPosition = newsFeed.newsfeedItem.get(position);
            content.setText("김진흥님이 댓글을 달았습니다.");
            date.setText(newfeedItemPosition.regist_date);

            return itemView;

        } else {

            itemView = convertView;

            content = (TextView) itemView.findViewById(R.id.alarm_content);
            date = (TextView) itemView.findViewById(R.id.alarm_date);

            if(newsFeed.newsfeedItem.size() != 0){

                final NewsfeedItem_Noti newfeedItemPosition = newsFeed.newsfeedItem.get(position);
                content.setText("김진흥님이 댓글을 달았습니다.");
                date.setText(newfeedItemPosition.regist_date);

            }


            return  convertView;

        }


        }


    }


