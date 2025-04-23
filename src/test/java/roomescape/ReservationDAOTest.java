package roomescape;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.dao.ReservationDAO;
import roomescape.entity.ReservationEntity;
import roomescape.entity.ReservationTimeEntity;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationDAOTest {
    @Autowired
    private ReservationDAO reservationDAO;

    @Test
    @DisplayName("같은 날짜, 같은 시각에 이미 예약이 존재하는 경우, 재생성할 수 없다.")
    void duplicateReservation() {
        // given
        LocalDate date = LocalDate.of(2025, 1, 2);
        LocalTime time = LocalTime.of(12, 0);
        ReservationTimeEntity timeEntity = new ReservationTimeEntity(1L, time);
        reservationDAO.save(new ReservationEntity(null, "test", date, timeEntity));

        // when & then
        assertThatThrownBy(() -> {
            reservationDAO.save(new ReservationEntity(null, "test2", date, timeEntity));
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("존재하지 않는 예약은 삭제할 수 없다.")
    void deleteNotExistedReservation() {
        // given

        // when & then
        assertThatThrownBy(() -> {
            reservationDAO.deleteById(1L);
        }).isInstanceOf(IllegalArgumentException.class);
    }
}
