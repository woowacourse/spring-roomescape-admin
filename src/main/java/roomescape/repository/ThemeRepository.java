package roomescape.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Theme;

@Repository
public class ThemeRepository {
    private static final RowMapper<Theme> THEME_ROW_MAPPER = (rs, rowNum) -> {
        return new Theme(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getString("thumbnail")
        );
    };

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public ThemeRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .usingGeneratedKeyColumns("id")
                .withTableName("theme");
    }

    public Theme save(Theme theme) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(theme);
        Number key = jdbcInsert.executeAndReturnKey(params);
        return new Theme(key.longValue(), theme);
    }

    public Optional<Theme> findById(Long id) {
        String sql = "select * from theme where id = ?";
        try {
            Theme theme = jdbcTemplate.queryForObject(sql, THEME_ROW_MAPPER, id);
            return Optional.of(theme);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<Theme> findAll() {
        String sql = "select * from theme";
        return jdbcTemplate.query(sql, THEME_ROW_MAPPER);
    }

    public List<Theme> findPopularThemes(LocalDate startDay, LocalDate endDay, int limit) {
        return jdbcTemplate.query("""
                select t.id, t.name, t.description, t.thumbnail, count(*) as reservation_count
                from theme t
                inner join reservation r on r.theme_id = t.id and r.date >= ? and r.date <= ? 
                group by t.id
                order by reservation_count desc
                limit ?
                """, THEME_ROW_MAPPER, startDay, endDay, limit);
    }

    public void delete(Long id) {
        String sql = "delete from theme where id = ?";
        jdbcTemplate.update(sql, id);
    }
}
