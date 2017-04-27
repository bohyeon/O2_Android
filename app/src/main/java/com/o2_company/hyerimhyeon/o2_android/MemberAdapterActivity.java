package com.o2_company.hyerimhyeon.o2_android;

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


    static class ViewHolder{
        ImageView profile_img;
        TextView name, type, belong, regist_date, content;

    }

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
        final ViewHolder viewHolder;
        if (convertView == null) {
            itemView = layoutInflater.inflate(R.layout.activity_member_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView) itemView.findViewById(R.id.member_lv_name);
            viewHolder.type = (TextView) itemView.findViewById(R.id.member_lv_type);
            viewHolder.belong = (TextView) itemView.findViewById(R.id.member_lv_belong);
            viewHolder.profile_img = (ImageView) itemView.findViewById(R.id.member_lv_img);
          
            final NewsfeedItem newfeedItemPosition = newsFeed.newsfeedItem.get(position);
            viewHolder.name.setText(newfeedItemPosition.name);
            viewHolder.type.setText(newfeedItemPosition.type);

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
                viewHolder.belong.setText(member_type);

        //        Log.d("response" , "member profile url :  " + newfeedItemPosition.name);

                if(newfeedItemPosition.profile_url.equals("")|| newfeedItemPosition.profile_url == null||newfeedItemPosition.profile_url == " "||newfeedItemPosition.profile_url == "null"|| newfeedItemPosition.profile_url == "" || newfeedItemPosition.profile_url=="http://" || newfeedItemPosition.profile_url=="http://null" || newfeedItemPosition.profile_url.equals("http://") || newfeedItemPosition.profile_url.equals("http:/null/")){
                    viewHolder.profile_img.setBackgroundResource(0);
                }else{
                    viewHolder.profile_img.setBackgroundResource(0);

                    Picasso.with(context)
                            .load("http://"+newfeedItemPosition.profile_url)
                            .resize(250,200)
                            .placeholder(R.drawable.blankimg)
                            .error(R.drawable.blankimg)
                            .into( viewHolder.profile_img);
                    // new DownLoadImageTask_profile(viewHolder.profile_img).execute("http://"+newfeedItemPosition.profile_url);

                }

                viewHolder.profile_img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent( context.getApplicationContext(), OtherPageActivity.class);
                        intent.putExtra("email",newfeedItemPosition.email);
                        intent.putExtra("name",newfeedItemPosition.name);
                        intent.putExtra("company",newfeedItemPosition.company);
                        intent.putExtra("member_type",newfeedItemPosition.member_type);
                        intent.putExtra("expert_type",newfeedItemPosition.expert_type);
                        intent.putExtra("sport_type",newfeedItemPosition.sport_type);
                        intent.putExtra("member_id",newfeedItemPosition.member_id);
                        intent.putExtra("profile_url",newfeedItemPosition.profile_url);
                        intent.putExtra("phone_number",newfeedItemPosition.phone_number);
                        intent.putExtra("birthday",newfeedItemPosition.birthday);
                        Log.d("response" , "phone number public :  " + newfeedItemPosition.is_phone_number_public);
                        intent.putExtra("is_phone_number_public",newfeedItemPosition.is_phone_number_public);
                        intent.putExtra("is_birthday_public",newfeedItemPosition.is_birthday_public);
                        intent.putExtra("region",newfeedItemPosition.region);
                        intent.putExtra("school_level",newfeedItemPosition.school_level);
                        intent.putExtra("school_name",newfeedItemPosition.school_name);
                        intent.putExtra("experience_1",newfeedItemPosition.experience_1);
                        intent.putExtra("experience_2",newfeedItemPosition.experience_2);
                        intent.putExtra("experience_3",newfeedItemPosition.experience_3);
                        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        ((Activity) getContext()).startActivity(intent);
                    }
                });

            }


            return itemView;

        } else {

            itemView = convertView;
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView) itemView.findViewById(R.id.member_lv_name);
            viewHolder.type = (TextView) itemView.findViewById(R.id.member_lv_type);
            viewHolder.belong = (TextView) itemView.findViewById(R.id.member_lv_belong);
            viewHolder.profile_img = (ImageView) itemView.findViewById(R.id.member_lv_img);


            if(newsFeed.newsfeedItem.size() != 0){

                final NewsfeedItem newfeedItemPosition = newsFeed.newsfeedItem.get(position);
                viewHolder.name.setText(newfeedItemPosition.name);
                viewHolder.type.setText(newfeedItemPosition.type);
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
                    viewHolder.belong.setText(member_type);
                }

         //       Log.d("response" , "member profile url :  " + newfeedItemPosition.name);


                if(newfeedItemPosition.profile_url.equals("")|| newfeedItemPosition.profile_url == null||newfeedItemPosition.profile_url == " "||newfeedItemPosition.profile_url == "null"|| newfeedItemPosition.profile_url == "" || newfeedItemPosition.profile_url=="http://" || newfeedItemPosition.profile_url=="http://null" || newfeedItemPosition.profile_url.equals("http://") || newfeedItemPosition.profile_url.equals("http:/null/")){
                    viewHolder.profile_img.setBackgroundResource(0);
                }else{
                    viewHolder.profile_img.setBackgroundResource(0);

                    Picasso.with(context)
                            .load("http://"+newfeedItemPosition.profile_url)
                            .resize(250,200)
                            .placeholder(R.drawable.blankimg)
                            .error(R.drawable.blankimg)
                            .into(viewHolder.profile_img);
                    // new DownLoadImageTask_profile(viewHolder.profile_img).execute("http://"+newfeedItemPosition.profile_url);

                }

                viewHolder.profile_img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent( context.getApplicationContext(), OtherPageActivity.class);
                        intent.putExtra("email",newfeedItemPosition.email);
                        intent.putExtra("name",newfeedItemPosition.name);
                        intent.putExtra("company",newfeedItemPosition.company);
                        intent.putExtra("member_type",newfeedItemPosition.member_type);
                        intent.putExtra("expert_type",newfeedItemPosition.expert_type);
                        intent.putExtra("sport_type",newfeedItemPosition.sport_type);
                        intent.putExtra("member_id",newfeedItemPosition.member_id);
                        intent.putExtra("profile_url",newfeedItemPosition.profile_url);
                        intent.putExtra("phone_number",newfeedItemPosition.phone_number);
                        intent.putExtra("birthday",newfeedItemPosition.birthday);
                       // Log.d("response" , "phone number public :  " + newfeedItemPosition.is_phone_number_public);
                        intent.putExtra("is_phone_number_public",newfeedItemPosition.is_phone_number_public);
                        intent.putExtra("is_birthday_public",newfeedItemPosition.is_birthday_public);
                        intent.putExtra("region",newfeedItemPosition.region);
                        intent.putExtra("school_level",newfeedItemPosition.school_level);
                        intent.putExtra("school_name",newfeedItemPosition.school_name);
                        intent.putExtra("experience_1",newfeedItemPosition.experience_1);
                        intent.putExtra("experience_2",newfeedItemPosition.experience_2);
                        intent.putExtra("experience_3",newfeedItemPosition.experience_3);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.getApplicationContext().startActivity(intent);
                    }
                });

            }


            return  convertView;

        }


        }


    }


