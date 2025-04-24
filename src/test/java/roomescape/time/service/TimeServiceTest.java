package roomescape.time.service;

import java.time.LocalTime;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import roomescape.time.dao.FakeTimeDao;
import roomescape.time.dto.TimeRequest;
import roomescape.time.utils.TimeMapper;

import static org.assertj.core.api.Assertions.*;

class TimeServiceTest {

    private TimeService timeService;

    @BeforeEach
    void setUp() {
        timeService = new TimeService(new FakeTimeDao(), new TimeMapper());
    }

    @Test
    void 데이터를_전달받아_시간을_추가한다() {
        // Given
        TimeRequest timeRequest = new TimeRequest(
                LocalTime.of(11, 30)
        );

        // When & Then
        assertThat(timeService.addTime(timeRequest).id())
                .isNotNull();
    }

    @Test
    void 저장된_모든_시간을_반환한다() {
        // When & Then
        assertThatNoException()
                .isThrownBy(() -> timeService.findAllTimes());
    }

    @Test
    void ID를_전달받아_DB에_해당_ID가_존재한다면_삭제한다() {
        // Given
        final long id = 1L;
        TimeRequest timeRequest = new TimeRequest(
                LocalTime.of(12, 10)
        );
        timeService.addTime(timeRequest);

        // When & Then
        assertThatNoException()
                .isThrownBy(() -> timeService.deleteTimeById(id));
    }

    @Test
    void ID를_전달받아_DB에_해당_ID가_존재하지_않는다면_예외가_발생한다() {
        // Given
        final long id = 1L;

        // When & Then
        assertThatThrownBy(() -> timeService.deleteTimeById(id))
                .isInstanceOf(NoSuchElementException.class);
    }
}
