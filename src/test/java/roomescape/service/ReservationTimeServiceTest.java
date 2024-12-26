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
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.exception.BadRequestException;
import roomescape.fixture.ReservationFixture;
import roomescape.fixture.ReservationTimeFixture;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;
import roomescape.service.dto.ReservationTimeRequest;
import roomescape.service.dto.ReservationTimeResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Sql(scripts = "/truncate.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
class ReservationTimeServiceTest {

    @Autowired
    private ReservationTimeService reservationTimeService;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ReservationTimeRepository reservationTimeRepository;

    @Test
    void create() {
        // given
        ReservationTimeRequest reservationTimeRequest = ReservationTimeFixture.request(1);

        // when
        ReservationTimeResponse result = reservationTimeService.create(reservationTimeRequest);

        // then
        ReservationTimeResponse expected = ReservationTimeFixture.response(result.id(), 1);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void findOne() {
        // given
        ReservationTimeRequest reservationTimeRequest = ReservationTimeFixture.request(1);
        ReservationTimeResponse expected = reservationTimeService.create(reservationTimeRequest);
        long id = expected.id();

        // when
        ReservationTimeResponse result = reservationTimeService.findOne(id);

        // then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void findAll() {
        // given
        ReservationTimeRequest request1 = ReservationTimeFixture.request(1);
        ReservationTimeRequest request2 = ReservationTimeFixture.request(1);
        ReservationTimeRequest request3 = ReservationTimeFixture.request(1);
        ReservationTimeResponse response1 = reservationTimeService.create(request1);
        ReservationTimeResponse response2 = reservationTimeService.create(request2);
        ReservationTimeResponse response3 = reservationTimeService.create(request3);

        // when
        List<ReservationTimeResponse> result = reservationTimeService.findAll();

        // then
        assertThat(result).containsExactly(response1, response2, response3);
    }

    @Test
    @DisplayName("시간을 삭제한다.")
    void remove() {
        // given
        ReservationTimeResponse savedResponse = reservationTimeService.create(ReservationTimeFixture.request(1));

        // when
        reservationTimeService.remove(savedResponse.id());

        // then
        assertThatThrownBy(() -> reservationTimeService.findOne(savedResponse.id()))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("예약이 존재하는 시간을 삭제하려고 시도하면 예외가 발생한다.")
    void removeFail() {
        // given
        ReservationTime time = ReservationTimeFixture.entity(0);
        ReservationTime savedTime = reservationTimeRepository.save(time);
        Reservation reservation = ReservationFixture.entity(savedTime);
        reservationRepository.save(reservation);

        // when & then
        assertThatThrownBy(() -> reservationTimeService.remove(savedTime.getId()))
                .isInstanceOf(BadRequestException.class);
    }
}
