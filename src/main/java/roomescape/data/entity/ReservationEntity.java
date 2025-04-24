package roomescape.data.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import org.springframework.jdbc.core.RowMapper;
import roomescape.business.domain.Reservation;

public record ReservationEntity(
        Long id,
        String name,
        LocalDate date,
        LocalTime time
) {

    private static final RowMapper<ReservationEntity> DEFAULT_ROW_MAPPER =
            (rs, rowNum) -> new ReservationEntity(
                    rs.getLong(1),
                    rs.getString(2),
                    rs.getDate(3).toLocalDate(),
                    rs.getTime(4).toLocalTime()
            );

    public static RowMapper<ReservationEntity> getDefaultRowMapper() {
        return DEFAULT_ROW_MAPPER;
    }

    public Reservation toDomain() {
        return new Reservation(id, name, date, time);
    }

    public static ReservationEntity from(final Reservation reservation) {
        return new ReservationEntity(
                reservation.getId(), reservation.getName(),
                reservation.getDate(), reservation.getTime()
        );
    }
}
