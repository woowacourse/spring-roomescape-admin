package roomescape.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.domain.Reservation;

public class FakeReservationDaoTest {
    private ReservationDao fakeReservationDao;

    @BeforeEach
    void init() {
        fakeReservationDao = new FakeReservationDao();
    }

    @DisplayName("존재하는 모든 entity를 보여준다.")
    @Test
    void findAll() {
        assertThat(fakeReservationDao.findAll()).isEmpty();
    }

    @DisplayName("도메인을 저장한다.")
    @Test
    void save() {
        //given
        Reservation reservation = new Reservation(1, "aa", "2023-10-10", "10:00");
        //when
        fakeReservationDao.save(reservation);
        //then
        assertThat(fakeReservationDao.findAll()).hasSize(1);
    }

    @DisplayName("중복되는 id의 도메인을 저장하면 오류가 발생한다.")
    @Test
    void invalidSave() {
        //given
        long id = 1;
        Reservation reservation1 = new Reservation(id, "aa", "2023-10-10", "10:00");
        Reservation reservation2 = new Reservation(id, "bb", "2023-10-20", "11:00");
        //when
        fakeReservationDao.save(reservation1);
        //then
        assertThatThrownBy(() -> fakeReservationDao.save(reservation2))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("duplicated key exists.");
    }

    @DisplayName("해당 id의 도메인을 삭제한다.")
    @Test
    void deleteById() {
        //given
        long id = 1;
        Reservation reservation = new Reservation(id, "aa", "2023-10-10", "10:00");
        fakeReservationDao.save(reservation);
        //when
        fakeReservationDao.deleteById(id);
        //then
        assertThat(fakeReservationDao.findAll()).hasSize(0);
    }
}
