package roomescape.dao;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.dto.ReservationRequest;

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
        ReservationRequest reservationRequest = new ReservationRequest("aa", "2023-10-10", "10:00");
        //when
        fakeReservationDao.save(reservationRequest);
        //then
        assertThat(fakeReservationDao.findAll()).hasSize(1);
    }

    @DisplayName("해당 id의 도메인이 존재하는지 판별한다.")
    @Test
    void existsById() {
        //given
        ReservationRequest reservationRequest = new ReservationRequest("aa", "2023-10-10", "10:00");
        //when
        fakeReservationDao.save(reservationRequest);
        //then
        assertThat(fakeReservationDao.existsById(1)).isTrue();
    }

    @DisplayName("해당 id의 도메인을 삭제한다.")
    @Test
    void deleteById() {
        //given
        ReservationRequest reservationRequest = new ReservationRequest("aa", "2023-10-10", "10:00");
        fakeReservationDao.save(reservationRequest);
        //when
        fakeReservationDao.deleteById(1);
        //then
        assertThat(fakeReservationDao.existsById(1)).isFalse();
    }
}
