package com.example.wk01hw02solo.utility;

public class Utils {

    public static boolean verifyUsername(String username, String persistedUsername) {

        return username.equals(persistedUsername);
    }

    public static boolean verifyPassword(String password, String persistedPassword) {

        return password.equals(persistedPassword);
    }
}
