package roomescape.repository;

import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Member;

@Repository
public class MemberRepository {

    //jdbcTemplate -> datasource 필요, simplejdbcinsert 필요
    private static final RowMapper<Member> MEMBER_ROW_MAPPER = ((rs, rowNum) -> {
        return new Member(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("password")
        );
    });

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public MemberRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("member")
                .usingGeneratedKeyColumns("id");
    }

    public Member save(Member member) {
        SqlParameterSource param = new BeanPropertySqlParameterSource(member);
        long id = jdbcInsert.executeAndReturnKey(param).longValue();

        return new Member(id, member);
    }

    public Optional<Member> findById(Long id) {
        String sql = "select * from member where id = ?";

        try {
            return Optional.of(jdbcTemplate.queryForObject(sql, MEMBER_ROW_MAPPER, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<Member> findByEmail(String email) {
        String sql = "select * from member where email = ?";

        try {
            return Optional.of(jdbcTemplate.queryForObject(sql, MEMBER_ROW_MAPPER, email));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
