package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.entity.Reservation;
import roomescape.repository.rowmapper.ReservationRowMapper;
import roomescape.repository.rowmapper.ReservationTimeRowMapper;

@JdbcTest
@Import(value = {ReservationDao.class, ReservationTimeDao.class, ReservationRowMapper.class,
        ReservationTimeRowMapper.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationDaoTest {

    @Autowired
    private ReservationDao reservationDao;

    @Autowired
    private ReservationTimeDao timeDao;

    @DisplayName("예약을 정상적으로 추가한다.")
    @Test
    void save() {
        // given
        timeDao.save(new ReservationTimeRegisterDetail("10:00"));

        // when
        Reservation saved = reservationDao.save(new ReservationRegisterDetail(
                "상돌", "2024-04-25", 1L, "10:00"
        ));

        // then
        assertThat(saved.getId()).isEqualTo(1L);
        assertThat(saved.getName()).isEqualTo("상돌");
        assertThat(saved.getDate()).isEqualTo("2024-04-25");
        assertThat(saved.getTime().getId()).isEqualTo(1);
        assertThat(saved.getTime().getStartAt()).isEqualTo("10:00");
    }

    @DisplayName("모든 예약을 조회한다.")
    @Test
    void findAll() {
        // given
        timeDao.save(new ReservationTimeRegisterDetail("10:00"));
        timeDao.save(new ReservationTimeRegisterDetail("11:00"));

        // when
        reservationDao.save(new ReservationRegisterDetail(
                "상돌", "2024-04-25", 1L, "10:00"
        ));
        reservationDao.save(new ReservationRegisterDetail(
                "상돌1", "2024-04-24", 2L, "11:00"
        ));

        // then
        List<Reservation> reservations = reservationDao.findAll();
        assertThat(reservations).hasSize(2);
        assertThat(reservations).extracting("id").containsExactly(1L, 2L);
        assertThat(reservations).extracting("name").containsExactly("상돌", "상돌1");
        assertThat(reservations).extracting("date").contains("2024-04-25", "2024-04-24");
        assertThat(reservations).extracting("time.startAt").contains("10:00", "11:00");
    }

    @DisplayName("ID로 시간을 제거한다.")
    @Test
    void delete() {
        // given
        timeDao.save(new ReservationTimeRegisterDetail("10:00"));

        // when
        Reservation saved = reservationDao.save(new ReservationRegisterDetail(
                "상돌", "2024-04-25", 1L, "10:00"
        ));
        int sizeBeforeDelete = reservationDao.findAll().size();

        reservationDao.deleteById(saved.getId());
        int sizeAfterDelete = reservationDao.findAll().size();

        // then
        assertThat(sizeBeforeDelete).isNotEqualTo(sizeAfterDelete);
    }
}
