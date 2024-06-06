package com.example.til.domain;


import java.beans.ConstructorProperties;

public class PostForm {
    private String title;
    private String content;
    private int viewCount; // 조회수 추가



    // 생성자, 게터, 세터
    @ConstructorProperties({"title", "content", "viewCount"})
    public PostForm(String title, String content, int viewCount) {
        this.title = title;
        this.content = content;
        this.viewCount = viewCount;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }
}

