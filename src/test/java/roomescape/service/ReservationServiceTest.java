package roomescape.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.dao.ReservationDAO;
import roomescape.dto.ReservationReqDto;
import roomescape.dto.ReservationResDto;
import roomescape.dto.ReservationTimeResDto;
import roomescape.fixture.ReservationDAOFixture;

import static org.assertj.core.api.Assertions.assertThat;

class ReservationServiceTest {

    private final ReservationDAO reservationDAO = new ReservationDAOFixture(new JdbcTemplate());
    private final ReservationService reservationService = new ReservationService(reservationDAO);

    @Test
    @DisplayName("전체 예약 데이터를 조회해 응답 DTO 객체로 반환한다")
    void findAllReservations() {
        // given
        String name = "브라운";
        LocalDate date = LocalDate.of(2024, 8, 10);
        long timeId = 1;
        LocalTime startAt = LocalTime.of(10, 0);
        reservationDAO.addAndGet(name, date, 1);

        // when
        List<ReservationResDto> actual = reservationService.findAll();
        List<ReservationResDto> expected = List.of(new ReservationResDto(1L, name, date, new ReservationTimeResDto(timeId, startAt)));

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("요청 DTO를 새 데이터로 저장하고, 생성된 데이터를 응답 DTO 객체로 반환한다")
    void addAndGetReservation() {
        // given
        String name = "브라운";
        LocalDate date = LocalDate.of(2024, 8, 10);
        long timeId = 1;
        LocalTime startAt = LocalTime.of(10, 0);

        // when
        ReservationReqDto request = new ReservationReqDto(name, date, timeId);
        ReservationResDto actual = reservationService.addAndGet(request);
        ReservationResDto expected = new ReservationResDto(1L, name, date, new ReservationTimeResDto(timeId, startAt));

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("요청으로 들어온 id에 해당하는 데이터를 삭제한다")
    void deleteReservation() {
        // given
        String name = "브라운";
        LocalDate date = LocalDate.of(2024, 8, 10);
        reservationDAO.addAndGet(name, date, 1);
        long id = 1L;

        // when
        reservationService.deleteById(id);
        List<ReservationResDto> actual = reservationService.findAll();

        // then
        assertThat(actual.size()).isEqualTo(0);
    }
}
