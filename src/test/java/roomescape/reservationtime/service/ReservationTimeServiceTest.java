package roomescape.reservationtime.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import java.util.List;
import org.springframework.boot.test.context.SpringBootTest;
import roomescape.reservationtime.ReservationTime;
import roomescape.reservationtime.dto.request.ReservationTimeRequest;
import roomescape.reservationtime.dto.response.ReservationTimeResponse;
import roomescape.reservationtime.stub.StubReservationTimeDao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class ReservationTimeServiceTest {

    private StubReservationTimeDao stubReservationTimeDao;
    private ReservationTimeService reservationTimeService;

    private final ReservationTime fakeReservationTime1 = new ReservationTime(1L, LocalTime.of(10, 0));
    private final ReservationTime fakeReservationTime2 = new ReservationTime(2L, LocalTime.of(11, 0));


    @BeforeEach
    void setUp() {
        stubReservationTimeDao = new StubReservationTimeDao(fakeReservationTime1, fakeReservationTime2);
        reservationTimeService = new ReservationTimeService(stubReservationTimeDao);
    }

    @Test
    void 예약_시간을_조회할_수_있다() {
        // given & when
        List<ReservationTimeResponse> all = reservationTimeService.findAll();

        // then
        assertThat(all.size()).isEqualTo(2);
        assertThat(all.get(0).startAt()).isEqualTo(LocalTime.of(10, 0));
        assertThat(all.get(1).startAt()).isEqualTo(LocalTime.of(11, 0));
    }

    @Test
    void 예약_시간을_추가할_수_있다() {
        // given & when
        ReservationTimeRequest newTime = new ReservationTimeRequest(LocalTime.of(2, 0));
        reservationTimeService.create(newTime);
        List<ReservationTimeResponse> all = reservationTimeService.findAll();

        // then
        assertThat(all.size()).isEqualTo(3);
        assertThat(all.getLast().startAt()).isEqualTo(LocalTime.of(2, 0));
    }

    @Test
    void 예약_시간을_삭제할_수_있다() {
        // given & when
        reservationTimeService.delete(fakeReservationTime1.getId());
        List<ReservationTimeResponse> all = reservationTimeService.findAll();

        // then
        assertThat(all.size()).isEqualTo(1);
        assertThat(all.getFirst().startAt()).isEqualTo(LocalTime.of(11, 0));
    }
}
