package roomescape.business.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import roomescape.business.domain.Time;
import roomescape.presentation.dto.TimeRequest;
import roomescape.presentation.dto.TimeResponse;

class TimeServiceTest {

    private static final LocalTime FORMATTED_MAX_LOCAL_TIME = LocalTime.of(23, 59);

    private TimeService timeService;

    @BeforeEach
    void setUp() {
        timeService = new TimeService(new FakeTimeDao());
    }

    @DisplayName("방탈출 시간을 저장한다.")
    @Test
    void create() {
        // given
        final TimeRequest timeRequest = new TimeRequest(FORMATTED_MAX_LOCAL_TIME);
        final TimeResponse expected = new TimeResponse(1L, FORMATTED_MAX_LOCAL_TIME);

        // when & then
        assertThat(timeService.create(timeRequest))
                .isEqualTo(expected);
    }

    @DisplayName("방탈출 시간을 조회한다.")
    @Test
    void find() {
        // given
        timeService.create(new TimeRequest(FORMATTED_MAX_LOCAL_TIME));

        final Long id = 1L;
        final Time expected = new Time(1L, FORMATTED_MAX_LOCAL_TIME);

        // when & then
        assertThat(timeService.find(id))
                .isEqualTo(expected);
    }

    @DisplayName("조회하려는 방탈출 시간 id가 없다면 예외가 발생한다.")
    @Test
    void find2() {
        // given
        final Long id = 1L;
        final Time expected = new Time(1L, FORMATTED_MAX_LOCAL_TIME);

        // when & then
        assertThatThrownBy(() ->timeService.find(id))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("모든 방탈출 시간을 조회한다.")
    @Test
    void findAll() {
        // given
        timeService.create(new TimeRequest(LocalTime.of(10, 0)));
        timeService.create(new TimeRequest(LocalTime.of(20, 15)));

        // when & then
        assertThat(timeService.findAll())
                .containsExactly(
                        new TimeResponse(1L, LocalTime.of(10, 0)),
                        new TimeResponse(2L, LocalTime.of(20, 15))
                );
    }
}
