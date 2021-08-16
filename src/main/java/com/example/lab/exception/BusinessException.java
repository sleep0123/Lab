package com.example.lab.exception;

/**
 * 業務例外用クラス
 */
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /** コード */
    private final String code;

    /** 挿入文字列 */
    private Object[] args;

    /**
     * コンストラクタ
     * @param code メッセージコード
     */
    public BusinessException(String code) {
        this.code = code;
    }

    /**
     * コンストラクタ
     * @param code メッセージコード
     * @param args 挿入文字列
     */
    public BusinessException(String code, Object... args) {
        this.code = code;
        this.args = args;
    }

    /**
     * コードを取得
     * @return コード
     */
    public String getCode() {
        return this.code;
    }

    /**
     * 挿入文字列を取得します。
     * @return 挿入文字列
     */
    public Object[] getArgs() {
        return this.args;
    }
}