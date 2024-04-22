package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
import roomescape.domain.exception.EntityNotFoundException;
import roomescape.support.IntegrationTestSupport;

class JdbcTemplateReservationTimeRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ReservationTimeRepository target;

    @DisplayName("신규 예약 시간을 저장할 수 있다.")
    @Test
    void save() {
        ReservationTime reservationTime = createReservationTime();

        target.save(reservationTime);

        int rowCount = countRow("reservation_time");
        assertThat(rowCount).isEqualTo(1);
    }

    @DisplayName("예약 시간을 삭제할 수 있다.")
    @Test
    void delete() {
        ReservationTime savedTime = target.save(createReservationTime());

        target.deleteBy(savedTime.getId());

        int rowCount = countRow("reservation_time");
        assertThat(rowCount).isZero();
    }

    @DisplayName("삭제하려는 예약 시간을 다른 예약에서 사용하고 있다면, 삭제할 수 없다.")
    @Test
    void cantDelete() {
        ReservationTime savedTime = target.save(createReservationTime());
        reservationRepository.save(createReservation(savedTime));

        assertThatThrownBy(() -> target.deleteBy(savedTime.getId()))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("다른 예약에서 해당 예약 시간을 사용하고 있습니다.");
    }

    @DisplayName("모든 예약 시간을 불러올 수 있다.")
    @Test
    void findAll() {
        target.save(createReservationTime());
        target.save(createReservationTime());

        List<ReservationTime> times = target.findAll();

        assertThat(times).hasSize(2);
    }

    @DisplayName("예약 시간이 존재하지 않으면 빈 리스트를 반환한다.")
    @Test
    void findAllWhenEmpty() {
        List<ReservationTime> times = target.findAll();

        assertThat(times).isEmpty();
    }

    @DisplayName("아이디에 해당되는 예약 시간을 불러올 수 있다.")
    @Test
    void findById() {
        ReservationTime savedTime = target.save(createReservationTime());

        ReservationTime found = target.findBy(savedTime.getId());

        assertThat(found.getId()).isEqualTo(savedTime.getId());
    }

    @DisplayName("아이디에 해당되는 예약 시간이 존재하지 않으면, 불러올 수 없다.")
    @Test
    void entityNotFound() {
        assertThatThrownBy(() -> target.findBy(-1))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("존재하지 않는 예약 시간입니다.");
    }

    private ReservationTime createReservationTime() {
        return new ReservationTime(LocalTime.now());
    }

    private Reservation createReservation(ReservationTime reservationTime) {
        return new Reservation(new Name("아톰"), LocalDate.now(), reservationTime);
    }
}
