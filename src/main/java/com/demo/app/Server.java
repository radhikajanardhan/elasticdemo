package com.demo.app;

import com.demo.app.service.ElasticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@ComponentScan(basePackages="com.demo.app")
public class Server extends SpringBootServletInitializer {

    @Autowired
    private ElasticService elasticService;

    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Server.class);
    }
    public static void main(String s[])
    {
        SpringApplication.run(Server.class,s);
    }

}
