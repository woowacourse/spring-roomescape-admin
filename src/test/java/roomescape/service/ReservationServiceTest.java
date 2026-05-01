package roomescape.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import roomescape.dto.ReservationRequestDto;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class ReservationServiceTest {

    @Autowired
    private ReservationService reservationService;

    @Test
    void notExistReservationTimeExceptionTest() {
        ReservationRequestDto requestDto = new ReservationRequestDto("fizz", LocalDate.of(2026, 5, 2), 1L);
        assertThatThrownBy(() -> reservationService.create(requestDto))
                .hasMessage("[ERROR] 해당 id의 예약 시간이 존재하지 않습니다.")
                .isInstanceOf(IllegalArgumentException.class);
    }
}
