package roomescape.service;

import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.dao.ReservationTimeDAO;
import roomescape.dto.ReservationTimeReqDto;
import roomescape.dto.ReservationTimeResDto;
import roomescape.fixture.ReservationTimeDAOFixture;

import static org.assertj.core.api.Assertions.assertThat;

class ReservationTimeServiceTest {

    private final ReservationTimeDAO reservationTimeDAO = new ReservationTimeDAOFixture(new JdbcTemplate());
    private final ReservationTimeService reservationTimeService = new ReservationTimeService(reservationTimeDAO);

    @Test
    @DisplayName("전체 시간 데이터를 조회해 응답 DTO 객체로 반환한다")
    void findAllReservationTimes() {
        // given
        LocalTime startAt = LocalTime.of(10, 0);
        reservationTimeDAO.addAndGet(startAt);

        // when
        List<ReservationTimeResDto> actual = reservationTimeService.findAll();
        List<ReservationTimeResDto> expected = List.of(new ReservationTimeResDto(1L, startAt));

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("요청 DTO를 새 데이터로 저장하고, 생성된 데이터를 응답 DTO 객체로 반환한다")
    void addAndGetReservationTime() {
        // given
        LocalTime startAt = LocalTime.of(10, 0);

        // when
        ReservationTimeReqDto request = new ReservationTimeReqDto(startAt);
        ReservationTimeResDto actual = reservationTimeService.addAndGet(request);
        ReservationTimeResDto expected = new ReservationTimeResDto(1L, startAt);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("요청으로 들어온 id에 해당하는 데이터를 삭제한다")
    void deleteReservationTime() {
        // given
        LocalTime startAt = LocalTime.of(10, 0);
        reservationTimeDAO.addAndGet(startAt);
        long id = 1L;

        // when
        reservationTimeService.deleteById(id);
        List<ReservationTimeResDto> actual = reservationTimeService.findAll();

        // then
        assertThat(actual.size()).isEqualTo(0);
    }
}
