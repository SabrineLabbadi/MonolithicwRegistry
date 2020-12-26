package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.ConcertDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Concert}.
 */
public interface ConcertService {

    /**
     * Save a concert.
     *
     * @param concertDTO the entity to save.
     * @return the persisted entity.
     */
    ConcertDTO save(ConcertDTO concertDTO);

    /**
     * Get all the concerts.
     *
     * @return the list of entities.
     */
    List<ConcertDTO> findAll();


    /**
     * Get the "id" concert.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ConcertDTO> findOne(Long id);

    /**
     * Delete the "id" concert.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
