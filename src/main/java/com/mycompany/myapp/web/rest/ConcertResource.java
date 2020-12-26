package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.ConcertService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.service.dto.ConcertDTO;

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
 * REST controller for managing {@link com.mycompany.myapp.domain.Concert}.
 */
@RestController
@RequestMapping("/api")
public class ConcertResource {

    private final Logger log = LoggerFactory.getLogger(ConcertResource.class);

    private static final String ENTITY_NAME = "concert";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConcertService concertService;

    public ConcertResource(ConcertService concertService) {
        this.concertService = concertService;
    }

    /**
     * {@code POST  /concerts} : Create a new concert.
     *
     * @param concertDTO the concertDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new concertDTO, or with status {@code 400 (Bad Request)} if the concert has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/concerts")
    public ResponseEntity<ConcertDTO> createConcert(@RequestBody ConcertDTO concertDTO) throws URISyntaxException {
        log.debug("REST request to save Concert : {}", concertDTO);
        if (concertDTO.getId() != null) {
            throw new BadRequestAlertException("A new concert cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConcertDTO result = concertService.save(concertDTO);
        return ResponseEntity.created(new URI("/api/concerts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /concerts} : Updates an existing concert.
     *
     * @param concertDTO the concertDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated concertDTO,
     * or with status {@code 400 (Bad Request)} if the concertDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the concertDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/concerts")
    public ResponseEntity<ConcertDTO> updateConcert(@RequestBody ConcertDTO concertDTO) throws URISyntaxException {
        log.debug("REST request to update Concert : {}", concertDTO);
        if (concertDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ConcertDTO result = concertService.save(concertDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, concertDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /concerts} : get all the concerts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of concerts in body.
     */
    @GetMapping("/concerts")
    public List<ConcertDTO> getAllConcerts() {
        log.debug("REST request to get all Concerts");
        return concertService.findAll();
    }

    /**
     * {@code GET  /concerts/:id} : get the "id" concert.
     *
     * @param id the id of the concertDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the concertDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/concerts/{id}")
    public ResponseEntity<ConcertDTO> getConcert(@PathVariable Long id) {
        log.debug("REST request to get Concert : {}", id);
        Optional<ConcertDTO> concertDTO = concertService.findOne(id);
        return ResponseUtil.wrapOrNotFound(concertDTO);
    }

    /**
     * {@code DELETE  /concerts/:id} : delete the "id" concert.
     *
     * @param id the id of the concertDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/concerts/{id}")
    public ResponseEntity<Void> deleteConcert(@PathVariable Long id) {
        log.debug("REST request to delete Concert : {}", id);
        concertService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
