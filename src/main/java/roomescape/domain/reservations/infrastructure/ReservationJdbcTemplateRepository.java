package roomescape.domain.reservations.infrastructure;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.reservations.entity.Reservation;

@Repository
public class ReservationJdbcTemplateRepository implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;
    private final DataSource dataSource;

    public ReservationJdbcTemplateRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.dataSource = dataSource;
    }

//    @Override
//    public Reservation save(Reservation reservation) {
//        KeyHolder keyHolder = new GeneratedKeyHolder();
//
//        jdbcTemplate.update(connection -> {
//            PreparedStatement ps = connection.prepareStatement(
//                    "INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)",
//                    Statement.RETURN_GENERATED_KEYS
//            );
//
//            ps.setString(1, reservation.getName());
//            ps.setString(2, reservation.getDate().toString());
//            ps.setString(3, reservation.getTime().toString());
//
//            return ps;
//        }, keyHolder);
//
//        Number key = keyHolder.getKey();
//        if (key == null) {
//            throw new IllegalStateException("ID 생성 실패");
//        }
//
//        Long id = key.longValue();
//
//        return Reservation.of(
//                id,
//                reservation.getName(),
//                reservation.getDate(),
//                reservation.getTime()
//        );
//    }

    @Override
    public Reservation save(Reservation reservation) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
        Map<String, Object> params = Map.of(
                "name", reservation.getName(),
                "date", reservation.getDate().toString(),
                "time", reservation.getTime().toString()
        );
        Long id = insert.executeAndReturnKey(params).longValue();
        return Reservation.of(
                id,
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime()
        );
    }

    @Override
    public Optional<Reservation> findById(Long id) {
        String sql = "SELECT * FROM reservation WHERE id = ?";
        Reservation reservation = jdbcTemplate.queryForObject(
                sql,
                (rs, rowNum) -> Reservation.of(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getDate("date").toLocalDate(),
                        rs.getTime("time").toLocalTime()
                ),
                id
        );
        return Optional.ofNullable(reservation);
    }

    @Override
    public List<Reservation> findAll() {
        String sql = "SELECT * FROM reservation";
        return jdbcTemplate.query(sql,
                (rs, rowNum) -> Reservation.of(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getDate("date").toLocalDate(),
                        rs.getTime("time").toLocalTime()
                ));
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM reservation WHERE id = ?", id);
    }
}
