package roomescape.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import java.util.NoSuchElementException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ReservationServiceImplTest {

    @Autowired
    private ReservationServiceImpl reservationService;

    @Test
    @DisplayName("존재하지 않는 아이디를 삭제시 예외 발생")
    void deleteException() {
        // given
        long invalidId = 1;

        // when & then
        assertThatThrownBy(() -> reservationService.delete(invalidId)).
                isInstanceOf(NoSuchElementException.class);
    }
}
