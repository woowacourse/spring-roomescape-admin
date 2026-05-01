package roomescape.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.domain.ReservationTime;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class ListReservationTimeRepositoryTest {

    @Test
    @DisplayName("List에 ReservationTime 데이터를 저장하면 ID가 할당된다.")
    public void save() {
        // given
        ListReservationTimeRepository repository = new ListReservationTimeRepository();
        ReservationTime reservationTime = new ReservationTime(LocalTime.of(12, 18));

        // when
        ReservationTime saved = repository.save(reservationTime);

        // then
        assertThat(saved.getId()).isNotNull();
        assertThat(saved).extracting(ReservationTime::getStartAt).isEqualTo(reservationTime.getStartAt());
    }

    @Test
    @DisplayName("특정 ID를 가진 ReservationTime 데이터를 조회한다.")
    public void findById() {
        // given
        ListReservationTimeRepository repository = new ListReservationTimeRepository();
        ReservationTime reservationTime = new ReservationTime(LocalTime.of(12, 18));

        ReservationTime saved = repository.save(reservationTime);

        // when
        Optional<ReservationTime> timeOptional = repository.findById(saved.getId());

        // then
        assertThat(timeOptional).isPresent();
    }

    @Test
    @DisplayName("List에 저장된 모든 ReservationTime 데이터 목록을 조회한다.")
    public void findAll() {
        // given
        ListReservationTimeRepository repository = new ListReservationTimeRepository();
        ReservationTime reservationTime1 = new ReservationTime(LocalTime.of(15, 40));
        ReservationTime reservationTime2 = new ReservationTime(LocalTime.of(16, 10));
        ReservationTime reservationTime3 = new ReservationTime(LocalTime.of(17, 30));

        repository.save(reservationTime1);
        repository.save(reservationTime2);
        repository.save(reservationTime3);

        // when
        List<ReservationTime> reservationTimes = repository.findAll();

        // then
        assertThat(reservationTimes).hasSize(3);
    }

    @Test
    @DisplayName("특정 ID를 가진 ReservationTime 데이터를 삭제한다.")
    public void delete() {
        // given
        ListReservationTimeRepository repository = new ListReservationTimeRepository();
        ReservationTime reservationTime = new ReservationTime(LocalTime.of(12, 18));

        ReservationTime saved = repository.save(reservationTime);

        // when
        repository.delete(saved.getId());

        // then
        List<ReservationTime> reservations = repository.findAll();
        assertThat(reservations).noneMatch(r -> r.getId().equals(saved.getId()));
    }

}
