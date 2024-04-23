package roomescape.dao;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.dao.fakedao.FakeReservationTimeDao;

class FakeReservationTimeDaoTest {
    private ReservationTimeDao reservationTimeDao;

    @BeforeEach
    void setUp() {
        reservationTimeDao = new FakeReservationTimeDao();
    }

    @DisplayName("모든 예약 시간을 보여준다")
    @Test
    void findAll() {
        assertThat(reservationTimeDao.findAll()).isEmpty();
    }

    @DisplayName("예약 시간을 저장한다.")
    @Test
    void save() {
        //when
        reservationTimeDao.save("10:00");
        //then
        assertThat(reservationTimeDao.findAll()).hasSize(1);
    }

    @DisplayName("해당 id의 예약 시간을 보여준다.")
    @Test
    void findById() {
        //when
        reservationTimeDao.save("10:00");
        //then
        assertThat(reservationTimeDao.findById(1).getStartAt()).isEqualTo("10:00");
    }

    @DisplayName("해당 id의 예약 시간을 삭제한다.")
    @Test
    void deleteById() {
        //given
        reservationTimeDao.save("10:00");
        //when
        reservationTimeDao.deleteById(1);
        //then
        assertThat(reservationTimeDao.findAll()).isEmpty();
    }

    @DisplayName("삭제 대상이 존재하면 true를 반환한다.")
    @Test
    void returnTrueWhenDeleted() {
        //given
        reservationTimeDao.save("10:00");
        //when
        boolean deleted = reservationTimeDao.deleteById(1);
        //then
        assertThat(deleted).isTrue();
    }

    @DisplayName("삭제 대상이 존재하지 않으면 false를 반환한다.")
    @Test
    void returnFalseWhenNotDeleted() {
        //when
        boolean deleted = reservationTimeDao.deleteById(1);
        //then
        assertThat(deleted).isFalse();
    }
}
