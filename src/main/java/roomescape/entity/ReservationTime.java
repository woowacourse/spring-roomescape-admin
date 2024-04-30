package roomescape.entity;

import java.time.LocalTime;

public class ReservationTime {

    private final long id;
    private final LocalTime startAt;

    public ReservationTime(long id, LocalTime startAt) {
        validateIdRange(id);
        this.id = id;
        this.startAt = startAt;
    }

    private void validateIdRange(long id) {
        if (id <= 0) {
            throw new RuntimeException("[ERROR] 데이터베이스 오류입니다. 관리자에게 문의해주세요.");
        }
    }

    public long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
