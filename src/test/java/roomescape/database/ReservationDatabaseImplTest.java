package roomescape.database;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.domain.Reservation;

import java.time.LocalDateTime;
import java.util.List;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationDatabaseImplTest {

    @DisplayName("존재하지 않는 예약 ID로 조회하면 예외가 발생한다.")
    @Test
    void findById_throwsExceptionByNonExistentId() {
        // given
        String dummyName1 = "kali";
        LocalDateTime dummyDateTime1 = LocalDateTime.now().plusDays(1);


        String dummyName2 = "pobi";
        LocalDateTime dummyDateTime2 = LocalDateTime.now().plusDays(2);

        List<Reservation> reservations = List.of(
                Reservation.of(dummyName1, dummyDateTime1.toLocalDate(), dummyDateTime1.toLocalTime()),
                Reservation.of(dummyName2, dummyDateTime2.toLocalDate(), dummyDateTime2.toLocalTime())
        );

        ReservationDatabaseImpl db = new ReservationDatabaseImpl();
        for (Reservation reservation : reservations) {
            db.add(reservation);
        }

        // when & then
        Assertions.assertThatCode(
                () -> db.findById(Long.MAX_VALUE)
        ).isInstanceOf(IllegalArgumentException.class);
    }

}
