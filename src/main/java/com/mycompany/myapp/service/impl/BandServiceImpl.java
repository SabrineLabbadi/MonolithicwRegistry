package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.BandService;
import com.mycompany.myapp.domain.Band;
import com.mycompany.myapp.repository.BandRepository;
import com.mycompany.myapp.service.dto.BandDTO;
import com.mycompany.myapp.service.mapper.BandMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Band}.
 */
@Service
@Transactional
public class BandServiceImpl implements BandService {

    private final Logger log = LoggerFactory.getLogger(BandServiceImpl.class);

    private final BandRepository bandRepository;

    private final BandMapper bandMapper;

    public BandServiceImpl(BandRepository bandRepository, BandMapper bandMapper) {
        this.bandRepository = bandRepository;
        this.bandMapper = bandMapper;
    }

    @Override
    public BandDTO save(BandDTO bandDTO) {
        log.debug("Request to save Band : {}", bandDTO);
        Band band = bandMapper.toEntity(bandDTO);
        band = bandRepository.save(band);
        return bandMapper.toDto(band);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BandDTO> findAll() {
        log.debug("Request to get all Bands");
        return bandRepository.findAll().stream()
            .map(bandMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<BandDTO> findOne(Long id) {
        log.debug("Request to get Band : {}", id);
        return bandRepository.findById(id)
            .map(bandMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Band : {}", id);
        bandRepository.deleteById(id);
    }
}
