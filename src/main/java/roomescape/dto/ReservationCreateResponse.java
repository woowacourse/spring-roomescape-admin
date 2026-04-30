package roomescape.dto;

public record ReservationCreateResponse (Long id, String name, String date, TimeCreateResponse time) {
}
