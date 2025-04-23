package roomescape.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import roomescape.controller.dto.ReservationCreateRequest;
import roomescape.controller.dto.ReservationTimeCreateRequest;
import roomescape.service.dto.ReservationResponse;
import roomescape.service.dto.ReservationTimeResponse;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class ReservationServiceTest {

    private final ReservationService reservationService = new ReservationService(new FakeReservationRepository());
    private final ReservationTimeService reservationTimeService = new ReservationTimeService(new FakeReservationTimeRepository());

    private final static LocalDate RESERVATION_DATE = LocalDate.now().plusDays(1);

    public void addReservationTime() {
        reservationTimeService.addReservationTime(new ReservationTimeCreateRequest(LocalTime.MIN));
    }

    public void addReservation() {
        ReservationCreateRequest request = new ReservationCreateRequest("test1", RESERVATION_DATE, 0L);
        reservationService.addReservation(request);
    }

    @Test
    @DisplayName("예약 추가 실패 - 오늘 이전의 날짜")
    void reservationAddTest1() {
        //given, when
        addReservationTime();

        ReservationCreateRequest request = new ReservationCreateRequest("test1", LocalDate.now().minusDays(1), 0L);

        // then
        assertThatThrownBy(() -> reservationService.addReservation(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("예약은 미래 날짜여야 합니다.");
    }

    @Test
    @DisplayName("예약 추가 성공")
    void reservationAddTest2() {
        //given, when
        addReservationTime();
        ReservationCreateRequest request = new ReservationCreateRequest("test1", RESERVATION_DATE, 0L);
        reservationService.addReservation(request);

        // then
        assertThat(reservationService.getAllReservations().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("예약 조회 성공")
    void reservationFindTest1() {
        //given, when
        addReservationTime();
        addReservation();

        //then
        assertThat(reservationService.getReservationById(0L))
                .isEqualTo(new ReservationResponse(0L, "test1", RESERVATION_DATE, new ReservationTimeResponse(0L, LocalTime.MIN)));
    }

    @Test
    @DisplayName("예약 조회 실패")
    void reservationFindTest2() {
        //given, when
        addReservationTime();
        addReservation();

        //then
        assertThatThrownBy(() -> reservationService.getReservationById(1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 예약입니다.");
    }

    @Test
    @DisplayName("예약 삭제 성공")
    void reservationDeleteTest1() {
        //given, when
        addReservationTime();
        addReservation();
        reservationService.deleteReservationById(0L);

        //then
        assertThat(reservationService.getAllReservations().size()).isEqualTo(0);
    }

    @Test
    @DisplayName("예약 삭제 실패")
    void reservationDeleteTest2() {
        //given, when
        addReservationTime();
        addReservation();

        //then
        assertThatThrownBy(() -> reservationService.deleteReservationById(1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 예약입니다.");
    }
}