package com.example.lab.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * アカウント詳細情報。
 */
public class AccountDetails implements UserDetails {

    private static final long serialVersionUID = 1L;

    /** アカウントのID */
    private Long id;

    /** アクセスコード */
    private String userName;

    /** パスワード */
    private String passWord;

    /** 権限 */
    private final Collection<GrantedAuthority> authorities;

    /**
     * コンストラクタ
     * @param id ID
     * @param userName アクセスコード
     * @param passWord パスワード
     * @param authorities 権限
     */
    public AccountDetails(Long id, String userName, String passWord, Collection<GrantedAuthority> authorities) {
        this.id = id;
        this.userName = userName;
        this.passWord = passWord;
        this.authorities = authorities;
    }

    /**
     * アカウントのIDを取得します。
     * @return アカウントのID
     */
    public Long getId() {
        return this.id;
    }

    /**
     * アクセスコードを取得します。
     * @return アクセスコード
     */
    @Override
    public String getUsername() {
        return this.userName;
    }

    /**
     * パスワードを取得します。
     * @return パスワード
     */
    @Override
    public String getPassword() {
        return this.passWord;
    }

    /**
     * 権限を取得します。
     * @return 権限
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    /**
     * 有効期限切れでないかをチェックします。
     * @return 有効期限切れでない場合true
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * アカウントロックでないかをチェックします。
     * @return アカウントロックでない場合true
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 資格情報が有効期限切れでないかをチェックします。
     * @return 資格情報が有効期限切れでない場合true
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 有効であるかチェックします。
     * @return 有効である場合true
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}