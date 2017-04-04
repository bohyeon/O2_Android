package com.example.hyerimhyeon.o2_android;


import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.DB.DBConnector;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;


public class NewsfeedUpdateActivity extends AppCompatActivity {
    private Button cameraBtn, galleryBtn;
    private ImageView backBtn;
    private ImageView selectedImg;
    private TextView writeCompleteBtn;
    private File file = null;
    private EditText editPost;
    private String postContent;
    private static int RESULT_LOAD_IMAGE = 1;

    NewsfeedUpdateActivity newsfeedWriteActivity = this;
    SharedPreferences pref;
    int memberId = 0;
    URLInString urlInString;

    private final int GALLERY_ACTIVITY_CODE=200;
    public SharedPreferences loginPreferences;

    TextView actionbar_title;
    Boolean buttonStateOpen;

    String token = "";
    String post_type = "";
    String post_img_url = "";
    String youtube_link = "";
    String youtube_title = "";
    String content = "";
    String sport_type;
    String post_id = "";


    private static final int REQUEST_EXTERNAL_STORAGE = 2;
    private static final int REQUEST_CAMERA = 1;
    private Uri mImageCaptureUri;


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
       // galleryBtn = (Button) findViewById(R.id.btnGallery);
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
        content = intent.getStringExtra("content");
        post_id = intent.getStringExtra("post_id");
//        youtube_title = intent.getStringExtra("youtube_title");
//        youtube_link = intent.getStringExtra("youtube_link");
        post_img_url = intent.getStringExtra("post_image_url");



        if(!post_img_url.equals("")){
            //Log.d("response", "post img url : " + post_img_url);
            Picasso.with(this)
                    .load("http://"+post_img_url)
                    .placeholder(R.drawable.blankimg)
                    .error(R.drawable.blankimg)
                    .into(selectedImg);
        }

        editPost.setText(content);

//        if(intent.getStringExtra("sport_type")==null){
//
//        }else{
//            sport_type = intent.getStringExtra("sport_type");
//          //  Log.d("response" , "sport_type :" + sport_type);
//        }


        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                new PostCameraCropActivity().setListener(new PostCameraCropActivity.PicturePickListener() {
                    @Override
                    public void isSuccess(File files) {
                        file = files;
                        String fname = new File(getFilesDir(), file.toString()).getAbsolutePath();
                        //Bitmap bitmap = BitmapFactory.decodeFile(fname);
                        Uri uri = Uri.fromFile(file);
                        selectedImg.setImageURI(uri);
                    }

                    @Override
                    public void isFail() {
                        Log.d("camera" , "fail");
                    }
                }).show(getSupportFragmentManager(), null);


//                Intent intent = new Intent( MediaStore.ACTION_IMAGE_CAPTURE );
//
//                // 임시로 사용할 파일의 경로를 생성
//                String url = "tmp_" + String.valueOf( System.currentTimeMillis() ) + ".jpg";
//                mImageCaptureUri = Uri.fromFile( new File( Environment.getExternalStorageDirectory(), url ) );
//
//                intent.putExtra( android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri );
//                // 특정기기에서 사진을 저장못하는 문제가 있어 다음을 주석처리 합니다.
//                intent.putExtra( "return-data", true );
//
//                int permissionCamera = ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA);
//                if(permissionCamera == PackageManager.PERMISSION_DENIED) {
//                    ActivityCompat.requestPermissions(newsfeedWriteActivity, new String[]{android.Manifest.permission.CAMERA}, REQUEST_CAMERA);
//                } else {
//                   // Log.d( "response", "camera permission authorized" );
//                    startActivityForResult( intent, 100 );
//                }


            }
        });

        writeCompleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                postContent = editPost.getText().toString();


               if(postContent.equals("")){
                    Toast.makeText(getApplicationContext(), "내용을 입력해주세요!",
                            Toast.LENGTH_LONG).show();
                }else{

                   String [] parts = postContent.split("\\s+");

                   // Attempt to convert each item into an URL.
                   for( String item : parts ) try {

                       URL url = new URL(item);
                       // If possible then replace with anchor...

                       if(url.toString().indexOf("youtube.com") != -1){
                           youtube_link = url.toString();
                       }

                       if(url.toString().indexOf("youtu.be") != -1 ){
                           Log.d("response" , "is youtube url: " + url);
                           youtube_link = url.toString();
                       }
                   } catch (MalformedURLException e) {
                       // If there was an URL that was not it!...

                   }



                   if(youtube_link.equals("")){

                       if(file != null){

                           new UploadImage().execute(new DBConnector());
                       }else{
                           new PutContent().execute(new DBConnector());
                       }

                   }else{
                       new GetYoutube().execute(new DBConnector());
                   }

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

    private class PutContent extends AsyncTask<DBConnector, Long, JSONObject> {


        @Override
        protected JSONObject doInBackground(DBConnector... params) {

            //it is executed on Background thread
                Log.d("response22 " , "update" + token + post_img_url);
            return params[0].PutContent(token, post_id, post_type, postContent, youtube_title, youtube_link, post_img_url);

        }

        @Override
        protected void onPostExecute(final JSONObject jsonObject) {

            settextToAdapter_putContent(jsonObject);

        }
    }

    public void settextToAdapter_putContent(JSONObject jsonObject) {


//        Intent intent = new Intent( this, MainActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        startActivity(intent);

        if(jsonObject == null){

        }else{

            finish();
        }

    }

    private static File getOutputMediaFile(){
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "CameraDemo");

        if (!mediaStorageDir.exists()){
            if (!mediaStorageDir.mkdirs()){
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return new File(mediaStorageDir.getPath() + File.separator +
                "IMG_"+ timeStamp + ".jpg");
    }


    private class GetYoutube extends AsyncTask<DBConnector, Long, JSONObject> {


        @Override
        protected JSONObject doInBackground(DBConnector... params) {

            //it is executed on Background thread

            return params[0].GetYoutube(youtube_link);

        }

        @Override
        protected void onPostExecute(final JSONObject jsonArray) {

            settextToAdapter_youtube(jsonArray);

        }
    }

    public void settextToAdapter_youtube(JSONObject jsonArray) {

        if(jsonArray == null){
            Toast.makeText(getApplicationContext(), "유튜브 링크를 다시 입력해주세요.",
                    Toast.LENGTH_LONG).show();
        }else{
           // Log.d("response" , "youtube_json : " + jsonArray);

            try {
                youtube_title = jsonArray.getString("title").toString();

                if(file!=null){
                    new UploadImage().execute(new DBConnector());
                }else{
                    new PutContent().execute(new DBConnector());
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

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
                    new PutContent().execute(new DBConnector());
                }

                Log.d("response" , "update img : " + post_img_url);

            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("response" , "update img e: " + e.toString());
            }



        }

    }
    private class Posts extends AsyncTask<DBConnector, Long, JSONObject> {


        @Override
        protected JSONObject doInBackground(DBConnector... params) {

            //it is executed on Background thread

            Log.d("response" , "post_url : " + youtube_link);
            return params[0].Posts(token, post_type, postContent, youtube_link , post_img_url , youtube_title);

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            int permissionReadStorage = ContextCompat.checkSelfPermission(newsfeedWriteActivity, android.Manifest.permission.READ_EXTERNAL_STORAGE);
            int permissionWriteStorage = ContextCompat.checkSelfPermission(newsfeedWriteActivity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if(permissionReadStorage == PackageManager.PERMISSION_DENIED || permissionWriteStorage == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(newsfeedWriteActivity, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_EXTERNAL_STORAGE);

                ImageView imageView = (ImageView) findViewById(R.id.post_selected_img);
                Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
                imageView.setImageBitmap(bitmap);
            } else {
                ImageView imageView = (ImageView) findViewById(R.id.post_selected_img);
                Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
                imageView.setImageBitmap(bitmap);
            }



            file = new File(picturePath);
        }else if(requestCode == 100){
            ImageView imageView = (ImageView) findViewById(R.id.post_selected_img);
            String url = mImageCaptureUri.toString().substring(7);
            Bitmap bitmap = BitmapFactory.decodeFile(url);
//            int resizedWidth = 200;
//            int resizedHeight = 300;
//
//            bitmap.createScaledBitmap(bitmap, resizedWidth, resizedHeight, false);

            int permissionReadStorage = ContextCompat.checkSelfPermission(newsfeedWriteActivity, android.Manifest.permission.READ_EXTERNAL_STORAGE);
            int permissionWriteStorage = ContextCompat.checkSelfPermission(newsfeedWriteActivity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if(permissionReadStorage == PackageManager.PERMISSION_DENIED || permissionWriteStorage == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(newsfeedWriteActivity, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_EXTERNAL_STORAGE);
                Picasso.with(newsfeedWriteActivity)
                        .load(mImageCaptureUri)
                        .placeholder(R.drawable.blankimg)
                        .error(R.drawable.blankimg)
                        .into(imageView);
            } else {
                Picasso.with(newsfeedWriteActivity)
                        .load(mImageCaptureUri)
                        .placeholder(R.drawable.blankimg)
                        .error(R.drawable.blankimg)
                        .into(imageView);
            }


           // imageView.setImageBitmap(bitmap);

            file = new File(url);

        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CAMERA:
                for (int i = 0; i < permissions.length; i++) {
                    String permission = permissions[i];
                    int grantResult = grantResults[i];
                    if (permission.equals(android.Manifest.permission.CAMERA)) {
                        if (grantResult == PackageManager.PERMISSION_GRANTED) {
                            Log.d("response", "camera permission authorized2");

                        } else {
                            Log.d("response", "camera permission denied");
                            //  resultText.setText("camera permission denied");
                        }
                        break;
                    }
                }
                break;


            case REQUEST_EXTERNAL_STORAGE:
                for (int i = 0; i < permissions.length; i++) {
                    String permission = permissions[i];
                    int grantResult = grantResults[i];
                    if (permission.equals(android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        if(grantResult == PackageManager.PERMISSION_GRANTED) {
                            Log.d("response", "read/write storage  permission authorized2");
                        } else {
                            Log.d("response", "read/write storage  permission denied2");

                        }
                        break;
                    }
                }
                break;
        }
    }
}
