package com.example.lab.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AccountBase implements Serializable{

  private static final long serialVersionUID = 1L;
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;
  
  /** ログインID */
  @Column(name = "login_id")
  private String loginId;
  
  /** パスワード */
  @Column(name = "password")
  private String password;
  
  /** 削除フラグ */
  @Column(name = "delete_flg", nullable = false)
  private Boolean deleteFlg;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getLoginId() {
    return loginId;
  }

  public void setLoginId(String loginId) {
    this.loginId = loginId;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Boolean getDeleteFlg() {
    return deleteFlg;
  }

  public void setDeleteFlg(Boolean deleteFlg) {
    this.deleteFlg = deleteFlg;
  }
}
