package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import roomescape.dao.ReservationDao;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationRequest;

@ExtendWith(MockitoExtension.class)
class ReservationServiceMockTest {

    @Mock
    private ReservationDao reservationDao;

    @Mock
    private ReservationTimeDao reservationTimeDao;

    @InjectMocks
    private ReservationService reservationService;

    @Test
    @DisplayName("전체 예약 목록을 조회한다.")
    void should_return_all_reservations() {
        final ReservationTime time = new ReservationTime(1L, "10:00");
        final List<Reservation> reservations = List.of(
                new Reservation(1L, "브라운", "2023-08-05", time),
                new Reservation(2L, "리사", "2023-08-06", time)
        );
        given(reservationDao.getReservations()).willReturn(reservations);

        final List<Reservation> result = reservationService.getReservations();

        assertThat(result).hasSize(2);
    }

    @Test
    @DisplayName("예약을 생성하고 반환한다.")
    void should_create_and_return_reservation() {
        final ReservationRequest request = new ReservationRequest("브라운", "2023-08-05", 1L);
        final ReservationTime time = new ReservationTime(1L, "10:00");
        given(reservationDao.insertAndGetId(request)).willReturn(1L);
        given(reservationTimeDao.findById(1L)).willReturn(time);

        final Reservation result = reservationService.createReservation(request);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("브라운");
        assertThat(result.getDate()).isEqualTo("2023-08-05");
        assertThat(result.getTime()).isEqualTo(time);
    }

    @Test
    @DisplayName("예약을 삭제한다.")
    void should_delete_reservation() {
        reservationService.deleteReservation(1L);

        then(reservationDao).should(times(1)).deleteReservation(1L);
    }
}
