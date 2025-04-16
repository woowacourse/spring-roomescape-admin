package roomescape.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.domain.Reservation;

class RoomescapeRepositoryTest {

    @DisplayName("같은 날짜 및 시간 예약이 존재하면 예외를 던진다")
    @Test
    void saveReservation() {
        //given
        LocalDate date = LocalDate.of(2025, 4, 16);
        LocalTime time = LocalTime.of(10, 10);
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(new Reservation("test", date, time));
        RoomescapeRepository roomescapeRepository = new RoomescapeRepository(reservations);

        //when & then
        Reservation duplicated = new Reservation("test2", date, time);
        Assertions.assertThatThrownBy(() -> roomescapeRepository.saveReservation(duplicated))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 이미 존재하는 예약시간입니다.");
    }

    @DisplayName("존재하지 않는 예약을 삭제하려는 경우 예외를 던진다")
    @Test
    void deleteById() {
        //given
        RoomescapeRepository roomescapeRepository = new RoomescapeRepository(new ArrayList<>());
        long notExistId = 1;

        //when & then
        Assertions.assertThatThrownBy(() -> roomescapeRepository.deleteById(notExistId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 존재하지 않는 예약입니다.");
    }

}
