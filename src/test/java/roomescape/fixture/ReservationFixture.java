package roomescape.fixture;

import roomescape.domain.reservation.Reservation;
import roomescape.domain.reservation.dto.ReservationReqDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ReservationFixture {

    public static Reservation createReservation(String name, LocalDate date, LocalTime time) {
        return Reservation.of(name, date, time);
    }

    public static Reservation createReservation(String name, LocalDateTime dateTime) {
        return createReservation(name, dateTime.toLocalDate(), dateTime.toLocalTime());
    }

    public static Reservation createFutureReservationAfterDays(String name, int day) {
        LocalDateTime future = LocalDateTime.now().plusDays(day);
        return createReservation(name, future.toLocalDate(), future.toLocalTime());
    }

    public static Reservation createFutureReservationAfterOneDay(String name) {
        LocalDateTime future = LocalDateTime.now().plusDays(1);
        return createReservation(name, future);
    }

    public static Reservation createPastReservationBeforeOneDay(String name) {
        LocalDateTime future = LocalDateTime.now().minusDays(1);
        return createReservation(name, future);
    }


    public static ReservationReqDto createDTO(String name, LocalDate date, LocalTime time) {
        return new ReservationReqDto(name, date, time);
    }

    public static ReservationReqDto createDTO(String name, LocalDateTime dateTime) {
        return new ReservationReqDto(name, dateTime.toLocalDate(), dateTime.toLocalTime());
    }

    public static ReservationReqDto createFutureDTOAfterDays(String name, int day) {
        LocalDateTime future = LocalDateTime.now().plusDays(day);
        return createDTO(name, future);
    }

    public static ReservationReqDto createFutureDTOAfterOneDay(String name) {
        LocalDateTime future = LocalDateTime.now().plusDays(1);
        return createDTO(name, future);
    }

    public static ReservationReqDto createPastDTOBeforeOneDay(String name) {
        LocalDateTime future = LocalDateTime.now().minusDays(1);
        return createDTO(name, future);
    }
}
