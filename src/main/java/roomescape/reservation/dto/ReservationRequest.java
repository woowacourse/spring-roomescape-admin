package roomescape.reservation.dto;

import lombok.Builder;

@Builder
public record ReservationRequest (String name, String date, String time) { }
