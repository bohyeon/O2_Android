package com.example.hyerimhyeon.o2_android;


import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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

    TextView actionbar_title;
    Boolean buttonStateOpen;
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


//                Intent gallery_Intent2 = new Intent(getApplicationContext(), CameraCropActivity.class);
//                startActivityForResult(gallery_Intent2, 300);

//                new PictureDialogFragment().setListener(new JoinActivity.PicturePickListener() {
//                    @Override
//                    public void isSuccess(File files) {
//                        file = files;
//                        Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
//                        selectedImg.setImageBitmap(bitmap);
//                    }
//
//                    @Override
//                    public void isFail() {
//
//                    }
//                }).show(getSupportFragmentManager(), null);

            }
        });

        writeCompleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postContent = editPost.getText().toString();


//                ServerQuery.writeNewPost( memberId, postContent, file,
//                        new Callback() {
//
//                            @Override
//                            public void onResponse(Call call, Response response ) {
//
//                                if ( response.isSuccessful() ) {
//                                    ResponseWritePost responseWritePost = (ResponseWritePost) response.body();
//                                    if ( responseWritePost.success ) {
//                                        Toast.makeText ( getApplicationContext(), "Success", Toast.LENGTH_LONG );
//                                    }
//                                    else {
//
//                                    }
//
//                                }
//                                else {
//                                    Toast.makeText( getApplicationContext(), "Gentle Map 불러오기 실패", Toast.LENGTH_LONG );
//                                }
//                            }
//
//                            @Override
//                            public void onFailure( Call call, Throwable t ) {
//                                Toast.makeText( getApplicationContext(), "Gentle Map 불러오기 실패", Toast.LENGTH_LONG );
//
//                            }
//                        } );



                finish();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
//
//    protected void onActivityResult(int requestCode, int resultCode, Intent intent){
////        Uri u = intent.getData();
//
//        Log.d("cameraurl","url2 : " + requestCode);
//    }


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
