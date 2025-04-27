package roomescape.infra.entity;

import org.springframework.jdbc.core.RowMapper;
import roomescape.business.domain.Customer;
import roomescape.business.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

public record ReservationEntity(
        Long id,
        String name,
        LocalDate date,
        ReservationTimeEntity time
) {
    public static final String TABLE_NAME = "reservation";

    public static final String ID_COL_NAME = "id";
    public static final String NAME_COL_NAME = "name";
    public static final String DATE_COL_NAME = "date";
    public static final String TIME_ID_COL_NAME = "time_id";
    public static final String TIME_VALUE_COL_NAME = "time_value";

    public static final RowMapper<ReservationEntity> ROW_MAPPER = (rs, rowNum) -> {
        final long id = rs.getLong(ID_COL_NAME);
        final String name = rs.getString(NAME_COL_NAME);
        final LocalDate date = rs.getDate(DATE_COL_NAME).toLocalDate();
        final long timeId = rs.getLong(TIME_ID_COL_NAME);
        final LocalTime timeValue = rs.getTime(TIME_VALUE_COL_NAME).toLocalTime();
        return new ReservationEntity(id, name, date, new ReservationTimeEntity(timeId, timeValue));
    };

    public static ReservationEntity beforeSave(final Reservation reservation, final long timeId) {
        return new ReservationEntity(null, reservation.customerName(), reservation.date(), new ReservationTimeEntity(timeId, null));
    }

    public Map<String, ?> toDataMap() {
        return Map.of(
                NAME_COL_NAME, name,
                DATE_COL_NAME, date,
                TIME_ID_COL_NAME, time.id()
        );
    }

    public Reservation toDomain() {
        return new Reservation(new Customer(name), date, time.toDomain());
    }

    public long timeId() {
        return time.id();
    }
}
