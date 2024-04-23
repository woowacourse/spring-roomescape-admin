package roomescape;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationDto;

public class ReservationTestSetting {

    private ReservationTestSetting() {
    }

    public static Reservation createReservation() {
        String name = "ted";
        LocalDate date = LocalDate.parse("2024-01-01");
        LocalTime time = LocalTime.parse("00:00");
        return new Reservation(null, name, date, time);
    }

    public static ReservationDto createReservationDto() {
        return new ReservationDto(null, "ted", "2024-01-01", "00:00");
    }

    public static boolean isEqualsReservation(Reservation reservation1, Reservation reservation2) {
        if (reservation1 == reservation2) {
            return true;
        }
        if (reservation1 == null || reservation2 == null) {
            return false;
        }

        return Objects.equals(
                reservation1.getId(), reservation2.getId())
                && Objects.equals(reservation1.getName(), reservation2.getName())
                && Objects.equals(reservation1.getDate(), reservation2.getDate())
                && Objects.equals(reservation1.getTime(), reservation2.getTime()
        );
    }
}
