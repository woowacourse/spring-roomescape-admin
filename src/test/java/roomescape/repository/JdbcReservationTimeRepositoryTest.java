package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import roomescape.time.entity.ReservationTime;
import roomescape.time.repository.JdbcReservationTimeRepository;

@SpringBootTest
@Transactional
public class JdbcReservationTimeRepositoryTest {

    @Autowired
    private JdbcReservationTimeRepository jdbcReservationTimeRepository;

    @BeforeEach
    void setup() {
        ReservationTime nonIdReservationTime = ReservationTime.createNew(LocalTime.parse("10:00"));
        jdbcReservationTimeRepository.save(nonIdReservationTime);
    }

    @Test
    @DisplayName("예약 시간 전체 조회")
    void reservationTime_findAll_test() {
        //given & when
        Optional<ReservationTime> reservationTime = jdbcReservationTimeRepository.finaAll()
                .stream()
                .findFirst();
        //then
        assertThat(reservationTime).isNotEmpty();
    }

    @Test
    @DisplayName("예약 시간 저장")
    void reservationTime_save_test() {
        //given
        ReservationTime nonIdReservationTime = ReservationTime.createNew(LocalTime.parse("11:00"));

        //when
        ReservationTime reservationTime = jdbcReservationTimeRepository.save(nonIdReservationTime);

        //then
        assertThat(reservationTime.getId()).isNotNull();
    }

    @Test
    @DisplayName("예약 시간 삭제")
    void reservationTime_delete_test() {
        // given
        int beforeSize = jdbcReservationTimeRepository.finaAll().size();

        ReservationTime reservationTime = jdbcReservationTimeRepository.finaAll()
                .stream()
                .findFirst()
                .orElseThrow();

        // when
        jdbcReservationTimeRepository.deleteById(reservationTime.getId());

        // then
        int afterSize = jdbcReservationTimeRepository.finaAll().size();

        assertThat(afterSize).isEqualTo(beforeSize - 1);
    }

    @Test
    @DisplayName("예약 시간 중복")
    void reservationTime_duplicate_test() {
        //given & when
        boolean result = jdbcReservationTimeRepository.existsByStartAt(LocalTime.parse("10:00"));

        //then
        assertThat(result).isTrue();
    }

}
