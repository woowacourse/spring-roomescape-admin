package roomescape.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import roomescape.domain.ReservationTime;
import roomescape.dto.reservationtime.ReservationTimeCreateRequest;
import roomescape.dto.reservationtime.ReservationTimeResponse;
import roomescape.repository.reservationtime.ReservationTimeRepository;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
@DisplayName("예약 시간 서비스")
class ReservationTimeServiceTest {

    private ReservationTimeService reservationTimeService;
    @Mock
    private ReservationTimeRepository reservationTimeRepository;
    private LocalTime startAt;

    @BeforeEach
    void setUp() {
        this.reservationTimeService = new ReservationTimeService(reservationTimeRepository);
        this.startAt = LocalTime.of(10, 10);
    }

    @DisplayName("에약 시간 서비스는 시간을 생성한다.")
    @Test
    void createTime() {
        // given
        Mockito.when(reservationTimeRepository.save(any()))
                .thenReturn(new ReservationTime(1L, startAt));
        ReservationTimeCreateRequest request = new ReservationTimeCreateRequest(startAt);

        // when
        ReservationTimeResponse reservationTime = reservationTimeService.createTime(request);

        // then
        assertThat(reservationTime.startAt())
                .isEqualTo(startAt);
    }

    @DisplayName("예약 시간 서비스는 id에 맞는 시간을 반환한다.")
    @Test
    void readReservationTime() {
        // given
        Long id = 1L;
        Mockito.when(reservationTimeRepository.findById(id))
                .thenReturn(Optional.of(new ReservationTime(id, startAt)));

        // when
        ReservationTimeResponse reservationTime = reservationTimeService.readReservationTime(id);

        // then
        assertThat(reservationTime.startAt())
                .isEqualTo(startAt);
    }

    @DisplayName("예약 시간 서비스는 시간들을 반환한다.")
    @Test
    void readReservationTimes() {
        // given
        Mockito.when(reservationTimeRepository.findAll())
                .thenReturn(List.of(new ReservationTime(1L, startAt)));

        // when
        List<ReservationTimeResponse> reservationTimes = reservationTimeService.readReservationTimes();

        // then
        assertThat(reservationTimes.size()).isEqualTo(1);
    }

    @DisplayName("예약 시간 서비스는 id에 맞는 시간을 삭제한다.")
    @Test
    void deleteTime() {
        // given
        Long id = 1L;
        Mockito.doNothing().when(reservationTimeRepository).deleteById(id);

        // when & then
        assertThatCode(() -> reservationTimeService.deleteTime(id))
                .doesNotThrowAnyException();
    }
}
