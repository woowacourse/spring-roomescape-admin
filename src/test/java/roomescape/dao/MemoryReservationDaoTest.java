package roomescape.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.domain.ReservationCreationRequest;
import roomescape.service.dto.ReservationCreationDto;

class MemoryReservationDaoTest {
    private final ReservationDao reservationDao = new MemoryReservationDao();

    @BeforeEach
    void setUp() {
        reservationDao.deleteAll();
        ReservationCreationRequest defaultReservation = new ReservationCreationRequest(
                "브라운", LocalDate.MAX, LocalTime.now()
        );
        reservationDao.add(ReservationCreationDto.from(defaultReservation));
    }

    @DisplayName("모든 예약을 조회한다.")
    @Test
    void findAllTest() {
        assertThat(reservationDao.findAll()).hasSize(1);
    }

    @DisplayName("예약을 추가한다.")
    @Test
    void addTest() {
        ReservationCreationRequest request = new ReservationCreationRequest(
                "페드로", LocalDate.MAX, LocalTime.now()
        );
        reservationDao.add(ReservationCreationDto.from(request));

        assertThat(reservationDao.isExist(2L)).isTrue();
    }

    @DisplayName("예약을 삭제한다.")
    @Test
    void deleteTest() {
        reservationDao.delete(1L);
        assertThat(reservationDao.isExist(1L)).isFalse();
    }
}
