package com.smhrd.seniorproject;

import java.io.Serializable;

public class GardianDTO implements Serializable {
    private String name;

    public GardianDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
