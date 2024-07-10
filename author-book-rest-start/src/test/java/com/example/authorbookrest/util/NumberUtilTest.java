package com.example.authorbookrest.util;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.junit.jupiter.api.Assertions.*;


class NumberUtilTest {


     NumberUtil numberUtil = new NumberUtil();


    @Test
    void trueV() {
        int [] arr= {1,34,57,8};
        int result = numberUtil.maxOfArray(arr);
        assertEquals(result,57);
    }


}