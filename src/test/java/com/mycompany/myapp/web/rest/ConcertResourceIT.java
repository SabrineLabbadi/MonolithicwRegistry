package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.MonolithicwRegistryApp;
import com.mycompany.myapp.domain.Concert;
import com.mycompany.myapp.repository.ConcertRepository;
import com.mycompany.myapp.service.ConcertService;
import com.mycompany.myapp.service.dto.ConcertDTO;
import com.mycompany.myapp.service.mapper.ConcertMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.mycompany.myapp.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ConcertResource} REST controller.
 */
@SpringBootTest(classes = MonolithicwRegistryApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ConcertResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    @Autowired
    private ConcertRepository concertRepository;

    @Autowired
    private ConcertMapper concertMapper;

    @Autowired
    private ConcertService concertService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restConcertMockMvc;

    private Concert concert;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Concert createEntity(EntityManager em) {
        Concert concert = new Concert()
            .name(DEFAULT_NAME)
            .date(DEFAULT_DATE)
            .address(DEFAULT_ADDRESS);
        return concert;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Concert createUpdatedEntity(EntityManager em) {
        Concert concert = new Concert()
            .name(UPDATED_NAME)
            .date(UPDATED_DATE)
            .address(UPDATED_ADDRESS);
        return concert;
    }

    @BeforeEach
    public void initTest() {
        concert = createEntity(em);
    }

    @Test
    @Transactional
    public void createConcert() throws Exception {
        int databaseSizeBeforeCreate = concertRepository.findAll().size();
        // Create the Concert
        ConcertDTO concertDTO = concertMapper.toDto(concert);
        restConcertMockMvc.perform(post("/api/concerts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(concertDTO)))
            .andExpect(status().isCreated());

        // Validate the Concert in the database
        List<Concert> concertList = concertRepository.findAll();
        assertThat(concertList).hasSize(databaseSizeBeforeCreate + 1);
        Concert testConcert = concertList.get(concertList.size() - 1);
        assertThat(testConcert.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testConcert.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testConcert.getAddress()).isEqualTo(DEFAULT_ADDRESS);
    }

    @Test
    @Transactional
    public void createConcertWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = concertRepository.findAll().size();

        // Create the Concert with an existing ID
        concert.setId(1L);
        ConcertDTO concertDTO = concertMapper.toDto(concert);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConcertMockMvc.perform(post("/api/concerts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(concertDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Concert in the database
        List<Concert> concertList = concertRepository.findAll();
        assertThat(concertList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllConcerts() throws Exception {
        // Initialize the database
        concertRepository.saveAndFlush(concert);

        // Get all the concertList
        restConcertMockMvc.perform(get("/api/concerts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(concert.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)));
    }
    
    @Test
    @Transactional
    public void getConcert() throws Exception {
        // Initialize the database
        concertRepository.saveAndFlush(concert);

        // Get the concert
        restConcertMockMvc.perform(get("/api/concerts/{id}", concert.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(concert.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.date").value(sameInstant(DEFAULT_DATE)))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS));
    }
    @Test
    @Transactional
    public void getNonExistingConcert() throws Exception {
        // Get the concert
        restConcertMockMvc.perform(get("/api/concerts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConcert() throws Exception {
        // Initialize the database
        concertRepository.saveAndFlush(concert);

        int databaseSizeBeforeUpdate = concertRepository.findAll().size();

        // Update the concert
        Concert updatedConcert = concertRepository.findById(concert.getId()).get();
        // Disconnect from session so that the updates on updatedConcert are not directly saved in db
        em.detach(updatedConcert);
        updatedConcert
            .name(UPDATED_NAME)
            .date(UPDATED_DATE)
            .address(UPDATED_ADDRESS);
        ConcertDTO concertDTO = concertMapper.toDto(updatedConcert);

        restConcertMockMvc.perform(put("/api/concerts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(concertDTO)))
            .andExpect(status().isOk());

        // Validate the Concert in the database
        List<Concert> concertList = concertRepository.findAll();
        assertThat(concertList).hasSize(databaseSizeBeforeUpdate);
        Concert testConcert = concertList.get(concertList.size() - 1);
        assertThat(testConcert.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testConcert.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testConcert.getAddress()).isEqualTo(UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void updateNonExistingConcert() throws Exception {
        int databaseSizeBeforeUpdate = concertRepository.findAll().size();

        // Create the Concert
        ConcertDTO concertDTO = concertMapper.toDto(concert);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConcertMockMvc.perform(put("/api/concerts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(concertDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Concert in the database
        List<Concert> concertList = concertRepository.findAll();
        assertThat(concertList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteConcert() throws Exception {
        // Initialize the database
        concertRepository.saveAndFlush(concert);

        int databaseSizeBeforeDelete = concertRepository.findAll().size();

        // Delete the concert
        restConcertMockMvc.perform(delete("/api/concerts/{id}", concert.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Concert> concertList = concertRepository.findAll();
        assertThat(concertList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
