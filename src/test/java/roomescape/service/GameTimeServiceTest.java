package roomescape.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static roomescape.fixture.DateTimeFixture.GAME_TIME_WITH_NO_ID_0300;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class GameTimeServiceTest {
    @Autowired
    private GameTimeService gameTimeService;

    @DisplayName("게임 시간이 이미 등록되어 있다면 저장 시 예외가 발생한다")
    @Test
    void gameTimeConflictTest() {
        gameTimeService.save(GAME_TIME_WITH_NO_ID_0300);
        assertThatThrownBy(() -> gameTimeService.save(GAME_TIME_WITH_NO_ID_0300))
                .isInstanceOf(IllegalStateException.class);
    }
}
