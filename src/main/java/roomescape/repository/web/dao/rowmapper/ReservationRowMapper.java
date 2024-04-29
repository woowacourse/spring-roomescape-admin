package roomescape.repository.web.dao.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import roomescape.domain.reservation.Name;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.reservation.ReservationDate;
import roomescape.domain.reservation.ReservationTime;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

@Component
public class ReservationRowMapper implements RowMapper<Reservation> {

    @Override
    public Reservation mapRow(ResultSet rs, int rowNum) {
        try {
            Long reservationId = rs.getLong("reservation_id");
            String name = rs.getString("name");
            ReservationDate date = new ReservationDate(rs.getObject("reservation_date", LocalDate.class));
            ReservationTime time = new ReservationTime(rs.getLong("time_id"), rs.getObject("time_value", LocalTime.class));
            return new Reservation(reservationId, new Name(name), date, time);
        } catch (SQLException e) {
            throw new RuntimeException("reservation table 접근 오류", e);
        }
    }
}
