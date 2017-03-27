package com.example.hyerimhyeon.o2_android;


import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.example.grid.DynamicHeightTextView;

import java.util.ArrayList;

/***
 * ADAPTER
 */

public class SubjectAdapterActivity extends ArrayAdapter<String> {

    private static final String TAG = "SampleAdapter";

    static class ViewHolder {
        DynamicHeightTextView txtLineOne;
        Button btnGo;
    }

    private final LayoutInflater mLayoutInflater;
   // private final Random mRandom;
    public final ArrayList<Integer> mBackgroundColors;

    private static final SparseArray<Double> sPositionHeightRatios = new SparseArray<Double>();

    public SubjectAdapterActivity(final Context context, final int textViewResourceId) {
        super(context, textViewResourceId);
        mLayoutInflater = LayoutInflater.from(context);
       // mRandom = new Random();
        mBackgroundColors = new ArrayList<Integer>();

    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        ViewHolder vh;

        double positionHeight = getPositionRatio(position);
//        int backgroundIndex = position >= mBackgroundColors.size() ?
//                position % mBackgroundColors.size() : position;

        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.list_item_sample, parent, false);
            vh = new ViewHolder();
            vh.txtLineOne = (DynamicHeightTextView) convertView.findViewById(R.id.txt_line1);


            convertView.setTag(vh);

            //mBackgroundColors.add(R.drawable.basketball);
          //  Log.d("response" , "mbackground size : " + mBackgroundColors.size());
            if (position < mBackgroundColors.size()){
                convertView.setBackgroundResource(mBackgroundColors.get(position));


//                Log.d(TAG, "getView position:" + position + " h:" + positionHeight);

                vh.txtLineOne.setHeightRatio(1);
                vh.txtLineOne.setText(getItem(position) + position);
            }else{

            }
        }
        else {
            vh = (ViewHolder) convertView.getTag();
            //mBackgroundColors.add(R.drawable.basketball);

            //mBackgroundColors.add(R.drawable.basketball);
           // Log.d("response" , "mbackground size : " + mBackgroundColors.size());
            if (position < mBackgroundColors.size()){
                convertView.setBackgroundResource(mBackgroundColors.get(position));


              //  Log.d(TAG, "getView position:" + position + " h:" + positionHeight);

                vh.txtLineOne.setHeightRatio(1);
                vh.txtLineOne.setText(getItem(position) + position);
            }else{

            }

        }





        return convertView;
    }

    private double getPositionRatio(final int position) {
        double ratio = sPositionHeightRatios.get(position, 0.0);
        // if not yet done generate and stash the columns height
        // in our real world scenario this will be determined by
        // some match based on the known height and width of the image
        // and maybe a helpful way to get the column height!
        if (ratio == 0) {
            ratio = getRandomHeightRatio();
            sPositionHeightRatios.append(position, ratio);
           // Log.d(TAG, "getPositionRatio:" + position + " ratio:" + ratio);
        }
        return 1;
    }

    private double getRandomHeightRatio() {
        return 0.5; // height will be 1.0 - 1.5 the width
    }
}