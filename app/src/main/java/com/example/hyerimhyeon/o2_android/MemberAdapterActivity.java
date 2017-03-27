package com.example.hyerimhyeon.o2_android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MemberAdapterActivity extends ArrayAdapter<NewsfeedItem> {

    LayoutInflater layoutInflater;
    NewsFeed newsFeed;
    MemberActivity memberActivity;
    Context context;
    NewsfeedItem newsfeedItem;

    MemberAdapterActivity newsfeedAdapterActivity;

    TextView name, type, belong, regist_date, content;
    ImageView profile_img;

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
            profile_img = (ImageView) itemView.findViewById(R.id.member_lv_img);
          
            final NewsfeedItem newfeedItemPosition = newsFeed.newsfeedItem.get(position);
            name.setText(newfeedItemPosition.name);
            type.setText(newfeedItemPosition.type);

            if(newfeedItemPosition.member_type.equals("")){

            }else{
                String member_type = "";
                String company = " ";
                if(newfeedItemPosition.member_type.equals("mentor")){
                    company = newfeedItemPosition.company;
                    member_type = "멘토" + " " + company;
                }else if(newfeedItemPosition.member_type.equals("mentee")){
                    member_type = "꿈나무";
                }else if(newfeedItemPosition.member_type.equals("expert")){
                    company = newfeedItemPosition.company;
                    if(company.equals(" ")){
                        member_type = "전문가";
                    }else{
                        member_type = "전문가 " + company;
                    }

                }else{
                    member_type = "";
                }
                belong.setText(member_type);

                Log.d("response" , "member profile url :  " + newfeedItemPosition.name);

                if(newfeedItemPosition.profile_url.equals("")|| newfeedItemPosition.profile_url == null||newfeedItemPosition.profile_url == " "||newfeedItemPosition.profile_url == "null"|| newfeedItemPosition.profile_url == "" || newfeedItemPosition.profile_url=="http://" || newfeedItemPosition.profile_url=="http://null" || newfeedItemPosition.profile_url.equals("http://") || newfeedItemPosition.profile_url.equals("http:/null/")){
                    profile_img.setBackgroundResource(0);
                }else{
                    profile_img.setBackgroundResource(0);

                    Picasso.with(context)
                            .load("http://"+newfeedItemPosition.profile_url)
                            .resize(250,200)
                            .placeholder(R.drawable.blankimg)
                            .error(R.drawable.blankimg)
                            .into(profile_img);
                    // new DownLoadImageTask_profile(viewHolder.profile_img).execute("http://"+newfeedItemPosition.profile_url);

                }

                profile_img.setOnClickListener(new View.OnClickListener() {
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
                        ((Activity) getContext()).startActivity(intent);
                    }
                });

            }


            return itemView;

        } else {

            itemView = convertView;

            name = (TextView) itemView.findViewById(R.id.member_lv_name);
            type = (TextView) itemView.findViewById(R.id.member_lv_type);
            belong = (TextView) itemView.findViewById(R.id.member_lv_belong);
            profile_img = (ImageView) itemView.findViewById(R.id.member_lv_img);


            if(newsFeed.newsfeedItem.size() != 0){

                final NewsfeedItem newfeedItemPosition = newsFeed.newsfeedItem.get(position);
                name.setText(newfeedItemPosition.name);
                type.setText(newfeedItemPosition.type);
                if(newfeedItemPosition.member_type.equals("")){

                }else{
                    String member_type = "";
                    String company = "";
                    if(newfeedItemPosition.member_type.equals("mentor")){
                        company = newfeedItemPosition.company;
                        member_type = "멘토" + " " + company;
                    }else if(newfeedItemPosition.member_type.equals("mentee")){
                        member_type = "꿈나무";
                    }else if(newfeedItemPosition.member_type.equals("expert")){
                        company = newfeedItemPosition.company;
                        member_type = "전문가 " + company;
                    }else{
                        member_type = "";
                    }
                    belong.setText(member_type);
                }

                Log.d("response" , "member profile url :  " + newfeedItemPosition.name);


                if(newfeedItemPosition.profile_url.equals("")|| newfeedItemPosition.profile_url == null||newfeedItemPosition.profile_url == " "||newfeedItemPosition.profile_url == "null"|| newfeedItemPosition.profile_url == "" || newfeedItemPosition.profile_url=="http://" || newfeedItemPosition.profile_url=="http://null" || newfeedItemPosition.profile_url.equals("http://") || newfeedItemPosition.profile_url.equals("http:/null/")){
                    profile_img.setBackgroundResource(0);
                }else{
                    profile_img.setBackgroundResource(0);

                    Picasso.with(context)
                            .load("http://"+newfeedItemPosition.profile_url)
                            .resize(250,200)
                            .placeholder(R.drawable.blankimg)
                            .error(R.drawable.blankimg)
                            .into(profile_img);
                    // new DownLoadImageTask_profile(viewHolder.profile_img).execute("http://"+newfeedItemPosition.profile_url);

                }

                profile_img.setOnClickListener(new View.OnClickListener() {
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
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        (getContext()).startActivity(intent);
                    }
                });

            }


            return  convertView;

        }


        }


    }


