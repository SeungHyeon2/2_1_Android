package com.example.myapplication;

// BlogItem 클래스 생성
// BlogItem 타입을 제네릭으로 사용하여 List에 넣을 목적
public class BlogItem {

    // 필드로 String 타입의 title과 link를 가짐
    String title;
    String link;

    // title 의 getter와 setter 메소드 생성
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // link의 getter와 setter 메소드 생성
    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
