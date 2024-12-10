package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import roomescape.ReservationFixture;
import roomescape.service.dto.ReservationRequest;
import roomescape.service.dto.ReservationResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Sql(scripts = "/truncate.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
class ReservationServiceTest {

    @Autowired
    private ReservationService reservationService;

    @Test
    void create() {
        // given
        ReservationRequest reservationRequest = ReservationFixture.request(1, 0);

        // when
        ReservationResponse result = reservationService.create(reservationRequest);

        // then
        ReservationResponse expected = ReservationFixture.response(result.id(), 1, 0);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void findOne() {
        // given
        ReservationRequest reservationRequest = ReservationFixture.request(1, 0);
        ReservationResponse expected = reservationService.create(reservationRequest);
        long id = expected.id();

        // when
        ReservationResponse result = reservationService.findOne(id);

        // then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void findAll() {
        // given
        ReservationRequest request1 = ReservationFixture.request(1, 0);
        ReservationRequest request2 = ReservationFixture.request(1, 0);
        ReservationRequest request3 = ReservationFixture.request(1, 0);
        ReservationResponse response1 = reservationService.create(request1);
        ReservationResponse response2 = reservationService.create(request2);
        ReservationResponse response3 = reservationService.create(request3);

        // when
        List<ReservationResponse> result = reservationService.findAll();

        // then
        assertThat(result).containsExactly(response1, response2, response3);
    }

    @Test
    @DisplayName("예약을 삭제한다.")
    void remove() {
        // given
        ReservationResponse savedResponse = reservationService.create(ReservationFixture.request(1, 1));

        // when
        reservationService.remove(savedResponse.id());

        // then
        assertThatThrownBy(() -> reservationService.findOne(savedResponse.id()))
                .isInstanceOf(NoSuchElementException.class);
    }
}
