package com.example.lab.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.lab.entity.Account;
import com.example.lab.entity.Diary;
import com.example.lab.model.form.DiaryForm;
import com.example.lab.repository.AccountRepository;
import com.example.lab.repository.DiaryRepository;
import com.example.lab.security.AccountDetails;

@Service
public class CalendarService implements UserDetailsService{
  
  @Autowired
  private AccountRepository accountRepository;
  
  @Autowired
  private DiaryRepository diaryRepository;
  
  @Autowired
  private PasswordEncoder passwordEncoder;
  
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    if (username.isBlank()) {
      throw new UsernameNotFoundException("Username is not found.");
  }

  final Account account = accountRepository.findByLoginId(username)
          .orElseThrow(() -> new UsernameNotFoundException("Account dose not exist."));

  final AccountDetails accountDetails = new AccountDetails(
          account.getId(),
          account.getLoginId(),
          account.getPassword(),
          null);

  return accountDetails;
  }
  
  public Diary saveDiary(DiaryForm diaryForm, Long accountId) {
    Diary diary = diaryRepository.findByaccountIdAndDate(accountId, diaryForm.getDate()).orElse(new Diary());
    diary.setAccountId(accountId);
    diary.setText(diaryForm.getText());
    diary.setDate(diaryForm.getDate());
    diary.setDeleteFlg(false);
    return diaryRepository.save(diary);
  }
  
  public Account saveAccount(String id, String password) {
    
    Account account = new Account();
    
    account.setLoginId(id);
    String hash = passwordEncoder.encode(password);
    boolean flg = new BCryptPasswordEncoder().matches(password, hash);
    //確認でif分使ってます
    if (flg) {
      account.setPassword(hash);
      account.setDeleteFlg(false);
      return accountRepository.save(account);
    }
    
    return account;
  }
}
