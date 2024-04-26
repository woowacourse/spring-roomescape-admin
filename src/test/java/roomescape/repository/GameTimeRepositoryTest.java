package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static roomescape.fixture.DateTimeFixture.GAME_TIME_WITH_NO_ID_0300;
import static roomescape.fixture.DateTimeFixture.GAME_TIME_WITH_NO_ID_0400;

import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.transaction.annotation.Transactional;
import roomescape.entity.GameTime;

@SpringBootTest
@Transactional
class GameTimeRepositoryTest {

    @Autowired
    private GameTimeRepository gameTimeRepository;

    @DisplayName("전체 예약 가능 시각을 조회할 수 있다")
    @Test
    void readAllTest() {
        GameTime reservation0300 = gameTimeRepository.save(GAME_TIME_WITH_NO_ID_0300);
        GameTime reservation0400 = gameTimeRepository.save(GAME_TIME_WITH_NO_ID_0400);

        List<GameTime> all = gameTimeRepository.readAll();
        assertThat(all).containsExactly(reservation0300, reservation0400);
    }

    @DisplayName("예약 가능 시간 단건을 저장할 수 있다")
    @Test
    void saveTest() {
        GameTime saved = gameTimeRepository.save(GAME_TIME_WITH_NO_ID_0300);
        assertThat(saved.getStartAt()).isEqualTo(LocalTime.of(3, 0));
    }

    @DisplayName("예약 가능 시간 단건을 조회할 수 있다")
    @Test
    void findByIdTest() {
        GameTime saved = gameTimeRepository.save(GAME_TIME_WITH_NO_ID_0300);
        assertThatCode(() -> gameTimeRepository.findById(saved.getId()))
                .doesNotThrowAnyException();
    }

    @DisplayName("예약 가능 시간 단건을 삭제할 수 있다")
    @Test
    void deleteByIdTest() {
        GameTime saved = gameTimeRepository.save(GAME_TIME_WITH_NO_ID_0300);
        gameTimeRepository.deleteById(saved.getId());

        assertThatThrownBy(() -> gameTimeRepository.findById(saved.getId()))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

    @DisplayName("같은 예약 가능 시간이 이미 존재하는 경우를 알 수 있다")
    @Test
    void existByStartAtTest() {
        GameTime saved = gameTimeRepository.save(GAME_TIME_WITH_NO_ID_0300);
        assertThat(gameTimeRepository.existByStartAt(GAME_TIME_WITH_NO_ID_0300)).isTrue();
    }
}
