package roomescape.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.database.ReservationDatabaseImpl;
import roomescape.domain.Reservation;
import roomescape.domain.dto.ReservationReqDto;
import roomescape.fixture.ReservationFixture;

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
            int dummyFuturePlusDay1 = 1;
            LocalDateTime dummyFuture = LocalDateTime.now().plusDays(dummyFuturePlusDay1);
            LocalDateTime duplicateDateTime = dummyFuture;
            Reservation reservation1 = ReservationFixture.createReservation(dummyName1, duplicateDateTime);


            String dummyName2 = "pobi";
            int dummyFuturePlusDay2 = 2;
            Reservation reservation2 = ReservationFixture.createFutureReservationAfterDays(dummyName2, dummyFuturePlusDay2);

            List<Reservation> reservations = List.of(reservation1, reservation2);

            ReservationDatabaseImpl db = new ReservationDatabaseImpl();
            for (Reservation reservation : reservations) {
                db.add(reservation);
            }
            ReservationService service = new ReservationService(db);

            // when & then
            String dummyName3 = "jason";
            ReservationReqDto reqDto = ReservationFixture.createDTO(dummyName3, duplicateDateTime);

            Assertions.assertThatCode(
                    () -> service.add(reqDto)
            ).isInstanceOf(IllegalArgumentException.class);
        }
    }
}
