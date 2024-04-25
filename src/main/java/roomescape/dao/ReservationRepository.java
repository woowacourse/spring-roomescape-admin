package roomescape.dao;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

@Repository
public class ReservationRepository {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public ReservationRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    public Reservation saveReservation(Reservation reservation) {
        SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(reservation);

        long id = jdbcInsert.executeAndReturnKey(sqlParameterSource).longValue();
        reservation.setId(id);
        return reservation;
    }

    public List<Reservation> findAllReservation() {
        String findAllReservationSql = "select id, name, `date`, `time` from reservation";

        return jdbcTemplate.query(
                findAllReservationSql, (resultSet, rowNum) -> new Reservation(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getDate("date").toLocalDate(),
                        resultSet.getTime("time").toLocalTime()
                ));
    }

    public void deleteReservationById(long reservationId) {
        String saveReservationSql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(saveReservationSql, reservationId);
    }
}
