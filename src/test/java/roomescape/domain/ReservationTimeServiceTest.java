package roomescape.domain;

import dao.ImMemoryReservationTimeDAO;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.service.ReservationTimeService;

import static org.assertj.core.api.Assertions.assertThat;

class ReservationTimeServiceTest {

    @Test
    @DisplayName("시간 데이터를 조회할 수 있어야 한다")
    void findAll() {
        //given
        ReservationTimeService reservationTimeService = new ReservationTimeService(
                new ImMemoryReservationTimeDAO(new ArrayList<>()));
        reservationTimeService.addReservationTime(new ReservationTime(LocalTime.of(10, 0)));

        //when
        List<ReservationTime> actual = reservationTimeService.findAll();

        //then
        assertThat(actual).hasSize(1);
    }
}
