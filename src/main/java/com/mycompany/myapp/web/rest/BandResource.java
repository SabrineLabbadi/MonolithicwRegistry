package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.BandService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.service.dto.BandDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Band}.
 */
@RestController
@RequestMapping("/api")
public class BandResource {

    private final Logger log = LoggerFactory.getLogger(BandResource.class);

    private static final String ENTITY_NAME = "band";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BandService bandService;

    public BandResource(BandService bandService) {
        this.bandService = bandService;
    }

    /**
     * {@code POST  /bands} : Create a new band.
     *
     * @param bandDTO the bandDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bandDTO, or with status {@code 400 (Bad Request)} if the band has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bands")
    public ResponseEntity<BandDTO> createBand(@RequestBody BandDTO bandDTO) throws URISyntaxException {
        log.debug("REST request to save Band : {}", bandDTO);
        if (bandDTO.getId() != null) {
            throw new BadRequestAlertException("A new band cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BandDTO result = bandService.save(bandDTO);
        return ResponseEntity.created(new URI("/api/bands/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bands} : Updates an existing band.
     *
     * @param bandDTO the bandDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bandDTO,
     * or with status {@code 400 (Bad Request)} if the bandDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bandDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bands")
    public ResponseEntity<BandDTO> updateBand(@RequestBody BandDTO bandDTO) throws URISyntaxException {
        log.debug("REST request to update Band : {}", bandDTO);
        if (bandDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BandDTO result = bandService.save(bandDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bandDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /bands} : get all the bands.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bands in body.
     */
    @GetMapping("/bands")
    public List<BandDTO> getAllBands() {
        log.debug("REST request to get all Bands");
        return bandService.findAll();
    }

    /**
     * {@code GET  /bands/:id} : get the "id" band.
     *
     * @param id the id of the bandDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bandDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bands/{id}")
    public ResponseEntity<BandDTO> getBand(@PathVariable Long id) {
        log.debug("REST request to get Band : {}", id);
        Optional<BandDTO> bandDTO = bandService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bandDTO);
    }

    /**
     * {@code DELETE  /bands/:id} : delete the "id" band.
     *
     * @param id the id of the bandDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bands/{id}")
    public ResponseEntity<Void> deleteBand(@PathVariable Long id) {
        log.debug("REST request to delete Band : {}", id);
        bandService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
