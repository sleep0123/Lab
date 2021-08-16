package com.example.lab.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.example.lab.entity.base.DiaryBase;

@Entity(name = "diary")
@Table(name = "diary")
public class Diary extends DiaryBase{

  private static final long serialVersionUID = 1L;
}
