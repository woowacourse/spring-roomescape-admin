package roomescape.domain;

import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Reservation {
    private Long id;
    private String name;
    private LocalDate date;
    private ReservationTime time;

    public void assignId(Long id) {
        this.id = id;
    }
}
