package com.example.lab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class LabApplication extends SpringBootServletInitializer {

    /**
     * アプリケーションを起動します。
     * @param args 引数
     */
    public static void main(String[] args) {
        SpringApplication.run(LabApplication.class, args);
    }
//
//    /**
//     * アプリケーション設定を行います。
//     * @param application 実行するアプリケーション
//     */
//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//        return application.sources(LabApplication.class);
//    }
}
