package com.example.hyerimhyeon.o2_android;


import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;

public class URLInString {
    public static void main(String args) {
        String s = args;
        // separate input by spaces ( URLs don't have spaces )
        String [] parts = s.split("\\s+");

        // Attempt to convert each item into an URL.
        for( String item : parts ) try {
            URL url = new URL(item);
            // If possible then replace with anchor...
            Log.d("response", "url : <a href=\"" + url + "\">" + url + "</a> ");
        } catch (MalformedURLException e) {
            // If there was an URL that was not it!...
            Log.d("response", "url : "+ item + " ");

        }

        System.out.println();
    }
}