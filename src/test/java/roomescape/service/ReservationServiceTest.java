package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import roomescape.ReservationService;
import roomescape.domain.Reservation;
import roomescape.repository.ReservationRepository;

@SpringBootTest
@Transactional
public class ReservationServiceTest {
    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ReservationRepository reservationRepository;

    @Test
    void 예약_생성() {
        //given
        LocalDateTime localDateTime = LocalDateTime.of(2023, 8, 5, 15, 40);
        // when
        Long id = reservationService.createReservation("브라운", localDateTime);
        //then
        Reservation reservation = reservationRepository.findById(id);
        assertThat(reservation).isNotNull();
        assertThat(reservation.getReservationName()).isEqualTo("브라운");
        assertThat(reservation.getReservationDateTime()).isEqualTo(localDateTime);
    }

    @Test
    void 예약_삭제() {
        //given
        LocalDateTime localDateTime = LocalDateTime.of(2023, 8, 5, 15, 40);
        Long id = reservationService.createReservation("브라운", localDateTime);
        // when
        reservationService.deleteReservation(id);
        //then
        Reservation reservation = reservationRepository.findById(id);
        assertThat(reservation).isNull();
    }

    @Test
    void 예약_조회() {
        //given
        LocalDateTime localDateTime = LocalDateTime.of(2023, 8, 5, 15, 40);
        Long id = reservationService.createReservation("브라운", localDateTime);
        // when
        Reservation reservation = reservationService.findReservationById(id);
        //then
        assertThat(reservation).isNotNull();
        assertThat(reservation.getReservationName()).isEqualTo("브라운");
        assertThat(reservation.getReservationDateTime()).isEqualTo(localDateTime);
    }
}
