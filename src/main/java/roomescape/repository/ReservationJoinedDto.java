package roomescape.repository;

import java.time.LocalDate;

public record ReservationJoinedDto(long id, String name, LocalDate date, long timeId, String startAt) {
}
