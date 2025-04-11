package org.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FirstConfiguration {
    @Bean
    public SecondBean secondBean() {return new SecondBean();}

}
