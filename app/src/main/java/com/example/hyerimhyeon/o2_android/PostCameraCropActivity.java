package com.example.hyerimhyeon.o2_android;


import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

public class PostCameraCropActivity extends android.support.v4.app.DialogFragment implements OnClickListener
{
    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int CROP_FROM_CAMERA = 2;

    private PostCameraCropActivity.PicturePickListener listener;

    private PostCameraCropActivity cameraCropActivity = this;

    private Uri mImageCaptureUri;
    private ImageView mPhotoImageView;
    private Button mButton;
    private static final String TAG = "AppPermission";

    private final int MY_PERMISSION_REQUEST_STORAGE = 100;

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 0;


    private static final int REQUEST_MICROPHONE = 3;
    private static final int REQUEST_EXTERNAL_STORAGE = 2;
    private static final int REQUEST_CAMERA = 1;



    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.camera_activity);
//
//        mButton = (Button) findViewById(R.id.button);
//        mPhotoImageView = (ImageView) findViewById(R.id.image);
//
//        mButton.setOnClickListener(this);
    }


    public Dialog onCreateDialog(Bundle savedInstanceState ) {

        if ( null != savedInstanceState )
            dismiss();
        final AlertDialog dialog = new AlertDialog.Builder( getActivity() )
                .setView( bindView() )
                .create();
        dialog.getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE );

        return dialog;
    }

    private View bindView() {

        View view = LayoutInflater.from( getActivity() ).inflate( R.layout.dialog_picture_pick, null );
        view.findViewById( R.id.view_camera ).setOnClickListener( new OnClickListener() {

            @Override
            public void onClick( View v ) {


                doTakePhotoAction();
            }
        } );

        view.findViewById( R.id.view_gallery ).setOnClickListener( new OnClickListener() {

            @Override
            public void onClick( View v ) {

                doTakeAlbumAction();
            }
        } );
        view.findViewById( R.id.view_x ).setOnClickListener( new OnClickListener() {

            @Override
            public void onClick( View v ) {

                dismiss();
            }
        } );
        return view;
    }


//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults ) {
//
//        super.onRequestPermissionsResult( requestCode, permissions, grantResults );
////		ExtraFragmentPermissionsDispatcher.onRequestPermissionsResult( this, requestCode, grantResults );
//    }

    /**
     * 카메라에서 이미지 가져오기
     */
    public void doTakePhotoAction()
    {

        int permissionCamera = ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.CAMERA);
        if(permissionCamera == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.CAMERA}, REQUEST_CAMERA);
        } else {
            Log.d( "response", "camera permission authorized" );

        }

    /*
     * 참고 해볼곳
     * http://2009.hfoss.org/Tutorial:Camera_and_Gallery_Demo
     * http://stackoverflow.com/questions/1050297/how-to-get-the-url-of-the-captured-image
     * http://www.damonkohler.com/2009/02/android-recipes.html
     * http://www.firstclown.us/tag/android/
     */

        Intent intent = new Intent( MediaStore.ACTION_IMAGE_CAPTURE );

        // 임시로 사용할 파일의 경로를 생성
        String url = "tmp_" + String.valueOf( System.currentTimeMillis() ) + ".jpg";
        mImageCaptureUri = Uri.fromFile( new File( Environment.getExternalStorageDirectory(), url ) );

        intent.putExtra( MediaStore.EXTRA_OUTPUT, mImageCaptureUri );
        // 특정기기에서 사진을 저장못하는 문제가 있어 다음을 주석처리 합니다.
        intent.putExtra( "return-data", true );

        startActivityForResult( intent, PICK_FROM_CAMERA );
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

    /**
     * 앨범에서 이미지 가져오기
     */
    private void doTakeAlbumAction()
    {
        int permissionReadStorage = ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE);
        int permissionWriteStorage = ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(permissionReadStorage == PackageManager.PERMISSION_DENIED || permissionWriteStorage == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_EXTERNAL_STORAGE);
        } else {
            Log.d("response", "read/write storage  permission authorized");
        }

        // 앨범 호출
        Log.d( "ALBUM", "GO" );
        Intent intent = new Intent( Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI );
        intent.setType( MediaStore.Images.Media.CONTENT_TYPE );
        startActivityForResult( intent, PICK_FROM_ALBUM );
    }

    @Override
    public void onActivityResult( int requestCode, int resultCode, Intent data ) {

        if ( resultCode != Activity.RESULT_OK ) {
            Log.d( "FAILED", "FAILED" );
            return;
        }

        switch ( requestCode ) {
            case CROP_FROM_CAMERA: {
                // 크롭이 된 이후의 이미지를 넘겨 받습니다.
                // 이미지뷰에 이미지를 보여준다거나 부가적인 작업 이후에
                // 임시 파일을 삭제합니다.

                File f = new File( mImageCaptureUri.getPath() );
                Log.d( "camera", "url123 : " + mImageCaptureUri.getPath() );


                if(f.length()>=1048576){
                    try
                    {
                        int inWidth = 0;
                        int inHeight = 0;

                        InputStream in = new FileInputStream(f.getAbsolutePath());

                        // decode image size (decode metadata only, not the whole image)
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inJustDecodeBounds = true;
                        BitmapFactory.decodeStream(in, null, options);
                        in.close();
                        in = null;

                        // save width and height
                        inWidth = options.outWidth;
                        inHeight = options.outHeight;

                        int dstWidth = inWidth/2;
                        int dstHeight = inHeight/2;

                        // decode full image pre-resized
                        in = new FileInputStream(f.getAbsolutePath());
                        options = new BitmapFactory.Options();
                        // calc rought re-size (this is no exact resize)
                        options.inSampleSize = Math.max(inWidth/dstWidth, inHeight/dstHeight);
                        // decode full image
                        Bitmap roughBitmap = BitmapFactory.decodeStream(in, null, options);

                        // calc exact destination size
                        Matrix m = new Matrix();
                        RectF inRect = new RectF(0, 0, roughBitmap.getWidth(), roughBitmap.getHeight());
                        RectF outRect = new RectF(0, 0, dstWidth, dstHeight);
                        m.setRectToRect(inRect, outRect, Matrix.ScaleToFit.CENTER);
                        float[] values = new float[9];
                        m.getValues(values);

                        // resize bitmap
                        Bitmap resizedBitmap = Bitmap.createScaledBitmap(roughBitmap, (int) (roughBitmap.getWidth() * values[0]), (int) (roughBitmap.getHeight() * values[4]), true);

                        // save image
                        try
                        {
                            FileOutputStream out = new FileOutputStream(f.getAbsolutePath());
                            resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 60, out);
                        }
                        catch (Exception e)
                        {
                            Log.e("Image", e.getMessage(), e);
                        }
                    }
                    catch (IOException e)
                    {
                        Log.e("Image", e.getMessage(), e);
                    }
                }

                listener.isSuccess( f );

                dismiss();
                break;
            }

            case PICK_FROM_ALBUM: {
                // 이후의 처리가 카메라와 같으므로 일단  break없이 진행합니다.
                // 실제 코드에서는 좀더 합리적인 방법을 선택하시기 바랍니다.

                Log.d( "ALBUM", "OK" );

                mImageCaptureUri = data.getData();
                File original_file = getImageFile( mImageCaptureUri );

                mImageCaptureUri = createSaveCropFile();
                File cpoy_file = new File( mImageCaptureUri.getPath() );

                // SD카드에 저장된 파일을 이미지 Crop을 위해 복사한다.
                try {
                    copyFile( original_file, cpoy_file );
                }
                catch ( IOException e ) {
                    e.printStackTrace();
                }

            }

            case PICK_FROM_CAMERA: {

                // 이미지를 가져온 이후의 리사이즈할 이미지 크기를 결정합니다.
                // 이후에 이미지 크롭 어플리케이션을 호출하게 됩니다.
                Intent intent = new Intent( "com.android.camera.action.CROP" );
                intent.setDataAndType( mImageCaptureUri, "image/*" );
                intent.putExtra( "output", mImageCaptureUri );
                intent.putExtra( "crop", "true" );
                intent.putExtra( "scale", true );
                intent.putExtra( "return-data", true );
                startActivityForResult( intent, CROP_FROM_CAMERA );
                break;
            }
        }
    }

    public static boolean copyFile( File srcFile, File destFile ) throws IOException {

        FileInputStream inStream = new FileInputStream( srcFile );
        FileOutputStream outStream = new FileOutputStream( destFile );
        FileChannel inChannel = inStream.getChannel();
        FileChannel outChannel = outStream.getChannel();
        inChannel.transferTo( 0, inChannel.size(), outChannel );
        inStream.close();
        outStream.close();
        return true;
    }

    private File getImageFile( Uri uri ) {

        String[] projection = { MediaStore.Images.Media.DATA };
        if ( uri == null ) {
            uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        }

        Cursor mCursor = getActivity().getContentResolver().query( uri, projection, null, null,
                MediaStore.Images.Media.DATE_MODIFIED + " desc" );
        if ( mCursor == null || mCursor.getCount() < 1 ) {
            return null; // no cursor or no record
        }
        int column_index = mCursor.getColumnIndexOrThrow( MediaStore.Images.Media.DATA );
        mCursor.moveToFirst();

        String path = mCursor.getString( column_index );

        if ( mCursor != null ) {
            mCursor.close();
            mCursor = null;
        }

        return new File( path );
    }

    private Uri createSaveCropFile() {

        Uri uri;
        String url = "tmp_" + String.valueOf( System.currentTimeMillis() ) + ".jpg";
        uri = Uri.fromFile( new File( Environment.getExternalStorageDirectory(), url ) );
        return uri;
    }

    public File SaveBitmapToFileCache( Bitmap bitmap, String strFilePath ) {

        File fileCacheItem = new File( strFilePath );
        OutputStream out = null;

        try {
            fileCacheItem.createNewFile();
            out = new FileOutputStream( fileCacheItem );

            bitmap.compress( Bitmap.CompressFormat.JPEG, 100, out );
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }
        finally {
            try {
                out.close();
            }
            catch ( IOException e ) {
                e.printStackTrace();
            }
        }

        return fileCacheItem;
    }

    private void startImageEditorActivity( String selectedImagePath ) {

        ArrayList<String> selectedImagePaths = new ArrayList<>();
        selectedImagePaths.add( selectedImagePath );
        startImageEditorActivity( selectedImagePaths );
    }

    private void startImageEditorActivity( ArrayList<String> selectedImagePaths ) {

        if ( true ) {

//			Intent intent = new Intent( getActivity(), ImageEditorActivity.class );
//			intent.putExtra( ImageEditorActivity.IMAGE_PATHES, selectedImagePaths );
//			intent.putExtra( ImageEditorActivity.ASPECT_X, 1 );
//			intent.putExtra( ImageEditorActivity.ASPECT_Y, 1 );
//			startActivityForResult( intent, REQUEST_CODE_EDIT_IMAGE );
        }
        else {
        }
    }

    public PostCameraCropActivity setListener(PostCameraCropActivity.PicturePickListener  listener ) {

        this.listener = listener;
        return this;
    }

    @Override
    public void onClick(View v) {

    }


    public interface PicturePickListener {

        void isSuccess(File file);

        void isFail();
    }


}

