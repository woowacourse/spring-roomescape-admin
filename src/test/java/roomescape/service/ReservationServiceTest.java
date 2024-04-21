package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.controller.dto.ReservationCreateRequest;
import roomescape.domain.Reservation;
import roomescape.repository.ReservationRepository;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationServiceTest {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ReservationRepository reservationRepository;

    @Test
    @DisplayName("예약을 추가한다.")
    void createReservation() {
        final ReservationCreateRequest request = new ReservationCreateRequest(
                "냥인", "2024-04-21", "10:25");

        final Reservation reservation = reservationService.createReservation(request);

        assertThat(reservation.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("예약 목록을 조회한다.")
    void readAllReservations() {
        getIdAfterCreateReservation();

        final List<Reservation> reservations = reservationService.readAllReservations();

        assertThat(reservations).hasSize(1);
    }
    
    @Test
    @DisplayName("예약을 취소한다.")
    void cancelReservation() {
        final Long id = getIdAfterCreateReservation();

        reservationService.cancelReservation(id);
        final List<Reservation> reservations = reservationRepository.findAll();

        assertThat(reservations).hasSize(0);
    }

    private Long getIdAfterCreateReservation() {
        final ReservationCreateRequest request = new ReservationCreateRequest(
                "냥인", "2024-04-21", "10:25");
        return reservationRepository.save(request);
    }
}
