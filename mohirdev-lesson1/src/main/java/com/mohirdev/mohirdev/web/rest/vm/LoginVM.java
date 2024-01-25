package com.mohirdev.mohirdev.web.rest.vm;

public class LoginVM {

    private String workername;
    private String password;
    private boolean rememberMe;

    public String getWorkername() {
        return workername;
    }

    public void setWorkername(String workername) {
        this.workername = workername;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }
}
