package roomescape.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;
import roomescape.entity.ReservationEntity;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Component
public class ReservationDAO {
    private final JdbcTemplate jdbcTemplate;

    public ReservationDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ReservationEntity save(ReservationEntity newReservation) {
        List<ReservationEntity> allReservations = findAll();
        if (allReservations.stream().anyMatch(reservation -> reservation.isDuplicatedWith(newReservation.getDateTime()))) {
            throw new IllegalArgumentException("이미 예약이 존재하는 날짜입니다.");
        }
        String query = "INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(query, new String[]{"id"});
            preparedStatement.setString(1, newReservation.name());
            preparedStatement.setString(2, newReservation.date().toString());
            preparedStatement.setString(3, newReservation.time().toString());
            return preparedStatement;
        }, keyHolder);
        final long id = keyHolder.getKey().longValue();
        return newReservation.changeId(id);
    }

    public void deleteById(final Long id) {
        String query = "DELETE FROM reservation WHERE id = ?";
        final int deletedCount = jdbcTemplate.update(query, id);
        if (deletedCount == 0) {
            throw new IllegalArgumentException("존재하지 않는 예약입니다.");
        }
    }

    public List<ReservationEntity> findAll() {
        String query = "SELECT id, name, date, time FROM reservation";
        return jdbcTemplate.query(query, (resultSet, rowNum) -> {
            LocalDate date = resultSet.getObject("date", LocalDate.class);
            LocalTime time = resultSet.getObject("time", LocalTime.class);
            return new ReservationEntity(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    date,
                    time
            );
        });
    }
}
