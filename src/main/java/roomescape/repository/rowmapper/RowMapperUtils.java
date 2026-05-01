package roomescape.repository.rowmapper;

import java.time.LocalDate;
import java.time.LocalTime;
import org.springframework.jdbc.core.RowMapper;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

public class RowMapperUtils {

    public static final RowMapper<Reservation> RESERVATION_ROW_MAPPER = (resultSet, rowNum) -> {
        long timeId = resultSet.getLong("time_id");
        LocalTime startAt = resultSet.getObject("start_at", LocalTime.class);

        return Reservation.retrieve(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getObject("date", LocalDate.class),
                ReservationTime.retrieve(timeId, startAt)
        );
    };

    public static final RowMapper<ReservationTime> RESERVATION_TIME_ROW_MAPPER = (resultSet, rowNum) -> {
        long id = resultSet.getLong("id");
        LocalTime startAt = resultSet.getObject("start_at", LocalTime.class);

        return ReservationTime.retrieve(id, startAt);
    };
}
