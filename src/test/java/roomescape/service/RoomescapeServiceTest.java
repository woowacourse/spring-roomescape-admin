package roomescape.service;

import java.time.LocalDate;
import java.time.LocalTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.domain.Reservation;
import roomescape.repository.RoomescapeRepositoryImpl;

class RoomescapeServiceTest {

    RoomescapeService service = new RoomescapeService(new RoomescapeRepositoryImpl());

    @DisplayName("같은 날짜 및 시간 예약이 존재하면 예외를 던진다")
    @Test
    void addReservation() {
        //given
        LocalDate date = LocalDate.of(2025, 4, 16);
        LocalTime time = LocalTime.of(10, 10);
        service.addReservation(new Reservation("test", date, time));

        //when & then
        Reservation duplicated = new Reservation("test2", date, time);
        Assertions.assertThatThrownBy(() -> service.addReservation(duplicated))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 이미 존재하는 예약시간입니다.");
    }

}
