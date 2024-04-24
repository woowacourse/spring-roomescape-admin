package roomescape.time.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import roomescape.reservation.domain.Name;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.repository.ReservationRepository;
import roomescape.time.domain.Time;

@DisplayName("시간 레포지토리")
@JdbcTest
@Import({TimeRepository.class, ReservationRepository.class})
class TimeRepositoryTest {
    @Autowired
    private TimeRepository timeRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    private Time savedTime;

    @BeforeEach
    void init() {
        Time time = new Time(null, LocalTime.parse("10:00"));
        savedTime = timeRepository.save(time);
    }

    @DisplayName("id로 시간 정보를 조회한다.")
    @Test
    void findById() {
        // given
        Optional<Time> findTime = timeRepository.findById(savedTime.getId());

        // when
        Time time = findTime.get();

        // then
        assertAll(
                () -> assertThat(savedTime.getId()).isEqualTo(time.getId()),
                () -> assertThat(savedTime.getStartAt()).isEqualTo(time.getStartAt())
        );
    }

    @DisplayName("모든 시간 정보를 조회한다.")
    @Test
    void findAll() {
        // given & when
        List<Time> reservations = timeRepository.findAll();

        // then
        assertThat(reservations).hasSize(1);
    }

    @DisplayName("time 기본키를 참조하는 예약이 있는지 조회한다.")
    @Test
    void test() {
        // given
        Reservation reservation = new Reservation(
                null,
                new Name("브라운"), LocalDate.parse("2024-08-05"),
                new Time(savedTime.getId(), LocalTime.parse("10:00"))
        );
        reservationRepository.save(reservation);

        Time savedTime2 = timeRepository.save(new Time(null, LocalTime.parse("11:00")));

        // when
        Optional<Time> time1 = timeRepository.findBySameReferId(savedTime.getId());
        Optional<Time> time2 = timeRepository.findBySameReferId(savedTime2.getId());

        // then
        assertAll(
                () -> assertThat(time1.isPresent()).isTrue(),
                () -> assertThat(time2.isPresent()).isFalse()
        );
    }

    @DisplayName("id로 시간 정보를 제거한다.")
    @Test
    void delete() {
        // given & when
        timeRepository.deleteById(savedTime.getId());

        // then
        assertThat(timeRepository.findById(savedTime.getId()).isEmpty()).isTrue();
    }
}
