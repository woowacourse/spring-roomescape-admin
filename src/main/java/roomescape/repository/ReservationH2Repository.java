package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.entity.Reservation;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Repository
public class ReservationH2Repository implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final ReservationTimeRepository timeRepository;

    public ReservationH2Repository(JdbcTemplate jdbcTemplate, DataSource dataSource, ReservationTimeRepository timeRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
        this.timeRepository = timeRepository;
    }

    private Reservation mapRowReservation(ResultSet rs, int rowNum) throws SQLException {
        return new Reservation(
                rs.getLong("id"),
                rs.getString("name"),
                LocalDate.parse(rs.getString("date")),
                timeRepository.findById(rs.getLong("time_id")).get()
        );
    }

    @Override
    public List<Reservation> findAll() {
        String sql = "SELECT * FROM reservation";

        return jdbcTemplate.query(sql, this::mapRowReservation);
    }

    @Override
    public Optional<Reservation> findById(Long id) {
        String sql = "SELECT * FROM reservation WHERE id = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, this::mapRowReservation, id));
    }

    @Override
    public Reservation save(Reservation reservation) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", reservation.name())
                .addValue("date", reservation.date().format(DateTimeFormatter.ISO_LOCAL_DATE))
                .addValue("time_id", reservation.time().id());
        Long id = simpleJdbcInsert.executeAndReturnKey(params).longValue();

        return reservation.assign(id, timeRepository.findById(reservation.time().id()).get());
    }

    @Override
    public int deleteById(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";

        return jdbcTemplate.update(sql, id);
    }
}
