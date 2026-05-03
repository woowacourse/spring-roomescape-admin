package roomescape.reservationtime.repository;

import static org.assertj.core.api.Assertions.assertThat;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import roomescape.reservationtime.domain.ReservationTime;

@SpringBootTest
@Transactional
class ReservationTimeRepositoryTest {

    @Autowired
    private ReservationTimeRepository reservationTimeRepository;

    @Test
    @DisplayName("예약 시간을 저장하면 생성된 ID를 포함한 객체를 반환한다")
    void 저장_테스트() {
        // given
        ReservationTime time = ReservationTime.builder()
                .startAt(LocalTime.of(14, 0))
                .build();

        // when
        ReservationTime saved = reservationTimeRepository.saveReservationTime(time);

        // then
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getStartAt()).isEqualTo(LocalTime.of(14, 0));
    }

    @Test
    @DisplayName("삭제 시 영향받은 행의 개수를 반환한다")
    void 삭제_테스트() {
        // given
        ReservationTime saved = reservationTimeRepository.saveReservationTime(
                ReservationTime.builder().startAt(LocalTime.of(10, 0)).build());

        // when
        int deletedRows = reservationTimeRepository.deleteById(saved.getId());
        int nonExistentRows = reservationTimeRepository.deleteById(999L);

        // then
        assertThat(deletedRows).isEqualTo(1);
        assertThat(nonExistentRows).isEqualTo(0);
    }

    @Test
    @DisplayName("전체 예약 시간을 리스트로 조회한다")
    void 전체_조회_테스트() {
        // given
        reservationTimeRepository.saveReservationTime(ReservationTime.builder().startAt(LocalTime.of(10, 0)).build());
        reservationTimeRepository.saveReservationTime(ReservationTime.builder().startAt(LocalTime.of(11, 0)).build());

        // when
        List<ReservationTime> times = reservationTimeRepository.findAllReservationTimes();

        // then
        assertThat(times).hasSize(2);
    }
}
