package roomescape.repository;

import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.dto.CreateReservationDto;
import roomescape.entity.Reservation;

@Repository
public class ReservationRepository {
    private JdbcTemplate jdbcTemplate;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> findAll() {
        String sql = "SELECT * FROM reservation";
        return jdbcTemplate.query(sql,
                (resultSet, rowNum) -> new Reservation(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getObject("dateTime", LocalDateTime.class)
                ));
    }

    public Reservation add(CreateReservationDto createReservationDto) {
        KeyHolder identifier = new GeneratedKeyHolder();
        String sql = "INSERT INTO reservation (name, dateTime) VALUES (?,?)";
        jdbcTemplate.update(connection -> {
            LocalDateTime dateTime = LocalDateTime.of(
                    createReservationDto.date(),
                    createReservationDto.time()
            );

            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, createReservationDto.name());
            ps.setString(2, dateTime.toString());
            return ps;
        }, identifier);

        String selectSql = "SELECT * FROM reservation WHERE id = ?";
        Reservation reservation = jdbcTemplate.queryForObject(selectSql,
                (resultSet, rowNum) -> new Reservation(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getObject("dateTime", LocalDateTime.class)
                ),
                identifier.getKey().longValue());
        return reservation;
    }

    public void deleteById(Long id) {
//        Reservation reservation = reservations.stream()
//                .filter(item -> item.getId().equals(id))
//                .findFirst()
//                .orElseThrow(InvalidReservationException::new);
//
//        reservations.remove(reservation);
    }
}
