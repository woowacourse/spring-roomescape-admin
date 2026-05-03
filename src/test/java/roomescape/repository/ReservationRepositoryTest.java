package roomescape.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Transactional
public class ReservationRepositoryTest {
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ReservationTimeRepository reservationTimeRepository;

    @Test
    void 예약_생성_및_조회() {
        // given
        LocalDate now = LocalDate.now();
        ReservationTime reservationTime = createReservationTime(LocalTime.of(10, 0));
        Reservation reservation = new Reservation("브라운", now, reservationTime);

        // when
        Long id = reservationRepository.save(reservation);
        Reservation foundReservation = reservationRepository.findById(id);

        // then
        Assertions.assertNotNull(foundReservation);
        Assertions.assertEquals("브라운", foundReservation.getReservationName());
        Assertions.assertEquals(now, foundReservation.getReservationDate());
        Assertions.assertEquals(reservationTime.getReservationTime(), foundReservation.getReservationTime().getReservationTime());
    }

     @Test
     void 예약_삭제() {
         // given
         LocalDate now = LocalDate.now();
         ReservationTime reservationTime = createReservationTime(LocalTime.of(10, 0));
         Reservation reservation = new Reservation("브라운", now, reservationTime);

         // when
         Long id = reservationRepository.save(reservation);
         reservationRepository.deleteById(id);

        // then
        Reservation foundReservation = reservationRepository.findById(id);
        Assertions.assertNull(foundReservation);
     }

     private ReservationTime createReservationTime(LocalTime reservationTime) {
         ReservationTime time = new ReservationTime(reservationTime);
         long id = reservationTimeRepository.save(time);

         return reservationTimeRepository.findById(id);
     }
}
