package roomescape.infra.entity;

import org.springframework.jdbc.core.RowMapper;
import roomescape.business.domain.ReservationTime;
import roomescape.dto.request.ReservationTimeCreateRequest;

import java.time.LocalTime;
import java.util.Map;

public record ReservationTimeEntity(
        Long id,
        LocalTime startAt
) {
    private static final RowMapper<ReservationTimeEntity> ROW_MAPPER = (rs, rowNum) -> {
        final long id = rs.getLong("id");
        final LocalTime startTime = rs.getTime("start_at").toLocalTime();
        return new ReservationTimeEntity(id, startTime);
    };

    public Map<String, ?> dataMap() {
        return Map.of("start_at", startAt);
    }

    public static RowMapper<ReservationTimeEntity> getRowMapper() {
        return ROW_MAPPER;
    }

    public static ReservationTimeEntity beforeSave(final ReservationTimeCreateRequest request) {
        return new ReservationTimeEntity(null, request.startAt());
    }

    public ReservationTime toDomain() {
        return new ReservationTime(startAt);
    }
}
