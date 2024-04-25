package roomescape.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.controller.time.TimeRequest;
import roomescape.controller.time.TimeResponse;

import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TimeServiceTest {

    private TimeService timeService;

    @BeforeEach
    void setUp() {
        timeService = new TimeService(new ReservationTimeFakeRepository());
    }

    @Test
    @DisplayName("예약 시간 목록을 조회한다.")
    void getTimes() {
        // given
        List<TimeResponse> expected = List.of(
                new TimeResponse(1L, "10:15"),
                new TimeResponse(2L, "11:20"),
                new TimeResponse(3L, "12:25")
        );

        // when
        List<TimeResponse> actual = timeService.getTimes();

        // then
        assertThat(actual).containsExactlyElementsOf(expected);
    }

    @Test
    @DisplayName("예약 시간을 추가한다.")
    void addTIme() {
        // given
        TimeRequest time = new TimeRequest(LocalTime.of(13, 30));
        TimeResponse expected = new TimeResponse(4L, "13:30");

        // when
        TimeResponse actual = timeService.addTime(time);

        // then
        assertThat(actual).isEqualTo(expected);
    }
}
