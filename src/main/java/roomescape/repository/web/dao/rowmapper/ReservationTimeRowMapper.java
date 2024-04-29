package roomescape.repository.web.dao.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import roomescape.domain.reservation.ReservationTime;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;

@Component
public class ReservationTimeRowMapper implements RowMapper<ReservationTime> {
    @Override
    public ReservationTime mapRow(ResultSet rs, int rowNum) {
        try {
            return new ReservationTime(rs.getLong("id"), rs.getObject("start_at", LocalTime.class));
        } catch (SQLException e) {
            throw new RuntimeException("reservation_time 테이블 접근 오류", e);
        }
    }
}
