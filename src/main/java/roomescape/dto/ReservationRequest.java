package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.model.Reservation;

public record ReservationRequest(Long id,
                                 String name,
                                 @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul") LocalDate date,
                                 @JsonFormat(pattern = "HH:mm", timezone = "Asia/Seoul") LocalTime time) {

    public Reservation toEntity(){
        return new Reservation(name,date,time);
    }
}
