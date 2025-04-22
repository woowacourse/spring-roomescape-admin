package roomescape.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.Reservation;

@Repository
public class JdbcTemplateRepository implements ReservationRepository {

    private final JdbcTemplate template;
    private final SimpleJdbcInsert inserter;

    public JdbcTemplateRepository(final DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
        this.inserter = new SimpleJdbcInsert(dataSource).withTableName("reservation")
                .usingGeneratedKeyColumns("id")
                .usingColumns("name", "date", "time");
    }

    @Override
    public List<Reservation> findAll() {
        final String sql = "select id, name, date, time from reservation";
        return template.query(sql, actorRowMapper);
    }

    private final RowMapper<Reservation> actorRowMapper = (resultSet, rowNum) -> {
        final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        LocalDate date = LocalDate.parse(resultSet.getString("date"), dateFormatter);
        LocalTime time = LocalTime.parse(resultSet.getString("time"), timeFormatter);

        return new Reservation(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                date,
                time
        );
    };

    @Override
    public boolean existsByDateAndTime(LocalDate date, LocalTime time) {
        final String sql = "select count(*) from reservation where date = ? and time = ?";
        final Integer count = template.queryForObject(sql, Integer.class, date, time);
        return count != null && count > 0;
    }

    @Override
    public Reservation create(final String name, final LocalDate date, final LocalTime time) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", name)
                .addValue("date", date)
                .addValue("time", time);

        Number newId = inserter.executeAndReturnKey(params);
        return new Reservation(newId.longValue(), name, date, time);
    }

    @Override
    public Optional<Reservation> findById(final Long id) {
        final String sql = "select id, name, date, time from reservation where id = ?";
        try {
            final Reservation reservation = template.queryForObject(sql, actorRowMapper, id);
            return Optional.of(reservation);
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void remove(final Long id) {
        final String sql = "delete from reservation where id = ?";
        final int rows = template.update(sql, id);
        if (rows == 0) {
            throw new NoSuchElementException("삭제하려고 하는 예약이 존재하지 않습니다. " + id);
        }
    }
}
