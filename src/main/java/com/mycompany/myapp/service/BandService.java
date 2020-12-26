package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.BandDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Band}.
 */
public interface BandService {

    /**
     * Save a band.
     *
     * @param bandDTO the entity to save.
     * @return the persisted entity.
     */
    BandDTO save(BandDTO bandDTO);

    /**
     * Get all the bands.
     *
     * @return the list of entities.
     */
    List<BandDTO> findAll();


    /**
     * Get the "id" band.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BandDTO> findOne(Long id);

    /**
     * Delete the "id" band.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
