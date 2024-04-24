package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
        LocalTime time1 = LocalTime.of(1, 0);
        LocalTime time2 = LocalTime.of(2, 0);
        GameTime gameTime1 = new GameTime(time1);
        GameTime gameTime2 = new GameTime(time2);

        gameTimeRepository.save(gameTime1);
        gameTimeRepository.save(gameTime2);

        List<GameTime> all = gameTimeRepository.readAll();

        assertThat(all)
                .extracting("startAt")
                .containsExactly(time1, time2);
    }

    @DisplayName("예약 가능 시간 단건을 저장할 수 있다")
    @Test
    void saveTest() {
        LocalTime time = LocalTime.of(1, 0);
        GameTime gameTime = new GameTime(time);
        GameTime saved = gameTimeRepository.save(gameTime);

        assertThat(saved.getStartAt()).isEqualTo(time);
    }

    @DisplayName("예약 가능 시간 단건을 조회할 수 있다")
    @Test
    void findByIdTest() {
        LocalTime time = LocalTime.of(1, 0);
        GameTime gameTime = new GameTime(time);
        GameTime saved = gameTimeRepository.save(gameTime);

        assertThat(gameTimeRepository.findById(saved.getId())).isEqualTo(saved);
    }

    @DisplayName("예약 가능 시간 단건을 삭제할 수 있다")
    @Test
    void deleteByIdTest() {
        LocalTime time = LocalTime.of(1, 0);
        GameTime gameTime = new GameTime(time);
        GameTime saved = gameTimeRepository.save(gameTime);
        gameTimeRepository.deleteById(saved.getId());

        assertThat(gameTimeRepository.readAll().size()).isEqualTo(0);
    }
}
