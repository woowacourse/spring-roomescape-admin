package roomescape.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.dto.CreateReservationDto;
import roomescape.entity.Reservation;
import roomescape.entity.ReservationTime;

@Repository
public class ReservationRepository {
    private final JdbcTemplate jdbcTemplate;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> findAll() {
        String sql = "SELECT r.id AS r_id, r.name, r.date, rt.id AS rt_id, rt.start_at "
                + "FROM reservation r "
                + "INNER JOIN reservation_time rt "
                + "ON r.time_id = rt.id ";
        return jdbcTemplate.query(sql, (resultSet, rowNum) -> getReservationData(resultSet));
    }

    public Long addAndGetId(CreateReservationDto createReservationDto) {
        SimpleJdbcInsert insertQuery = new SimpleJdbcInsert(jdbcTemplate.getDataSource())
                .withTableName("reservation")
                .usingColumns("name", "date", "time_id")
                .usingGeneratedKeyColumns("id");

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("name", createReservationDto.name())
                .addValue("date", createReservationDto.date())
                .addValue("time_id", createReservationDto.timeId());

        return insertQuery.executeAndReturnKey(parameters).longValue();
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public Reservation findById(Long id) {
        String selectSql = "SELECT r.id AS r_id, r.name, r.date, rt.id AS rt_id, rt.start_at "
                + "FROM reservation r "
                + "INNER JOIN reservation_time rt "
                + "ON r.time_id = rt.id "
                + "WHERE r.id = ?";

        return jdbcTemplate.queryForObject(selectSql,
                (resultSet, rowNum) -> getReservationData(resultSet),
                id);
    }

    private Reservation getReservationData(ResultSet resultSet) throws SQLException {
        ReservationTime reservationTime = new ReservationTime(
                resultSet.getLong("rt_id"),
                resultSet.getString("start_at")
        );
        return new Reservation(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("date"),
                reservationTime
        );
    }
}
