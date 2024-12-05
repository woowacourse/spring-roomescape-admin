package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import roomescape.ReservationFixture;

@SpringBootTest
class ReservationServiceTest {

    @Autowired
    private ReservationService reservationService;

    @Test
    void create() {
        // given
        ReservationRequest reservationRequest = ReservationFixture.request(1);

        // when
        ReservationResponse result = reservationService.create(reservationRequest);

        // then
        ReservationResponse expected = ReservationFixture.response(result.id(), 1);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void findOne() {
        // given
        ReservationRequest reservationRequest = ReservationFixture.request(1);
        ReservationResponse expected = reservationService.create(reservationRequest);
        long id = expected.id();

        // when
        ReservationResponse result = reservationService.findOne(id);

        // then
        assertThat(result).isEqualTo(expected);
    }
}
