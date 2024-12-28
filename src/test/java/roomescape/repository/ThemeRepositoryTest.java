package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import roomescape.domain.Theme;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Sql(scripts = "/truncate.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/test_data.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
class ThemeRepositoryTest {

    @Autowired
    private ThemeRepository themeRepository;

    @Autowired
    private DataSource dataSource;

    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void init() {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Test
    @DisplayName("인기 테마를 찾는다.")
    void findPopularThemes() {
        // given
        // theme1 : 예약 3개
        // theme2 : 예약 2개
        // theme3 : 날짜 범위 벗어난 예약 3개
        // theme4 : 예약 1개
        jdbcTemplate.update("insert into theme (name, description, thumbnail) values ('theme4', 'none', 'none')");
        jdbcTemplate.update(
                "insert into reservation (name, date, time_id, theme_id) values ('reservator4', '2100-11-30', 1, 3)");
        jdbcTemplate.update(
                "insert into reservation (name, date, time_id, theme_id) values ('reservator5', '2100-11-30', 2, 3)");
        jdbcTemplate.update(
                "insert into reservation (name, date, time_id, theme_id) values ('reservator6', '2100-11-30', 3, 3)");
        jdbcTemplate.update(
                "insert into reservation (name, date, time_id, theme_id) values ('reservator7', '2100-12-02', 1, 1)");
        jdbcTemplate.update(
                "insert into reservation (name, date, time_id, theme_id) values ('reservator8', '2100-12-02', 1, 2)");
        jdbcTemplate.update(
                "insert into reservation (name, date, time_id, theme_id) values ('reservator9', '2100-12-02', 1, 4)");

        LocalDate startDay = LocalDate.of(2100, 12, 1);
        LocalDate endDay = LocalDate.of(2100, 12, 2);
        int limit = 2;

        // when
        List<Theme> popularThemes = themeRepository.findPopularThemes(startDay, endDay, limit);

        // then
        List<String> names = popularThemes.stream().map(Theme::getName).toList();
        assertThat(names).containsExactly("theme1", "theme2");
    }
}
