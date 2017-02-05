package com.example.hyerimhyeon.o2_android;

import android.util.Log;

import java.util.ArrayList;


public class NewsFeed {

    private static NewsFeed NEWS_FEED = null;

    NewsfeedAdapterActivity newsfeedAdapterActivity;
    NewsFeed newsFeed;
    ArrayList<NewsfeedItem> newsfeedItem;

    private NewsFeed() {

//        newsFeed = new NewsFeed();
        this.newsfeedItem =  new ArrayList<NewsfeedItem>();
        Log.d("","뚠뚜니332");
        this.addmockupData();
    }


    private void addmockupData() {

        ArrayList<NewsfeedItem> newsfeedItems = this.newsfeedItem;

        NewsfeedItem item;

        item = new NewsfeedItem();
        item.name = "김진흥";
        item.type = "멘토";
        item.belong = "삼성생명 레슬링 코치";
        item.regist_date = "2017년 8월 21일 오후 11:02";
        item.content = "저에게 레슨 받아볼 꿈나무~\n" +
                "101명만 지원 받아요^^ ";

        newsfeedItems.add(item);

        item = new NewsfeedItem();
        item.name = "김진흥2";
        item.type = "멘토";
        item.belong = "삼성생명 레슬링 코치";
        item.regist_date = "2017년 8월 21일 오후 11:02";
        item.content = "저에게 레슨 받아볼 꿈나무~\n" +
                "10명만 지원 받아요^^ ";

        newsfeedItems.add(item);

        item = new NewsfeedItem();
        item.name = "김진흥3";
        item.type = "멘토";
        item.belong = "삼성생명 레슬링 코치";
        item.regist_date = "2017년 8월 21일 오후 11:02";
        item.content = "저에게 레슨 받아볼 꿈나무~\n" +
                "20명만 지원 받아요^^ ";

        newsfeedItems.add(item);

        item = new NewsfeedItem();
        item.name = "김진흥4";
        item.type = "멘토";
        item.belong = "삼성생명 레슬링 코치";
        item.regist_date = "2017년 8월 21일 오후 11:02";
        item.content = "저에게 레슨 받아볼 꿈나무~\n" +
                "30명만 지원 받아요^^ ";

        newsfeedItems.add(item);

        item = new NewsfeedItem();
        item.name = "김진흥5";
        item.type = "멘토";
        item.belong = "삼성생명 레슬링 코치";
        item.regist_date = "2017년 8월 21일 오후 11:02";
        item.content = "저에게 레슨 받아볼 꿈나무~\n" +
                "40명만 지원 받아요^^ ";

        newsfeedItems.add(item);

        item = new NewsfeedItem();
        item.name = "김진흥6";
        item.type = "멘토";
        item.belong = "삼성생명 레슬링 코치";
        item.regist_date = "2017년 8월 21일 오후 11:02";
        item.content = "저에게 레슨 받아볼 꿈나무~\n" +
                "50명만 지원 받아요^^ ";

        newsfeedItems.add(item);

        item = new NewsfeedItem();
        item.name = "김진흥7";
        item.type = "멘토";
        item.belong = "삼성생명 레슬링 코치";
        item.regist_date = "2017년 8월 21일 오후 11:02";
        item.content = "저에게 레슨 받아볼 꿈나무~\n" +
                "60명만 지원 받아요^^ ";

        newsfeedItems.add(item);

        item = new NewsfeedItem();
        item.name = "김진흥8";
        item.type = "멘토";
        item.belong = "삼성생명 레슬링 코치";
        item.regist_date = "2017년 8월 21일 오후 11:02";
        item.content = "저에게 레슨 받아볼 꿈나무~\n" +
                "70명만 지원 받아요^^ ";

        newsfeedItems.add(item);

    }



    public static NewsFeed getNewsFeed() {

      //  Log.d("뚠뚜니","뚠뚜니4");
        if( NEWS_FEED == null ) {
            NEWS_FEED = new NewsFeed();
        }

        return NEWS_FEED ;
    }

    public void restartNewsFeed(){
        if(NEWS_FEED != null){
            NEWS_FEED = null;
        }

    }



}
