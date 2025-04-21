package roomescape.reservation.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import roomescape.reservation.dao.FakeReservationDao;
import roomescape.reservation.dto.ReservationRequest;
import roomescape.reservation.dto.ReservationResponse;
import roomescape.reservation.utils.ReservationMapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class ReservationServiceTest {

    @Autowired
    private ReservationMapper reservationMapper;

    private ReservationService reservationService;

    @BeforeEach
    void setUp() {
        FakeReservationDao fakeReservationDao = new FakeReservationDao(reservationMapper);
        reservationService = new ReservationService(fakeReservationDao, reservationMapper);
    }

    @Test
    void 데이터를_전달받아_예약을_저장하고_반환한다() {
        // Given
        ReservationRequest reservationRequest = new ReservationRequest(
                "시소",
                LocalDate.of(2025, 1, 1),
                LocalTime.of(12, 10)
        );

        // When & Then
        assertThat(reservationService.addReservation(reservationRequest))
                .isInstanceOf(ReservationResponse.class);
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
                LocalDate.of(2025, 1, 1),
                LocalTime.of(12, 10)
        );
        reservationService.addReservation(reservationRequest);

        // When & Then
        assertThatNoException()
                .isThrownBy(() -> reservationService.deleteReservationById(id));
    }

    @Test
    void ID를_전달받아_DB에_해당_ID가_존재하지_않는다면_예외가_발생한다() {
        // Given
        final long id = 1L;

        // When & Then
        assertThatThrownBy(() -> reservationService.deleteReservationById(id))
                .isInstanceOf(NoSuchElementException.class);
    }
}
