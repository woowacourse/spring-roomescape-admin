package roomescape.domain.entity;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
public class Reservation {
    private Long id;
    private String name;
    private LocalDate date;
    private ReservationTime time;

    public static Reservation create(Long id, String name, LocalDate date, ReservationTime time) {
        validateName(name);
        validateNull(date, time);

        return Reservation.builder()
                .id(id)
                .name(name)
                .date(date)
                .time(time)
                .build();
    }

    private static void validateNull(LocalDate date, ReservationTime time) {
        if (date == null) {
            throw new IllegalArgumentException("예약 날짜는 필수 값입니다.");
        }

        if (time == null) {
            throw new IllegalArgumentException("예약 시간은 필수 값입니다.");
        }
    }

    private static void validateName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("이름은 필수 값입니다.");
        }

        if (name.isBlank()) {
            throw new IllegalArgumentException("이름 형식이 올바르지 않습니다.");
        }
    }
}
