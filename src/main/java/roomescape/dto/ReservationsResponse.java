package roomescape.dto;

import roomescape.model.Reservation;

import java.util.ArrayList;
import java.util.List;

public class ReservationsResponse {

    private final List<ReservationResponse> reservationsResponse;

    private ReservationsResponse(List<ReservationResponse> reservationsDto) {
        this.reservationsResponse = reservationsDto;
    }

    public static ReservationsResponse from(List<Reservation> reservations) {
        List<ReservationResponse> responses = new ArrayList<>();

        for (Reservation reservation : reservations) {
            responses.add(ReservationResponse.from(reservation));
        }

        return new ReservationsResponse(responses);
    }

    public List<ReservationResponse> getReservationsResponse() {
        return reservationsResponse;
    }
}
