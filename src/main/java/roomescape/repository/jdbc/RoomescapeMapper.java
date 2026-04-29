package roomescape.repository.jdbc;

import org.springframework.jdbc.core.RowMapper;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

public final class RoomescapeMapper {

    public static final RowMapper<Reservation> RESERVATION_ROW_MAPPER = (rs, rowNum) -> {
        ReservationTime time = new ReservationTime(
                rs.getLong("time_id"),
                rs.getTime("time_start").toLocalTime()
        );
        return new Reservation(
                rs.getLong("res_id"),
                rs.getString("res_name"),
                rs.getDate("res_date").toLocalDate(),
                time
        );
    };
    public static final RowMapper<ReservationTime> RESERVATION_TIME_MAPPER = (rs, rowNum) -> new ReservationTime(
            rs.getLong("id"),
            rs.getTime("start_at").toLocalTime()
    );

    private RoomescapeMapper() {}
}
