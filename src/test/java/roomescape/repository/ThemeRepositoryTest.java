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
import org.springframework.data.domain.PageRequest;
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
        jdbcTemplate.update(
                "insert into theme (name, description, thumbnail, created_date, last_modified_date) values ('theme4', 'none', 'none', '2100-11-29', '2100-11-29')");

        jdbcTemplate.update(
                "insert into reservation_slot (date, time_id, theme_id, created_date, last_modified_date) values ('2100-11-30', 1, 3, '2100-11-29', '2100-11-29')");
        jdbcTemplate.update(
                "insert into reservation_slot (date, time_id, theme_id, created_date, last_modified_date) values ('2100-11-30', 2, 3, '2100-11-29', '2100-11-29')");
        jdbcTemplate.update(
                "insert into reservation_slot (date, time_id, theme_id, created_date, last_modified_date) values ('2100-11-30', 3, 3, '2100-11-29', '2100-11-29')");
        jdbcTemplate.update(
                "insert into reservation_slot (date, time_id, theme_id, created_date, last_modified_date) values ('2100-12-02', 1, 1, '2100-11-29', '2100-11-29')");
        jdbcTemplate.update(
                "insert into reservation_slot (date, time_id, theme_id, created_date, last_modified_date) values ('2100-12-02', 1, 2, '2100-11-29', '2100-11-29')");
        jdbcTemplate.update(
                "insert into reservation_slot (date, time_id, theme_id, created_date, last_modified_date) values ('2100-12-02', 1, 4, '2100-11-29', '2100-11-29')");
        jdbcTemplate.update(
                "insert into reservation (member_id, slot_id, status, created_date, last_modified_date) values (1, 1, 'RESERVED', '2100-11-29', '2100-11-29')");
        jdbcTemplate.update(
                "insert into reservation (member_id, slot_id, status, created_date, last_modified_date) values (1, 2, 'RESERVED', '2100-11-29', '2100-11-29')");
        jdbcTemplate.update(
                "insert into reservation (member_id, slot_id, status, created_date, last_modified_date) values (1, 3, 'RESERVED', '2100-11-29', '2100-11-29')");
        jdbcTemplate.update(
                "insert into reservation (member_id, slot_id, status, created_date, last_modified_date) values (1, 4, 'RESERVED', '2100-11-29', '2100-11-29')");
        jdbcTemplate.update(
                "insert into reservation (member_id, slot_id, status, created_date, last_modified_date) values (1, 5, 'RESERVED', '2100-11-29', '2100-11-29')");
        jdbcTemplate.update(
                "insert into reservation (member_id, slot_id, status, created_date, last_modified_date) values (1, 6, 'RESERVED', '2100-11-29', '2100-11-29')");

        LocalDate startDay = LocalDate.of(2100, 12, 1);
        LocalDate endDay = LocalDate.of(2100, 12, 2);
        PageRequest pageRequest = PageRequest.of(0, 2);

        // when
        List<Theme> popularThemes = themeRepository.findPopularThemes(startDay, endDay, pageRequest);

        // then
        List<String> names = popularThemes.stream().map(Theme::getName).toList();
        assertThat(names).containsExactly("theme1", "theme2");
    }
}
