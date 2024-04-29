package roomescape.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeCreateRequest;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationTimeDaoTest {
    @Autowired
    ReservationTimeDao reservationTimeDao;

    ReservationTimeCreateRequest request = new ReservationTimeCreateRequest(LocalTime.parse("17:15"));

    @Test
    void insert_Test() {
        reservationTimeDao.insert(request);
        assertThat(reservationTimeDao.findAll().size()).isEqualTo(1);
    }

    @Test
    void findById_Test() {
        ReservationTime reservationTime=new ReservationTime(1L, LocalTime.parse("17:15"));
        reservationTimeDao.insert(request);
        assertThat(reservationTimeDao.findById(1L)).usingRecursiveComparison().isEqualTo(reservationTime);
    }

    @Test
    void delete_Test(){
        reservationTimeDao.insert(request);
        reservationTimeDao.delete(1L);
        assertThat(reservationTimeDao.findAll().size()).isEqualTo(0);
    }

    @Test
    void findAll_Test(){
        reservationTimeDao.insert(request);
        reservationTimeDao.insert(request);
        assertThat(reservationTimeDao.findAll().size()).isEqualTo(2);
    }

}
