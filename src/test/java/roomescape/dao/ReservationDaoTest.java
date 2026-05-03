package roomescape.dao;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ReservationDaoTest {

    @Autowired
    ReservationDao reservationDao;

    @Test
    void 존재하지_않는_예약_삭제() {
        assertThatThrownBy(() -> reservationDao.deleteReservation(1L))
                .isInstanceOf(NoSuchElementException.class);
    }

}