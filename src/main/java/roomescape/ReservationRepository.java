package roomescape;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.dto.RequestReservation;

import java.util.List;

@Repository
public class ReservationRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Long add(RequestReservation requestReservation) { //  TODO DTO 를 Repository 까지 보내는게 맞을까??
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("RESERVATION")
                .usingGeneratedKeyColumns("id");
        SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(requestReservation);
        return simpleJdbcInsert.executeAndReturnKey(sqlParameterSource).longValue();
    }

    public List<Reservation> findAll() {
        String SQL = "SELECT * FROM RESERVATION";
        return jdbcTemplate.query(SQL, (resultSet, rowNum) -> {
            Reservation reservation = new Reservation(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getString("date"),
                    resultSet.getString("time")
            );
            return reservation;
        });
    }

    public void remove(Long id) {
        String SQL = "DELETE FROM RESERVATION WHERE id = ?";
        jdbcTemplate.update(SQL, id);
    }
}
