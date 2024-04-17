package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.domain.Reservation;

public class ReservationRequest {

    private final LocalDate date;
    private final String name;
    private final LocalTime time;

    public ReservationRequest(LocalDate date, String name, LocalTime time) {
        this.date = date;
        this.name = name;
        this.time = time;
    }

    public static Reservation of(Long id, ReservationRequest request) {
        validate(request);
        return new Reservation(id, request.name, request.date, request.time);
    }

    private static void validate(ReservationRequest request) {
        if (request.name.isEmpty()) {
            throw new IllegalArgumentException("예약자명은 필수입니다.");
        }
        if (request.date == null) {
            throw new IllegalArgumentException("예약 날짜는 필수입니다.");
        }
        if (request.time == null) {
            throw new IllegalArgumentException("예약 시간은 필수입니다.");
        }
    }

    public LocalDate getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public LocalTime getTime() {
        return time;
    }
}
