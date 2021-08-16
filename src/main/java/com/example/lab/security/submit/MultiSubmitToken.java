package com.example.lab.security.submit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 多重登録チェック用アノテーション
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MultiSubmitToken {

    /**
     * 多重登録用トークンタイプ
     */
    public enum MultiSubmitTokenType {
        BEGIN, CHECK
    }

    /**
     * トークンタイプ。
     * @return トークンタイプ
     */
    MultiSubmitTokenType tokenType();

    /**
     * トークンキー。
     * @return トークンキー
     */
    String tokenKey() default "";

    /**
     * トークン削除指示。
     * トークンの検証後、トークンを削除可否
     * @return トークン削除可否
     */
    boolean remove() default true;

    /**
     * トークン再生成指示。
     * トークンの削除後、再生成可否
     * @return トークン再生可否
     */
    boolean regenerate() default false;
}
