package roomescape.reservation.dao;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.Time.domain.Time;
import roomescape.reservation.domain.Reservation;

@Repository
public class ReservationJdbcDao implements ReservationDao {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public ReservationJdbcDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    public void save(Reservation reservation) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("name", reservation.getName())
                .addValue("date", reservation.getDate())
                .addValue("time_id", reservation.getReservationTime().getId());

        long id = jdbcInsert.executeAndReturnKey(sqlParameterSource).longValue();
        reservation.setId(id);
    }

    public List<Reservation> findAll() {
        String findAllReservationSql = "SELECT r.id, r.name, r.`date`, t.id AS timeId, t.start_at "
                + "FROM reservation r "
                + "INNER JOIN reservation_time t "
                + "ON r.time_id = t.id "
                + "ORDER BY r.date ASC , t.`start_at` ASC";
        return jdbcTemplate.query(findAllReservationSql, (resultSet, rowNum) -> new Reservation(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getDate("date").toLocalDate(),
                new Time(resultSet.getLong("timeId"),
                        resultSet.getTime("start_at").toLocalTime())));
    }

    public void deleteById(long reservationId) {
        String deleteReservationSql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(deleteReservationSql, reservationId);
    }
}
