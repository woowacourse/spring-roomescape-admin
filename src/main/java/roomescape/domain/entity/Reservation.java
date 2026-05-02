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

        return Reservation.builder()
                .id(id)
                .name(name)
                .date(date)
                .time(time)
                .build();
    }

    public boolean isSameDate(LocalDate date) {
        return this.date.equals(date);
    }

    private static void validateName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("[ERROR] 이름은 필수 값입니다.");
        }

        if (name.isBlank()) {
            throw new IllegalArgumentException("[ERROR] 이름 형식이 올바르지 않습니다.");
        }
    }
}
