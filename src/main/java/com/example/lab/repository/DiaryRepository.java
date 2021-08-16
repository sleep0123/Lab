package com.example.lab.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.lab.base.BaseRepository;
import com.example.lab.entity.Diary;

@Repository
public interface DiaryRepository extends BaseRepository<Diary, Long>{

  @Query(value = "select d from diary d"
      + " where d.accountId = :accountId"
      + " and d.date = :date"
      + " and d.deleteFlg = false")
  public Optional<Diary> findByaccountIdAndDate(@Param("accountId")Long accountId, @Param("date")LocalDate date);
  
  @Query(value = "select d from diary d"
      + " where d.accountId = :accountId"
      + " and trim(d.text) is not null"
      + " and d.deleteFlg = false"
      )
  public List<Diary> findByAccountId(@Param("accountId") Long accountId);
}
