package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import roomescape.domain.Name;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationRepository;
import roomescape.domain.ReservationTime;
import roomescape.domain.ReservationTimeRepository;
import roomescape.support.IntegrationTestSupport;

class JdbcTemplateReservationRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private ReservationTimeRepository reservationTimeRepository;

    @Autowired
    private ReservationRepository target;

    @DisplayName("신규 예약을 저장할 수 있다.")
    @Test
    void save() {
        ReservationTime savedTime = reservationTimeRepository.save(createReservationTime());
        Reservation reservation = createReservation(savedTime);

        Reservation savedReservation = target.save(reservation);

        int rowCount = countRowWhere("reservation", "id = " + savedReservation.getId());
        assertThat(rowCount).isEqualTo(1);
    }

    @DisplayName("예약을 삭제할 수 있다.")
    @Test
    void delete() {
        ReservationTime savedTime = reservationTimeRepository.save(createReservationTime());
        Reservation savedReservation = target.save(createReservation(savedTime));

        target.deleteBy(savedReservation.getId());

        int rowCount = countRow("reservation");
        assertThat(rowCount).isZero();
    }

    @DisplayName("모든 예약을 불러올 수 있다.")
    @Test
    void findAll() {
        ReservationTime savedTime = reservationTimeRepository.save(createReservationTime());
        target.save(createReservation(savedTime));
        target.save(createReservation(savedTime));

        List<Reservation> reservations = target.findAll();

        assertThat(reservations).hasSize(2);
    }

    @DisplayName("예약이 존재하지 않으면 빈 리스트를 반환한다.")
    @Test
    void findAllWhenEmpty() {
        List<Reservation> reservations = target.findAll();

        assertThat(reservations).isEmpty();
    }

    @DisplayName("주어진 시간을 사용하는 예약의 수를 조회할 수 있다.")
    @Test
    void countBy() {
        ReservationTime savedTime = reservationTimeRepository.save(createReservationTime());
        target.save(createReservation(savedTime));
        target.save(createReservation(savedTime));

        int countRow = target.countBy(savedTime.getId());

        assertThat(countRow).isEqualTo(2);
    }

    @DisplayName("주어진 시간을 사용하는 예약이 존재하지 않는 경우, 0을 반환한다.")
    @Test
    void noCount() {
        ReservationTime savedTime = reservationTimeRepository.save(createReservationTime());
        target.save(createReservation(savedTime));
        target.save(createReservation(savedTime));

        int countRow = target.countBy(-1);

        assertThat(countRow).isEqualTo(0);
    }

    private ReservationTime createReservationTime() {
        return new ReservationTime(LocalTime.now());
    }

    private Reservation createReservation(ReservationTime reservationTime) {
        return new Reservation(new Name("아톰"), LocalDate.now(), reservationTime);
    }
}
