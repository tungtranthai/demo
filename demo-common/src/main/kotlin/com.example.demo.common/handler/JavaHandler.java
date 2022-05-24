package com.example.demo.common.handler;

import java.util.List;

public class JavaHandler {
    /**
     * @return
     */
    public String toString() {
        String sampleString = null;
        return sampleString.toString(); // exception here
    }

    /**
     * cast object in Java
     *
     * @param object
     * @return
     */
    public int getDefaultSize(Object object) {
        if (object instanceof String) {
            return ((String) object).length();
        } else if (object instanceof List) {
            return ((List) object).size();
        }
        return 0;
    }

    /**
     * NullPointExceptionHandler
     */
    public void NullPointExceptionHandler() {
        String sample1 = "Java";
        sample1 = null;
        sample1.toString(); // A "NullPointerException" could be thrown;

    }
}
