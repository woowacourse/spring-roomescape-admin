package roomescape.reservation.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JdbcTemplateReservationsRepositoryTest {

    @Autowired
    ReservationsRepository reservationsRepository;

    @DisplayName("기본적으로는 아무런 예약도 존재하지 않는다.")
    @Test
    void getReservations_empty() {
        //when
        List<ReservationEntity> reservationEntities = reservationsRepository.getReservations();

        //then
        assertThat(reservationEntities).isEmpty();
    }

    @DisplayName("새로운 예약을 추가한다.")
    @Test
    void saveReservation() {
        //given
        ReservationEntity entity1 = ReservationEntity.of("name1", LocalDate.now(), LocalTime.now());
        ReservationEntity entity2 = ReservationEntity.of("name2", LocalDate.now(), LocalTime.now());
        ReservationEntity entity3 = ReservationEntity.of("name3", LocalDate.now(), LocalTime.now());

        //when
        reservationsRepository.saveReservation(entity1);
        reservationsRepository.saveReservation(entity2);
        reservationsRepository.saveReservation(entity3);

        //then
        assertThat(reservationsRepository.getReservations().size()).isEqualTo(3);
    }

    @DisplayName("id에 해당하는 예약을 삭제한다.")
    @Test
    void deleteReservationById_success() {
        //given
        ReservationEntity entity = ReservationEntity.of("name1", LocalDate.now(), LocalTime.now());
        ReservationEntity entityWithId = reservationsRepository.saveReservation(entity);

        //when
        reservationsRepository.deleteReservationById(entityWithId.id());

        //then
        assertThat(reservationsRepository.getReservations()).isEmpty();
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
