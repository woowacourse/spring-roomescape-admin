package roomescape.repository;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.entity.Reservation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Repository
public class MemoryReservationRepository implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;
    private final ReservationTimeRepository timeRepository;

    public MemoryReservationRepository(JdbcTemplate jdbcTemplate, ReservationTimeRepository timeRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.timeRepository = timeRepository;
    }

    private Reservation mapRowReservation(ResultSet rs, int rowNum) throws SQLException {
        return new Reservation(
                rs.getLong("id"),
                rs.getString("name"),
                LocalDate.parse(rs.getString("date")),
                timeRepository.findById(rs.getLong("time_id"))
        );
    }

    @Override
    public List<Reservation> findAll() {
        String sql = "SELECT * FROM reservation";

        return jdbcTemplate.query(sql, this::mapRowReservation);
    }

    @Override
    public Reservation findById(Long id) {
        String sql = "SELECT * FROM reservation WHERE id = ?";

        try {
            return jdbcTemplate.queryForObject(sql, this::mapRowReservation, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Reservation save(Reservation reservation) {
        String sql = "INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator creator = con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservation.name());
            ps.setString(2, reservation.date().format(DateTimeFormatter.ISO_LOCAL_DATE));
            ps.setLong(3, reservation.time().id());
            return ps;
        };
        jdbcTemplate.update(creator, keyHolder);

        return reservation.assign(
                keyHolder.getKeyAs(Long.class),
                timeRepository.findById(reservation.time().id()));
    }

    @Override
    public int deleteById(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";

        return jdbcTemplate.update(sql, id);
    }
}
