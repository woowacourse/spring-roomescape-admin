package roomescape.dto;

import roomescape.domain.ReservationCommand;

public record AddReservationRequest(String date, String name, long timeId) {
    public ReservationCommand to() {
        return new ReservationCommand(date, name, timeId);
    }
}
