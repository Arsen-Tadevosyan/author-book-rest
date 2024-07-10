package com.example.authorbookrest.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringUtilTest {

    StringUtil stringUtil = new StringUtil();

    @Test
    void isEmptyWithNull() {
        boolean result = stringUtil.isEmpty(null);
        assertTrue(result);
    }
    @Test
    void isEmptyWithEmptyString() {
        boolean result = stringUtil.isEmpty("");
        assertTrue(result);
    }
    @Test
    void isEmptyWithValidValue() {
        boolean result = stringUtil.isEmpty("asdf");

        assertFalse(result);
    }
}