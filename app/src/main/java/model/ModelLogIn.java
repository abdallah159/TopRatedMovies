package model;

import java.util.List;

import iti.abdallah.logintest.LoginAnswer;

/**
 * Created by abdallah on 10/09/17.
 */

public class ModelLogIn {

    private String status;
    private LoginAnswer result;

    public ModelLogIn(String status, LoginAnswer result) {
        this.status = status;
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LoginAnswer getResult() {
        return result;
    }

    public void setResult(LoginAnswer result) {
        this.result = result;
    }
}
