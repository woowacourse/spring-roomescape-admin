package roomescape.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationCreateRequest;
import roomescape.dto.ReservationTimeCreateRequest;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationDaoTest {
    @Autowired
    ReservationDao reservationDao;
    @Autowired
    ReservationTimeDao reservationTimeDao;

    ReservationCreateRequest reservationCreateRequest = new ReservationCreateRequest(LocalDate.parse("2022-04-29"), "a", 1L);
    ReservationTimeCreateRequest reservationTimeCreateRequest = new ReservationTimeCreateRequest(LocalTime.parse("17:15"));


    @BeforeEach
    void insertTime(){
        reservationTimeDao.insert(reservationTimeCreateRequest);
    }
    @Test
    void findAll_Test() {
        reservationDao.insert(reservationCreateRequest, reservationTimeCreateRequest.toTime(1L));
        reservationDao.insert(reservationCreateRequest, reservationTimeCreateRequest.toTime(1L));

        assertThat(reservationDao.findAll().size()).isEqualTo(2);
    }

    @Test
    void insert_Test(){
        reservationDao.insert(reservationCreateRequest,reservationTimeCreateRequest.toTime(1L));
        Reservation reservation = reservationCreateRequest.toReservation(1L, reservationTimeCreateRequest.toTime(1L));

        assertThat(reservationDao.findAll().get(0)).usingRecursiveComparison().isEqualTo(reservation);
    }

    @Test
    void delete_Test(){
        reservationDao.insert(reservationCreateRequest,reservationTimeCreateRequest.toTime(1L));
        reservationDao.delete(1L);

        assertThat(reservationDao.findAll().size()).isEqualTo(0);
    }

}
