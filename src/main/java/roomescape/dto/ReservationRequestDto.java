package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.domain.Reservation;

public record ReservationRequestDto(
        String name,
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate date,
        @JsonFormat(pattern = "HH:mm")
        LocalTime time
) {
    public Reservation toEntity(Long generatedId) {
        return Reservation.builder()
                .id(generatedId)
                .name(this.name)
                .date(this.date)
                .time(this.time)
                .build();
    }
}
