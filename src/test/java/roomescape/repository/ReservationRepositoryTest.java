package roomescape.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import roomescape.entity.Reservation;
import roomescape.entity.ReservationTime;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Sql(scripts = {"/drop.sql", "/schema.sql"},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@JdbcTest
class ReservationRepositoryTest {

    private final JdbcTemplate jdbcTemplate;
    private final ReservationRepository reservationRepository;

    @Autowired
    ReservationRepositoryTest(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.reservationRepository = new ReservationH2Repository(jdbcTemplate, dataSource);
    }

    @BeforeEach
    void setUp() {
        setUpReservationTimes();
        setUpReservations();
    }

    void setUpReservationTimes() {
        String sql = "INSERT INTO reservation_time (start_at) VALUES (?)";
        List<ReservationTime> times = List.of(
                new ReservationTime(null, LocalTime.of(10, 15)),
                new ReservationTime(null, LocalTime.of(11, 20)),
                new ReservationTime(null, LocalTime.of(12, 25))
        );
        List<Object[]> batchArgs = times.stream().map(time -> new Object[]{
                time.id(),
                time.startAt().format(DateTimeFormatter.ISO_LOCAL_TIME)
        }).toList();
        jdbcTemplate.batchUpdate(sql, batchArgs);
    }

    void setUpReservations() {
        String sql = "INSERT INTO reservation(NAME, DATE, TIME_ID) VALUES (?, ?, ?)";
        List<Reservation> reservations = List.of(
                new Reservation(
                        null,
                        "seyang",
                        LocalDate.of(2024, 1, 20),
                        new ReservationTime(11L, null)
                ),
                new Reservation(
                        null,
                        "hana",
                        LocalDate.of(2024, 2, 19),
                        new ReservationTime(12L, null)
                ),
                new Reservation(
                        null,
                        "mura",
                        LocalDate.of(2024, 3, 18),
                        new ReservationTime(13L, null)
                )
        );
        final List<Object[]> batchArgs = reservations.stream().map(reservation -> new Object[]{
                reservation.id(),
                reservation.name(),
                reservation.date().format(DateTimeFormatter.ISO_LOCAL_DATE),
                reservation.time().id()
        }).toList();
        jdbcTemplate.batchUpdate(sql, batchArgs);
    }

    @Test
    @DisplayName("모든 예약 목록을 조회한다.")
    void findAll() {
        // given
        final List<Reservation> expected = List.of(
                new Reservation(
                        11L,
                        "seyang",
                        LocalDate.of(2024, 1, 20),
                        new ReservationTime(11L, LocalTime.of(10, 15))
                ),
                new Reservation(
                        12L,
                        "hana",
                        LocalDate.of(2024, 2, 19),
                        new ReservationTime(12L, LocalTime.of(11, 20))
                ),
                new Reservation(
                        13L,
                        "mura",
                        LocalDate.of(2024, 3, 18),
                        new ReservationTime(13L, LocalTime.of(12, 25))
                )
        );

        // when
        final List<Reservation> reservations = reservationRepository.findAll();

        // then
        assertThat(reservations).containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    @DisplayName("특정 id를 통해 예약을 조회한다.")
    void findById() {
        // given
        final Reservation expected = new Reservation(
                12L,
                "hana",
                LocalDate.of(2024, 2, 19),
                new ReservationTime(12L, LocalTime.of(11, 20))
        );

        // when
        final Reservation reservation = reservationRepository.findById(12L).get();

        // then
        assertThat(reservation).isEqualTo(expected);
    }

    @Test
    @DisplayName("예약 정보를 저장하면 새로운 아이디가 부여된다.")
    void save() {
        // given
        final Reservation reservation = new Reservation(
                null,
                "gana",
                LocalDate.of(2024, 3, 1),
                new ReservationTime(12L, null)
        );
        Reservation expected = new Reservation(
                1L,
                "gana",
                LocalDate.of(2024, 3, 1),
                new ReservationTime(12L, LocalTime.of(11, 20))
        );

        // when
        reservationRepository.save(reservation);
        Reservation actual = reservationRepository.findById(1L).get();

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("등록된 예약 번호로 삭제한다.")
    void deleteAssignedId() {
        // given
        Long id = 13L;

        // when & then
        assertThat(reservationRepository.findById(id)).isNotNull();
        assertThat(reservationRepository.deleteById(id)).isNotZero();
    }

    @Test
    @DisplayName("없는 예약 번호로 삭제할 경우 아무런 영향이 없다.")
    void deleteNotExistId() {
        // given
        Long id = 14L;

        // when & then
        assertThat(reservationRepository.findById(id)).isNull();
        assertThat(reservationRepository.deleteById(id)).isZero();
    }
}
