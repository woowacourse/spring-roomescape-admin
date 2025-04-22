package roomescape.service;

import java.time.LocalDate;
import java.time.LocalTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.dto.ReservationRequest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class RoomescapeServiceTest {

    @Autowired
    RoomescapeService service;

    @DisplayName("같은 날짜 및 시간 예약이 존재하면 예외를 던진다")
    @Test
    void addReservation() {
        //given
        LocalDate date = LocalDate.of(2025, 4, 16);
        LocalTime time = LocalTime.of(10, 10);
        service.addReservation(new ReservationRequest("test", date, time));

        //when & then
        ReservationRequest duplicated = new ReservationRequest("test2", date, time);
        Assertions.assertThatThrownBy(() -> service.addReservation(duplicated))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 이미 존재하는 예약시간입니다.");
    }

    @DisplayName("존재하지 않는 예약을 삭제하려는 경우 예외를 던진다")
    @Test
    void removeReservation() {
        //given
        long notExistId = 1;

        //when & then
        Assertions.assertThatThrownBy(() -> service.removeReservation(notExistId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 예약번호 1번은 존재하지 않습니다.");
    }

}
