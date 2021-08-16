package com.example.lab.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.lab.base.BaseRepository;
import com.example.lab.entity.Account;

/**
 * アカウントリポジトリ
 */
@Repository
public interface AccountRepository extends BaseRepository<Account, Long> {
  
  @Query(value = "select a from account a"
      + " where a.loginId = :loginId"
      + " and a.deleteFlg = false")
  public Optional<Account> findByLoginId(@Param("loginId")String loginId);
}
