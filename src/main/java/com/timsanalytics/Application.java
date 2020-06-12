package com.timsanalytics;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
        /*
            Used when deploying a WAR, instead of running from the command line.
            "The first step in producing a deploy-able WAR file is to provide a
            SpringBootServletInitializer subclass and override its configure method. Doing so makes
            use of Spring Framework's Servlet 3.0 support and lets you configure your application
            when it is launched by the servlet container."
         */
    }

    public static void main(String args[]) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            /*
            System.out.println("Let's inspect the beans provided by Spring Boot:");
            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for(String beanName: beanNames) {
                System.out.println(beanName);
            }
            */
        };
    }
}
