package roomescape.dto.request;

import java.time.LocalDate;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

public record ReservationRequest(

        LocalDate date,
        String name,
        Long timeId
) {
    public ReservationRequest {
        if (date == null) {
            throw new IllegalArgumentException("[ERROR] 날짜는 필수입니다.");
        }
        if (name == null) {
            throw new IllegalArgumentException("[ERROR] 이름은 필수입니다.");
        }
        if (timeId == null) {
            throw new IllegalArgumentException("[ERROR] 시간은 필수입니다.");
        }
    }

    public Reservation fromEntity() {
        final ReservationTime reservationTime = new ReservationTime(timeId, null);
        return new Reservation(null, name, date, reservationTime);
    }
}
