package com.example.lab.security;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.servlet.support.csrf.CsrfRequestDataValueProcessor;
import org.springframework.web.servlet.support.RequestDataValueProcessor;

import com.example.lab.security.submit.MultiSubmitInterceptor;

/**
 * セキュリティ適用リクエスト変換処理クラス。
 * リクエストデータを検査し値の変換等をかけます。
 */
public class SecurityRequestDataValueProcessor implements RequestDataValueProcessor {

    /** CSRF処理用リクエスト変換処理クラス */
    public CsrfRequestDataValueProcessor processor = new CsrfRequestDataValueProcessor();

    /**
     * アクション実行時の処理。
     * @param request リクエスト
     * @param action アクション
     * @param httpMethod HTTPメソッド
     * @return Actionの戻り値
     */
    @Override
    public String processAction(HttpServletRequest request, String action, String httpMethod) {
        return processor.processAction(request, action, httpMethod);
    }

    /**
     * フォームフィールド処理。
     * @param request リクエスト
     * @param name 名称
     * @param value 値
     * @param type 型
     * @return フォームフィールド値
     */
    @Override
    public String processFormFieldValue(HttpServletRequest request, String name, String value, String type) {
        return processor.processFormFieldValue(request, name, value, type);
    }

    /**
     * Hiiddenフィールド処理。
     * @param request リクエスト
     * @return Hiddenフィールド
     */
    @Override
    public Map<String, String> getExtraHiddenFields(HttpServletRequest request) {
        final Map<String, String> extraHiddenFields = processor.getExtraHiddenFields(request);

        // CSRFトークンが設定されていない場合は設定しない
        if (extraHiddenFields != null && extraHiddenFields.isEmpty()) {
            return extraHiddenFields;
        }

        final String nextToken = (String) request.getAttribute(
                MultiSubmitInterceptor.NEXT_TOKEN_REQUEST_ATTRIBUTE_NAME);
        if (nextToken != null) {
            extraHiddenFields.put(MultiSubmitInterceptor.TOKEN_REQUEST_PARAMETER, nextToken);
        }

        return extraHiddenFields;
    }

    /**
     * URLが表示またはリダイレクトする際の処理。
     * @param request リクエスト
     * @param url URL
     * @return 使用されるURL
     */
    @Override
    public String processUrl(HttpServletRequest request, String url) {
        return processor.processUrl(request, url);
    }
}
