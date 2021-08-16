package com.example.lab.entity.base;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class DiaryBase implements Serializable{

 private static final long serialVersionUID = 1L;
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;
  
  /** アカウントID */
  @Column(name = "account_id")
  private Long accountId;
  
  /** 日記 */
  @Column(name = "text")
  private String text;
  
  @Column(name = "date")
  private LocalDate date;
  
  /** 削除フラグ */
  @Column(name = "delete_flg", nullable = false)
  private Boolean deleteFlg;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getAccountId() {
    return accountId;
  }

  public void setAccountId(Long accountId) {
    this.accountId = accountId;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public Boolean getDeleteFlg() {
    return deleteFlg;
  }

  public void setDeleteFlg(Boolean deleteFlg) {
    this.deleteFlg = deleteFlg;
  }
}
