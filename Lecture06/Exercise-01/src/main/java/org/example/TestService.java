package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TestService {
    private SecondBean secondBean;
    @Autowired
    private WiredBean wiredBean;

    @Value("${first.property}")
    private String firstProperty;
    @Value("${second.property}")
    private String secondProperty;

    @Autowired
    private ApplicationProperties applicationProperties;

    public TestService(SecondBean secondBean) {
        this.secondBean = secondBean;
    }

    public String test() {
        return "Message from TestService. " +
                "\nIncoming message from SecondBean: " +
                secondBean.message() +
                "\nIncoming message from WiredBean: " +
                wiredBean.message() +
                "\nFirst property value: " +
                firstProperty +
                "\nSecond property value: " +
                secondProperty +
                "\nApplication property one value: " +
                applicationProperties.getOne() +
                "\nApplication property two value: " +
                applicationProperties.getTwo();
    }
}
