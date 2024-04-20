package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

import java.util.List;

@Repository
public class H2ReservationRepositoryImpl implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    public H2ReservationRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Reservation> findAll() {
        String sql = "select * from reservation";

        return jdbcTemplate.query(sql, (resultSet, rowNum) -> new Reservation(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getDate("date").toLocalDate(),
                resultSet.getTime("time").toLocalTime()
        ));
    }

    @Override
    public Reservation save(Reservation reservation) {
        String sql = "insert into reservation values (?, ?, ?, ?)";

        jdbcTemplate.update(sql, reservation.getId(), reservation.getName(),
                reservation.getDate(), reservation.getTime());

        return reservation;
    }

    @Override
    public void deleteById(long id) {
        String sql = "delete from reservation where id = ?";

        jdbcTemplate.update(sql, id);
    }
}
