package roomescape.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.dao.ReservationDao;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.entity.Reservation;
import roomescape.entity.ReservationTime;
import roomescape.exceptions.EntityNotFoundException;

public class ReservationServiceTest {

    private final ReservationService reservationService = new ReservationService(new ReservationTestDao());

    @Test
    @DisplayName("조회된 엔티티를 DTO로 매핑해 반환한다.")
    void test_readReservation() {
        //given & when
        List<ReservationResponse> actual = reservationService.readReservation();
        //then
        assertThat(actual.size()).isEqualTo(1);
        assertThat(actual.getFirst().id()).isEqualTo(1);
    }

    @Test
    @DisplayName("저장한 엔티티를 DTO로 반환한다.")
    void test_postReservation() {
        //given
        long timeId = 1L;
        ReservationRequest request = new ReservationRequest("브라운", LocalDate.now(), timeId);
        //when
        ReservationResponse actual = reservationService.postReservation(request);
        //then
        assertThat(actual.id()).isEqualTo(1);
    }

    @Test
    @DisplayName("저장소에 없는 값을 삭제하려할 경우, 예외가 발생한다.")
    void test_deleteReservation() {
        assertThatThrownBy(() -> reservationService.deleteReservation(Long.MAX_VALUE))
                .isInstanceOf(EntityNotFoundException.class);
    }

    private static class ReservationTestDao implements ReservationDao {

        @Override
        public List<Reservation> findAll() {
            return List.of(
                    new Reservation(1L, "브라운", LocalDate.of(2025, 1, 1), new ReservationTime(1L, LocalTime.now())));
        }

        @Override
        public Reservation save(Reservation reservation, long timeId) {
            return new Reservation(1L, reservation.name(), reservation.date(),
                    reservation.time());
        }

        @Override
        public void deleteById(long id) {
            throw new EntityNotFoundException("");
        }
    }
}
