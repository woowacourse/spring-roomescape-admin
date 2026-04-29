package roomescape.reservation.dao;

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
    public List<ReservationEntity> getReservations() {
        String sql = "SELECT name, date, time FROM reservation";

        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> new ReservationEntity(
                        rs.getString(NAME_COLUMN),
                        LocalDateTime.of(
                                rs.getDate(DATE_COLUMN).toLocalDate(),
                                rs.getTime(TIME_COLUMN).toLocalTime()
                        )
                )
        );
    }

    @Override
    public Long saveReservation(ReservationEntity reservationEntity) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO reservation(name, date, time) VALUES (?, ?, ?)";

        jdbcTemplate.update(connection ->
                        createPreparedStatement(reservationEntity, connection, sql),
                keyHolder
        );

        return keyHolder.getKey().longValue();
    }

    private PreparedStatement createPreparedStatement(
            ReservationEntity reservationEntity,
            Connection connection,
            String sql
    ) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                sql,
                Statement.RETURN_GENERATED_KEYS
        );

        preparedStatement.setString(1, reservationEntity.getName());
        preparedStatement.setDate(2, Date.valueOf(reservationEntity.getReservedDate()));
        preparedStatement.setTime(3, Time.valueOf(reservationEntity.getReservedTime()));

        return preparedStatement;
    }

    @Override
    public void deleteReservationById(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
