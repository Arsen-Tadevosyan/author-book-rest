package com.example.authorbookrest.util;

public final class StringUtil {

    public boolean isEmpty(String value) {
        if (value == null || value.length() == 0) {
            return true;
        }
        return false;
    }
}
