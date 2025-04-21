package roomescape.dto.request;

import java.time.LocalTime;
import java.util.Map;

public record ReservationTimeCreateRequest(
        LocalTime startAt
) {
    public Map<String, ?> dataMap() {
        return Map.of("start_at", startAt);
    }
}
