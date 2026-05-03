package roomescape.reservationtime.domain;

import java.time.LocalTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReservationTime {
    private static final LocalTime OPEN_TIME = LocalTime.of(10, 0);
    private static final LocalTime CLOSE_TIME = LocalTime.of(22, 0);

    private Long id;
    private LocalTime startAt;

    @Builder
    public ReservationTime(Long id, LocalTime startAt) {
        validate(startAt);
        this.id = id;
        this.startAt = startAt;
    }

    private void validate(LocalTime startAt) {
        if(startAt == null) {
            throw new IllegalArgumentException("[ERROR] 시간 정보는 필수입니다.");
        }
        if(startAt.isBefore(OPEN_TIME)) {
            throw new IllegalArgumentException("[ERROR] 영업 시작 시간 이전입니다.");
        }
        if(startAt.isAfter(CLOSE_TIME)) {
            throw new IllegalArgumentException("[ERROR] 영업 종료 시간 이후입니다.");
        }
    }
}
