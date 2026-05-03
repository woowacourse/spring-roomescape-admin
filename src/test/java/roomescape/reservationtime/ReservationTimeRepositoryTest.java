package roomescape.reservationtime;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(properties = "spring.datasource.url=jdbc:h2:mem:repository-test")
@Transactional
class ReservationTimeRepositoryTest {

    @Autowired
    private ReservationTimeRepository reservationTimeRepository;

    @Test
    void 삽입이_정상적으로_수행되면_id를_반환한다() {
        ReservationTime reservationTime = new ReservationTime(LocalTime.of(10, 0));

        Long id = reservationTimeRepository.insert(reservationTime);

        assertThat(id).isPositive();
    }

    @Test
    void 존재하는_예약시간_id가_입력되면_예약을_삭제한다() {
        Long id = reservationTimeRepository.insert(new ReservationTime(LocalTime.of(10, 0)));

        reservationTimeRepository.delete(id);

        boolean exists = reservationTimeRepository.findAllReservationTimes().stream()
                .anyMatch(it -> it.getId().equals(id));
        assertThat(exists).isFalse();
    }

    @Test
    void 존재하지_않는_예약시간_id가_입력되면_빈_값을_반환한다() {
        assertThat(reservationTimeRepository.findById(999L)).isEmpty();
    }

    @Test
    void 예약시간_리스트를_반환한다() {
        reservationTimeRepository.insert(new ReservationTime(LocalTime.of(10, 0)));
        reservationTimeRepository.insert(new ReservationTime(LocalTime.of(11, 0)));

        List<ReservationTime> reservationTimes = reservationTimeRepository.findAllReservationTimes();

        assertThat(reservationTimes)
                .extracting(ReservationTime::getStartAt)
                .contains(LocalTime.of(10, 0), LocalTime.of(11, 0));
    }

}
