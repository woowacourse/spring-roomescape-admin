package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalTime;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;
import roomescape.dao.ReservationTimeRepository;
import roomescape.domain.FakeReservationTimeRepository;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequest;

class ReservationTimeServiceTest {

    private final ReservationTimeRepository reservationTimeRepository = new FakeReservationTimeRepository();

    @Test
    void 예약시간_생성_테스트() {
        ReservationTimeService reservationTimeService = new ReservationTimeService(reservationTimeRepository);
        ReservationTimeRequest request = new ReservationTimeRequest(LocalTime.of(15, 30));

        ReservationTime time = reservationTimeService.createReservationTime(request);

        assertThat(time).extracting(
                ReservationTime::getId,
                ReservationTime::getStartAt
        ).containsExactly(
                1L,
                LocalTime.of(15, 30)
        );
    }

    @Test
    void 아이디에_해당하는_예약시간_조회_테스트() {
        ReservationTimeService reservationTimeService = new ReservationTimeService(reservationTimeRepository);

        ReservationTimeRequest request = new ReservationTimeRequest(LocalTime.of(15, 30));
        reservationTimeService.createReservationTime(request);

        Long findId = 1L;

        assertThat(reservationTimeService.findReservationTime(findId)).extracting(
                ReservationTime::getId,
                ReservationTime::getStartAt
        ).containsExactly(
                1L,
                LocalTime.of(15, 30)
        );
    }

    @Test
    void 모든_예약시간_조회_테스트() {
        ReservationTimeService reservationTimeService = new ReservationTimeService(reservationTimeRepository);
        ReservationTimeRequest requestFirst = new ReservationTimeRequest(LocalTime.of(15, 30));
        ReservationTimeRequest requestSecond = new ReservationTimeRequest(LocalTime.of(16, 30));

        reservationTimeService.createReservationTime(requestFirst);
        reservationTimeService.createReservationTime(requestSecond);

        assertThat(reservationTimeService.findAllReservationTimes()).hasSize(2);
    }

    @Test
    void 아이디에_해당하는_예약시간_삭제_테스트() {
        ReservationTimeService reservationTimeService = new ReservationTimeService(reservationTimeRepository);

        ReservationTimeRequest request = new ReservationTimeRequest(LocalTime.of(15, 30));
        reservationTimeService.createReservationTime(request);

        Long deleteId = 1L;
        reservationTimeService.deleteReservationTime(deleteId);

        assertThatThrownBy(() -> reservationTimeService.findReservationTime(deleteId))
                .isInstanceOf(NoSuchElementException.class);
    }
}
