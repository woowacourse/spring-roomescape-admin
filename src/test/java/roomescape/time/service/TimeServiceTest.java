package roomescape.time.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doReturn;

import java.time.LocalTime;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import roomescape.error.ReferDataDeleteException;
import roomescape.time.domain.Time;
import roomescape.time.repository.TimeRepository;

@DisplayName("시간 서비스")
@ExtendWith(MockitoExtension.class)
class TimeServiceTest {
    @Mock
    private TimeRepository timeRepository;

    @InjectMocks
    private TimeService timeService;

    @DisplayName("존재하지 않는 id일 경우 예외가 발생한다.")
    @Test
    void findByIdExceptionTest() {
        // given
        Long id = 1L;

        doReturn(Optional.empty()).when(timeRepository)
                .findById(id);

        // when & then
        assertThatThrownBy(() -> timeService.findById(id))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("time id를 참조하고 있는 reservation이 있을 때, 해당 time을 삭제하면 ReferDataDeleteException 예외가 발생한다.")
    @Test
    void deleteByIdExceptionTest() {
        // given
        Long id = 1L;
        Time time = new Time(id, LocalTime.parse("10:00"));

        doReturn(Optional.of(time)).when(timeRepository)
                .findById(id);

        doReturn(Optional.of(time)).when(timeRepository)
                .findBySameReferId(id);

        // when & then
        assertThatThrownBy(() -> timeService.deleteById(id))
                .isInstanceOf(ReferDataDeleteException.class);
    }
}
