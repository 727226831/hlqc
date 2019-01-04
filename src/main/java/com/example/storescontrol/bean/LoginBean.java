package com.example.storescontrol.bean;

import java.util.ArrayList;
import java.util.List;


/**
 * Copyright 2018 bejson.com
 */

/**
 * Auto-generated: 2018-11-16 11:20:42
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class LoginBean {

    private String Resultcode;
    private String ResultMessage;
    private String usercode;
    private String username;
    private String acccode;
    private String accname;

    public String getVersionNumber() {
        return VersionNumber;
    }

    public void setVersionNumber(String versionNumber) {
        VersionNumber = versionNumber;
    }

    private String VersionNumber;
    private List<User> data=new ArrayList<>();
    public void setResultcode(String Resultcode) {
        this.Resultcode = Resultcode;
    }
    public String getResultcode() {
        return Resultcode;
    }

    public void setResultMessage(String ResultMessage) {
        this.ResultMessage = ResultMessage;
    }
    public String getResultMessage() {
        return ResultMessage;
    }

    public void setUsercode(String usercode) {
        this.usercode = usercode;
    }
    public String getUsercode() {
        return usercode;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public String getUsername() {
        return username;
    }

    public void setAcccode(String acccode) {
        this.acccode = acccode;
    }
    public String getAcccode() {
        return acccode;
    }

    public void setAccname(String accname) {
        this.accname = accname;
    }
    public String getAccname() {
        return accname;
    }

    public void setData(List<User> data) {
        this.data = data;
    }
    public List<User> getData() {
        return data;
    }
    public class User {

        private String menucode;
        private String menuname;
        public void setMenucode(String menucode) {
            this.menucode = menucode;
        }
        public String getMenucode() {
            return menucode;
        }

        public void setMenuname(String menuname) {
            this.menuname = menuname;
        }
        public String getMenuname() {
            return menuname;
        }

    }

}

