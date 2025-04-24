package roomescape.domain.reservation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationReqDto(String name,  @JsonFormat(pattern = "yyyy-MM-dd") LocalDate date, @JsonFormat(pattern = "HH:mm:ss") LocalTime time) {

    public ReservationReqDto {
        time = time.withNano(0);
    }
}
