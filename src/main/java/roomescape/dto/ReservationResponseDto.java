package roomescape.dto;

import java.util.concurrent.atomic.AtomicLong;

public record ReservationResponseDto(
        AtomicLong id,
        String name,
        String date,
        String time
) {
}
