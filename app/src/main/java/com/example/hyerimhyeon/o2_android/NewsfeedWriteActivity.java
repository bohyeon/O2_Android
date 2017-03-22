package com.example.hyerimhyeon.o2_android;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.DB.DBConnector;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

//import com.o2.o2sport.applicataion.server.response.ResponseWritePost;
//import com.tsengvn.typekit.TypekitContextWrapper;

public class NewsfeedWriteActivity extends AppCompatActivity {
    private Button cameraBtn, galleryBtn;
    private ImageView backBtn;
    private ImageView selectedImg;
    private TextView writeCompleteBtn;
    private File file = null;
    private EditText editPost;
    private String postContent;
    SharedPreferences pref;
    int memberId = 0;

    private final int GALLERY_ACTIVITY_CODE=200;
    public SharedPreferences loginPreferences;

    TextView actionbar_title;
    Boolean buttonStateOpen;

    String token = "";
    String post_type = "sport_knowledge_feed";
    String post_img_url = "";
//    @Override
//    protected void attachBaseContext(Context newBase) {
//      //  super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref = getPreferences(MODE_PRIVATE);
        memberId = pref.getInt( "userId", 0 );
        setContentView(R.layout.activity_newsfeed_write);

        backBtn = (ImageView) findViewById(R.id.write_back_btn);
        cameraBtn = (Button) findViewById(R.id.btnCamera);
        selectedImg = (ImageView) findViewById(R.id.post_selected_img);
        writeCompleteBtn = (TextView) findViewById(R.id.write_complete);
        editPost = (EditText) findViewById(R.id.edit_post);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // get a token
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        token = loginPreferences.getString("token", "");

        // post_type 받아오기
        Intent intent = getIntent();
        post_type = intent.getStringExtra("post_type");

        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                new CameraCropActivity().setListener(new CameraCropActivity.PicturePickListener() {
                    @Override
                    public void isSuccess(File files) {
                        Log.d("camera" , "camera 3 : " + files);
                        file = files;
                        Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
                        selectedImg.setImageBitmap(bitmap);
                    }

                    @Override
                    public void isFail() {
                        Log.d("camera" , "fail");
                    }
                }).show(getSupportFragmentManager(), null);

            }
        });

        writeCompleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                postContent = editPost.getText().toString();

                if(file != null){
                    new UploadImage().execute(new DBConnector());
                }else{
                    new Posts().execute(new DBConnector());
                }




            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private class UploadImage extends AsyncTask<DBConnector, Long, JSONObject> {


        @Override
        protected JSONObject doInBackground(DBConnector... params) {

            //it is executed on Background thread
           // String token = "db4fdaf8d9cb71b454a47c114422e29c4165e203";

            return params[0].UploadPostImage(file, token);

        }

        @Override
        protected void onPostExecute(final JSONObject jsonObject) {

            settextToAdapter_postImage(jsonObject);

        }
    }

    public void settextToAdapter_postImage(JSONObject jsonObject) {

        if(jsonObject == null){

        }else{

            post_img_url = "";
            try {
                post_img_url = jsonObject.getString("post_image_url").toString();

                Log.d("response" , "profile_img L " + post_img_url);
                if(post_img_url != ""){
                    new Posts().execute(new DBConnector());
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }



        }

    }
    private class Posts extends AsyncTask<DBConnector, Long, JSONObject> {


        @Override
        protected JSONObject doInBackground(DBConnector... params) {

            //it is executed on Background thread

            return params[0].Posts(token, post_type, postContent, "" , post_img_url);

        }

        @Override
        protected void onPostExecute(final JSONObject jsonObject) {

            settextToAdapter(jsonObject);



        }
    }

    public void settextToAdapter(JSONObject jsonObject) {

        Toast.makeText(getApplicationContext(), "게시물이 업로드 되었습니다.",
                Toast.LENGTH_LONG).show();

        finish();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }


}
