package roomescape.dto;

public record ReservationResponse(long id, String name, String date, ReservationTimeResponse time) {
}
