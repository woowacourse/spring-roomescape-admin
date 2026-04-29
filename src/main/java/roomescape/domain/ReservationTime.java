package roomescape.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ReservationTime {
    @Setter
    private Long id;
    private LocalDateTime startAt;

    public static ReservationTime create(Long id, LocalDateTime startAt){
        return ReservationTime.builder()
                .id(id)
                .startAt(startAt)
                .build();
    }
}
