package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationRequestLegacy(String name, LocalDate date, LocalTime time) {}
