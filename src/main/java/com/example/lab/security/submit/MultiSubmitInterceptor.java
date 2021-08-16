package com.example.lab.security.submit;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.example.lab.exception.BusinessException;
import com.example.lab.model.message.MessageStore;
import com.example.lab.security.submit.MultiSubmitToken.MultiSubmitTokenType;

/**
 * 多重登録チェック用インターセプター。
 */
public class MultiSubmitInterceptor implements HandlerInterceptor {

    /** リクエスト用トークン設定キー */
    public static final String TOKEN_CONTEXT_REQUEST_ATTRIBUTE_NAME = MultiSubmitInterceptor.class
            .getName() + ".TOKEN_CONTEXT";

    /** 新しいトークン用トークン設定キー */
    public static final String NEXT_TOKEN_REQUEST_ATTRIBUTE_NAME = MultiSubmitInterceptor.class
            .getName() + ".NEXT_TOKEN";

    /** トークン用リクエストパラメーター名 */
    public static final String TOKEN_REQUEST_PARAMETER = "_multiSubmitToken";

    @Autowired
    private MessageStore messageStore;

    /**
     * 事前処理。
     * @param request リクエスト
     * @param response レスポンス
     * @param handler ハンドラ
     * @return 成功した場合true
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception {

        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        final HandlerMethod handlerMethod = (HandlerMethod) handler;
        final MultiSubmitToken annotation = handlerMethod.getMethodAnnotation(MultiSubmitToken.class);

        if (annotation == null) {
            return true;
        }

        if (annotation.tokenType() == MultiSubmitTokenType.BEGIN) {
            createToken(request, annotation);
        } else if (annotation.tokenType() == MultiSubmitTokenType.CHECK) {
            checkToken(request, annotation);
        }

        return true;
    }

    /**
     * 事後処理
     * @param request リクエスト
     * @param response レスポンス
     * @param handler ハンドラ
     * @param modelAndView モデルビュー
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {

        if (!(handler instanceof HandlerMethod)) {
            return;
        }

        final HandlerMethod handlerMethod = (HandlerMethod) handler;
        final MultiSubmitToken annotation = handlerMethod.getMethodAnnotation(MultiSubmitToken.class);

        if (annotation == null) {
            return;
        }

        if (!hasOccurredValidationError(modelAndView)
                && !messageStore.hasErrors()
                && annotation.tokenType() == MultiSubmitTokenType.CHECK
                && annotation.remove()) {

            removeToken(request, annotation);

            if (annotation.regenerate()) {
                createToken(request, annotation);
            }
        } else {
            resetToken(request, annotation);
        }
    }

    /**
     * トークンを作成します。
     * @param request リクエスト
     * @param annotation アノテーション。
     */
    private void createToken(final HttpServletRequest request, final MultiSubmitToken annotation) {
        final HttpSession session = request.getSession(true);
        final String token = nextToken();
        request.setAttribute(NEXT_TOKEN_REQUEST_ATTRIBUTE_NAME, token);
        session.setAttribute(TOKEN_CONTEXT_REQUEST_ATTRIBUTE_NAME + annotation.tokenKey(), token);
    }

    /**
     * トークンを確認します。
     * @param request リクエスト
     * @param annotation アノテーション。
     */
    private void checkToken(final HttpServletRequest request, final MultiSubmitToken annotation) {
        final HttpSession session = request.getSession(true);
        final String sessionToken = (String) session
                .getAttribute(TOKEN_CONTEXT_REQUEST_ATTRIBUTE_NAME + annotation.tokenKey());
        final String requestToken = request.getParameter(TOKEN_REQUEST_PARAMETER);

        if (sessionToken == null || requestToken == null || !sessionToken.equals(requestToken)) {
            throw new BusinessException("ERROR.ALREADY_OPERATION_COMPLETED");
        }
    }

    /**
     * トークンを削除します。
     * @param request リクエスト
     * @param annotation アノテーション。
     */
    private void removeToken(final HttpServletRequest request, final MultiSubmitToken annotation) {
        final HttpSession session = request.getSession(true);
        session.removeAttribute(TOKEN_CONTEXT_REQUEST_ATTRIBUTE_NAME + annotation.tokenKey());
    }

    /**
     * トークンをリセットします。
     * @param request リクエスト
     * @param annotation アノテーション。
     */
    private void resetToken(final HttpServletRequest request, final MultiSubmitToken annotation) {
        final HttpSession session = request.getSession(true);
        request.setAttribute(NEXT_TOKEN_REQUEST_ATTRIBUTE_NAME,
                session.getAttribute(TOKEN_CONTEXT_REQUEST_ATTRIBUTE_NAME + annotation.tokenKey()));
    }

    /**
     * バリデーションエラーが発生しているかチェックします。
     * @param modelAndView モデルビュー
     * @return バリデーションエラーが発生している場合true
     */
    private boolean hasOccurredValidationError(ModelAndView modelAndView) {
        for (final Object arg : modelAndView.getModel().values()) {
            if (arg instanceof BeanPropertyBindingResult) {
                return ((BeanPropertyBindingResult) arg).getErrorCount() > 0;
            }
        }

        return false;
    }

    /**
     * トークンを生成します。
     * @return トークン
     */
    private synchronized String nextToken() {
        return UUID.randomUUID().toString();
    }
}