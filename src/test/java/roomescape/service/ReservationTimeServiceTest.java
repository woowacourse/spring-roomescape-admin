package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static roomescape.fixture.ReservationTimeFixture.INITIAL_RESERVATION_TIME_SIZE;
import static roomescape.fixture.ReservationTimeFixture.NO_RESERVATION_TIME_ID;
import static roomescape.fixture.ReservationTimeFixture.TIME_1_ID;
import static roomescape.fixture.ReservationTimeFixture.newTimeRequest;
import static roomescape.fixture.ReservationTimeFixture.newTimeResponse;
import static roomescape.fixture.ThemeFixture.THEME_1_ID;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import roomescape.exception.BadRequestException;
import roomescape.fixture.ReservationFixture;
import roomescape.fixture.ReservationTimeFixture;
import roomescape.service.dto.AvailableReservationTimeResponse;
import roomescape.service.dto.ReservationTimeRequest;
import roomescape.service.dto.ReservationTimeResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Sql(scripts = "/truncate.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/test_data.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
class ReservationTimeServiceTest {

    @Autowired
    private ReservationTimeService reservationTimeService;

    @Test
    void create() {
        // given
        ReservationTimeRequest request = newTimeRequest();

        // when
        ReservationTimeResponse result = reservationTimeService.create(request);

        // then
        ReservationTimeResponse expected = newTimeResponse();
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void findOne() {
        // when
        ReservationTimeResponse result = reservationTimeService.findOne(TIME_1_ID);

        // then
        assertThat(result.startAt()).isEqualTo(
                ReservationTimeFixture.time1().getStartAt().format(DateTimeFormatter.ofPattern("HH:mm"))
        );
    }

    @Test
    void findAll() {
        // when
        List<ReservationTimeResponse> result = reservationTimeService.findAll();

        // then
        assertThat(result.size()).isEqualTo(INITIAL_RESERVATION_TIME_SIZE);
    }

    @Test
    @DisplayName("시간을 삭제한다.")
    void remove() {
        // when
        reservationTimeService.remove(NO_RESERVATION_TIME_ID);

        // then
        assertThatThrownBy(() -> reservationTimeService.findOne(NO_RESERVATION_TIME_ID))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("예약이 존재하는 시간을 삭제하려고 시도하면 예외가 발생한다.")
    void removeFail() {
        // when & then
        assertThatThrownBy(() -> reservationTimeService.remove(TIME_1_ID))
                .isInstanceOf(BadRequestException.class);
    }

    @Test
    @DisplayName("특정 날짜와 테마에 예약이 됐는지 여부와 함께 시간들을 조회한다.")
    void findAllWithAvailable() {
        // when
        List<AvailableReservationTimeResponse> result = reservationTimeService.findAllWithAvailable(
                ReservationFixture.reservation1().getDate(), THEME_1_ID);

        // then
        assertThat(result).containsExactly(
                new AvailableReservationTimeResponse(ReservationTimeFixture.time1(), true),
                new AvailableReservationTimeResponse(ReservationTimeFixture.time2(), true),
                new AvailableReservationTimeResponse(ReservationTimeFixture.noReservationTime(), false)
        );
    }
}
