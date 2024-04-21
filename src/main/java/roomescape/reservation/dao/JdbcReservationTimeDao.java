package roomescape.reservation.dao;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.reservation.domain.ReservationTime;

@Repository
public class JdbcReservationTimeDao implements ReservationTimeDao{

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<ReservationTime> reservationTimeMapper = (resultSet, row) ->
            new ReservationTime(
                    resultSet.getLong("id"),
                    resultSet.getTime("start_at").toLocalTime()
            );
    private SimpleJdbcInsert reservationInsert;

    public JdbcReservationTimeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.reservationInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public ReservationTime insert(ReservationTime reservationTime) {
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(reservationTime);
        Number id = reservationInsert.executeAndReturnKey(parameterSource);
        return new ReservationTime(id.longValue(), reservationTime.getStartAt());
    }

    @Override
    public List<ReservationTime> findAllReservationTimes() {
        String sql = "SELECT * FROM reservation_time";
        return jdbcTemplate.query(sql, reservationTimeMapper);
    }

    @Override
    public ReservationTime findById(Long id) {
        String sql = "SELECT id, start_at FROM reservation_time WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, reservationTimeMapper, id);
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM reservation_time WHERE id = ?";

        jdbcTemplate.update(sql, id);
    }
}
