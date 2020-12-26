package com.mycompany.myapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class ConcertDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConcertDTO.class);
        ConcertDTO concertDTO1 = new ConcertDTO();
        concertDTO1.setId(1L);
        ConcertDTO concertDTO2 = new ConcertDTO();
        assertThat(concertDTO1).isNotEqualTo(concertDTO2);
        concertDTO2.setId(concertDTO1.getId());
        assertThat(concertDTO1).isEqualTo(concertDTO2);
        concertDTO2.setId(2L);
        assertThat(concertDTO1).isNotEqualTo(concertDTO2);
        concertDTO1.setId(null);
        assertThat(concertDTO1).isNotEqualTo(concertDTO2);
    }
}
