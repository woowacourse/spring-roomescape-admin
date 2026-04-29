package roomescape.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import roomescape.domain.Reservation;
import roomescape.util.DateAndTimeConverter;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReservationMapper implements RowMapper<Reservation> {
    @Nullable
    @Override
    public Reservation mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return Reservation.create(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                DateAndTimeConverter.parseToDate(resultSet.getString("date")),
                DateAndTimeConverter.parseToTime(resultSet.getString("time"))
        );
    }
}
