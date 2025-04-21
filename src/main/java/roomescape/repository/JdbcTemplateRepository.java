package roomescape.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import roomescape.Reservation;

@Repository
public class JdbcTemplateRepository implements ReservationRepository {

    private JdbcTemplate template;

    public JdbcTemplateRepository(final DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
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
}
