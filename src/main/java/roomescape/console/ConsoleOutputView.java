package roomescape.console;

import roomescape.controller.dto.response.ReservationResponse;
import roomescape.controller.dto.response.ReservationTimeResponse;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

import java.util.List;

public class ConsoleOutputView {

    public static void printTime(ReservationTimeResponse reservationTime) {
        System.out.println(reservationTime);
    }

    public static void printTimeList(List<ReservationTime> reservationTimes) {
        System.out.println(reservationTimes);
    }

    public static void printReservation(ReservationResponse reservation) {
        System.out.println(reservation);
    }

    public static void printReservationList(List<Reservation> reservations) {
        System.out.println(reservations);
    }

    public static void printErrorMessage(String message) {
        System.out.println("[Error] " + message);

    }
}
