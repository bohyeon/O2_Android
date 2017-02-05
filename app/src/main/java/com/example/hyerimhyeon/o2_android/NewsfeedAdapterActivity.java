package com.example.hyerimhyeon.o2_android;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

public class NewsfeedAdapterActivity extends ArrayAdapter<NewsfeedItem> {

    LayoutInflater layoutInflater;
    NewsFeed newsFeed;
    MainActivity mainActivity;
    Context context;
    NewsfeedItem newsfeedItem;

    NewsfeedAdapterActivity newsfeedAdapterActivity;

    TextView name, type, belong, regist_date, content, like;

    private PopupWindow popWindow;

    public NewsfeedAdapterActivity(Context context, NewsFeed newsFeed, MainActivity mainActivity) {

        super(context, R.layout.activity_main_newsfeed, newsFeed.newsfeedItem);

        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.newsFeed = newsFeed;
        this.mainActivity = mainActivity;
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
            like = (TextView) itemView.findViewById(R.id.main_newsfeed_like);

            final NewsfeedItem newfeedItemPosition = newsFeed.newsfeedItem.get(position);
            name.setText(newfeedItemPosition.name);
            type.setText(newfeedItemPosition.type);
            belong.setText(newfeedItemPosition.belong);
            regist_date.setText(newfeedItemPosition.regist_date);
            content.setText(newfeedItemPosition.content);

            like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onShowPopup(v);
                }
            });

            return itemView;

        } else {

            itemView = convertView;

            name = (TextView) itemView.findViewById(R.id.main_newsfeed_lv_name);
            type = (TextView) itemView.findViewById(R.id.main_newsfeed_lv_type);
            belong = (TextView) itemView.findViewById(R.id.main_newsfeed_lv_belong);
            regist_date = (TextView) itemView.findViewById(R.id.main_newsfeed_lv_registDate);
            content = (TextView) itemView.findViewById(R.id.main_newsfeed_lv_content);
            like = (TextView) itemView.findViewById(R.id.main_newsfeed_like);

            if(newsFeed.newsfeedItem.size() != 0){

                final NewsfeedItem newfeedItemPosition = newsFeed.newsfeedItem.get(position);
                name.setText(newfeedItemPosition.name);
                type.setText(newfeedItemPosition.type);
                belong.setText(newfeedItemPosition.belong);
                regist_date.setText(newfeedItemPosition.regist_date);
                content.setText(newfeedItemPosition.content);

            }

            like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onShowPopup(v);
                }
            });

            return  convertView;

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
        setSimpleList(listView);


        // set height depends on the device size
        popWindow = new PopupWindow(inflatedView, size.x - 50,size.y - 250, true );
        // set a background drawable with rounders corners
        popWindow.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.comment_rounded));
        // make it focusable to show the keyboard to enter in `EditText`
        popWindow.setFocusable(true);
        // make it outside touchable to dismiss the popup window
        popWindow.setOutsideTouchable(true);

        // show the popup at bottom of the screen and set some margin at bottom ie,
        popWindow.showAtLocation(v, Gravity.BOTTOM, 0,100);
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


