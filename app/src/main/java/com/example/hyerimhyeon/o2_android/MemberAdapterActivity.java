package com.example.hyerimhyeon.o2_android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MemberAdapterActivity extends ArrayAdapter<NewsfeedItem> {

    LayoutInflater layoutInflater;
    NewsFeed newsFeed;
    MemberActivity memberActivity;
    Context context;
    NewsfeedItem newsfeedItem;

    MemberAdapterActivity newsfeedAdapterActivity;

    TextView name, type, belong, regist_date, content;

    public MemberAdapterActivity(Context context, NewsFeed newsFeed, MemberActivity memberActivity) {

        super(context, R.layout.activity_member, newsFeed.newsfeedItem);

        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.newsFeed = newsFeed;
        this.memberActivity = memberActivity;
        this.context = context;
        this.newsfeedItem = newsfeedItem;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View itemView;

        if (convertView == null) {
            itemView = layoutInflater.inflate(R.layout.activity_member_item, parent, false);

            name = (TextView) itemView.findViewById(R.id.member_lv_name);
            type = (TextView) itemView.findViewById(R.id.member_lv_type);
            belong = (TextView) itemView.findViewById(R.id.member_lv_belong);
          
            final NewsfeedItem newfeedItemPosition = newsFeed.newsfeedItem.get(position);
            name.setText(newfeedItemPosition.name);
            type.setText(newfeedItemPosition.type);
            belong.setText(newfeedItemPosition.belong);
          
            return itemView;

        } else {

            itemView = convertView;

            name = (TextView) itemView.findViewById(R.id.member_lv_name);
            type = (TextView) itemView.findViewById(R.id.member_lv_type);
            belong = (TextView) itemView.findViewById(R.id.member_lv_belong);
          
            if(newsFeed.newsfeedItem.size() != 0){

                final NewsfeedItem newfeedItemPosition = newsFeed.newsfeedItem.get(position);
                name.setText(newfeedItemPosition.name);
                type.setText(newfeedItemPosition.type);
                belong.setText(newfeedItemPosition.belong);
               
            }


            return  convertView;

        }


        }


    }


