package roomescape.time.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.time.LocalTime;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.time.dao.FakeTimeJdbcDao;
import roomescape.time.domain.Time;
import roomescape.time.dto.ReservationTimeRequest;
import roomescape.time.dto.ReservationTimeResponse;
import roomescape.time.repository.TimeRepository;

class TimeServiceTest {
    private final Time time = new Time(1L, LocalTime.of(17, 3));

    private TimeService timeService;

    @BeforeEach
    void setUp() {
        TimeRepository timeRepository = new TimeRepository(new FakeTimeJdbcDao());
        this.timeService = new TimeService(timeRepository);
    }

    @Test
    @DisplayName("시간을 추가한다.")
    void addReservationTime() {
        ReservationTimeRequest timeRequest = new ReservationTimeRequest(time.getStartAt());
        ReservationTimeResponse timeResponse = timeService.addReservationTime(timeRequest);

        Assertions.assertThat(timeResponse.id()).isEqualTo(1);
    }

    @Test
    @DisplayName("시간을 찾는다.")
    void findReservationTimes() {
        List<ReservationTimeResponse> timeResponses = timeService.findReservationTimes();

        Assertions.assertThat(timeResponses.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("시간을 지운다.")
    void removeReservationTime() {
        assertDoesNotThrow(() -> timeService.removeReservationTime(time.getId()));
    }
}
