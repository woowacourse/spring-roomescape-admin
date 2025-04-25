package roomescape.business.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.business.domain.Time;
import roomescape.presentation.dto.TimeRequest;
import roomescape.presentation.dto.TimeResponse;

class TimeServiceTest {

    private TimeService timeService;

    @BeforeEach
    void setUp() {
        timeService = new TimeService(new FakeTimeDao());
    }

    @DisplayName("방탈출 시간을 저장한다.")
    @Test
    void create() {
        // given
        final TimeRequest timeRequest = new TimeRequest(LocalTime.MAX);
        final TimeResponse expected = new TimeResponse(1L, LocalTime.MAX);

        // when & then
        assertThat(timeService.create(timeRequest))
                .isEqualTo(expected);
    }

    @DisplayName("방탈출 시간을 조회한다.")
    @Test
    void find() {
        // given
        timeService.create(new TimeRequest(LocalTime.MAX));

        final Long id = 1L;
        final Time expected = new Time(1L, LocalTime.of(23, 59));

        // when & then
        assertThat(timeService.find(id))
                .isEqualTo(expected);
    }
}
