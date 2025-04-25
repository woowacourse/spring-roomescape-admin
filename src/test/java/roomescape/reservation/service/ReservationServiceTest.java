package roomescape.reservation.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import roomescape.reservation.database.ReservationRepositoryImpl;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.domain.dto.ReservationReqDto;
import roomescape.globalException.CustomException;
import roomescape.reservation.fixture.ReservationFixture;
import roomescape.reservationTime.repository.ReservationTimeRepositoryImpl;

import java.time.LocalDateTime;

@JdbcTest
@Import({ReservationService.class, ReservationRepositoryImpl.class, ReservationTimeRepositoryImpl.class})
class ReservationServiceTest {

    @Autowired
    private ReservationService service;
    @Autowired
    private ReservationRepositoryImpl db;

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

            db.add(reservation1);
            db.add(reservation2);

            // when & then
            String dummyName3 = "jason";
            ReservationReqDto reqDto = ReservationFixture.createDTO(dummyName3, duplicateDateTime);

            Assertions.assertThatThrownBy(
                    () -> service.add(reqDto)
            ).isInstanceOf(CustomException.class);
        }
    }
}
