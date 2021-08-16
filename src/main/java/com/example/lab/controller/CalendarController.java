package com.example.lab.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.lab.entity.Diary;
import com.example.lab.model.form.DiaryForm;
import com.example.lab.model.form.LoginForm;
import com.example.lab.repository.AccountRepository;
import com.example.lab.repository.DiaryRepository;
import com.example.lab.security.AccountDetails;
import com.example.lab.service.CalendarService;

@Controller
public class CalendarController{
  
  @Autowired
  private HttpServletRequest request;
  
  @Autowired
  private CalendarService calendarService;
  
  @Autowired
  private AccountRepository accountRepository;
  
  @Autowired
  private DiaryRepository diaryRepository;

  /**
   * ログイン画面を表示する
   * @param model モデル
   * @return 遷移先
   */
  @GetMapping("/login")
  public String login(Model model) {
    LoginForm loginForm = new LoginForm();
    if (request.getUserPrincipal() != null) {
      return "redirect:calendar";
    }
    model.addAttribute(loginForm);
    return "login";
  }
  
  @PostMapping("/login")
  public String login(@Validated LoginForm loginForm, BindingResult result, Model model) {
    if (result.hasErrors()) {
      return "login";
    }

    try {
      request.login(loginForm.getLoginId(), loginForm.getPassword());
    } catch (ServletException e) {
      return "login";
    }

    return "redirect:calendar";
  }
  
  /**
   * カレンダー画面を表示する
   * @param model モデル
   * @return 遷移先
   */
  @GetMapping("/calendar")
  public String diary(@AuthenticationPrincipal AccountDetails accountDetails, Model model) {
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/M/d");
    final List<String> datesOfMonth = diaryRepository.findByAccountId(accountDetails.getId())
        .stream().filter(d -> !d.getText().isBlank()).map(d -> d.getDate().format(dtf)).collect(Collectors.toList());
    
    model.addAttribute("datesOfMonth", datesOfMonth);
    model.addAttribute("username", accountDetails.getUsername());
    return "calendar";
  }
  
  /**
   * 日記画面を表示する
   * @param model モデル
   * @return 遷移先
   */
  @GetMapping("/diary")
  public String diary(
      @RequestParam("date")Integer date, @RequestParam("month")Integer month, @RequestParam("year")Integer year, 
      @AuthenticationPrincipal AccountDetails accountDetails, Model model) {
  
    DiaryForm diaryForm = new DiaryForm();
    diaryForm.setDate(LocalDate.of(year, month+1, date));
    final Diary diary = diaryRepository.findByaccountIdAndDate(accountDetails.getId(), diaryForm.getDate()).orElse(null);
    if(diary != null) {
      diaryForm.setText(diary.getText());
    }
    
    model.addAttribute("username", accountDetails.getUsername());
    model.addAttribute("diaryForm", diaryForm);
    return "diary";
    
  }
  
  /**
   * 日記画面を保存する
   * @param model モデル
   * @return 遷移先
   */
  @PostMapping("/diary/save")
  public String save(@AuthenticationPrincipal AccountDetails accountDetails, @Validated DiaryForm daiaryForm, Model model) {
    calendarService.saveDiary(daiaryForm, accountDetails.getId());
    return "redirect:/calendar";
  }
  
  /**
   * アカウント生成画面を表示する
   * @param model モデル
   * @return 遷移先
   */
  @GetMapping("/signup")
  public String signup(Model model) {
    LoginForm loginForm = new LoginForm();
    
    model.addAttribute(loginForm);
    return "signup";
  }
  
  /**
   * アカウント生成
   * @param loginForm ログインフォーム
   * @param model モデル
   * @return 遷移先
   */
  @PostMapping("/signup")
  public String signup(@Validated LoginForm loginForm, Model model) {
    final String id = loginForm.getLoginId();
    final String password = loginForm.getPassword();
    if(id.isBlank() || id == null || password.isBlank() || password == null) {
      return "signup";
    }
    
    if(accountRepository.findByLoginId(id).orElse(null) != null) {
      return "signup";
    }
    
    calendarService.saveAccount(id, password);
    return "login";
  }
}
