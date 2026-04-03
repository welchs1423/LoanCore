package com.finance.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TcpSocketClientTest {

    private TcpSocketClient tcpSocketClient;

    @BeforeEach
    public void setUp() {
        tcpSocketClient = new TcpSocketClient();
    }

    @Test
    public void testSendAndReceive_ErrorOnInvalidHost() {
        String response = tcpSocketClient.sendAndReceive("256.256.256.256", 9999, "TEST");
        assertEquals("ERROR", response);
    }
}