package roomescape.domain.repository;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

@Repository
//@Primary
public class H2ReservationRepository implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    public H2ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Reservation> findAllReservations() {
        String sql = "SELECT * FROM reservation";
        RowMapper<Reservation> rowMapper = (resultSet, rowNum) -> {
            Reservation reservation = new Reservation(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getDate("date").toLocalDate(),
                    resultSet.getTime("time").toLocalTime()
            );
            return reservation;
        };

        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public Reservation createReservation(Reservation reservation) {
        return null;
    }

    @Override
    public boolean deleteReservation(Long id) {
        return false;
    }
}
