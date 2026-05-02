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
        validateStartAt(startAt);

        return ReservationTime.builder()
                .id(id)
                .startAt(startAt)
                .build();
    }

    private static void validateStartAt(LocalTime startAt) {
        if (startAt == null) {
            throw new IllegalArgumentException("예약 시간은 필수 값입니다.");
        }
    }
}
