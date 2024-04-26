package roomescape.service;

import java.time.LocalDate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import roomescape.general.db.ReservationDao;
import roomescape.general.service.ReservationService;
import roomescape.web.db.ReservationDaoH2Impl;
import roomescape.web.db.ReservationTimeDaoH2Impl;
import roomescape.general.dto.ReservationRequest;


@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationServiceTest {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    @DisplayName("존재하지 않는 아이디면 예외가 발생한다")
    void create() {
        final ReservationDao reservationDaoH2Impl = new ReservationDaoH2Impl(jdbcTemplate);
        final ReservationTimeDaoH2Impl reservationTimeDaoH2Impl = new ReservationTimeDaoH2Impl(jdbcTemplate);
        final ReservationRequest reservationRequest = new ReservationRequest("a", LocalDate.now(), 1L);
        final ReservationService reservationService = new ReservationService(reservationDaoH2Impl,
                reservationTimeDaoH2Impl);

        Assertions.assertThatThrownBy(() -> reservationService.create(reservationRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 time id입니다.");
    }

    @Test
    @DisplayName("값이 null이면 예외가 발생한다")
    void createNotNull() {
        final ReservationDao reservationDaoH2Impl = new ReservationDaoH2Impl(jdbcTemplate);
        final ReservationTimeDaoH2Impl reservationTimeDaoH2Impl = new ReservationTimeDaoH2Impl(jdbcTemplate);
        final ReservationService reservationService = new ReservationService(reservationDaoH2Impl,
                reservationTimeDaoH2Impl);

        Assertions.assertThatThrownBy(() -> reservationService.create(null))
                .isInstanceOf(NullPointerException.class);
    }
}
