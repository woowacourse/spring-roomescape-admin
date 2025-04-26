package roomescape.repositiory;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.domain.dto.ReservationRequestDto;

@JdbcTest
class ReservationH2RepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ReservationTimeRepository reservationTimeRepository;
    private Long reservationTimeId;

    @BeforeEach
    void setUp() {
        reservationTimeId = reservationTimeRepository.add(new ReservationTime(LocalTime.now()));
    }

    @DisplayName("예약 객체를 추가한다")
    @Test
    void add() {
        // given
        ReservationRepository reservationRepository = new ReservationRepository(jdbcTemplate);
        ReservationRequestDto reservation = new ReservationRequestDto("예약자", LocalDate.now(), reservationTimeId);
        ReservationTime reservationTime = reservationTimeRepository.findById(reservation.timeId());

        // when
        Long id = reservationRepository.add(
                new Reservation(reservation.name(), reservation.date(), reservationTime));

        // then
        Assertions.assertThat(id).isEqualTo(reservationRepository.findById(id).getId());
        Assertions.assertThat(reservationRepository.findAll()).hasSize(1);
    }

    @DisplayName("모든 예약 객체를 반환한다")
    @Test
    void findAll() {
        // given
        ReservationRepository reservationRepository = new ReservationRepository(jdbcTemplate);
        ReservationRequestDto reservationRequestDto = new ReservationRequestDto("예약자", LocalDate.now(),
                reservationTimeId);
        ReservationTime reservationTime = reservationTimeRepository.findById(reservationRequestDto.timeId());
        reservationRepository.add(
                new Reservation(reservationRequestDto.name(), reservationRequestDto.date(), reservationTime));

        // when
        List<Reservation> reservations = reservationRepository.findAll();

        // then
        Assertions.assertThat(reservations).hasSize(1);
    }

    @DisplayName("아이디로 예약 객체를 찾아 반환한다")
    @Test
    void findById() {
        // given
        ReservationRepository reservationRepository = new ReservationRepository(jdbcTemplate);
        ReservationRequestDto reservationDto = new ReservationRequestDto("예약자", LocalDate.now(), reservationTimeId);
        ReservationTime reservationTime = reservationTimeRepository.findById(reservationDto.timeId());
        Long id = reservationRepository.add(
                new Reservation(reservationDto.name(), reservationDto.date(), reservationTime));

        // when
        Reservation findReservation = reservationRepository.findById(id);

        // then
        Assertions.assertThat(findReservation.getId()).isEqualTo(id);
    }

    @DisplayName("예약 객체를 삭제한다")
    @Test
    void delete() {
        // given
        ReservationRepository reservationRepository = new ReservationRepository(jdbcTemplate);
        ReservationRequestDto reservationDto = new ReservationRequestDto("예약자", LocalDate.now(), reservationTimeId);
        ReservationTime reservationTime = reservationTimeRepository.findById(reservationDto.timeId());
        Long id = reservationRepository.add(
                new Reservation(reservationDto.name(), reservationDto.date(), reservationTime));

        // when
        reservationRepository.delete(id);

        // then
        Assertions.assertThat(reservationRepository.findAll()).isEmpty();
    }
}