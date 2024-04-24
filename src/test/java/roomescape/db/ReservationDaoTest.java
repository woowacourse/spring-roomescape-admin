package roomescape.db;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.StatusResultMatchersExtensionsKt.isEqualTo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cglib.core.Local;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

@SpringBootTest
class ReservationDaoTest {
    @Autowired
    ReservationDao reservationDao;

    @Autowired
    ReservationTimeDao reservationTimeDao;

    @Test
    void findTimeId() {
    }

    @Test
    void testFindTimeId() {
        Optional<Reservation> timeId = reservationDao.findTimeId(1);
    }
}
