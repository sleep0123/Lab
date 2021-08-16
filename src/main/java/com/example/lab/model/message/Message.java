package com.example.lab.model.message;

/**
 * メッセージ蓄積用モデル。
 * MessageStoreクラスを介して管理する
 */
public class Message {

    /**
     * メッセージタイプ
     */
    public enum MessageType {
        /** エラーメッセージ */
        MESSAGE_TYPE_ERROR,
        /** 警告メッセージ */
        MESSAGE_TYPE_WARNING
    }

    private final String fieldName;
    private final String messageCode;
    private final MessageType messageType;
    private final String message;

    /**
     * メッセージを初期化します。<br>
     * メッセージ文字列は自動でセットされます。
     * @param messageCode メッセージコード
     * @param messageType メッセージタイプ
     * @param message メッセージ文字列
     */
    public Message(String messageCode, MessageType messageType, String message) {
        this(messageCode, messageType, message, null);
    }

    /**
     * メッセージを初期化します。<br>
     * メッセージ文字列は自動でセットされます。
     * @param messageCode メッセージコード
     * @param messageType メッセージタイプ
     * @param message メッセージ文字列
     * @param fieldName フィールド名
     */
    public Message(String messageCode, MessageType messageType, String message, String fieldName) {
        this.fieldName = fieldName;
        this.messageCode = messageCode;
        this.messageType = messageType;
        this.message = message;
    }

    /**
     * フィールド名を取得します。
     * @return フィールド名
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * メッセージコードを取得します。
     * @return メッセージコード
     */
    public String getMessageCode() {
        return messageCode;
    }

    /**
     * メッセージタイプを取得します。
     * @return メッセージタイプ
     */
    public MessageType getMessageType() {
        return messageType;
    }

    /**
     * メッセージ文字列を取得します。
     * @return メッセージ文字列
     */
    public String getMessage() {
        return message;
    }

    /**
     * メッセージがエラーであるか確認します
     * @return エラーの場合true
     */
    public boolean isError() {
        return messageType == MessageType.MESSAGE_TYPE_ERROR && fieldName == null;
    }

    /**
     * メッセージがフィールドエラーであるか確認します
     * @return フィールドエラーの場合true
     */
    public boolean isFieldError() {
        return messageType == MessageType.MESSAGE_TYPE_ERROR && fieldName != null;
    }

    /**
     * メッセージが警告であるか確認します
     * @return 警告の場合true
     */
    public boolean isWarning() {
        return messageType == MessageType.MESSAGE_TYPE_WARNING;
    }
}