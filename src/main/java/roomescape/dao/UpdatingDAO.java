package roomescape.dao;

import java.sql.PreparedStatement;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.dto.ReservationReqDto;
import roomescape.dto.ReservationResDto;

@Repository
public class UpdatingDAO {

    private final JdbcTemplate jdbcTemplate;

    public UpdatingDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ReservationResDto addAndGet(ReservationReqDto dto) {
        String sql = "INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, dto.name());
            ps.setString(2, dto.date().toString());
            ps.setString(3, dto.time().toString());
            return ps;
        }, keyHolder);

        long id = keyHolder.getKey().longValue();

        return new ReservationResDto(id, dto.name(), dto.date(), dto.time());
    }

    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM reservation WHERE id = ?", id);
    }
}
