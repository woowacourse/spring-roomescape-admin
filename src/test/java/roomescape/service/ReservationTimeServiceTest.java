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

    private final int INSERT_INCREMENT = 1;
    private final LocalTime time = LocalTime.of(10, 0);
    private final ReservationTime reservationTime = new ReservationTime(time);

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

    @Nested
    class Deregister {

        @Test
        void 존재하는_ID로_삭제하면_저장소에서_제거된다() {
            // given
            List<ReservationTime> emptyReservationTimes = List.of();
            ReservationTimeService service = new ReservationTimeService(
                    new TestReservationTimeDao(emptyReservationTimes)
            );
            ReservationTime savedReservationTime = service.register(time);

            // when
            service.deregister(savedReservationTime.getId());

            // then
            Assertions.assertThat(service.readAll())
                    .isEmpty();
        }

        @Test
        void 존재하지_않는_ID로_삭제하면_예외가_발생한다() {
            // given
            ReservationTimeService service = new ReservationTimeService(
                    new TestReservationTimeDao(new ArrayList<>())
            );

            // when & then
            Assertions.assertThatThrownBy(() -> service.deregister(Long.MIN_VALUE))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("[ERROR] 존재하지 않는 id 이므로, ReservationTime을 삭제할 수 없습니다.");
        }

    }

    private ReservationTimeService generateReservationService(List<ReservationTime> times) {
        TestReservationTimeDao testReservationTimeDao = new TestReservationTimeDao(times);
        return new ReservationTimeService(testReservationTimeDao);
    }

}
