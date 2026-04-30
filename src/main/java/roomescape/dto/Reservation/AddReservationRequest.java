package roomescape.dto.Reservation;

import roomescape.domain.Reservation.ReservationCommand;

public record AddReservationRequest(String name, String date, long timeId) {
    public ReservationCommand to() {
        return new ReservationCommand(name, date, timeId);
    }
}
