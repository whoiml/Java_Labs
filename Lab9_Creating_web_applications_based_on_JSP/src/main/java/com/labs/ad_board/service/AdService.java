package com.labs.ad_board.service;

import com.labs.ad_board.db.entity.Ad;
import com.labs.ad_board.db.repository.AdRepository;
import com.labs.ad_board.db.repository.UserRepository;
import com.labs.ad_board.dto.AdCreateEditDto;
import com.labs.ad_board.dto.AdFindOptionDto;
import com.labs.ad_board.dto.AdReadDto;
import com.labs.ad_board.exception.IdException;
import com.labs.ad_board.mapper.AdMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdService {

    private final AdRepository adRepository;
    private final AdMapper adMapper;
    private final UserRepository userRepository;

    public List<AdReadDto> getAllAds() {
        return adRepository
                .getAllAds()
                .stream()
                .map(adMapper::map)
                .toList();
    }

    @Transactional
    public AdReadDto save(AdCreateEditDto adCreateEditDto) {
        return adMapper.map(adRepository.save(adMapper.map(adCreateEditDto)));
    }

    public AdReadDto getAdById(int id) {
        return adMapper.map(
            adRepository.getAdById(id)
                .orElseThrow(() -> new IdException(
                    "ad by id: " + id + " is not found!"
                ))
        );
    }

    public List<AdReadDto> getAllSortingBy(AdFindOptionDto optionDto) {
        return adRepository
            .findBy(optionDto)
            .stream()
            .sorted(Comparator.comparing(Ad::getCreationDate))
            .map(adMapper::map)
            .toList();
    }

    @Transactional
    public void deleteAd(int id) {
        adRepository.delete(
            adRepository.getAdById(id)
                .orElseThrow(() ->
                    new IdException("ad with id: " + id + "was not found!")
                ));
    }

    @Transactional
    public void update(int id, AdCreateEditDto adCreateEditDto) {
        adRepository.update(
            id,
            adCreateEditDto.getAdName(),
            adCreateEditDto.getDescription(),
            adCreateEditDto.getTheme(),
            LocalDateTime.now()
        );
    }
}
