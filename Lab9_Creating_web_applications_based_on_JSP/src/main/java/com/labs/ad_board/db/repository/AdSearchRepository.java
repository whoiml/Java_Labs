package com.labs.ad_board.db.repository;

import com.labs.ad_board.db.entity.Ad;
import com.labs.ad_board.dto.AdFindOptionDto;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface AdSearchRepository {

    List<Ad> findBy(AdFindOptionDto optionDto);
}
