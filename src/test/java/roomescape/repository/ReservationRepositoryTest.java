package roomescape.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@Sql(scripts = {"/sample.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = {"/drop.sql", "/schema.sql"},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@JdbcTest
class ReservationRepositoryTest {

    private final ReservationRepository reservationRepository;

    @Autowired
    ReservationRepositoryTest(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.reservationRepository = new H2ReservationRepository(jdbcTemplate, dataSource);
    }

    @Test
    @DisplayName("모든 예약 목록을 조회한다.")
    void findAll() {
        // given
        final List<Reservation> expected = List.of(
                new Reservation(
                        1L,
                        "al",
                        LocalDate.of(2024, 1, 20),
                        new ReservationTime(1L, null)
                ),
                new Reservation(
                        2L,
                        "be",
                        LocalDate.of(2024, 2, 19),
                        new ReservationTime(2L, null)
                )
        );

        // when
        List<Reservation> actual = reservationRepository.findAll();

        // then
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    @DisplayName("특정 id를 통해 예약을 조회한다.")
    void findByIdPresent() {
        // given
        Long id = 2L;
        Reservation expected = new Reservation(
                id,
                "be",
                LocalDate.of(2024, 2, 19),
                new ReservationTime(2L, null)
        );

        // when
        Optional<Reservation> actual = reservationRepository.findById(id);

        // then
        assertThat(actual).hasValue(expected);
    }

    @Test
    @DisplayName("존재하지 않는 예약을 조회할 경우 빈 값을 반환한다.")
    void findByIdNotPresent() {
        // given
        Long id = 3L;

        // when
        Optional<Reservation> actual = reservationRepository.findById(id);

        // then
        assertThat(actual).isEmpty();
    }

    @Test
    @DisplayName("예약 정보를 저장하면 새로운 아이디가 부여된다.")
    void save() {
        // given
        Reservation reservation = new Reservation(
                null,
                "cha",
                LocalDate.of(2024, 3, 1),
                new ReservationTime(2L, null)
        );
        Reservation expected = new Reservation(
                3L,
                "cha",
                LocalDate.of(2024, 3, 1),
                new ReservationTime(2L, null)
        );

        // when
        Reservation actual = reservationRepository.save(reservation);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("존재하지 않는 예약 시간으로 예약 정보를 저장하면 예외가 발생한다.")
    void exceptionOnSavingWithNotPresentTime() {
        // given
        Reservation reservation = new Reservation(
                null,
                "cha",
                LocalDate.of(2024, 3, 1),
                new ReservationTime(4L, null)
        );
        Reservation expected = new Reservation(
                3L,
                "cha",
                LocalDate.of(2024, 3, 1),
                new ReservationTime(4L, null)
        );

        // when & then
        assertThatCode(() -> reservationRepository.save(reservation))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 예약 시간입니다.");
    }

    @Test
    @DisplayName("등록된 예약 번호로 삭제한다.")
    void deleteAssignedId() {
        // given
        Long id = 2L;

        // when & then
        assertThat(reservationRepository.findById(id)).isPresent();
        assertThat(reservationRepository.deleteById(id)).isNotZero();
    }

    @Test
    @DisplayName("없는 예약 번호로 삭제할 경우 아무런 영향이 없다.")
    void deleteNotExistId() {
        // given
        Long id = 3L;

        // when & then
        assertThat(reservationRepository.findById(id)).isEmpty();
        assertThat(reservationRepository.deleteById(id)).isZero();
    }
}
