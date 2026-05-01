package roomescape.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.dto.ReservationsResponse;
import roomescape.model.Reservation;
import roomescape.model.ReservationTime;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private ReservationTimeRepository reservationTimeRepository;

    @InjectMocks
    private ReservationService reservationService;

    @Test
    @DisplayName("예약을 생성한다")
    void create() {
        // given
        ReservationRequest request = new ReservationRequest("브라운", LocalDate.of(2026, 12, 25), 1L);
        ReservationTime mockTime = new ReservationTime(1L, LocalTime.of(10, 0));
        given(reservationTimeRepository.findById(1L)).willReturn(mockTime);
        given(reservationRepository.create(any())).willReturn(1L);

        // when
        ReservationResponse response = reservationService.create(request);

        // then
        assertThat(response.getName()).isEqualTo("브라운");
        verify(reservationRepository).create(any());
    }

    @Test
    @DisplayName("모든 예약을 조회한다")
    void findAll() {
        // given
        List<Reservation> mockReservations = List.of(
                new Reservation(1L, "브라운", LocalDate.of(2026, 12, 25), new ReservationTime(1L, LocalTime.of(10, 0))),
                new Reservation(2L, "루크", LocalDate.of(2026, 12, 26), new ReservationTime(2L, LocalTime.of(13, 0)))
        );
        given(reservationRepository.findAll()).willReturn(mockReservations);

        // when
        ReservationsResponse response = reservationService.findAll();

        // then
        assertThat(response.getReservationsResponse()).hasSize(2);
        assertThat(response.getReservationsResponse().get(1).getName()).isEqualTo("루크");
        verify(reservationRepository).findAll();
    }

    @Test
    @DisplayName("예약을 삭제한다")
    void delete() {
        // given
        long reservationId = 1L;
        given(reservationRepository.delete(reservationId)).willReturn(1);

        // when
        int result = reservationService.delete(reservationId);

        // then
        assertThat(result).isEqualTo(1);
        verify(reservationRepository).delete(reservationId);
    }
}
