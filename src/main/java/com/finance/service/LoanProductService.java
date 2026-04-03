package com.finance.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class LoanProductService {

    public static class Product {
        public String id;
        public String name;
        public BigDecimal minLimit;
        public BigDecimal maxLimit;
        public double baseRate;

        public Product(String id, String name, BigDecimal min, BigDecimal max, double rate) {
            this.id = id; this.name = name; this.minLimit = min; this.maxLimit = max; this.baseRate = rate;
        }
    }

    public List<Product> getAvailableProducts() {
        List<Product> products = new ArrayList<>();
        products.add(new Product("P001", "직장인 신용대출", new BigDecimal("1000000"), new BigDecimal("100000000"), 4.5));
        products.add(new Product("P002", "주택담보대출", new BigDecimal("10000000"), new BigDecimal("500000000"), 3.2));
        products.add(new Product("P003", "햇살론(서민금융)", new BigDecimal("500000"), new BigDecimal("20000000"), 8.5));
        return products;
    }
}