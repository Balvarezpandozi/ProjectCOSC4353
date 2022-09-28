package com.softwaredesign;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class MainTest {

    @Test
    void testSum() {
        Main m = new Main();

        Assertions.assertEquals(15,m.sum(6,9));
    }
}