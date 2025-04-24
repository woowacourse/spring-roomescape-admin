package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.exception.reservation.ReservationNotFoundException;
import roomescape.repository.ReservationRepository;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private ReservationTimeService timeService;

    @InjectMocks
    private ReservationService reservationService;

    @DisplayName("예약을 생성한다")
    @Test
    void create() {
        // given
        LocalDate today = LocalDate.now();
        ReservationRequest request = new ReservationRequest("브라운", today, 1L);
        ReservationTime mockTime = new ReservationTime(1L, LocalTime.now());
        Reservation saved = new Reservation(1L, "브라운", today, mockTime);

        when(timeService.getBy(1L)).thenReturn(mockTime);
        when(reservationRepository.add(any(Reservation.class))).thenReturn(saved);

        // when
        ReservationResponse result = reservationService.create(request);

        // then
        assertThat(result.getName()).isEqualTo("브라운");
    }

    @DisplayName("전체 예약을 조회한다")
    @Test
    void getAll() {
        // given
        ReservationTime mockTime = new ReservationTime(1L, LocalTime.now());
        Reservation reservation1 = new Reservation(1L, "브라운", LocalDate.now(), mockTime);
        Reservation reservation2 = new Reservation(2L, "브라운2", LocalDate.now(), mockTime);

        when(reservationRepository.findAll()).thenReturn(List.of(reservation1, reservation2));

        // when
        List<ReservationResponse> responses = reservationService.getAll();

        // then
        assertThat(responses).hasSize(2);
    }

    @DisplayName("존재하지 않는 id의 예약 삭제시 예외를 발생시킨다")
    @Test
    void delete() {
        // given
        Long id = 99L;
        when(reservationRepository.deleteBy(id)).thenReturn(0);

        // when // then
        assertThatThrownBy(() -> reservationService.deleteBy(id))
                .isInstanceOf(ReservationNotFoundException.class);
    }
}
