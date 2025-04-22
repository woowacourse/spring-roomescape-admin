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
    public static final String TABLE_NAME = "reservation_time";

    public static final String ID_COL_NAME = "id";
    public static final String START_AT_COL_NAME = "start_at";

    public static final RowMapper<ReservationTimeEntity> ROW_MAPPER = (rs, rowNum) -> {
        final long id = rs.getLong(ID_COL_NAME);
        final LocalTime startTime = rs.getTime(START_AT_COL_NAME).toLocalTime();
        return new ReservationTimeEntity(id, startTime);
    };

    public static ReservationTimeEntity beforeSave(final ReservationTimeCreateRequest request) {
        return new ReservationTimeEntity(null, request.startAt());
    }

    public Map<String, ?> toDataMap() {
        return Map.of(START_AT_COL_NAME, startAt);
    }

    public ReservationTime toDomain() {
        return new ReservationTime(startAt);
    }
}
