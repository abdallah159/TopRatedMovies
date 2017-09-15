package iti.abdallah.logintest;

import java.util.ArrayList;

/**
 * Created by abdallah on 12/09/17.
 */

public class LoginAnswer {

    private int id ;
    private String name;
//    private String phone;
//    private ArrayList friends;
//    private ArrayList users;

    public LoginAnswer(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
