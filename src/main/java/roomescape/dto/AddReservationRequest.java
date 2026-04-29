package roomescape.dto;

import roomescape.domain.ReservationCommand;

public record AddReservationRequest(String name, String date, long timeId) {
    public ReservationCommand to() {
        return new ReservationCommand(name, date, timeId);
    }
}
