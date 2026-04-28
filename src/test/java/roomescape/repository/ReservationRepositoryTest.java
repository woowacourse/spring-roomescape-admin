package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.domain.Reservation;

class ReservationRepositoryTest {

    private static final String TESTER_NAME = "라티";
    private static final String TEST_DATE = "2026-24-28";
    private static final String TEST_TIME = "18:00";

    ReservationEntityMapper mapper = new ReservationEntityMapper();
    ReservationRepository reservationRepository = new ReservationRepository(mapper);

    @Test
    @DisplayName("저장을 하고, ID가 있는 Reservation를 반환한다")
    void save_success() {
        //given
        Reservation reservation = Reservation.constructWithNoId(TESTER_NAME, TEST_DATE, TEST_TIME);

        //when
        Reservation result = reservationRepository.save(reservation);

        //then
        Assertions.assertNotNull(result.id());
        clearRepository(result);
    }

    @Test
    @DisplayName("찾기를 하면 모든 결과물을 반환한다")
    void findAll_success() {
        Reservation reservation1 = Reservation.constructWithNoId(TESTER_NAME, TEST_DATE, TEST_TIME);
        Reservation reservation2 = Reservation.constructWithNoId(TESTER_NAME, TEST_DATE, TEST_TIME);
        Reservation reservation3 = Reservation.constructWithNoId(TESTER_NAME, TEST_DATE, TEST_TIME);
        Reservation saved1 = reservationRepository.save(reservation1);
        Reservation saved2 = reservationRepository.save(reservation2);
        Reservation saved3 = reservationRepository.save(reservation3);

        List<Reservation> result = reservationRepository.findAll();

        Assertions.assertEquals(3, result.size());
        clearRepository(saved1, saved2, saved3);
    }

    @Test
    @DisplayName("찾기는 비어 있어도 오류가 발생시키지 않고, 빈 리스트를 반환한다.")
    void findAll_success_when_repository_is_empty() {
        List<Reservation> result = reservationRepository.findAll();

        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("삭제 대상이 존재하면 잘 삭제한다")
    void delete_success() {
        Reservation reservation1 = Reservation.constructWithNoId(TESTER_NAME, TEST_DATE, TEST_TIME);
        Reservation reservation2 = Reservation.constructWithNoId(TESTER_NAME, TEST_DATE, TEST_TIME);
        Reservation reservation3 = Reservation.constructWithNoId(TESTER_NAME, TEST_DATE, TEST_TIME);
        Reservation deleteTarget = reservationRepository.save(reservation1);
        Reservation saved = reservationRepository.save(reservation2);
        Reservation saved2 = reservationRepository.save(reservation3);

        Long deleteTargetId = deleteTarget.id();
        reservationRepository.delete(deleteTargetId);

        Optional<Reservation> deleteTargetFromReservations = findDeleteTargetFromStorage(deleteTargetId);
        Assertions.assertTrue(deleteTargetFromReservations.isEmpty());

        clearRepository(saved, saved2);
    }

    private Optional<Reservation> findDeleteTargetFromStorage(Long deleteTargetId) {
        List<Reservation> leftReservation = reservationRepository.findAll();
        return leftReservation.stream()
                .filter(reservation -> reservation.id().equals(deleteTargetId))
                .findAny();
    }

    @Test
    @DisplayName("삭제 대상이 없으면 오류를 발생시킨다")
    void delete_throw_exception_when_target_is_not_exist() {
        assertThatThrownBy(
                () -> reservationRepository.delete(1L)
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("삭제 대상이 존재하지 않습니다");
    }

    private void clearRepository(Reservation... reservations) {
        for (Reservation reservation : reservations) {
            reservationRepository.delete(reservation.id());
        }
    }
}
