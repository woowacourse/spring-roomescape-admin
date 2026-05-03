package roomescape.service;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static roomescape.TestFixture.createReservation;
import static roomescape.TestFixture.createTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import roomescape.domain.Reservation;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;

class ReservationTimeServiceTest {
    private final ReservationRepository reservationRepository = mock(ReservationRepository.class);
    private final ReservationTimeRepository reservationTimeRepository = mock(ReservationTimeRepository.class);
    private final ReservationTimeService timeService;

    public ReservationTimeServiceTest() {
        this.timeService = new ReservationTimeService(reservationRepository, reservationTimeRepository);
    }

    @Nested
    @DisplayName("delete(): ")
    class Delete {
        @Test
        @DisplayName("존재하는 예약이 없다면, 예약시간 삭제에 성공한다.")
        void delete() {
            Reservation reservation = createReservation();
            when(reservationRepository.isExistsByTimeId(reservation.getId())).thenReturn(false);

            assertThatNoException().isThrownBy(() -> timeService.delete(createTime().getId()));
        }

        @Test
        @DisplayName("존재하는 예약이 있다면, 예외를 반환한다.")
        void throwIllegalArgumentException_when_isExistsReservation() {
            Reservation reservation = createReservation();
            Long timeId = createTime().getId();
            when(reservationRepository.isExistsByTimeId(reservation.getId())).thenReturn(true);

            assertThatThrownBy(() -> timeService.delete(timeId))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }
}
