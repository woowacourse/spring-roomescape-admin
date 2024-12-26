package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

@JdbcTest
class ReservationRepositoryTest {

    private static final LocalDate DATE = LocalDate.now().plusDays(1);
    @Autowired
    private DataSource dataSource;

    private ReservationRepository repository;
    private ReservationTimeRepository timeRepository;
    private ReservationTime time;

    @BeforeEach
    void setUp() {
        repository = new ReservationRepository(dataSource);
        timeRepository = new ReservationTimeRepository(dataSource);
        time = timeRepository.save(new ReservationTime(LocalTime.now()));
    }

    @Test
    @DisplayName("예약을 추가한다.")
    void save() {
        // given
        Reservation reservation = new Reservation("카고", DATE, time);

        // when
        Reservation savedReservation = repository.save(reservation);

        // then
        Reservation found = repository.findById(savedReservation.getId()).get();
        assertThat(savedReservation).isEqualTo(found);
    }

    @Test
    void findById() {
        // given
        Reservation reservation1 = new Reservation("카고", DATE, time);
        Reservation reservation2 = new Reservation("카고", DATE.plusDays(1), time);
        Reservation savedReservation1 = repository.save(reservation1);
        repository.save(reservation2);

        // when
        Reservation found = repository.findById(savedReservation1.getId()).get();

        // then
        assertThat(found).isEqualTo(savedReservation1);
    }

    @Test
    void findAll() {
        // given
        Reservation reservation1 = new Reservation("카고", DATE, time);
        Reservation reservation2 = new Reservation("카고", DATE.plusDays(1), time);
        Reservation saved1 = repository.save(reservation1);
        Reservation saved2 = repository.save(reservation2);

        // when
        List<Reservation> result = repository.findAll();

        // then
        assertThat(result).containsExactly(saved1, saved2);
    }

    @Test
    @DisplayName("예약을 삭제한다.")
    void delete() {
        // given
        Reservation reservation = new Reservation("카고", DATE, time);
        Reservation saved = repository.save(reservation);

        // when
        repository.delete(saved.getId());

        // then
        assertThat(repository.findById(saved.getId())).isEmpty();
    }

    @Test
    @DisplayName("특정 시간에 예약이 존재하면 true를 반환한다.")
    void existsByTimeIdTrue() {
        // given
        Reservation reservation = new Reservation("카고", DATE, time);
        repository.save(reservation);

        // when
        boolean result = repository.existsByTimeId(time.getId());

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("특정 시간에 예약이 존재하지 않으면 false를 반환한다.")
    void existsByTimeIdFalse() {
        // given
        Reservation reservation = new Reservation("카고", DATE, time);
        repository.save(reservation);
        Long notExistTimeId = time.getId() + 1;

        // when
        boolean result = repository.existsByTimeId(notExistTimeId);

        // then
        assertThat(result).isFalse();
    }
}
