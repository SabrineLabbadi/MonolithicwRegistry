package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.ConcertService;
import com.mycompany.myapp.domain.Concert;
import com.mycompany.myapp.repository.ConcertRepository;
import com.mycompany.myapp.service.dto.ConcertDTO;
import com.mycompany.myapp.service.mapper.ConcertMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Concert}.
 */
@Service
@Transactional
public class ConcertServiceImpl implements ConcertService {

    private final Logger log = LoggerFactory.getLogger(ConcertServiceImpl.class);

    private final ConcertRepository concertRepository;

    private final ConcertMapper concertMapper;

    public ConcertServiceImpl(ConcertRepository concertRepository, ConcertMapper concertMapper) {
        this.concertRepository = concertRepository;
        this.concertMapper = concertMapper;
    }

    @Override
    public ConcertDTO save(ConcertDTO concertDTO) {
        log.debug("Request to save Concert : {}", concertDTO);
        Concert concert = concertMapper.toEntity(concertDTO);
        concert = concertRepository.save(concert);
        return concertMapper.toDto(concert);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ConcertDTO> findAll() {
        log.debug("Request to get all Concerts");
        return concertRepository.findAll().stream()
            .map(concertMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ConcertDTO> findOne(Long id) {
        log.debug("Request to get Concert : {}", id);
        return concertRepository.findById(id)
            .map(concertMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Concert : {}", id);
        concertRepository.deleteById(id);
    }
}
