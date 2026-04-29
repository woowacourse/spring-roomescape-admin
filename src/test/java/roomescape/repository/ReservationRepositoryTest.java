package roomescape.repository;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.Reservation;

@SpringBootTest
@Transactional
public class ReservationRepositoryTest {
    @Autowired
    private ReservationRepository reservationRepository;

    @Test
    void 예약_생성_및_조회() {
        // given
        // 초단위 버리기
        LocalDateTime now = LocalDateTime.now().withSecond(0).withNano(0);
        Reservation reservation = new Reservation("브라운", now);

        // when
        Long id = reservationRepository.save(reservation);
        Reservation foundReservation = reservationRepository.findById(id);

        // then
        Assertions.assertNotNull(foundReservation);
        Assertions.assertEquals("브라운", foundReservation.getReservationName());
        Assertions.assertEquals(now, foundReservation.getReservationDateTime());
    }

     @Test
     void 예약_삭제() {
         // given
         LocalDateTime now = LocalDateTime.now();
         Reservation reservation = new Reservation("브라운", now);

         // when
         Long id = reservationRepository.save(reservation);
         reservationRepository.deleteById(id);

        // then
        Reservation foundReservation = reservationRepository.findById(id);
        Assertions.assertNull(foundReservation);
     }
}
