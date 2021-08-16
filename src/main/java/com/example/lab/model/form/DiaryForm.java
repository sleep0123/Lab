package com.example.lab.model.form;

import java.time.LocalDate;

public class DiaryForm {

  private LocalDate date;
  
  private String text;
  
  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }
}
