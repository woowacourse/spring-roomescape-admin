package roomescape.repository;

import java.sql.PreparedStatement;
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
public class JdbcReservationRepository implements ReservationRepository {

    private static final String INSERT_SQL = "INSERT INTO reservation(name, date, time_id) VALUES(?, ?, ?)";
    private static final String SELECT_SQL = "SELECT \n"
            + "    r.id as reservation_id, \n"
            + "    r.name, \n"
            + "    r.date, \n"
            + "    t.id as time_id, \n"
            + "    t.start_at as time_value \n"
            + "FROM reservation as r \n"
            + "inner join reservation_time as t \n"
            + "on r.time_id = t.id";
    private static final String DELETE_SQL = "DELETE FROM reservation";
    private static final String WHERE_ID = " WHERE id = ?";

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Reservation> rowMapper = (resultSet, rowNum) -> {
        Reservation reservation = new Reservation(
                resultSet.getLong("reservation_id"),
                resultSet.getString("name"),
                LocalDate.parse(resultSet.getString("date")),
                new ReservationTime(
                        resultSet.getLong("time_id"),
                        LocalTime.parse(resultSet.getString("time_value"))
                )
        );
        return reservation;
    };

    public JdbcReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long save(Reservation reservation) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL, new String[]{"id"});
            preparedStatement.setString(1, reservation.getName());
            preparedStatement.setString(2, reservation.getDate().toString());
            preparedStatement.setLong(3, reservation.getTime().getId());
            return preparedStatement;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    @Override
    public List<Reservation> findAll() {
        return jdbcTemplate.query(SELECT_SQL, rowMapper);
    }

    @Override
    public Reservation findById(Long id) {
        return jdbcTemplate.queryForObject(SELECT_SQL + WHERE_ID, rowMapper, id);
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(DELETE_SQL + WHERE_ID, id);
    }
}
