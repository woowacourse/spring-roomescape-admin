package roomescape.time.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
class JdbcTemplateTimesRepositoryTest {

    @Autowired JdbcTemplate jdbcTemplate;
    JdbcTemplateTimesRepository jdbcTemplateTimesRepository;

    @BeforeEach
    void beforeEach() {
        jdbcTemplateTimesRepository = new JdbcTemplateTimesRepository(jdbcTemplate);
    }

    @DisplayName("시간을 추가하면 id를 할당받는다.")
    @Test
    void saveTime() {
        //given
        TimeEntity entity = TimeEntity.of(LocalTime.of(10, 0));

        //when
        TimeEntity entityWithId = jdbcTemplateTimesRepository.saveTime(entity);

        //then
        assertThat(entityWithId.id()).isNotNull();
    }


}
