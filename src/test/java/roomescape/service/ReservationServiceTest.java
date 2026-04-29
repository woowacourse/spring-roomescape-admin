package roomescape.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import roomescape.dto.ReservationCreateReqDto;
import roomescape.dto.ReservationResDto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

class ReservationServiceTest {

    ReservationService reservationService = new ReservationService();

    @Test
    void 예약_생성_정상() {
        // given
        String name = "브라운";
        LocalDate date = LocalDate.of(2023, 7, 4);
        LocalTime time = LocalTime.of(15, 40);

        // when
        ReservationResDto reservation = reservationService.createReservation(new ReservationCreateReqDto(name, date, time));
        ReservationResDto findReservation = reservationService.getReservationById(reservation.getId());

        // then
        Assertions.assertEquals(name, findReservation.getName());
        Assertions.assertEquals(date, findReservation.getDate());
        Assertions.assertEquals(time, findReservation.getTime());
    }

    @Test
    void 예약_단일_조회_정상() {
        // given
        String name = "브라운";
        LocalDate date = LocalDate.of(2023, 7, 4);
        LocalTime time = LocalTime.of(15, 40);
        ReservationResDto reservation = reservationService.createReservation(new ReservationCreateReqDto(name, date, time));

        // when
        ReservationResDto findReservation = reservationService.getReservationById(reservation.getId());

        // then
        Assertions.assertEquals(name, findReservation.getName());
        Assertions.assertEquals(date, findReservation.getDate());
        Assertions.assertEquals(time, findReservation.getTime());
    }

    @Test
    void 예약_단일_조회_에러() {
        // given
        String name = null;
        LocalDate date = LocalDate.of(2023, 7, 4);
        LocalTime time = LocalTime.of(15, 40);

        // when && then
        Assertions.assertThrows(IllegalArgumentException.class, () -> reservationService.createReservation(new ReservationCreateReqDto(name, date, time)));
    }

    @Test
    void 예약_목록_조회_정상() {
        // given
        String name = "브라운";
        LocalDate date = LocalDate.of(2023, 5, 3);
        LocalTime time = LocalTime.of(15, 20);
        reservationService.createReservation(new ReservationCreateReqDto(name, date, time));

        String name2 = "포비";
        LocalDate date2 = LocalDate.of(2025, 7, 4);
        LocalTime time2 = LocalTime.of(17, 40);
        reservationService.createReservation(new ReservationCreateReqDto(name2, date2, time2));

        // when
        List<ReservationResDto> reservations = reservationService.getReservations();

        // then
        Assertions.assertEquals(2, reservations.size());

        Assertions.assertEquals(name, reservations.get(0).getName());
        Assertions.assertEquals(name2, reservations.get(1).getName());
    }
}
