package roomescape.infra.entity;

import roomescape.business.domain.ReservationTime;
import roomescape.dto.request.ReservationTimeCreateRequest;

import java.time.LocalTime;
import java.util.Map;

public class ReservationTimeEntity {

    private final Long id;
    private final LocalTime startAt;

    public ReservationTimeEntity(final Long id, final LocalTime startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public static ReservationTimeEntity beforeSave(final ReservationTimeCreateRequest request) {
        return new ReservationTimeEntity(null, request.startAt());
    }

    public Map<String, ?> dataMap() {
        return Map.of("start_at", startAt);
    }

    public ReservationTime toDomain() {
        return new ReservationTime(startAt);
    }

    public Long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
