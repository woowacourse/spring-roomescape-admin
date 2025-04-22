package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import roomescape.domain.ReservationTime;

class MemoryReservationTimeRepositoryTest {

    private MemoryReservationTimeRepository reservationTimeRepository = new MemoryReservationTimeRepository();

    @BeforeEach
    void setUp() {
        //reservationTimeRepository = new MemoryReservationTimeRepository();
    }

    @Test
    void 전체_예약시간을_조회한다() {
        // given
        reservationTimeRepository.add(new ReservationTime(null, LocalTime.of(9, 0)));
        // when
        List<ReservationTime> reservationTimes = reservationTimeRepository.findAll();
        // then
        assertThat(reservationTimes).hasSize(1);
    }

    @Test
    void 예약을_추가한다() {
        // given
        ReservationTime reservationTime = new ReservationTime(null, LocalTime.of(9, 0));
        // when
        reservationTimeRepository.add(reservationTime);
        // then
        int reservationTimeCount = reservationTimeRepository.findAll().size();
        assertThat(reservationTimeCount).isEqualTo(1);
    }

    @ParameterizedTest
    @CsvSource({
            "1, true",
            "2, true",
            "3, false",
    })
    void ID로_예약을_조회한다(Long id, boolean expected) {
        // given
        reservationTimeRepository.add(new ReservationTime(null, LocalTime.of(9, 0)));
        reservationTimeRepository.add(new ReservationTime(null, LocalTime.of(9, 0)));
        // when
        Optional<ReservationTime> optionalReservationTime = reservationTimeRepository.findById(id);
        // then
        assertThat(optionalReservationTime.isPresent()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
            "1, 0",
            "2, 1"
    })
    void 예약을_삭제한다(Long id, int count) {
        // given
        reservationTimeRepository.add(new ReservationTime(null, LocalTime.of(9, 0)));
        // when
        reservationTimeRepository.deleteById(id);
        // then
        int size = reservationTimeRepository.findAll().size();
        assertThat(size).isEqualTo(count);
    }
}