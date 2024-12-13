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
import roomescape.fixture.ReservationTimeFixture;
import roomescape.service.dto.ReservationTimeRequest;
import roomescape.service.dto.ReservatonTimeResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Sql(scripts = "/truncate.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
class ReservationTimeServiceTest {

    @Autowired
    private ReservationTimeService reservationTimeService;

    @Test
    void create() {
        // given
        ReservationTimeRequest reservationTimeRequest = ReservationTimeFixture.request(1);

        // when
        ReservatonTimeResponse result = reservationTimeService.create(reservationTimeRequest);

        // then
        ReservatonTimeResponse expected = ReservationTimeFixture.response(result.id(), 1);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void findOne() {
        // given
        ReservationTimeRequest reservationTimeRequest = ReservationTimeFixture.request(1);
        ReservatonTimeResponse expected = reservationTimeService.create(reservationTimeRequest);
        long id = expected.id();

        // when
        ReservatonTimeResponse result = reservationTimeService.findOne(id);

        // then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void findAll() {
        // given
        ReservationTimeRequest request1 = ReservationTimeFixture.request(1);
        ReservationTimeRequest request2 = ReservationTimeFixture.request(1);
        ReservationTimeRequest request3 = ReservationTimeFixture.request(1);
        ReservatonTimeResponse response1 = reservationTimeService.create(request1);
        ReservatonTimeResponse response2 = reservationTimeService.create(request2);
        ReservatonTimeResponse response3 = reservationTimeService.create(request3);

        // when
        List<ReservatonTimeResponse> result = reservationTimeService.findAll();

        // then
        assertThat(result).containsExactly(response1, response2, response3);
    }

    @Test
    @DisplayName("예약을 삭제한다.")
    void remove() {
        // given
        ReservatonTimeResponse savedResponse = reservationTimeService.create(ReservationTimeFixture.request(1));

        // when
        reservationTimeService.remove(savedResponse.id());

        // then
        assertThatThrownBy(() -> reservationTimeService.findOne(savedResponse.id()))
                .isInstanceOf(NoSuchElementException.class);
    }
}
