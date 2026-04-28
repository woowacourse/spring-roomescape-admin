package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class RequestDto {

    @Getter
    @NoArgsConstructor
    public static class ReservationCreateDto {
        private String name;
        private LocalDate date;
        private LocalTime time;
    }
}
