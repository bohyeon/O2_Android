package com.example.FCM;


import android.os.AsyncTask;

import com.example.DB.DBConnector;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService
{
    private static final String TAG = "FCM GET ID";
    String user_token;
    String refreshedToken;

    public void onTokenRefresh(String token)
    {

        // Get updated InstanceID token.
        refreshedToken = FirebaseInstanceId.getInstance().getToken();
        user_token = token;
//        Log.d(TAG, "Refreshed token1: " + token);
//        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // TODO: Implement this method to send any registration to your app's servers.
        sendRegistrationToServer(token, refreshedToken);
    }

    private void sendRegistrationToServer(String token, String refreshedToken)
    {
        new PushToken().execute(new DBConnector());
    }

    private class PushToken extends AsyncTask<DBConnector, Long, String> {


        @Override
        protected String doInBackground(DBConnector... params) {

            //it is executed on Background thread

            return params[0].PushToken(user_token,refreshedToken);

        }

        @Override
        protected void onPostExecute(final String jsonObject) {

            settextToAdapter(jsonObject);

        }
    }

    public void settextToAdapter(String jsonObject) {

        if(jsonObject == null){

        }else{


        }


    }


}
