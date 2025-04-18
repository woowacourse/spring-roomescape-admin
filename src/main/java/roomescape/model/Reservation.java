package roomescape.model;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record Reservation(
    Long id,

    @NotBlank(message = "[ERROR] 이름은 반드시 필요합니다.")
    String name,

    @NotNull(message = "[ERROR] 날짜는 반드시 필요합니다.")
    LocalDate date,

    @NotNull(message = "[ERROR] 시간은 반드시 필요합니다.")
    LocalTime time
) {

    public Reservation withId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("[ERROR] 전달받은 id는 null일 수 없습니다.");
        }
        return new Reservation(id, name, date, time);
    }
}
