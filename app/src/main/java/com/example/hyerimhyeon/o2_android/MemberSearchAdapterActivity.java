package com.example.hyerimhyeon.o2_android;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubePlayerView;
import com.squareup.picasso.Picasso;

public class MemberSearchAdapterActivity extends ArrayAdapter<NewsfeedItem> {

    LayoutInflater layoutInflater;
    NewsFeed newsFeed;
    MemberActivity memberActivity;
    Context context;
    NewsfeedItem newsfeedItem;

    MemberSearchAdapterActivity newsfeedAdapterActivity;

  //  TextView name, type, belong, regist_date, content;

    public MemberSearchAdapterActivity(Context context, NewsFeed newsFeed, MemberActivity memberActivity) {

        super(context, R.layout.activity_member, newsFeed.newsfeedItem);

        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.newsFeed = newsFeed;
        this.memberActivity = memberActivity;
        this.context = context;
        this.newsfeedItem = newsfeedItem;

    }


    static class ViewHolder{
        LinearLayout content_layout;
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
        YouTubePlayerView youTubePlayerView = null;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View itemView;
        final ViewHolder viewHolder;


        if (convertView == null) {

            viewHolder = new ViewHolder();
            itemView = layoutInflater.inflate(R.layout.activity_member_item, parent, false);

            viewHolder.name = (TextView) itemView.findViewById(R.id.member_lv_name);
            viewHolder.type = (TextView) itemView.findViewById(R.id.member_lv_type);
            viewHolder.belong = (TextView) itemView.findViewById(R.id.member_lv_belong);
            viewHolder.profile_img = (ImageView) itemView.findViewById(R.id.member_lv_img);

            final NewsfeedItem newfeedItemPosition = newsFeed.newsfeedItem.get(position);
            viewHolder.name.setText(newfeedItemPosition.name);
            viewHolder.type.setText(newfeedItemPosition.type);

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

            }


            if(newfeedItemPosition.member_type != null) {
                String type_str = "";
                if (newfeedItemPosition.member_type.equals("mentor")) {
                    type_str = "멘토";
                } else if (newfeedItemPosition.member_type.equals("mentee")) {
                    type_str = "꿈나무";
                } else if (newfeedItemPosition.member_type.equals("expert")) {
                    type_str = "전문가";
                }

                viewHolder.type.setText(type_str);


                if (newfeedItemPosition.member_type.equals("mentor")) {
                    viewHolder.belong.setText(newfeedItemPosition.company);
                } else if (newfeedItemPosition.member_type.equals("expert")) {
                    // Log.d("response" , "belong : " + newfeedItemPosition.name + newfeedItemPosition.expert_type);
                    viewHolder.belong.setText(newfeedItemPosition.expert_type);
                } else {
                    viewHolder.belong.setText(" ");
                }
            }


            viewHolder.profile_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent( context.getApplicationContext(), OtherPageActivity.class);
                    intent.putExtra("name",newfeedItemPosition.name);
                    intent.putExtra("company",newfeedItemPosition.company);
                    intent.putExtra("member_type",newfeedItemPosition.member_type);
                    intent.putExtra("expert_type",newfeedItemPosition.expert_type);
                    intent.putExtra("sport_type",newfeedItemPosition.sport_type);
                    intent.putExtra("member_id",newfeedItemPosition.member_id);
                    intent.putExtra("profile_url",newfeedItemPosition.profile_url);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
          
            return itemView;

        } else {

            viewHolder = new ViewHolder();
            itemView = convertView;

            viewHolder.name = (TextView) itemView.findViewById(R.id.member_lv_name);
            viewHolder.type = (TextView) itemView.findViewById(R.id.member_lv_type);
            viewHolder.belong = (TextView) itemView.findViewById(R.id.member_lv_belong);
            viewHolder.profile_img = (ImageView) itemView.findViewById(R.id.member_lv_img);


            if(newsFeed.newsfeedItem.size() != 0){

                final NewsfeedItem newfeedItemPosition = newsFeed.newsfeedItem.get(position);
                viewHolder.name.setText(newfeedItemPosition.name);
                viewHolder.type.setText(newfeedItemPosition.type);

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

                }

                if(newfeedItemPosition.member_type != null) {
                    String type_str = "";
                    if (newfeedItemPosition.member_type.equals("mentor")) {
                        type_str = "멘토";
                    } else if (newfeedItemPosition.member_type.equals("mentee")) {
                        type_str = "꿈나무";
                    } else if (newfeedItemPosition.member_type.equals("expert")) {
                        type_str = "전문가";
                    }

                    viewHolder.type.setText(type_str);


                    if (newfeedItemPosition.member_type.equals("mentor")) {
                        viewHolder.belong.setText(newfeedItemPosition.company);
                    } else if (newfeedItemPosition.member_type.equals("expert")) {
                        // Log.d("response" , "belong : " + newfeedItemPosition.name + newfeedItemPosition.expert_type);
                        viewHolder.belong.setText(newfeedItemPosition.expert_type);
                    } else {
                        viewHolder.belong.setText(" ");
                    }
                }


                viewHolder.profile_img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent( context.getApplicationContext(), OtherPageActivity.class);
                        intent.putExtra("name",newfeedItemPosition.name);
                        intent.putExtra("company",newfeedItemPosition.company);
                        intent.putExtra("member_type",newfeedItemPosition.member_type);
                        intent.putExtra("expert_type",newfeedItemPosition.expert_type);
                        intent.putExtra("sport_type",newfeedItemPosition.sport_type);
                        intent.putExtra("member_id",newfeedItemPosition.member_id);
                        intent.putExtra("profile_url",newfeedItemPosition.profile_url);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                });


            }


            return  convertView;

        }


        }


    }


