package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import roomescape.dao.ReservationDao;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationRequest;

public class ReservationServiceTest {

    private ReservationService reservationService;
    private ReservationTimeDao reservationTimeDao;
    private ReservationDao reservationDao;

    @BeforeEach
    void setUp() {
        DataSource testDataSource = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScripts("schema.sql", "data.sql")
                .build();
        reservationTimeDao = new ReservationTimeDao(testDataSource);
        reservationDao = new ReservationDao(testDataSource);
        reservationService = new ReservationService(reservationDao, reservationTimeDao);
    }

    @Test
    @DisplayName("예약 시간을 저장하는데 성공한다")
    void createReservationTest() {
        // given
        ReservationRequest request = new ReservationRequest(
                "검프",
                LocalDate.of(2025, 10, 1),
                1L
        );

        // when
        Reservation reservation = reservationService.createReservation(request);

        // then
        Optional<ReservationTime> time = reservationTimeDao.findById(1L);
        assertThat(time).isPresent();
        assertThat(reservation.getTime().getStartAt()).isEqualTo(time.get().getStartAt());
        assertThat(reservation.getName()).isEqualTo("검프");
        assertThat(reservation.getDate()).isEqualTo(LocalDate.of(2025, 10, 1));
    }

    @Test
    @DisplayName("예약 시간이 존재하지 않으면 예외가 발생한다")
    void createWithNonExistReservationTimeTest() {
        // given
        ReservationRequest request = new ReservationRequest(
                "검프",
                LocalDate.of(2025, 10, 1),
                4L
        );

        // when, then
        assertThatThrownBy(() -> reservationService.createReservation(request))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("모든 예약 내역 조회")
    void getAllReservationsTest() {
        // when
        List<Reservation> allReservations = reservationService.getAllReservations();

        // then
        assertThat(allReservations).hasSize(3);
    }

    @Test
    @DisplayName("id로 예약 내역 조회 성공")
    void getReservationByIdTest() {
        // given
        Long id = 1L;

        // when
        Reservation reservation = reservationService.getReservation(id);

        // then
        assertThat(reservation).isNotNull();
        Optional<ReservationTime> time = reservationTimeDao.findById(1L);
        assertThat(time).isPresent();
        assertThat(reservation.getTime().getStartAt()).isEqualTo(time.get().getStartAt());
        assertThat(reservation.getName()).isEqualTo("브라운");
        assertThat(reservation.getDate()).isEqualTo(LocalDate.of(2023, 3, 3));
    }

    @Test
    @DisplayName("존재하지 않는 id로 조회 시 예외 발생")
    void getReservationWithNonExistIdTest() {
        // given
        Long id = 4L;

        // when, then
        assertThatThrownBy(() -> reservationService.getReservation(id))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("예약 삭제 성공")
    void deleteReservationTest() {
        // given
        Long id = 1L;

        // when
        reservationService.deleteReservation(id);

        // then
        Optional<Reservation> reservation = reservationDao.findById(id);
        assertThat(reservation).isEmpty();
    }

    @Test
    @DisplayName("존재하지 않는 id로 삭제 시 예외 발생")
    void deleteWithNonExistIdTest() {
        // given
        Long id = 4L;

        // when, then
        assertThatThrownBy(() -> reservationService.deleteReservation(id))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
