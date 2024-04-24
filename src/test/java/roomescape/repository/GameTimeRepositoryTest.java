package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static roomescape.fixture.DateTimeFixture.TIME_03_00_NO_ID;
import static roomescape.fixture.DateTimeFixture.TIME_04_00_NO_ID;

import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import roomescape.entity.GameTime;

@SpringBootTest
@Transactional
@Rollback
class GameTimeRepositoryTest {

    @Autowired
    private GameTimeRepository gameTimeRepository;

    @DisplayName("전체 예약 가능 시각을 조회할 수 있다")
    @Test
    void readAllTest() {
        gameTimeRepository.save(TIME_03_00_NO_ID);
        gameTimeRepository.save(TIME_04_00_NO_ID);

        List<GameTime> all = gameTimeRepository.readAll();
        assertThat(all)
                .extracting("startAt")
                .containsExactly(LocalTime.of(3, 0), LocalTime.of(4, 0));
    }

    @DisplayName("예약 가능 시간 단건을 저장할 수 있다")
    @Test
    void saveTest() {
        GameTime saved = gameTimeRepository.save(TIME_03_00_NO_ID);
        assertThat(saved.getStartAt()).isEqualTo(LocalTime.of(3, 0));
    }

    @DisplayName("예약 가능 시간 단건을 조회할 수 있다")
    @Test
    void findByIdTest() {
        GameTime saved = gameTimeRepository.save(TIME_03_00_NO_ID);
        assertThatCode(() -> gameTimeRepository.findById(saved.getId()))
                .doesNotThrowAnyException();
    }

    @DisplayName("예약 가능 시간 단건을 삭제할 수 있다")
    @Test
    void deleteByIdTest() {
        GameTime saved = gameTimeRepository.save(TIME_03_00_NO_ID);
        gameTimeRepository.deleteById(saved.getId());

        assertThatThrownBy(() -> gameTimeRepository.findById(saved.getId()))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }
}
