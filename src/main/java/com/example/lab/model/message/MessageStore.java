package com.example.lab.model.message;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.example.lab.model.message.Message.MessageType;

/**
 * メッセージを蓄積・管理するクラス。<br>
 * エラー一覧は、リクエストごとに初期化されます。
 */
@Component
@RequestScope
public class MessageStore {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private HttpSession session;

    private final List<Message> messages;

    /**
     * デフォルトコンストラクタ。
     */
    public MessageStore() {
        messages = new ArrayList<Message>();
    }

    /**
     * メッセージを作成します。
     * @param code コード
     * @param args 引数
     * @return メッセージ
     */
    public String createMessage(String code, Object... args) {
        Optional<Locale> locale = Optional.ofNullable((Locale) session.getAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME));
        return messageSource.getMessage(code, args, locale.orElse(Locale.getDefault()));
    }

    /**
     * エラーメッセージを追加する。
     * @param code メッセージコード
     */
    public void addErrorMessage(String code) {
        final String messageStr = createMessage(code, (Object[]) null);
        final Message messageObj = new Message(code, MessageType.MESSAGE_TYPE_ERROR, messageStr);
        messages.add(messageObj);
    }

    /**
     * エラーメッセージを追加する。
     * @param code メッセージコード
     * @param args メッセージの引数
     */
    public void addErrorMessage(String code, Object... args) {
        final String messageStr = createMessage(code, args);
        final Message messageObj = new Message(code, MessageType.MESSAGE_TYPE_ERROR, messageStr);
        messages.add(messageObj);
    }

    /**
     * フィールドエラーメッセージを追加する。
     * @param fieldName フィールド名
     * @param code メッセージコード
     */
    public void addFieldErrorMessage(String fieldName, String code) {
        final String messageStr = createMessage(code, (Object[]) null);
        final Message messageObj = new Message(code, MessageType.MESSAGE_TYPE_ERROR, messageStr, fieldName);
        messages.add(messageObj);
    }

    /**
     * フィールドエラーメッセージを追加する。
     * @param fieldName フィールド名
     * @param code メッセージコード
     * @param args メッセージの引数
     */
    public void addFieldErrorMessage(String fieldName, String code, Object... args) {
        final String messageStr = createMessage(code, args);
        final Message messageObj = new Message(code, MessageType.MESSAGE_TYPE_ERROR, messageStr, fieldName);
        messages.add(messageObj);
    }

    /**
     * 警告メッセージを追加する。
     * @param code メッセージコード
     */
    public void addWarningMessage(String code) {
        final String messageStr = createMessage(code, (Object[]) null);
        final Message messageObj = new Message(code, MessageType.MESSAGE_TYPE_WARNING, messageStr);
        messages.add(messageObj);
    }

    /**
     * 警告メッセージを追加する。
     * @param code メッセージコード
     * @param args メッセージの引数
     */
    public void addWarningMessage(String code, Object... args) {
        final String messageStr = createMessage(code, args);
        final Message messageObj = new Message(code, MessageType.MESSAGE_TYPE_WARNING, messageStr);
        messages.add(messageObj);
    }

    /**
     * エラーメッセージが存在するか。
     * @return エラーメッセージが一件以上存在する場合true
     */
    public boolean hasErrors() {
        return this.getErrorMessages().size() > 0 || getFiledErrorMessages().size() > 0;
    }

    /**
     * 警告メッセージが存在するか。
     * @return 警告メッセージが一件以上存在する場合true
     */
    public boolean hasWarnings() {
        return this.getWarningMessages().size() > 0;
    }

    /**
     * エラーメッセージ一覧を取得する。
     * @return エラーメッセージの一覧
     */
    public List<Message> getErrorMessages() {
        return messages.stream()
                .filter(m -> m.isError())
                .collect(Collectors.toList());
    }

    /**
     * 警告メッセージ一覧を取得する。
     * @return 警告メッセージの一覧
     */
    public List<Message> getWarningMessages() {
        return messages.stream()
                .filter(m -> m.isWarning())
                .collect(Collectors.toList());
    }

    /**
     * フィールドエラーメッセージ一覧を取得する。
     * @return エラーメッセージの一覧
     */
    public List<Message> getFiledErrorMessages() {
        return messages.stream()
                .filter(m -> m.isFieldError())
                .collect(Collectors.toList());
    }

    /**
     * すべてのメッセージを削除する。
     */
    public void deleteAllMessage() {
        messages.clear();
    }
}