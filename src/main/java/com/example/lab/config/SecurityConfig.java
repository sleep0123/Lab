package com.example.lab.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.support.RequestDataValueProcessor;

import com.example.lab.security.SecurityRequestDataValueProcessor;
import com.example.lab.service.CalendarService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  
  @Autowired
  private CalendarService calendarService;
  
  /**
   * Webセキュリティの設定を行います。
   * @param web Webセキュリティ
   */
  @Override
  public void configure(WebSecurity web) throws Exception {
      web.ignoring().antMatchers("/css/**", "/dist/**", "/images/**", "/js/**", "/pdf/**");
  }

  /**
   * HTTPセキュリティの設定を行います。
   * @param http HTTPセキュリティ
   */
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    
    http.authorizeRequests()
    .antMatchers("/login", "/login2", "/signup").permitAll()
    .anyRequest().authenticated()
    .and()
        .formLogin()
        .loginPage("/login")
        .loginProcessingUrl("/auth")
        .failureUrl("/login")
        .usernameParameter("loginId")
        .passwordParameter("password")
    .and()
      .logout()
      .logoutUrl("/logout")
      .logoutSuccessUrl("/login")
      .permitAll();
  }
  
  /**
   * 認証管理の設定を行います。
   * @param auth 認証管理用ビルダー
   */
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
      auth.userDetailsService(calendarService)
              .passwordEncoder(passwordEncoder());
  }

  /**
   * パスワードエンコーダー。
   * @return パスワードエンコーダー
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
  }

  /**
   * リクエストのデータを扱う設定を返します。
   * @return リクエストデータ設定
   */
  @Bean
  public RequestDataValueProcessor requestDataValueProcessor() {
      return new SecurityRequestDataValueProcessor();
  }
}