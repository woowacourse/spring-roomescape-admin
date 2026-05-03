package roomescape.reservation.domain;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import roomescape.reservationtime.domain.ReservationTime;

@Getter
public class Reservation {
    private Long id;
    private String name;
    private LocalDate date;
    private ReservationTime time;

    @Builder
    public Reservation(Long id, String name, LocalDate date, ReservationTime time) {
        validate(name, date, time);

        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    private void validate(String name, LocalDate date, ReservationTime time) {
        validateName(name);
        validateDate(date);
        validateReservationTime(time);
    }

    private void validateReservationTime(ReservationTime time) {
        if(time == null) {
            throw new IllegalArgumentException("[ERROR] 시간 정보는 필수입니다.");
        }
    }

    private void validateDate(LocalDate date) {
        if(date == null) {
            throw new IllegalArgumentException("[ERROR] 예약 날짜는 필수입니다.");
        }
        if (date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("[ERROR] 예약 날짜는 오늘 이후여야 합니다.");
        }
    }

    private void validateName(String name) {
        if(name == null || name.isBlank()) {
            throw new IllegalArgumentException("[ERROR] 이름은 필수입니다.");
        }
        if(name.length() < 2 || name.length() > 5) {
            throw new IllegalArgumentException("[ERROR] 이름은 2자 이상 5자 이하만 가능합니다.");
        }
    }
}
