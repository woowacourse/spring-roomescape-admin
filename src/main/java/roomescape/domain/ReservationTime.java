package roomescape.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalTime;

public class ReservationTime {
    private Long id;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime startAt;

    private void validate(LocalTime startAt) {
        if (startAt == null) {
            throw new IllegalArgumentException("예약 시간은 필수입니다.");
        }
    }

    public ReservationTime(Long id, LocalTime startAt) {
        validate(startAt);
        this.id = id;
        this.startAt = startAt;
    }

    public Long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }

    public static ReservationTime toEntity(ReservationTime reservationTime, Long id) {
        return new ReservationTime(id, reservationTime.getStartAt());
    }
}
