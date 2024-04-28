package roomescape.dao;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.console.fake.FakeReservationDao;
import roomescape.console.fake.InMemoryReservationDb;
import roomescape.console.fake.InMemoryReservationTimeDb;

class FakeReservationDaoTest {
    private InMemoryReservationTimeDb inMemoryReservationTimeDb;
    private ReservationDao reservationDao;

    @BeforeEach
    void setUp() {
        InMemoryReservationDb inMemoryReservationDb = new InMemoryReservationDb();
        inMemoryReservationTimeDb = new InMemoryReservationTimeDb();
        reservationDao = new FakeReservationDao(inMemoryReservationDb, inMemoryReservationTimeDb);
    }

    @DisplayName("존재하는 모든 예약을 보여준다.")
    @Test
    void findAll() {
        assertThat(reservationDao.findAll()).isEmpty();
    }

    @DisplayName("예약을 저장한다.")
    @Test
    void save() {
        //given
        inMemoryReservationTimeDb.insert("10:00");
        //when
        reservationDao.save("aa", "2023-10-10", 1);
        //then
        assertThat(reservationDao.findAll()).hasSize(1);
    }

    @DisplayName("해당 id의 예약을 삭제한다.")
    @Test
    void deleteById() {
        //given
        inMemoryReservationTimeDb.insert("10:00");
        reservationDao.save("aa", "2023-10-10", 1);
        //when
        reservationDao.deleteById(1);
        //then
        assertThat(reservationDao.findAll()).hasSize(0);
    }

    @DisplayName("삭제 대상이 존재하면 true를 반환한다.")
    @Test
    void returnTrueWhenDeleted() {
        //given
        inMemoryReservationTimeDb.insert("10:00");
        reservationDao.save("aa", "2023-10-10", 1);
        //when
        boolean deleted = reservationDao.deleteById(1);
        //then
        assertThat(deleted).isTrue();
    }

    @DisplayName("삭제 대상이 존재하지 않으면 false를 반환한다.")
    @Test
    void returnFalseWhenNotDeleted() {
        //when
        boolean deleted = reservationDao.deleteById(1);
        //then
        assertThat(deleted).isFalse();
    }
}
