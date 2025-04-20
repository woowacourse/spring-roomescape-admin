package roomescape.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.database.ReservationDatabaseImpl;
import roomescape.domain.Reservation;
import roomescape.domain.dto.ReservationReqDto;

import java.time.LocalDateTime;
import java.util.List;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationServiceTest {

    @Nested
    @DisplayName("예약 추가하기 기능")
    class add {

        @DisplayName("이미 같은 시간에 예약이 존재한다면 예외 처리한다.")
        @Test
        void add_failure_byDuplicateDateTime() {
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
            ReservationService service = new ReservationService(db);

            // when & then
            String dummyName3 = "jason";
            ReservationReqDto reqDto = new ReservationReqDto(dummyName3, dummyDateTime1.toLocalDate(), dummyDateTime1.toLocalTime());

            Assertions.assertThatCode(
                    () -> service.add(reqDto)
            ).isInstanceOf(IllegalArgumentException.class);
        }
    }
}
