package com.timsanalytics.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Component
public class RequestResponseInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    public RequestResponseInterceptor() {

    }

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) {

        // This is ONLY used to insert attributes into the request that can be pulled later by the Filter.
        request.setAttribute("requestStartTime", new Date().getTime());
        return true;
    }

    @Override
    public void afterCompletion(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            Exception ex) {
    }

}
