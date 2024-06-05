package com.labs.ad_board.db.repository;

import com.labs.ad_board.db.entity.Ad;
import com.labs.ad_board.dto.AdFindOptionDto;
import com.labs.ad_board.service.UserService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class AdRepositoryImpl implements AdSearchRepository {

    @PersistenceContext
    private final EntityManager entityManager;
    private final UserService userService;

    @Override
    public List<Ad> findBy(AdFindOptionDto optionDto) {
        var cb = entityManager.getCriteriaBuilder();
        var criteria = cb.createQuery(Ad.class);

        var ad = criteria.from(Ad.class);

        List<Predicate> predicates = new ArrayList<>();
        if (isUsernameExist(optionDto)) {
            predicates.add(cb.equal(
                ad.get("userId"),
                userService.getUserIdByUsername(optionDto.getUsername())
            ));
        }
        if (isThemeExist(optionDto)) {
            predicates.add(cb.like(ad.get("theme"), optionDto.getTheme()));
        }
        if (isDateExist(optionDto)) {
            predicates.add(cb.between(
                ad.get("creationDate"),

                optionDto.getDate().atStartOfDay(),

                optionDto.getDate()
                    .plusDays(1).atStartOfDay())
            );
        }
        criteria.where(predicates.toArray(Predicate[]::new));
        return entityManager.createQuery(criteria).getResultList();
    }

    private boolean isUsernameExist(AdFindOptionDto optionDto) {
        return optionDto.getUsername() != null &&
            !optionDto.getUsername().isEmpty();
    }

    private boolean isThemeExist(AdFindOptionDto optionDto) {
        return optionDto.getTheme() != null &&
            !optionDto.getTheme().isEmpty();
    }

    private boolean isDateExist(AdFindOptionDto optionDto) {
        return optionDto.getDate() != null;
    }
}
