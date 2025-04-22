package roomescape.reservation;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class InMemoryReservationDaoTest {
    LocalDate date = LocalDate.of(2025, 4, 22);
    LocalTime time = LocalTime.of(10, 0);

    Reservation mimiReservation = new Reservation(1L, "mimi", date, time);
    Reservation norangReservation = new Reservation(2L, "norang", date, time);
    Reservation mintReservation = new Reservation(3L, "mint", date, time);


    @DisplayName("예약 정보를 저장할 수 있다.")
    @Test
    void test1() {
        //given
        InMemoryReservationDao inMemoryReservationRepository = new InMemoryReservationDao();

        //when
        Reservation savedReservation = inMemoryReservationRepository.add(mimiReservation);

        //then
        assertThat(savedReservation).isEqualTo(mimiReservation);
    }

    @DisplayName("예약 정보를 전부 조회할 수 있다.")
    @Test
    void test3() {
        //given
        InMemoryReservationDao inMemoryReservationRepository = new InMemoryReservationDao(List.of(
                mimiReservation, norangReservation, mintReservation
        ));

        //when
        List<Reservation> all = inMemoryReservationRepository.getAll();

        //then
        assertThat(all).hasSize(3);
        assertThat(all.contains(mimiReservation)).isTrue();
        assertThat(all.contains(norangReservation)).isTrue();
        assertThat(all.contains(mintReservation)).isTrue();
    }

    @DisplayName("id 해당하는 예약 정보를 삭제할 수 있다.")
    @Test
    void test4() {
        //given
        InMemoryReservationDao inMemoryReservationRepository = new InMemoryReservationDao(new ArrayList<>(List.of(
                mimiReservation, norangReservation, mintReservation
        )));

        Long mimiId = 1L;
        InMemoryReservationDao expectedInMemoryReservationRepository = new InMemoryReservationDao(List.of(
                norangReservation, mintReservation
        ));

        //when
        inMemoryReservationRepository.deleteById(mimiId);

        //then
        assertThat(inMemoryReservationRepository).isEqualTo(expectedInMemoryReservationRepository);
    }

    @DisplayName("존재하지 않는 id의 예약 정보를 삭제하려는 경우 예외가 발생한다.")
    @Test
    void test5() {
        //given
        InMemoryReservationDao inMemoryReservationRepository = new InMemoryReservationDao(List.of(
                new Reservation("mimi", date, time),
                new Reservation("norang", date, time),
                new Reservation("mint", date, time)
        ));
        Long notExistedId = 4L;

        //when & then
        assertThatThrownBy(() -> inMemoryReservationRepository.deleteById(notExistedId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 존재하지 않는 id 입니다.");
    }
}
