package com.smhrd.seniorproject;

import java.io.Serializable;

public class MemberDTO implements Serializable {
    private String id;
    private String pw;
    private String name;
    private String tel;
    private String type;


    public MemberDTO(String id, String pw, String name, String tel, String type) {
        this.id = id;
        this.pw = pw;
        this.name = name;
        this.tel = tel;
        this.type = type;

    }

    public String getId() {
        return id;
    }

    public String getPw() {
        return pw;
    }

    public String getName() {
        return name;
    }

    public String getTel() {
        return tel;
    }

    public String getType() {
        return type;
    }
}


