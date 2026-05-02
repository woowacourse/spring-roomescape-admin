package roomescape.repository;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationJoinedDto(long id, String name, LocalDate date, long timeId, LocalTime startAt) {
}
