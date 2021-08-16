package com.example.lab.base;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Repositoryのベースクラス
 * @param <T> 対応エンティティ
 * @param <ID> ID型
 */
@NoRepositoryBean
public interface BaseRepository<T, ID> extends JpaRepository<T, ID> {

    /**
     * 削除されていないエンティティを全件取得します。
     */
    @Override
    @Query("select e from #{#entityName} e where e.deleteFlg = false")
    public List<T> findAll();

    /**
     * 削除されていないエンティティを全件取得してページに変換します。
     */
    @Override
    @Query("select e from #{#entityName} e where e.deleteFlg is false")
    public Page<T> findAll(Pageable pageable);

    /**
     * 削除されていないエンティティをソートして全件取得。
     */
    @Override
    @Query("select e from #{#entityName} e where e.deleteFlg is false")
    public List<T> findAll(Sort sort);

    /**
     * ID一覧に紐づく削除されていないエンティティを取得します。
     */
    @Override
    @Query("select e from #{#entityName} e where e.id in ?1 and e.deleteFlg = false")
    public List<T> findAllById(Iterable<ID> ids);

    /**
     * IDに紐づく削除されていないエンティティを取得します。
     */
    @Override
    @Query("select e from #{#entityName} e where e.id = ?1 and e.deleteFlg = false")
    public Optional<T> findById(ID id);
}