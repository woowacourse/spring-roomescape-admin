package roomescape.dto;

import java.util.List;

public class ReservationsResponse {

    private final List<ReservationResponse> reservationsResponse;

    private ReservationsResponse(List<ReservationResponse> reservationsDto) {
        this.reservationsResponse = reservationsDto;
    }

    public static ReservationsResponse from(List<ReservationResponse> reservations) {
        return new ReservationsResponse(reservations);
    }

    public List<ReservationResponse> getReservationsResponse() {
        return reservationsResponse;
    }
}
