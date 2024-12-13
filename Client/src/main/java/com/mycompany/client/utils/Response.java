package com.mycompany.client.utils;

public class Response <T>{

    private boolean success = false;
    private String message = "";
    private T attachment = null;


    public T getAttachment() {
        return attachment;
    }
    public void setAttachment(T attachment) {
        this.attachment = attachment;
    }
    public boolean isSuccess() {
        return success;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    

    

}
