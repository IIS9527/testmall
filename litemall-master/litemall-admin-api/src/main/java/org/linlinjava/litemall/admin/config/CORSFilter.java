package org.linlinjava.litemall.admin.config;


import org.springframework.stereotype.Component;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CORSFilter implements Filter {

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) {
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;
        //放行所有,类似*,这里*无效
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Credentials", "true");
        //允许请求方式
        response.setHeader("Access-Control-Allow-Methods", "POST,PUT, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        //需要放行header头部字段 如需鉴权字段，自行添加，如Authorization
        response.setHeader("Access-Control-Allow-Headers", "content-type,x-requested-with,token,Authorization,authorization");
        try {
            chain.doFilter(request, response);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void init(FilterConfig filterConfig) {
    }

    public void destroy() {
    }

}