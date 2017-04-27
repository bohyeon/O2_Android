package com.o2_company.hyerimhyeon.o2_android;


import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class SolventViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView countryName;
    public ImageView countryPhoto;

  //  List<String> strings = Arrays.asList("축구", "농구" ,"배구", "탁구" ,"테니스" , "역도" ,"유도" ,"태권도","복싱","레슬링","승마","체조","수영","육상","펜싱","요트","조정","카누/카약","사격","하키","핸드볼","근대5종","트라이애슬론(철인3종)","배드민턴","골프","사이클","수영","럭비","야구","씨름","보디빌딩","수상스키","정구","당구","라켓볼","바둑","루지","스켈레톤","봅슬레이","알파인스키","스키점프","프리스타일스키","바이애슬론","노르딕복합","아이스하키","컬링","스피드스케이팅","쇼트트랙","피겨스케이팅","스노우보드","크로스컨트리","스키점프");
    List<String> strings = Arrays.asList("축구","야구","농구","배구","탁구","테니스","역도","유도","태권도","복싱","레슬링","승마","체조","육상","펜싱","요트","조정","사격","하키","핸드볼","근대5종","철인3종","배드민턴","정구","골프","싸이클","수영","럭비","씨름","보디빌딩","봅슬레이,루지,스켈레톤","바이애슬론","스키,스노우보드","아이스하키","컬링","빙상","공수도","양궁","볼링","검도","롤러","세팍타크로","소프트볼","스쿼시","기타");


    public SolventViewHolders(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
      //  countryName = (TextView) itemView.findViewById(R.id.country_name);
        countryPhoto = (ImageView) itemView.findViewById(R.id.country_photo);

//        countryPhoto.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("response" , "photo : " + countryPhoto.getTag());
//                countryPhoto.getTag();
//            }
//        });
    }

    @Override
    public void onClick(View view) {

     //   Toast.makeText(view.getContext(), "Clicked Position = " + getPosition(), Toast.LENGTH_SHORT).show();

        String str = strings.get(getPosition());
        Intent intent = new Intent(view.getContext() , SubjectFeedActivity2.class);
        intent.putExtra("sport_type" , str);

        view.getContext().startActivity(intent);

    }
}