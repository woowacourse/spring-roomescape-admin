package roomescape.repository.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import roomescape.domain.TimeSlot;

public class TimeSlotRowMapper implements RowMapper<TimeSlot> {

    private static final TimeSlotRowMapper INSTANCE = new TimeSlotRowMapper();

    private TimeSlotRowMapper() {
    }

    public static TimeSlotRowMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public TimeSlot mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new TimeSlot(
                rs.getLong("id"),
                rs.getString("start_at")
        );
    }
}
