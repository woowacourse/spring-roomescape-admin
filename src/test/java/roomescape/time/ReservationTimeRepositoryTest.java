package roomescape.time;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import roomescape.TestSupport;
import roomescape.time.domain.ReservationTime;
import roomescape.time.repository.TimeRepository;

class ReservationTimeRepositoryTest extends TestSupport {

    @Autowired
    private TimeRepository timeRepository;

    @Test
    @DisplayName("데이터를 저장한다")
    void saveTest() {
        ReservationTime reservationTime = new ReservationTime(null, LocalTime.now());
        Long saveId = timeRepository.save(reservationTime);

        assertThat(saveId).isEqualTo(1);
    }

    @Test
    @DisplayName("id 로 엔티티를 찾는다.")
    void findByIdTest() {
        ReservationTime reservationTime1 = new ReservationTime(1L, LocalTime.now());
        ReservationTime reservationTime2 = new ReservationTime(2L, LocalTime.now());
        timeRepository.save(reservationTime1);
        timeRepository.save(reservationTime2);
        ReservationTime findReservationTime = timeRepository.findById(reservationTime2.getId());
        assertThat(findReservationTime.getId()).isEqualTo(2);
    }

    @Test
    @DisplayName("전체 엔티티를 조회한다.")
    void findAllTest() {
        ReservationTime reservationTime1 = new ReservationTime(1L, LocalTime.now());
        ReservationTime reservationTime2 = new ReservationTime(2L, LocalTime.now());
        timeRepository.save(reservationTime1);
        timeRepository.save(reservationTime2);
        List<ReservationTime> reservationTimes = timeRepository.findAll();
        assertThat(reservationTimes.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("id를 받아 삭제한다.")
    void deleteTest() {
        ReservationTime reservationTime = new ReservationTime(1L, LocalTime.now());
        timeRepository.save(reservationTime);
        timeRepository.delete(1L);
        List<ReservationTime> reservationTimes = timeRepository.findAll();

        assertThat(reservationTimes.size()).isEqualTo(0);
    }
}
