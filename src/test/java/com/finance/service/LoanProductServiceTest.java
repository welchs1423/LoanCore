package com.finance.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;
import org.junit.jupiter.api.Test;

public class LoanProductServiceTest {

    @Test
    public void testGetAvailableProducts() {
        LoanProductService service = new LoanProductService();
        List<LoanProductService.Product> products = service.getAvailableProducts();
        assertEquals(3, products.size());
        assertEquals("직장인 신용대출", products.get(0).name);
    }
}