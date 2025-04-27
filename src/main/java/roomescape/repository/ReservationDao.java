package roomescape.repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import roomescape.model.Reservation;
import roomescape.model.ReservationTime;

@Repository
public class ReservationDao {

    private final JdbcTemplate jdbcTemplate;

    public ReservationDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> findAll() {
        String findAllSql = """
                SELECT
                    r.id as reservation_id,
                    r.name,
                    r.date,
                    t.id as time_id,
                    t.start_at as time_value
                FROM reservation as r
                inner join reservation_time as t
                on r.time_id = t.id
                """;

        return jdbcTemplate.query(findAllSql, getReservationRowMapper());
    }

    private RowMapper<Reservation> getReservationRowMapper() {
        return (resultSet, rowNum) -> new Reservation(
                resultSet.getLong("reservation_id"),
                resultSet.getString("name"),
                LocalDate.parse(resultSet.getString("date")),
                new ReservationTime(
                        resultSet.getLong("time_id"),
                        LocalTime.parse(resultSet.getString("time_value"))
                ));
    }

    public Reservation insert(final Reservation reservation) {
        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        String insertSql = """
                INSERT INTO reservation (name, date, time_id)
                VALUES (?, ?, ?)
                """;
        jdbcTemplate.update(getPreparedStatementCreator(reservation, insertSql), generatedKeyHolder);

        return new Reservation(generatedKeyHolder.getKey().longValue(), reservation.getName(),
                reservation.getDate(), reservation.getTime());
    }

    private PreparedStatementCreator getPreparedStatementCreator(
            final Reservation reservation, final String insertSql
    ) {
        return connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(insertSql,
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, reservation.getName());
            preparedStatement.setString(2, reservation.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            preparedStatement.setLong(3, reservation.getTime().getId());
            return preparedStatement;
        };
    }

    public int deleteById(final Long id) {
        String deleteSql = """
                DELETE FROM reservation
                WHERE id = ?
                """;
        return jdbcTemplate.update(deleteSql, id);
    }
}
