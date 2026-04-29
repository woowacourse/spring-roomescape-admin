package roomescape.reservation.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.reservation.domain.Reservation;
import roomescape.time.domain.ReservationTime;

class InMemoryReservationRepositoryTest {
    private InMemoryReservationRepository inMemoryReservationRepository;

    @BeforeEach
    void setup() {
        inMemoryReservationRepository = new InMemoryReservationRepository();
        inMemoryReservationRepository.save(new Reservation(
                inMemoryReservationRepository.generateId(), "한다", LocalDate.of(2023, 8, 5),
                new ReservationTime(1L, LocalTime.of(15, 40))));
        inMemoryReservationRepository.save(new Reservation(
                inMemoryReservationRepository.generateId(), "판다", LocalDate.of(2023, 10, 5),
                new ReservationTime(2L, LocalTime.of(15, 40))));
    }

    @Test
    @DisplayName("모든 예약 정보를 조회한다.")
    void findAll() {
        assertThat(inMemoryReservationRepository.findAll().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("예약을 추가한다.")
    void save() {
        //given & when
        inMemoryReservationRepository.save(
                new Reservation(3L, "새로운사람", LocalDate.of(2023, 6, 5), new ReservationTime(3L, LocalTime.of(12, 0))));

        //then
        assertThat(inMemoryReservationRepository.findAll().size()).isEqualTo(3);
    }

    @Test
    @DisplayName("예약 추가시 중복되는 아이디가 존재하면 예외가 발생한다.")
    void save_id_already_exists() {
        assertThatThrownBy(() -> inMemoryReservationRepository.save(
                new Reservation(1L, "새로운사람", LocalDate.of(2023, 6, 5), new ReservationTime(3L, LocalTime.of(12, 0)))))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("중복된 예약 id가 존재합니다.");
    }

    @Test
    @DisplayName("예약을 삭제한다.")
    void delete() {
        //given & when
        inMemoryReservationRepository.delete(2L);

        //then
        assertThat(inMemoryReservationRepository.findAll().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("존재하지 않는 예약을 삭제하면 예외가 발생한다. ")
    void delete_does_not_exists() {
        assertThatThrownBy(() -> inMemoryReservationRepository.delete(3L))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("존재하지 않는 예약입니다.");
    }

    @Test
    @DisplayName("새 Id를 발급받는다.")
    void generateId() {
        //given
        Long expected = 3L;

        //when
        Long actual = inMemoryReservationRepository.generateId();

        //then
        assertThat(expected).isEqualTo(actual);
    }
}