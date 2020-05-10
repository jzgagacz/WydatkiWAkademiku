package com.example.dormitoryexpenses;

public class LoggedInUser {

    private static String user;

    public static void setUser(String username){
        user = username;
    }

    public static String getUser(){
        return user;
    }
}
