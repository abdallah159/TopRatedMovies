package model;

/**
 * Created by abdallah on 10/09/17.
 */

public class ModelSignUp {

    private String status;
    private String result;

    public ModelSignUp(String status, String result) {
        this.status = status;
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
