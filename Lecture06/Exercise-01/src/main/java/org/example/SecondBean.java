package org.example;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

public class SecondBean {
    @PostConstruct
    public void init() {
        System.out.println("Wired bean created");
    }
    @PreDestroy
    public void destroy() {
        System.out.println("Wired bean destroyed");
    }
    public String message() {return "Second bean message";}
}
