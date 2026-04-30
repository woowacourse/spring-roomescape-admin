package roomescape.reservation.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.sql.Date;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class InMemoryReservationsRepositoryTest {

    ReservationsRepository reservationsRepository = new InMemoryReservationsRepository();

    @DisplayName("새로운 예약을 추가한다.")
    @Test
    void saveReservation() {
        //given
        ReservationEntity entity1 = ReservationEntity.of("name1", Date.valueOf(LocalDate.now()), 1L);
        ReservationEntity entity2 = ReservationEntity.of("name2",Date.valueOf(LocalDate.now()),1L);
        ReservationEntity entity3 = ReservationEntity.of("name3", Date.valueOf(LocalDate.now()),1L);

        //when
        ReservationEntity entity1WithId = reservationsRepository.saveReservation(entity1);
        ReservationEntity entity2WithId = reservationsRepository.saveReservation(entity2);
        ReservationEntity entity3WithId = reservationsRepository.saveReservation(entity3);

        //then
        assertThat(entity1WithId.id()).isEqualTo(1);
        assertThat(entity2WithId.id()).isEqualTo(2);
        assertThat(entity3WithId.id()).isEqualTo(3);
    }

    @DisplayName("id에 해당하는 예약을 삭제한다.")
    @Test
    void deleteReservationById_success() {
        //given
        ReservationEntity entity = ReservationEntity.of("name1", Date.valueOf(LocalDate.now()), 1L);
        ReservationEntity entityWithId = reservationsRepository.saveReservation(entity);

        //when
        reservationsRepository.deleteReservationById(entityWithId.id());

        //then
        assertThat(reservationsRepository.findAllReservationsWithTime())
                .isEmpty();
    }

    @DisplayName("id에 해당하는 예약이 없으면 예외가 발생한다.")
    @Test
    void deleteReservationById_fail() {
        //when & then
        assertThatThrownBy(() -> reservationsRepository.deleteReservationById(1L))
                .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("해당 예약은 존재하지 않습니다.");
    }
}
