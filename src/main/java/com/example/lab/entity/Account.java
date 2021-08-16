package com.example.lab.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.example.lab.entity.base.AccountBase;

@Entity(name = "account")
@Table(name = "account")
public class Account extends AccountBase {

  private static final long serialVersionUID = 1L;
}
