package roomescape.infra.entity;

import org.springframework.jdbc.core.RowMapper;
import roomescape.business.domain.Customer;
import roomescape.business.domain.Reservation;
import roomescape.dto.request.ReservationCreateRequest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

public record ReservationEntity(
        Long id,
        String name,
        LocalDate date,
        ReservationTimeEntity time
) {
    private static final RowMapper<ReservationEntity> ROW_MAPPER = (rs, rowNum) -> {
        final long id = rs.getLong("reservation_id");
        final String name = rs.getString("name");
        final LocalDate date = rs.getDate("date").toLocalDate();
        final long timeId = rs.getLong("time_id");
        final LocalTime timeValue = rs.getTime("time_value").toLocalTime();
        return new ReservationEntity(id, name, date, new ReservationTimeEntity(timeId, timeValue));
    };

    public Map<String, ?> dataMap() {
        return Map.of(
                "name", name,
                "date", date,
                "time_id", time.id()
        );
    }

    public static RowMapper<ReservationEntity> getRowMapper() {
        return ROW_MAPPER;
    }

    public static ReservationEntity beforeSave(final ReservationCreateRequest request) {
        return new ReservationEntity(null, request.name(), request.date(), new ReservationTimeEntity(request.timeId(), null));
    }

    public Reservation toDomain() {
        return new Reservation(new Customer(name), date, time.toDomain());
    }

    public long timeId() {
        return time.id();
    }
}
