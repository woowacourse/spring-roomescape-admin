package roomescape.repository.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import roomescape.entity.ReservationTime;

@Component
public class ReservationTimeRowMapper implements RowMapper<ReservationTime> {

    @Override
    public ReservationTime mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new ReservationTime(
                rs.getLong("id"),
                rs.getString("start_at")
        );
    }
}
