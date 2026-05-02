package roomescape.controller;

public record ReservationResponse(long id, String name, String date, ReservationTimeResponse time) {
}
