package roomescape.reservation.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import roomescape.reservation.dao.FakeReservationDao;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.dto.ReservationRequest;
import roomescape.reservation.service.utils.ReservationMapper;
import roomescape.time.dao.FakeReservationTimeDao;
import roomescape.time.domain.ReservationTime;
import roomescape.time.service.utils.ReservationTimeMapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ReservationServiceTest {

    private final ReservationService reservationService;
    private final FakeReservationDao fakeReservationDao;
    private final FakeReservationTimeDao fakeReservationTimeDao;

    ReservationServiceTest() {
        fakeReservationDao = new FakeReservationDao();
        fakeReservationTimeDao = new FakeReservationTimeDao();
        reservationService = new ReservationService(
                fakeReservationDao,
                fakeReservationTimeDao,
                new ReservationMapper(new ReservationTimeMapper())
        );
    }

    @BeforeEach
    void setUp() {
        ReservationTime savedTime = fakeReservationTimeDao.insert(new ReservationTime(
                LocalTime.of(12, 30)
        ));

        fakeReservationDao.insert(new Reservation(
                "시소",
                LocalDate.of(2025, 12, 30),
                savedTime
        ));
    }

    @Test
    void 데이터를_전달받아_예약을_저장하고_반환한다() {
        // Given
        ReservationRequest reservationRequest = new ReservationRequest(
                "시소",
                LocalDate.of(2025, 6, 1),
                1L
        );

        // When & Then
        assertThat(reservationService.addReservation(reservationRequest).id())
                .isNotNull();
    }

    @Test
    void 저장된_모든_예약내역을_반환한다() {
        // When & Then
        assertThatNoException()
                .isThrownBy(() -> reservationService.findAllReservations());
    }

    @Test
    void ID를_전달받아_DB에_해당_ID가_존재한다면_삭제한다() {
        // Given
        final long id = 1L;
        ReservationRequest reservationRequest = new ReservationRequest(
                "시소",
                LocalDate.of(2025, 6, 1),
                1L
        );
        reservationService.addReservation(reservationRequest);

        // When & Then
        assertThatNoException()
                .isThrownBy(() -> reservationService.deleteReservationById(id));
    }

    @Test
    void ID를_전달받아_DB에_해당_ID가_존재하지_않는다면_예외가_발생한다() {
        // Given
        final long id = 10L;

        // When & Then
        assertThatThrownBy(() -> reservationService.deleteReservationById(id))
                .isInstanceOf(NoSuchElementException.class);
    }
}
