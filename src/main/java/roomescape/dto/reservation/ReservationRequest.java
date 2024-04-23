package roomescape.dto.reservation;

import java.time.LocalDate;

public record ReservationRequest(String name, LocalDate date, long timeId) { // timeid Long -> null 체크
}
