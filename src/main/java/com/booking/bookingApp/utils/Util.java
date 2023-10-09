package com.booking.bookingApp.utils;

public class Util {
    public static String nullToString(String value) {
        if (value != null) {
            return value;
        } else {
            return "";
        }
    }
}
