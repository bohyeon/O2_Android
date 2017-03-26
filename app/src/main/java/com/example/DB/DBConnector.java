package com.example.DB;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class DBConnector {


    public static JSONObject LoginDBConnector(String id_str, String pw_str) {

        JSONArray jsonArray = null;
        JSONObject jsonObject = null;

        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://o-two-sport.com/api/users/login/");

       // Log.d("response","response 03 : " + id_str + pw_str);
        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("email",id_str));
            nameValuePairs.add(new BasicNameValuePair("password",pw_str));
         //   httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "EUC-KR"));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            String bobo = EntityUtils.toString(response.getEntity());
           // response.setHeader("Content-Type", "text/xml; charset=UTF-8");
            //Log.d("response","response 01 : " + response.toString());
            //Log.d("response","response 02 : " + bobo.toString());

            jsonObject = new JSONObject(bobo.toString());
         //   Log.d("response","response 03 : " + jsonObject);
           // jsonArray = new JSONArray(bobo.toString());



        } catch (ClientProtocolException e) {
            e.printStackTrace();
            Log.d("response","response 04 : " + e.toString());
            // TODO Auto-generated catch block
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            Log.d("response","response 05 : " + e.toString());
            // TODO Auto-generated catch block
        }

       // Log.d("response","response 06 : " + jsonObject.toString());
        return  jsonObject;
    }


    public static JSONObject PostCode(String invite_code) {

        JSONArray jsonArray = null;
        JSONObject jsonObject = null;

        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://o-two-sport.com/api/users/invite_code/");

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("invite_code",invite_code));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            String bobo = EntityUtils.toString(response.getEntity());

         //   Log.d("response","response 01 : " + response.toString());
         //   Log.d("response","response 02 : " + invite_code.toString());

          //  Log.d("response","response 03 : " + bobo.toString());
            jsonObject = new JSONObject(bobo.toString());

            // jsonArray = new JSONArray(bobo.toString());



        } catch (ClientProtocolException e) {
            e.printStackTrace();
      //      Log.d("response","response 04 : " + e.toString());
            // TODO Auto-generated catch block
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        //    Log.d("response","response 05 : " + e.toString());
            // TODO Auto-generated catch block
        }

        // Log.d("response","response 06 : " + jsonObject.toString());
        return  jsonObject;
    }


    public static JSONArray ProfileImage() {

        JSONArray jsonArray = null;

        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("");

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("function", "get_courier_delivering"));


            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            String bobo = EntityUtils.toString(response.getEntity());

            //Log.d("1/5","delivering 01 : " + response.toString());

            jsonArray = new JSONArray(bobo.toString());


        } catch (ClientProtocolException e) {
            e.printStackTrace();
            // TODO Auto-generated catch block
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            // TODO Auto-generated catch block
        }

        return  jsonArray;
    }

    public static JSONObject MenteeResgister(String email, String name, String password,String password_check, String profile_image_url,
                                            String phone_number, String phone_number_open, String birthday, String birthday_open, String sport_type,
                                            String region, String school_level, String school_name, String invite_code) {

        JSONObject jsonObject = null;

        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://o-two-sport.com/api/users/register/mentee/");

      //  Log.d("response" , "profile_img 2 " + profile_image_url);

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("email",email));
            nameValuePairs.add(new BasicNameValuePair("name",name));
            nameValuePairs.add(new BasicNameValuePair("password",password));
            nameValuePairs.add(new BasicNameValuePair("password_check",password_check));
            nameValuePairs.add(new BasicNameValuePair("profile_url",profile_image_url));
            nameValuePairs.add(new BasicNameValuePair("phone_number",phone_number));
            nameValuePairs.add(new BasicNameValuePair("phone_number_open",phone_number_open));
            nameValuePairs.add(new BasicNameValuePair("birthday",birthday));
            nameValuePairs.add(new BasicNameValuePair("birthday_open",birthday_open));
            nameValuePairs.add(new BasicNameValuePair("sport_type",sport_type));
            nameValuePairs.add(new BasicNameValuePair("region",region));
            nameValuePairs.add(new BasicNameValuePair("school_level",school_level));
            nameValuePairs.add(new BasicNameValuePair("school_name",school_name));
            nameValuePairs.add(new BasicNameValuePair("invite_code",invite_code));

       //     Log.d("response " , "namevalue : " + nameValuePairs.toString());

          //  httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            String bobo = EntityUtils.toString(response.getEntity());

       //     Log.d("response","response mentee " + response.toString());

            jsonObject = new JSONObject(bobo.toString());


        } catch (ClientProtocolException e) {
            e.printStackTrace();
       //     Log.d("response","response error " + e.toString());
            // TODO Auto-generated catch block
        } catch (IOException | JSONException e) {
            e.printStackTrace();
      //      Log.d("response","response error2 " + e.toString());
            // TODO Auto-generated catch block
        }

        return  jsonObject;
    }

    public static JSONObject MentoResgister(String email, String name, String password,String password_check, String profile_image_url,
                                             String phone_number, String phone_number_open, String birthday, String birthday_open, String sport_type,
                                             String region, String mentor_type, String company, String invite_code) {

        JSONObject jsonObject = null;

        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://o-two-sport.com/api/users/register/mentor/");

     //   Log.d("response" , "profile_img 2 " + profile_image_url);

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("email",email));
            nameValuePairs.add(new BasicNameValuePair("name",name));
            nameValuePairs.add(new BasicNameValuePair("password",password));
            nameValuePairs.add(new BasicNameValuePair("password_check",password_check));
            nameValuePairs.add(new BasicNameValuePair("member_type","mentor"));
            nameValuePairs.add(new BasicNameValuePair("profile_url",profile_image_url));
            nameValuePairs.add(new BasicNameValuePair("phone_number",phone_number));
            nameValuePairs.add(new BasicNameValuePair("phone_number_open",phone_number_open));
            nameValuePairs.add(new BasicNameValuePair("birthday",birthday));
            nameValuePairs.add(new BasicNameValuePair("birthday_open",birthday_open));
            nameValuePairs.add(new BasicNameValuePair("sport_type",sport_type));
            nameValuePairs.add(new BasicNameValuePair("region",region));
            nameValuePairs.add(new BasicNameValuePair("mentor_type",mentor_type));
            nameValuePairs.add(new BasicNameValuePair("company",company));
            nameValuePairs.add(new BasicNameValuePair("invite_code",invite_code));

      //      Log.d("response " , "namevalue mentor : " + nameValuePairs.toString());

          //  httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            String bobo = EntityUtils.toString(response.getEntity());

         //   Log.d("response","response mentee " + response.toString());

            jsonObject = new JSONObject(bobo.toString());


        } catch (ClientProtocolException e) {
            e.printStackTrace();
       //     Log.d("response","response error " + e.toString());
            // TODO Auto-generated catch block
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        //    Log.d("response","response error2 " + e.toString());
            // TODO Auto-generated catch block
        }

        return  jsonObject;
    }

    public static JSONObject ExpertResgister(String email, String name, String password,String password_check, String profile_image_url,
                                             String phone_number, String phone_number_open, String birthday, String birthday_open, String expert_type,
                                            String experience01, String experience02, String experience03, String invite_code, String company) {

        JSONObject jsonObject = null;

        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://o-two-sport.com/api/users/register/expert/");

       // Log.d("response" , "profile_img 2 " + profile_image_url);

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("email",email));
            nameValuePairs.add(new BasicNameValuePair("name",name));
            nameValuePairs.add(new BasicNameValuePair("password",password));
            nameValuePairs.add(new BasicNameValuePair("password_check",password_check));
            nameValuePairs.add(new BasicNameValuePair("profile_url",profile_image_url));
            nameValuePairs.add(new BasicNameValuePair("phone_number",phone_number));
            nameValuePairs.add(new BasicNameValuePair("phone_number_open",phone_number_open));
            nameValuePairs.add(new BasicNameValuePair("birthday",birthday));
            nameValuePairs.add(new BasicNameValuePair("birthday_open",birthday_open));
            nameValuePairs.add(new BasicNameValuePair("expert_type",expert_type));
            nameValuePairs.add(new BasicNameValuePair("experience_1",experience01));
            nameValuePairs.add(new BasicNameValuePair("experience_2",experience02));
            nameValuePairs.add(new BasicNameValuePair("experience_3",experience03));
            nameValuePairs.add(new BasicNameValuePair("invite_code",invite_code));
            nameValuePairs.add(new BasicNameValuePair("company",company));

      //      Log.d("response " , "namevalue : " + nameValuePairs.toString());

         //   httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            String expert = EntityUtils.toString(response.getEntity());

     //       Log.d("response","response expert " + expert.toString());

            jsonObject = new JSONObject(expert.toString());


        } catch (ClientProtocolException e) {
            e.printStackTrace();
          //  Log.d("response","response error " + e.toString());
            // TODO Auto-generated catch block
        } catch (IOException | JSONException e) {
            e.printStackTrace();
         //   Log.d("response","response error2 " + e.toString());
            // TODO Auto-generated catch block
        }

        return  jsonObject;
    }



    public static JSONObject Posts(String token, String post_type, String content, String youtube_link, String post_image_url, String youtube_title) {

        JSONObject jsonObject = null;

        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://o-two-sport.com/api/posts/");
        httppost.addHeader("Authorization","Token " +token);

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("post_type",post_type));
            nameValuePairs.add(new BasicNameValuePair("content",content));
            nameValuePairs.add(new BasicNameValuePair("youtube_link",youtube_link));
            nameValuePairs.add(new BasicNameValuePair("post_image_url",post_image_url));
            nameValuePairs.add(new BasicNameValuePair("youtube_title",youtube_title));
           // Log.d("response2", "response img: " + nameValuePairs.toString());
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            String bobo = EntityUtils.toString(response.getEntity());

        //    Log.d("response2", "response : " + bobo.toString());

            jsonObject = new JSONObject(bobo.toString());

          //  Log.d("response2", "response1 : " + jsonObject.toString());

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        //    Log.d("response2", "response2 : " + e.toString());
            // TODO Auto-generated catch block
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            // TODO Auto-generated catch block
        }

        return  jsonObject;
    }

    public static JSONObject UploadImage(File image, String token) {

        JSONObject jsonObject = null;

        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://o-two-sport.com/api/users/profile_image/");

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
         //   nameValuePairs.add(new BasicNameValuePair("post_type",post_type));

            File fileToUse = new File(image.toString()); //e.g. /temp/dinnerplate-special.jpg
            FileBody data = new FileBody(fileToUse);

            MultipartEntity reqEntity = new MultipartEntity();
            reqEntity.addPart("file", new StringBody( fileToUse.getName() ) );
            reqEntity.addPart("file", data);
        //    Log.d("response2", "image1 : " + data.toString());
            httppost.setEntity(reqEntity);


            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            String bobo = EntityUtils.toString(response.getEntity());

        //    Log.d("response2", "image : " + bobo.toString());

            jsonObject = new JSONObject(bobo.toString());

            //  Log.d("response2", "response1 : " + jsonObject.toString());

        } catch (ClientProtocolException e) {
            e.printStackTrace();
         //   Log.d("response2", "image error : " + e.toString());
            //  Log.d("response2", "response2 : " + e.toString());
            // TODO Auto-generated catch block
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            // TODO Auto-generated catch block
        }

        return  jsonObject;
    }

    public static JSONObject UploadPostImage(File image, String token) {

        JSONObject jsonObject = null;

        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://o-two-sport.com/api/posts/post_image/");
        httppost.addHeader("Authorization","Token " +token);

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            //   nameValuePairs.add(new BasicNameValuePair("post_type",post_type));

            File fileToUse = new File(image.toString()); //e.g. /temp/dinnerplate-special.jpg
            FileBody data = new FileBody(fileToUse);

            MultipartEntity reqEntity = new MultipartEntity();
            reqEntity.addPart("file", new StringBody( fileToUse.getName() ) );
            reqEntity.addPart("file", data);
         //   Log.d("response2", "image1 : " + data.toString());
            httppost.setEntity(reqEntity);


            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            String bobo = EntityUtils.toString(response.getEntity());

        //    Log.d("response2", "image : " + bobo.toString());

            jsonObject = new JSONObject(bobo.toString());

            //  Log.d("response2", "response1 : " + jsonObject.toString());

        } catch (ClientProtocolException e) {
            e.printStackTrace();
         //   Log.d("response2", "image error : " + e.toString());
            //  Log.d("response2", "response2 : " + e.toString());
            // TODO Auto-generated catch block
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            // TODO Auto-generated catch block
        }

        return  jsonObject;
    }


    public static JSONObject Like(String token, String post_id) {

        JSONObject jsonObject = null;

        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://o-two-sport.com/api/likes/"+post_id+"/");
        httppost.addHeader("Authorization","Token " +token);

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);


            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            String bobo = EntityUtils.toString(response.getEntity());


            jsonObject = new JSONObject(bobo.toString());

             // Log.d("response2", "response1 : " + jsonObject.toString());

        } catch (ClientProtocolException e) {
            e.printStackTrace();
            //  Log.d("response2", "response2 : " + e.toString());
            // TODO Auto-generated catch block
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            // TODO Auto-generated catch block
        }

        return  jsonObject;
    }

    public static JSONObject DeleteLike(String token, String post_id) {

        JSONObject jsonObject = null;

        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpDelete httppost = new HttpDelete("http://o-two-sport.com/api/likes/"+post_id+"/");
        httppost.addHeader("Authorization","Token " +token);

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);

           // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            String bobo = EntityUtils.toString(response.getEntity());


            jsonObject = new JSONObject(bobo.toString());

        //    Log.d("response2", "delete : " + jsonObject.toString());

        } catch (ClientProtocolException e) {
            e.printStackTrace();
            //  Log.d("response2", "response2 : " + e.toString());
            // TODO Auto-generated catch block
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            // TODO Auto-generated catch block
        }

        return  jsonObject;
    }

    public static JSONObject DeleteComment(String token, String comment_id) {

        JSONObject jsonObject = null;

        String message = null;
        int code = -1;

        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpDelete httppost = new HttpDelete("http://o-two-sport.com/api/comments/"+comment_id+"/");
        httppost.addHeader("Authorization","Token " +token);

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);

//            HttpResponse httpResponse = httpclient.execute(httppost);
//            if (httpResponse != null && httpResponse.getStatusLine().getStatusCode() == 200) {
//                HttpEntity httpEntity = httpResponse.getEntity();
//                String retSrc = EntityUtils.toString(httpEntity);
//                // parsing JSON
//                jsonObject = new JSONObject(retSrc); //Convert String to JSON Object
//            }


//            String bobo = EntityUtils.toString(response.getEntity());

//            jsonObject = new JSONObject(bobo.toString());
            Log.d("response2", "comment remove : " + response);
            //    Log.d("response2", "delete : " + jsonObject.toString());

        } catch (ClientProtocolException e) {
            e.printStackTrace();
            //  Log.d("response2", "response2 : " + e.toString());
            // TODO Auto-generated catch block
        } catch (IOException e) {
            e.printStackTrace();
            // TODO Auto-generated catch block
        }

        return  jsonObject;
    }


    public static JSONObject DeleteLogout(String token) {

        JSONObject jsonObject = null;

        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpDelete httppost = new HttpDelete("http://o-two-sport.com/api/users/logout/");
        httppost.addHeader("Authorization","Token " +token);

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            String bobo = EntityUtils.toString(response.getEntity());


            jsonObject = new JSONObject(bobo.toString());

        //    Log.d("response2", "delete : " + jsonObject.toString());

        } catch (ClientProtocolException e) {
            e.printStackTrace();
            //  Log.d("response2", "response2 : " + e.toString());
            // TODO Auto-generated catch block
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            // TODO Auto-generated catch block
        }

        return  jsonObject;
    }


    public static JSONObject Comment(String token, String post_id, String content) {

        JSONObject jsonObject = null;

        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://o-two-sport.com/api/comments/post/"+post_id+"/");
        httppost.addHeader("Authorization","Token " +token);

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("content",content));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            String bobo = EntityUtils.toString(response.getEntity());


            jsonObject = new JSONObject(bobo.toString());

            // Log.d("response2", "response1 : " + jsonObject.toString());

        } catch (ClientProtocolException e) {
            e.printStackTrace();
            //  Log.d("response2", "response2 : " + e.toString());
            // TODO Auto-generated catch block
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            // TODO Auto-generated catch block
        }

        return  jsonObject;
    }


    public  JSONArray GetPost(String token, String post_type, String sport_type){

       // Log.d("response","res toekn : " + token);
        JSONArray jsonArray = null;
     //   Log.d("response","getPost token : " + token.toString());

        try {
            URL url = new URL("http://o-two-sport.com/api/posts/?post_type="+post_type);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Authorization","Token " +token);
            conn.connect();

            InputStream is = conn.getInputStream();
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            StringBuilder responseStrBuilder = new StringBuilder();
            String inputStr;
            while ((inputStr = streamReader.readLine()) != null)
                responseStrBuilder.append(inputStr);

      //      Log.d("response","post reponse : " + responseStrBuilder.toString());
            jsonArray = new JSONArray(responseStrBuilder.toString());

        } catch (IOException | JSONException e) {

            e.printStackTrace();
        //    Log.d("response","post reponse2 : " + e.toString());
        }

        return jsonArray;
    }

    public  JSONArray GetPost_sport(String token, String sport_type){

        // Log.d("response","res toekn : " + token);
        JSONArray jsonArray = null;
    //    Log.d("response","getPost token : " + token.toString());

        try {
            URL url = new URL("http://o-two-sport.com/api/posts/?sport_type="+sport_type);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Authorization","Token " +token);
            conn.connect();

            InputStream is = conn.getInputStream();
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            StringBuilder responseStrBuilder = new StringBuilder();
            String inputStr;
            while ((inputStr = streamReader.readLine()) != null)
                responseStrBuilder.append(inputStr);

       //     Log.d("response","post reponse : " + responseStrBuilder.toString());
            jsonArray = new JSONArray(responseStrBuilder.toString());

        } catch (IOException | JSONException e) {

            e.printStackTrace();
            //    Log.d("response","post reponse2 : " + e.toString());
        }

        return jsonArray;
    }


    public  JSONArray GetUser(String token, String search, String member_type){

        // Log.d("response","res toekn : " + token);
        JSONArray jsonArray = null;

        try {
            URL url = new URL("http://o-two-sport.com/api/users/?search="+search+"&member_type="+member_type);
    //        Log.d("response","get user type: " + search + member_type);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Authorization","Token " +token);
            conn.connect();

            InputStream is = conn.getInputStream();
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            StringBuilder responseStrBuilder = new StringBuilder();
            String inputStr;
            while ((inputStr = streamReader.readLine()) != null)
                responseStrBuilder.append(inputStr);

       //     Log.d("response","get user : " +responseStrBuilder.toString());
            jsonArray = new JSONArray(responseStrBuilder.toString());

        } catch (IOException | JSONException e) {

            e.printStackTrace();
            //    Log.d("response","post reponse2 : " + e.toString());
        }

        return jsonArray;
    }

    public  JSONArray GetSearch(String token, String url_str){

        JSONArray jsonArray = null;

        try {
         //   URL url = new URL("http://o-two-sport.com/api/posts/?post_type="+post_type+"&sport_type="+sport_type+"&mentor_type="+mentor_type+"&school_level="+school_level+"&expert_type="+expert_type+"&content_query="+content_query);
            URL url = new URL(url_str);
        //    Log.d("respon" , "search arr2 : " + url_str);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Authorization","Token " +token);
            conn.connect();

            InputStream is = conn.getInputStream();
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            StringBuilder responseStrBuilder = new StringBuilder();
            String inputStr;
            while ((inputStr = streamReader.readLine()) != null)
                responseStrBuilder.append(inputStr);

            jsonArray = new JSONArray(responseStrBuilder.toString());

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return jsonArray;
    }


    public  JSONArray GetComment(String token, String post_id){

        // Log.d("response","res toekn : " + token);
        JSONArray jsonArray = null;

        try {
            URL url = new URL("http://o-two-sport.com/api/comments/post/"+post_id+"/");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Authorization","Token " +token);
            conn.connect();

            InputStream is = conn.getInputStream();
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            StringBuilder responseStrBuilder = new StringBuilder();
            String inputStr;
            while ((inputStr = streamReader.readLine()) != null)
                responseStrBuilder.append(inputStr);

         //   Log.d("response","post reponse : " + responseStrBuilder.toString());
            jsonArray = new JSONArray(responseStrBuilder.toString());

        } catch (IOException | JSONException e) {

            e.printStackTrace();
            //    Log.d("response","post reponse2 : " + e.toString());
        }

        return jsonArray;
    }

    public  JSONArray GetMyPost(String token){

        // Log.d("response","res toekn : " + token);
        JSONArray jsonArray = null;

        try {
            URL url = new URL("http://o-two-sport.com/api/posts/self/");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Authorization","Token " +token);
            conn.connect();

            InputStream is = conn.getInputStream();
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            StringBuilder responseStrBuilder = new StringBuilder();
            String inputStr;
            while ((inputStr = streamReader.readLine()) != null)
                responseStrBuilder.append(inputStr);

         //   Log.d("response","post reponse : " + responseStrBuilder.toString());
            jsonArray = new JSONArray(responseStrBuilder.toString());

        } catch (IOException | JSONException e) {

            e.printStackTrace();
            //    Log.d("response","post reponse2 : " + e.toString());
        }

        return jsonArray;
    }


    public  JSONArray GetMyCommentPost(String token, String id){

        // Log.d("response","res toekn : " + token);
        JSONArray jsonArray = null;

        try {
            URL url = new URL("http://o-two-sport.com/api/posts/?comment_user_id="+id);
            Log.d("response","post url : " + url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Authorization","Token " +token);
            conn.connect();

            InputStream is = conn.getInputStream();
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            StringBuilder responseStrBuilder = new StringBuilder();
            String inputStr;
            while ((inputStr = streamReader.readLine()) != null)
                responseStrBuilder.append(inputStr);

            //   Log.d("response","post reponse : " + responseStrBuilder.toString());
            jsonArray = new JSONArray(responseStrBuilder.toString());

        } catch (IOException | JSONException e) {

            e.printStackTrace();
                Log.d("response","post reponse2 : " + e.toString());
        }

        return jsonArray;
    }

    public  JSONObject InviteCode(String token){

        // Log.d("response","res toekn : " + token);
        JSONObject jsonObject = null;

        try {
            URL url = new URL("http://o-two-sport.com/api/users/invite_code/");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Authorization","Token " +token);
            conn.connect();

            InputStream is = conn.getInputStream();
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            StringBuilder responseStrBuilder = new StringBuilder();
            String inputStr;
            while ((inputStr = streamReader.readLine()) != null)
                responseStrBuilder.append(inputStr);

        //    Log.d("response","post reponse : " + responseStrBuilder.toString());
            jsonObject = new JSONObject(responseStrBuilder.toString());

        } catch (IOException | JSONException e) {

            e.printStackTrace();
            //    Log.d("response","post reponse2 : " + e.toString());
        }

        return jsonObject;
    }

    public  JSONObject InviteCode2(String token){

        // Log.d("response","res toekn : " + token);
        JSONObject jsonObject = null;

        try {
            URL url = new URL("http://o-two-sport.com/api/users/invite/");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Authorization","Token " +token);
            conn.connect();

            InputStream is = conn.getInputStream();
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            StringBuilder responseStrBuilder = new StringBuilder();
            String inputStr;
            while ((inputStr = streamReader.readLine()) != null)
                responseStrBuilder.append(inputStr);

         //   Log.d("response","code reponse : " + responseStrBuilder.toString());
            jsonObject = new JSONObject(responseStrBuilder.toString());

        } catch (IOException | JSONException e) {

            e.printStackTrace();
            //    Log.d("response","post reponse2 : " + e.toString());
        }

        return jsonObject;
    }


    public static JSONObject PutUserInfo(String token, String name, String password, String profile_image_url, String phone_number, String is_phone_number_public, String birthday,String is_birthday_public, String sport_type, String mentor_type, String expert_type, String region, String school_level, String school_name, String company, String experience_1, String experience_2, String experience_3){


    JSONObject jsonObject = null;

        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpPut httppost = new HttpPut("http://o-two-sport.com/api/users/self/");
        httppost.addHeader("Authorization","Token " +token);

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);

            nameValuePairs.add(new BasicNameValuePair("name",name));
            nameValuePairs.add(new BasicNameValuePair("password",password));
            nameValuePairs.add(new BasicNameValuePair("profile_image_url",profile_image_url));
            nameValuePairs.add(new BasicNameValuePair("phone_number",phone_number));
            nameValuePairs.add(new BasicNameValuePair("is_phone_number_public",is_phone_number_public));
            nameValuePairs.add(new BasicNameValuePair("birthday",birthday));
            nameValuePairs.add(new BasicNameValuePair("is_birthday_public",is_birthday_public));
            nameValuePairs.add(new BasicNameValuePair("sport_type",sport_type));
            nameValuePairs.add(new BasicNameValuePair("mentor_type",mentor_type));
            nameValuePairs.add(new BasicNameValuePair("expert_type",expert_type));
            nameValuePairs.add(new BasicNameValuePair("region",region));
            nameValuePairs.add(new BasicNameValuePair("school_level",school_level));
            nameValuePairs.add(new BasicNameValuePair("school_name",school_name));
            nameValuePairs.add(new BasicNameValuePair("company",company));
            nameValuePairs.add(new BasicNameValuePair("experience_1",experience_1));
            nameValuePairs.add(new BasicNameValuePair("experience_2",experience_2));
            nameValuePairs.add(new BasicNameValuePair("experience_3",experience_3));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            String bobo = EntityUtils.toString(response.getEntity());


            jsonObject = new JSONObject(bobo.toString());

     //        Log.d("response2", "response1 : " + jsonObject.toString());

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        //      Log.d("response2", "response2 : " + e.toString());
            // TODO Auto-generated catch block
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            // TODO Auto-generated catch block
        }

        return  jsonObject;
    }


    public  JSONObject GetYoutube(String youtube_link){

        // Log.d("response","res toekn : " + token);
        JSONObject jsonArray = null;


        try {
            URL url = new URL("https://www.youtube.com/oembed?url="+youtube_link+"&t=469s&format=json");
        //    Log.d("response","youtube reponse url: " + url.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.connect();

            InputStream is = conn.getInputStream();
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            StringBuilder responseStrBuilder = new StringBuilder();
            String inputStr;
            while ((inputStr = streamReader.readLine()) != null)
                responseStrBuilder.append(inputStr);

            //Log.d("response","youtube reponse : " + responseStrBuilder.toString());
            jsonArray = new JSONObject(responseStrBuilder.toString());

        } catch (IOException | JSONException e) {

            e.printStackTrace();
                //Log.d("response","youtube e : " + e.toString());
        }

        return jsonArray;
    }



//    public static JSONArray GetComment(String token, String post_id) {
//
//        JSONArray jsonArray = null;
//
//        // Create a new HttpClient and Post Header
//        HttpClient httpclient = new DefaultHttpClient();
//        HttpGet httppost = new HttpGet("http://o-two-sport.com/api/comments/"+post_id+"/");
//        httppost.addHeader("Authorization","Token " +token);
//
//        try {
//            // Add your data
//            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
//
////            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
////            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));
//
//            // Execute HTTP Post Request
//            HttpResponse response = httpclient.execute(httppost);
//            String bobo = EntityUtils.toString(response.getEntity());
//
//            Log.d("response2", "response comment : " + bobo.toString());
//            jsonArray = new JSONArray(bobo.toString());
//
//
//        } catch (ClientProtocolException e) {
//            e.printStackTrace();
//            //  Log.d("response2", "response2 : " + e.toString());
//            // TODO Auto-generated catch block
//        } catch (IOException | JSONException e) {
//            e.printStackTrace();
//            // TODO Auto-generated catch block
//        }
//
//        return  jsonArray;
//    }

}

