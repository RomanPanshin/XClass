package com.example.serverexample;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class Person {
    public static String name;
    public static String key;
    public static String claim;
    public static String uId;
    public static String idclass;


    public Person() {
    }


    //потом поменять модификаторы доступа у переменных

    /*public Person(String name, String key, String claim, String uId, String idclass){
        this.name =name;
        this.key = key;
        this.claim = claim;
        this.uId = uId;
        this.idclass = idclass;
    }*/

    public String getClassId() { return idclass; }

    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }

    public String getClaim() {
        return claim;
    }

    public String getuId() {
        return uId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setClaim(String claim) {
        this.claim = claim;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public void setClassId(String idclass) {
        this.idclass = idclass;
    }
}
