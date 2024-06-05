package com.labs.ad_board.db.repository;

import com.labs.ad_board.db.entity.Ad;
import com.labs.ad_board.dto.AdCreateEditDto;
import com.labs.ad_board.dto.AdFindOptionDto;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AdRepository extends JpaRepository<Ad, Integer>, AdSearchRepository {

    @Query("select a from Ad a")
    List<Ad> getAllAds();

    @Query("select a from Ad a where a.userId = :userId")
    List<Ad> getAllAdsByUserId(int userId);

    @Query("select a from Ad a where a.theme = :theme")
    List<Ad> getAllAdsByTheme(String theme);

    @Query("select a from Ad a where a.creationDate = DATE(:date) ")
    List<Ad> getAllAdsByCreationDate(LocalDateTime date);

    @Query("select a from Ad a where a.id = :id")
    Optional<Ad> getAdById(int id);

    @Modifying
    @Query("update Ad a " +
        "set a.adName = :adName, " +
        "a.description = :description, " +
        "a.theme = :theme, " +
        "a.creationDate = :currentTime " +
        "where a.id = :id")
    void update(
        int id,
        String adName,
        String description,
        String theme,
        LocalDateTime currentTime
    );
}
