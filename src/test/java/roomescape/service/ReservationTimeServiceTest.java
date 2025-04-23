package roomescape.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import roomescape.controller.dto.ReservationTimeCreateRequest;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ReservationTimeServiceTest {

    private final ReservationTimeService reservationTimeService = new ReservationTimeService(new FakeReservationTimeRepository());

    public void addReservationTime() {
        reservationTimeService.addReservationTime(new ReservationTimeCreateRequest(LocalTime.MIN));
    }

    @Test
    @DisplayName("예약 시간 추가 테스트")
    public void addReservationTimeTest() {
        //given, when
        reservationTimeService.addReservationTime(new ReservationTimeCreateRequest(LocalTime.MAX));

        //then
        assertThat(reservationTimeService.getAllReservationTimes().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("예약 시간 전체 조회 테스트")
    public void getAllReservationTimesTest() {
        //given, when
        addReservationTime();
        reservationTimeService.addReservationTime(new ReservationTimeCreateRequest(LocalTime.MAX));

        //then
        assertThat(reservationTimeService.getAllReservationTimes().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("예약 시간 삭제 테스트")
    public void getReservationTimeTest() {
        //given, when
        addReservationTime();

        //then
        assertThat(reservationTimeService.getAllReservationTimes().size()).isEqualTo(1);
        reservationTimeService.deleteReservationTimeById(0L);
        assertThat(reservationTimeService.getAllReservationTimes().size()).isEqualTo(0);
    }
}
