package edu.own.svmr.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.own.svmr.model.Hr;
import edu.own.svmr.model.RespBean;
import edu.own.svmr.service.HrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Author:江斌
 * @Description:
 * @Date: Created in 2021/8/19 19:57
 * @ModifiedBy:
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    HrService hrService;

    /**
     * 密码加密
     * @return
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 基于数据库认证
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(hrService);
    }

    /**
     * 登录表单详细配置
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests() //开启HttpSecurity配置
                .anyRequest().authenticated() //任意请求必须登录后访问
                .and()
                .formLogin()
                .usernameParameter("username")
                .passwordParameter("password")
                .loginPage("/login")
                .loginProcessingUrl("/doLogin")
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse resp, Authentication auth) throws IOException, ServletException {
                        resp.setContentType("application/json;charset=utf-8");
                        PrintWriter out = resp.getWriter();
                        Hr hr = (Hr) auth.getPrincipal();
                        hr.setPassword(null);
                        RespBean ok = RespBean.ok("登录成功!", hr);
                        ObjectMapper om = new ObjectMapper();
                        String s = om.writeValueAsString(ok);
                        out.write(s);
                        out.flush();
                        out.close();
                    }
                })
                .failureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest req, HttpServletResponse resp, AuthenticationException e) throws IOException, ServletException {
                        resp.setContentType("application/json;charset=utf-8");
                        PrintWriter out = resp.getWriter();
                        RespBean error = RespBean.error("登入失败!");
                        if (e instanceof LockedException) {
                            error.setMsg("账户被锁定,登录失败！");
                        } else if (e instanceof DisabledException) {
                            error.setMsg("账户被禁用，登录失败！");
                        } else if (e instanceof AccountExpiredException) {
                            error.setMsg("账户已过期，登入失败!");
                        } else if (e instanceof CredentialsExpiredException) {
                            error.setMsg("密码已过期，登录失败！");
                        } else if (e instanceof BadCredentialsException) {
                            error.setMsg("账户或密码输入错误，请重新输入！");
                        }
                        ObjectMapper om = new ObjectMapper();
                        String s = om.writeValueAsString(error);
                        out.write(s);
                        out.flush();
                        out.close();
                    }
                })
                .permitAll()
                .and()
                .logout()
                .logoutSuccessHandler(new LogoutSuccessHandler() {
                    @Override
                    public void onLogoutSuccess(HttpServletRequest req, HttpServletResponse resp, Authentication auth) throws IOException, ServletException {
                        resp.setContentType("application/json;charset=utf-8");
                        PrintWriter out = resp.getWriter();
                        RespBean logout = RespBean.ok("注销成功！");
                        ObjectMapper om = new ObjectMapper();
                        String s = om.writeValueAsString(logout);
                        out.write(s);
                        out.flush();
                        out.close();
                    }
                })
                .permitAll()
                .and()
                .csrf()
                .disable();
    }
}
