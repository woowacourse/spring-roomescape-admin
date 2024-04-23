package roomescape.dto;

public record CreateReservationRequest(
        String name,
        String date,
        long timeId) {
}
