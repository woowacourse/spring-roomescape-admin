package roomescape.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

@Repository
public class ReservationDao {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Reservation> rowMapper = (resultSet, rowNum) -> new Reservation(
            resultSet.getLong("reservation_id"), resultSet.getString("name"),
            LocalDate.parse(resultSet.getString("date")),
            new ReservationTime(resultSet.getLong("time_id"), LocalTime.parse(resultSet.getString("start_at"))));

    public ReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Reservation save(Reservation reservation) {
        String sql = "INSERT INTO reservation(name, date, time_id) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> createPreparedStatement(connection, sql, reservation), keyHolder);

        long id = extractGeneratedId(keyHolder);

        return Reservation.of(
                id,
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime()
        );
    }

    private long extractGeneratedId(KeyHolder keyHolder) {
        Number key = keyHolder.getKey();

        if (key == null) {
            throw new IllegalStateException("생성된 키를 조회할 수 없습니다.");
        }

        return key.longValue();
    }

    private PreparedStatement createPreparedStatement(Connection connection, String sql,
                                                      Reservation reservation)
            throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(sql, new String[]{"id"});
        preparedStatement.setString(1, reservation.getName());
        preparedStatement.setString(2, reservation.getDate().toString());
        preparedStatement.setLong(3, reservation.getTime().getId());
        return preparedStatement;
    }

    public List<Reservation> findAll() {
        return jdbcTemplate.query(selectSql(), rowMapper);
    }

    public Reservation findById(Long id) {
        String sql = selectSql() + " WHERE r.id = ?";
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    private String selectSql() {
        return """
                SELECT r.id AS reservation_id, r.name, r.date, t.id AS time_id, t.start_at
                FROM reservation AS r
                INNER JOIN reservation_time AS t
                ON r.time_id = t.id
                """;
    }
}
