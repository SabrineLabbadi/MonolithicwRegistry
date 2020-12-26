package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class ConcertTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Concert.class);
        Concert concert1 = new Concert();
        concert1.setId(1L);
        Concert concert2 = new Concert();
        concert2.setId(concert1.getId());
        assertThat(concert1).isEqualTo(concert2);
        concert2.setId(2L);
        assertThat(concert1).isNotEqualTo(concert2);
        concert1.setId(null);
        assertThat(concert1).isNotEqualTo(concert2);
    }
}
