package roomescape.domain.entity;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;

@Getter
@Builder
public class ReservationTime {
    private Long id;
    private LocalTime startAt;

    public static ReservationTime create(Long id, LocalTime startAt) {
        return ReservationTime.builder()
                .id(id)
                .startAt(startAt)
                .build();
    }

    public boolean isSameTime(LocalTime time){
        return startAt.equals(time);
    }
}
