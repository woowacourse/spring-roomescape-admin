package roomescape.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RoomescapeRepositoryTest {

    RoomescapeRepository repository = new RoomescapeRepositoryImpl();

    @AfterEach
    void tearDown() {
        repository.clear();
    }

    @DisplayName("존재하지 않는 예약을 삭제하려는 경우 예외를 던진다")
    @Test
    void deleteById() {
        //given
        long notExistId = 1;

        //when & then
        Assertions.assertThatThrownBy(() -> repository.deleteById(notExistId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 예약번호 1번은 존재하지 않습니다.");
    }

}
