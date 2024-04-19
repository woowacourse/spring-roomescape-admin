package roomescape.dao;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;

@Repository
public class JdbcReservationDao implements ReservationDao {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Reservation> reservationMapper = (resultSet, row) ->
            new Reservation(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getDate("date").toLocalDate(),
                    resultSet.getTime("time").toLocalTime()
            );
    private SimpleJdbcInsert reservationInsert;

    public JdbcReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.reservationInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public List<Reservation> findAllReservations() {
        String sql = "SELECT id, name, date, time from reservation";

        return jdbcTemplate.query(sql, reservationMapper);
    }

    public Long insert(ReservationRequest reservationRequest) {
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(reservationRequest);
        Number id = reservationInsert.executeAndReturnKey(parameterSource);
        return id.longValue();
    }

    public void delete(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";

        jdbcTemplate.update(sql, id);
    }
}
