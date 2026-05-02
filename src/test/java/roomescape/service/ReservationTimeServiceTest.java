package roomescape.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import roomescape.domain.ReservationTime;
import roomescape.fixture.TestReservationTimeDao;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ReservationTimeServiceTest {

    private static final int INSERT_INCREMENT = 1;
    private static final LocalTime time = LocalTime.of(10, 0);
    private static final ReservationTime reservationTime = new ReservationTime(time);

    @Nested
    class ReadAll {

        @Test
        void 등록된_예약시간이_없으면_빈_리스트를_반환한다() {
            // given
            List<ReservationTime> emptyReservationTimes = List.of();
            ReservationTimeService reservationTimeService = generateReservationService(emptyReservationTimes);

            // when
            List<ReservationTime> actual = reservationTimeService.readAll();

            // then
            Assertions.assertThat(actual)
                    .isEmpty();
        }

        @Test
        void 등록된_예약시간이_여러개이면_조회시_등록된_개수만큼_반환한다() {
            // given
            List<ReservationTime> reservationTimes = List.of(reservationTime, reservationTime);
            ReservationTimeService reservationTimeService = generateReservationService(reservationTimes);

            // when
            List<ReservationTime> actual = reservationTimeService.readAll();

            // then
            Assertions.assertThat(actual)
                    .hasSize(reservationTimes.size());
        }

    }

    @Nested
    class Register {

        @Test
        void 등록시_예약시간_데이터수가_1증가한다() {
            // given
            List<ReservationTime> emptyReservationTimes = List.of();
            ReservationTimeService service = new ReservationTimeService(
                    new TestReservationTimeDao(emptyReservationTimes)
            );

            // when
            service.register(time);

            // then
            Assertions.assertThat(service.readAll())
                    .hasSize(emptyReservationTimes.size() + INSERT_INCREMENT);
        }

        @Test
        void 예약시간을_등록하면_반환된_시작시간이_입력값과_일치한다() {
            // given
            List<ReservationTime> emptyReservationTimes = List.of();
            ReservationTimeService service = new ReservationTimeService(
                    new TestReservationTimeDao(emptyReservationTimes)
            );

            // when
            ReservationTime actual = service.register(time);

            // then
            Assertions.assertThat(actual.getStartAt())
                    .isEqualTo(time);
        }

    }

    private ReservationTimeService generateReservationService(List<ReservationTime> times) {
        TestReservationTimeDao testReservationTimeDao = new TestReservationTimeDao(times);
        return new ReservationTimeService(testReservationTimeDao);
    }

}
