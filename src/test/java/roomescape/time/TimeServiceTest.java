package roomescape.time;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doReturn;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
}
