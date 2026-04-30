package roomescape.control.dto;

public record CreateReservationRequest(
        String name,
        String date,
        long timeId
) {
}
