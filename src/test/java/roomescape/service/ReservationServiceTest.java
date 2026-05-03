package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.Reservation;
import roomescape.repository.ReservationRepository;

@SpringBootTest
@Transactional
public class ReservationServiceTest {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    ReservationTimeService reservationTimeService;

    @Test
    void 예약_생성() {
        //given
        final LocalDate localDate = LocalDate.of(2023, 8, 5);
        final long timeId = 타임_생성();
        // when
        Long id = reservationService.createReservation("브라운", localDate, timeId);
        //then
        Reservation reservation = reservationRepository.findById(id);
        assertThat(reservation).isNotNull();
        assertThat(reservation.getReservationName()).isEqualTo("브라운");
        assertThat(reservation.getReservationDate()).isEqualTo(localDate);
        assertThat(reservation.getReservationTime().getId()).isEqualTo(timeId);
        assertThat(reservation.getReservationTime().getReservationTime()).isEqualTo(LocalTime.of(10, 0));
    }

    @Test
    void 예약_삭제() {
        //given
        final LocalDate localDate = LocalDate.of(2023, 8, 5);
        final long timeId = 타임_생성();
        final Long id = reservationService.createReservation("브라운", localDate, timeId);
        // when
        reservationService.deleteReservation(id);
        //then
        Reservation reservation = reservationRepository.findById(id);
        assertThat(reservation).isNull();
    }

    @Test
    void 예약_조회() {
        //given
        final LocalDate localDate = LocalDate.of(2023, 8, 5);
        final long timeId = 타임_생성();

        // when
        Long id = reservationService.createReservation("브라운", localDate, timeId);
        Reservation reservation = reservationService.findReservationById(id);
        //then
        assertThat(reservation).isNotNull();
        assertThat(reservation.getReservationName()).isEqualTo("브라운");
        assertThat(reservation.getReservationDate()).isEqualTo(localDate);
        assertThat(reservation.getReservationTime().getId()).isEqualTo(timeId);
        assertThat(reservation.getReservationTime().getReservationTime()).isEqualTo(LocalTime.of(10, 0));
    }

    
    long 타임_생성() {
        final long timeId = reservationTimeService.createTime(LocalTime.of(10, 0));
        return timeId;
    }
}
