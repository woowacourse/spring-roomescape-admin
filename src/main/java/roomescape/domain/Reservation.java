package roomescape.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class Reservation {
    @NotNull(message = "[ERROR] id는 비어 있을 수 없습니다.")
    private final Long id;

    @NotBlank(message = "[ERROR] 이름은 비어 있을 수 없습니다.")
    private final String name;

    @NotNull(message = "[ERROR] 날짜는 비어 있을 수 없습니다.")
    private final LocalDate date;

    @NotNull(message = "[ERROR] 예약 시간은 비어 있을 수 없습니다.")
    private final ReservationTime time;

    public Reservation(Long id, String name, LocalDate date, ReservationTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public ReservationTime getTime() {
        return time;
    }
}
