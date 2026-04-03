package com.finance.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpSession;

public class CustomerAuthControllerTest {

    private CustomerAuthController controller;

    @BeforeEach
    public void setUp() {
        controller = new CustomerAuthController();
    }

    @Test
    public void testLogin() {
        MockHttpSession session = new MockHttpSession();
        String viewName = controller.login("L001", session);
        
        assertEquals("redirect:/customer/mypage?id=L001", viewName);
        assertEquals("L001", session.getAttribute("CUSTOMER_APP_ID"));
    }

    @Test
    public void testLogout() {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("CUSTOMER_APP_ID", "L001");
        
        String viewName = controller.logout(session);
        
        assertEquals("redirect:/customer/login", viewName);
        assertEquals(null, session.getAttribute("CUSTOMER_APP_ID"));
    }
}