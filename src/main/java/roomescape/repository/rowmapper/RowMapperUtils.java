package roomescape.repository.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

public class RowMapperUtils {

    public static final RowMapper<Reservation> RESERVATION_ROW_MAPPER = (resultSet, rowNum) -> {
        long timeId = resultSet.getLong("time_id");
        String startAt = resultSet.getString("start_at");

        return Reservation.retrieve(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("date"),
                ReservationTime.retrieve(timeId, startAt)
        );
    };

    public static final RowMapper<ReservationTime> RESERVATION_TIME_ROW_MAPPER = (resultSet, rowNum) -> {
        long id = resultSet.getLong("id");
        String startAt = resultSet.getString("start_at");

        return ReservationTime.retrieve(id, startAt);
    };
}
