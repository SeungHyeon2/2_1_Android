package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.bumptech.glide.Glide;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    // Url 변수, List 변수, ListView 변수, BlogAdapter 변수, BlogItem 변수를 사용하기 위해 미리 각각 선언
    private String requestUrl;
    List<BlogItem> blogList;
    ListView listView;
    BlogAdapter adapter;
    BlogItem blogItem;

    // 생명주기의 onResume 메소드를 재정의하여 뷰가 보여지고 있을때 AsyncTask를 실행하게 함
    @Override
    protected void onResume(){
        super.onResume();
        new MyAsyncTask().execute();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // listView 레이아웃을 변수에 저장
        listView =  findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() { //리스트뷰 클릭시
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // listView를 클릭했을때 일어나는 이벤트를 정의한 리스너
                Intent intent = new Intent(Intent.ACTION_VIEW);
                //Intent 시용
                Uri uri = Uri.parse(blogList.get(position).link);
                //리스트뷰의 링크 위치값을 받아와서 uri를 인터넷으로 열게함.
                intent.setData(uri);
                startActivity(intent);
            }
        });

    }

    //AsyncTask로 비동기 방식으로 데이터 파싱
    class MyAsyncTask extends AsyncTask<String, Void, String> {

        // AsyncTask의 doInBackground를 재정의
        @Override
        protected String doInBackground(String... strings) {

            requestUrl = "http://rss.blog.naver.com/enazzim.xml";
            //url에 교수님의 블로그를 지정
            try {

                boolean b_title = false;
                boolean b_link = false;

                // 파싱을 위해 requestUrl 변수를 넣어 url 생성
                URL url = new URL(requestUrl);
                InputStream is = url.openStream();
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                XmlPullParser parser = factory.newPullParser();
                parser.setInput(new InputStreamReader(is, "UTF-8"));
                // XmlPullParser를 이용해 데이터 파싱 스트림리더로 한글형식 받아옴
                String tag;
                int eventType = parser.getEventType();

                // 파서가 문서를 읽을때 동작을 while문과 switch문을 적용하여 정의
                while(eventType != XmlPullParser.END_DOCUMENT){
                    //반복문을 통한 XML파일의 끝에 도달했을때 반환값
                    switch (eventType){
                        case XmlPullParser.START_DOCUMENT:
                            blogList = new ArrayList<BlogItem>();
                            break;
                        //XML 파일의 맨 처음의 반환값
                        case XmlPullParser.END_DOCUMENT:
                            break;
                        case XmlPullParser.END_TAG:
                            //요소의 종료태그를 만났을때 반환값

                            if(parser.getName().equals("title")) {
                            }
                            if(parser.getName().equals("link")) {
                            }
                            break;
                        case XmlPullParser.START_TAG:

                            if(parser.getName().equals("title")){
                                //title태그면 BlogItem 생성
                                blogItem = new BlogItem();
                                b_title = true;
                            }
                            if(parser.getName().equals("link")){
                                //link태그면 title,link를 리스트에 담음
                                blogList.add(blogItem);
                                b_link = true;
                            }
                            break;
                        case XmlPullParser.TEXT:
                            //요소의 문자를 만났을때 반환값
                            if (b_link){
                                blogItem.setLink(parser.getText());
                                //link를 만났을때 BlogItem link 값 저장
                                b_link = false;
                            }
                            else if (b_title){
                                blogItem.setTitle(parser.getText());
                                //title를 만났을때 BlogItem title 값 저장
                                b_title = false;
                            }
                            break;

                    }
                    eventType = parser.next();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        //AsyncTask의 onPostExecute 메소드를 재정의해서 결과값 전달방식 정의
        @Override
        protected void onPostExecute(String s) {
            //결과값
            super.onPostExecute(s);
            adapter = new BlogAdapter(getApplicationContext(), blogList);
            //리스트를 어댑터에 넣어 리스트뷰에 셋어댑터
            listView.setAdapter(adapter);
        }
    }
}