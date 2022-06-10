package com.example.myapplication;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

// BaseAdapter 인터페이스를 상속받는 BlogAdapter 클래스 생성

// BlogAdapter는 데이터의 원본을 관리하고, 어댑터 뷰가 출력할 수 있는 형태로
// 데이터를 제공하는 중간 객체 역할을 할 예정

public class BlogAdapter extends BaseAdapter {

    // BlogAdapter의 필드를 private 접근 제한자로 선언
    private Context context;
    private List<BlogItem> blogList;

    // BlogAdapter 생성자 정의
    // parameter로 context와 List<BlogItem>을 가짐
    public BlogAdapter(Context context, List<BlogItem> blogList) {
        this.context = context;
        this.blogList = blogList;
    }

    // BlogAdapter는 리스트의 사이즈, Id, 아이템을 리턴받는 메소드를 각각 가짐
    @Override
    public int getCount() {
        return blogList.size();
    }

    @Override
    public Object getItem(int i) {
        return blogList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    // getView 메소드는 blog_item.xml 파일을 전개하여 View 타입의 v 변수에 전개하여 리턴함
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.blog_item, null);


        TextView title = (TextView)v.findViewById(R.id.title);
        TextView link = (TextView)v.findViewById(R.id.link);

        title.setText(blogList.get(i).getTitle());
        link.setText(blogList.get(i).getLink());


        v.setTag(blogList.get(i).getTitle());
        return v;
    }

}