package hr.foi.fbrd.sensei.models;

import java.util.ArrayList;

public class BaseResponse<T> {

    private int status;
    private ArrayList<T> message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ArrayList<T> getMessage() {
        return message;
    }

    public void setMessage(ArrayList<T> message) {
        this.message = message;
    }
}
