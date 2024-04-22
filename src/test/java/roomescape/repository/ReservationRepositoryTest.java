package roomescape.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.entity.Reservation;
import roomescape.entity.ReservationTime;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ReservationRepository reservationRepository;

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
                time.startAt().format(DateTimeFormatter.ISO_LOCAL_TIME)
        }).toList();
        jdbcTemplate.batchUpdate(sql, batchArgs);
    }

    void setUpReservations() {
        String sql = "INSERT INTO reservation(name, date, time_id) VALUES (?, ?, ?)";
        List<Reservation> reservations = List.of(
                new Reservation(
                        null,
                        "seyang",
                        LocalDate.of(2024, 1, 20),
                        new ReservationTime(1L, null)
                ),
                new Reservation(
                        null,
                        "hana",
                        LocalDate.of(2024, 2, 19),
                        new ReservationTime(2L, null)
                ),
                new Reservation(
                        null,
                        "mura",
                        LocalDate.of(2024, 3, 18),
                        new ReservationTime(3L, null)
                )
        );
        final List<Object[]> batchArgs = reservations.stream().map(reservation -> new Object[]{
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
        final List<Reservation> reservations = reservationRepository.findAll();

        // when & then
        assertThat(reservations).containsExactlyInAnyOrder(
                new Reservation(
                        1L,
                        "seyang",
                        LocalDate.of(2024, 1, 20),
                        new ReservationTime(1L, LocalTime.of(10, 15))
                ),
                new Reservation(
                        2L,
                        "hana",
                        LocalDate.of(2024, 2, 19),
                        new ReservationTime(2L, LocalTime.of(11, 20))
                ),
                new Reservation(
                        3L,
                        "mura",
                        LocalDate.of(2024, 3, 18),
                        new ReservationTime(3L, LocalTime.of(12, 25))
                )
        );
    }

    @Test
    @DisplayName("특정 id를 통해 예약을 조회한다.")
    void findById() {
        // given
        final Reservation reservation = reservationRepository.findById(2L);

        // when & then
        assertThat(reservation).isEqualTo(new Reservation(
                2L,
                "hana",
                LocalDate.of(2024, 2, 19),
                new ReservationTime(2L, LocalTime.of(11, 20))
        ));
    }

    @Test
    @DisplayName("예약 정보를 저장하면 새로운 아이디가 부여된다.")
    void save() {
        // given
        final Reservation reservation = reservationRepository.save(new Reservation(
                null,
                "gana",
                LocalDate.of(2024, 3, 1),
                new ReservationTime(2L, LocalTime.of(12, 25))
        ));

        // when & then
        assertThat(reservation.id()).isEqualTo(4L);
    }

    @Test
    @DisplayName("등록된 예약 번호로 삭제한다.")
    void deleteAssignedId() {
        // given
        final int deleteCount = reservationRepository.deleteById(3L);

        // when & then
        assertThat(deleteCount).isNotZero();
    }

    @Test
    @DisplayName("없는 예약 번호로 삭제할 경우 아무런 영향이 없다.")
    void deleteNotExistId() {
        // given
        final int deleteCount = reservationRepository.deleteById(4L);

        // when & then
        assertThat(deleteCount).isZero();
    }
}
