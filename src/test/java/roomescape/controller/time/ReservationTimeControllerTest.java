package roomescape.controller.time;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.LongStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import roomescape.domain.time.ReservationTime;
import roomescape.dto.time.ReservationTimeCreateRequest;
import roomescape.dto.time.ReservationTimeResponse;
import roomescape.repository.time.FakeReservationTimeDao;

class ReservationTimeControllerTest {

    private FakeReservationTimeDao reservationTimeDao;
    private ReservationTimeController controller;

    private static List<ReservationTime> createReservationTimes(int count) {
        return LongStream.rangeClosed(1, count)
            .mapToObj(id -> new ReservationTime(id, LocalTime.now()))
            .toList();
    }

    @BeforeEach
    void setUp() {
        reservationTimeDao = new FakeReservationTimeDao(new ArrayList<>());
        controller = new ReservationTimeController(reservationTimeDao);
    }

    @Test
    void 예약_시간을_하나_생성한다() {
        LocalTime startAt = LocalTime.now();

        ReservationTimeCreateRequest request = new ReservationTimeCreateRequest(startAt);

        controller.createReservationTime(request);
        ReservationTime reservationTime = new ReservationTime(1L, startAt);

        assertThat(reservationTimeDao.findAll()).containsExactly(reservationTime);
    }

    @Test
    void 모든_예약_시간_정보를_반환한다() {
        List<ReservationTime> reservationTimes = createReservationTimes(2);

        reservationTimeDao.addAll(reservationTimes);

        List<ReservationTimeResponse> expected = reservationTimes.stream()
            .map(ReservationTimeResponse::from)
            .toList();
        List<ReservationTimeResponse> response = controller.getReservationTimes().getBody();

        assertThat(response).containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    void 예약_시간을_삭제한다() {
        ReservationTime reservationTime = createReservationTimes(1).getFirst();
        reservationTimeDao.save(reservationTime);

        controller.deleteReservationTime(reservationTime.getId());

        assertThat(reservationTimeDao.findAll()).isEmpty();
    }
}
