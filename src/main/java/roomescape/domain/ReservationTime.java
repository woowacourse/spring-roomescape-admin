package roomescape.domain;

import java.util.Objects;
import org.springframework.util.StringUtils;
import roomescape.exception.InvalidReservationTimeException;

public class ReservationTime {

    private final Long id;
    private final String startAt;

    private ReservationTime(
            Long id,
            String startAt
    ) {
        this.id = id;
        this.startAt = startAt;
    }

    public Long getId() {
        return id;
    }

    public String getStartAt() {
        return startAt;
    }

    public static ReservationTime create(
            String startAt
    ) {
        validateStartAt(startAt);

        return new ReservationTime(
                null,
                startAt
        );
    }

    public static ReservationTime retrieve(
            long id,
            String startAt
    ) {
        return new ReservationTime(
                id,
                startAt
        );
    }

    public ReservationTime with(long id) {
        return new ReservationTime(
                id,
                this.startAt
        );
    }

    private static void validateStartAt(String startAt) {
        if (!StringUtils.hasText(startAt)) {
            throw new InvalidReservationTimeException("예약 시간엔 시간 정보가 존재해야 합니다.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ReservationTime that = (ReservationTime) o;
        return Objects.equals(id, that.id) && Objects.equals(startAt, that.startAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startAt);
    }
}
