package roomescape.repository.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import roomescape.domain.Reservation;

public class ReservationRowMapper implements RowMapper<Reservation> {

    private static final ReservationRowMapper INSTANCE = new ReservationRowMapper();

    private ReservationRowMapper() {
    }

    @Override
    public Reservation mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Reservation(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("date"),
                TimeSlotRowMapper.getInstance().mapRow(rs, rowNum)
        );
    }

    public static ReservationRowMapper getInstance() {
        return INSTANCE;
    }
}
