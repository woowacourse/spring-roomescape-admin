package roomescape.time.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import roomescape.time.application.dto.TimeInfo;
import roomescape.time.application.dto.TimeRequest;
import roomescape.time.repository.TimeEntity;
import roomescape.time.repository.TimesRepository;

@ExtendWith(MockitoExtension.class)
class TimesServiceTest {

    @Mock
    TimesRepository timesRepository;

    @InjectMocks
    TimesService timesService;

    @DisplayName("새로운 시간을 등록한다.")
    @Test
    void register() {
        //given
        given(timesRepository.saveTime(any(TimeEntity.class)))
                .willReturn(
                        new TimeEntity(
                                1L,
                                LocalTime.of(10, 0)
                        )
                );

        TimeRequest request = new TimeRequest(LocalTime.of(10, 0));

        //when
        TimeInfo registered = timesService.register(request);

        //then
        assertThat(registered.id()).isEqualTo(1L);
        assertThat(registered.startAt()).isEqualTo(LocalTime.of(10, 0));

        verify(timesRepository).saveTime(any(TimeEntity.class));
    }

    @DisplayName("모든 시간들을 조회한다.")
    @Test
    void getTimes() {
        //given
        given(timesRepository.getTimes())
                .willReturn(List.of(
                        new TimeEntity(1L, LocalTime.of(10, 0)),
                        new TimeEntity(2L, LocalTime.of(11, 0)),
                        new TimeEntity(3L, LocalTime.of(12, 0))
                ));

        //when
        List<TimeInfo> timeInfos = timesService.getTimes();

        //then
        assertThat(timeInfos.size()).isEqualTo(3);
        verify(timesRepository).getTimes();
    }
}
