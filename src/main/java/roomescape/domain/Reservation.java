package roomescape.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Reservation {
    private String name;
    private LocalDate date;
    private LocalTime time;

    public static Reservation create(String name, LocalDate date, LocalTime time) {
        validateName(name);
        validateDate(date);
        validateTime(time);

        return Reservation.builder()
                .name(name)
                .date(date)
                .time(time)
                .build();
    }

    private static void validateName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("[ERROR] 이름은 필수 값입니다.");
        }

        if (name.isBlank()) {
            throw new IllegalArgumentException("[ERROR] 이름 형식이 올바르지 않습니다.");
        }
    }

    private static void validateDate(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("[ERROR] 날짜는 필수 값입니다.");
        }
    }

    private static void validateTime(LocalTime time) {
        if (time == null) {
            throw new IllegalArgumentException("[ERROR] 시간은 필수 값입니다.");
        }
    }
}
