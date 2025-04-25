package roomescape.reservation.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.domain.repository.TimeFakeRepository;
import roomescape.reservation.controller.dto.TimeRequest;
import roomescape.reservation.controller.dto.TimeResponse;
import roomescape.reservation.domain.Time;
import roomescape.reservation.domain.repository.TimeRepository;

class TimeServiceTest {

    private TimeService timeService;

    @BeforeEach
    void setup() {
        TimeRepository timeRepository = new TimeFakeRepository();

        List<Time> times = List.of(
                new Time(null, LocalTime.of(3, 12)),
                new Time(null, LocalTime.of(11, 33)),
                new Time(null, LocalTime.of(16, 54)),
                new Time(null, LocalTime.of(23, 53))
        );

        for (Time time : times) {
            timeRepository.saveAndReturnId(time);
        }

        timeService = new TimeService(timeRepository);
    }

    @DisplayName("에약 시간 정보를 추가한다")
    @Test
    void add_test() {
        // given
        TimeRequest request = new TimeRequest(LocalTime.of(17, 25));

        // when
        TimeResponse response = timeService.add(request);

        // then
        TimeResponse expected = new TimeResponse(5L, LocalTime.of(17, 25));
        assertThat(response).isEqualTo(expected);
    }

    @DisplayName("예약 시간 정보 정상적으로 삭제하면 예외가 발생하지 않는다")
    @Test
    void remove_test() {
        // given
        Long removeId = 3L;

        // when && then
        assertThatCode(() -> timeService.remove(removeId))
                .doesNotThrowAnyException();
    }

    @DisplayName("예약 정보 시간을 모두 조회한다")
    @Test
    void get_times_test() {
        // when
        List<TimeResponse> timeResponses = timeService.getTimes();

        // then
        assertThat(timeResponses).hasSize(4);
    }

}
