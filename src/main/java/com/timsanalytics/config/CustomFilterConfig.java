package com.timsanalytics.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomFilterConfig {

    // IMPORTANT!!
    // "We may sometimes want a filter to only apply to certain URL patterns... we have to remove the
    // @Component annotation from the filter class definition and register the filter using a FilterRegistrationBean.
    // Ref: https: //www.baeldung.com/spring-boot-add-filter

    @Bean
    public FilterRegistrationBean<ApiFilter> apiFilter() {
        FilterRegistrationBean<ApiFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new ApiFilter());
        filterRegistrationBean.addUrlPatterns("/api/*");
        filterRegistrationBean.setOrder(1);
        return filterRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean<AuthFilter> authFilter() {
        FilterRegistrationBean<AuthFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new AuthFilter());
        filterRegistrationBean.addUrlPatterns("/api/*");
        filterRegistrationBean.setOrder(1);
        return filterRegistrationBean;
    }
}
