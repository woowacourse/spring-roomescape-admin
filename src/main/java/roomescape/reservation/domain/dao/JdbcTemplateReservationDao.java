package roomescape.reservation.domain.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.reservation.domain.Reservation;

@Repository
public class JdbcTemplateReservationDao implements ReservationsDao{

    private final static String NAME_COLUMN = "name";
    private final static String DATE_COLUMN = "date";
    private final static String TIME_COLUMN = "time";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcTemplateReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Reservation> getReservations() {
        String sql = "SELECT name, date, time FROM reservation";

        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> new Reservation(
                        rs.getString(NAME_COLUMN),
                        LocalDateTime.of(
                                rs.getDate(DATE_COLUMN).toLocalDate(),
                                rs.getTime(TIME_COLUMN).toLocalTime()
                        )
                )
        );
    }

    @Override
    public Long saveReservation(Reservation reservation) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO reservation(name, date, time) VALUES (?, ?, ?)";

        jdbcTemplate.update(connection ->
                        createPreparedStatement(reservation, connection, sql),
                keyHolder
        );

        return keyHolder.getKey().longValue();
    }

    private PreparedStatement createPreparedStatement(
            Reservation reservation,
            Connection connection,
            String sql
    ) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                sql,
                Statement.RETURN_GENERATED_KEYS
        );

        preparedStatement.setString(1, reservation.getName());
        preparedStatement.setDate(2, Date.valueOf(reservation.getReservedDate()));
        preparedStatement.setTime(3, Time.valueOf(reservation.getReservedTime()));

        return preparedStatement;
    }

    @Override
    public void deleteReservationById(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
