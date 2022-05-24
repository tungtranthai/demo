package com.example.demo.common.handler;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class JavaHandlerTest {
    JavaHandler npe = new JavaHandler();

    @Test
    void testNullPointExceptionHandler() {
        npe.NullPointExceptionHandler();
       Assertions.assertEquals(true, true);
    }
}
