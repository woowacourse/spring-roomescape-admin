package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ReservationCreationRequest {
    private final Name name;
    private final LocalDate date;
    private final LocalTime time;

    public ReservationCreationRequest(String name, LocalDate date, LocalTime time) {
        validateDateTime(date, time);
        this.name = new Name(name);
        this.date = date;
        this.time = time;
    }

    private void validateDateTime(LocalDate date, LocalTime time) {
        LocalDateTime requestedDateTime = LocalDateTime.of(date, time);
        if (isInValidDateTime(requestedDateTime)) {
            throw new IllegalArgumentException("과거의 시각은 예약할 수 없습니다.");
        }
    }

    private boolean isInValidDateTime(LocalDateTime requestedDateTime) {
        return requestedDateTime.isBefore(LocalDateTime.now());
    }

    public String getName() {
        return name.getName();
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }
}
