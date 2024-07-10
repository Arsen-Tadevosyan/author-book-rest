package com.example.authorbookrest.util;

import org.springframework.stereotype.Component;

import java.util.Arrays;

public class NumberUtil {

    public int maxOfArray(int[] arr){
        if (arr==null){
            return 0;
        }
        return Arrays.stream(arr).max().getAsInt();
    }


}
