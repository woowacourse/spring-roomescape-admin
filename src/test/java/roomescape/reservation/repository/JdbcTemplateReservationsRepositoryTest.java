package roomescape.reservation.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import roomescape.reservation.repository.dto.Reservation;
import roomescape.time.repository.JdbcTemplateTimesRepository;
import roomescape.time.repository.TimeEntity;
import roomescape.time.repository.TimesRepository;

@JdbcTest
@Transactional
class JdbcTemplateReservationsRepositoryTest {

    @Autowired JdbcTemplate jdbcTemplate;

    JdbcTemplateReservationsRepository reservationsRepository;
    TimesRepository timesRepository;

    Long timeId;

    @BeforeEach
    void beforeEach() {
        reservationsRepository = new JdbcTemplateReservationsRepository(jdbcTemplate);
        timesRepository = new JdbcTemplateTimesRepository(jdbcTemplate);

        TimeEntity timeEntity = timesRepository.saveTime(
                TimeEntity.of(LocalTime.of(10, 0))
        );
        timeId = timeEntity.id();
    }

    @DisplayName("기본적으로는 아무런 예약도 존재하지 않는다.")
    @Test
    void findAllReservationsWithTime_empty() {
        //when
        List<Reservation> reservationsWithTime = reservationsRepository.findAllReservationsWithTime();

        //then
        assertThat(reservationsWithTime).isEmpty();
    }

    @DisplayName("새로운 예약을 추가한다.")
    @Test
    void saveReservation() {
        //given
        ReservationEntity entity1 = ReservationEntity.of(
                "name1", Date.valueOf(LocalDate.now()),
                timeId
        );
        ReservationEntity entity2 = ReservationEntity.of(
                "name2",
                Date.valueOf(LocalDate.now()),
                timeId
        );
        ReservationEntity entity3 = ReservationEntity.of(
                "name3",
                Date.valueOf(LocalDate.now()),
                timeId
        );

        //when
        reservationsRepository.saveReservation(entity1);
        reservationsRepository.saveReservation(entity2);
        reservationsRepository.saveReservation(entity3);

        //then
        assertThat(
                reservationsRepository.findAllReservationsWithTime().size()
        ).isEqualTo(3);
    }

    @DisplayName("id에 해당하는 예약을 삭제한다.")
    @Test
    void deleteReservationById_success() {
        //given
        ReservationEntity entity = ReservationEntity.of(
                "name1",
                Date.valueOf(LocalDate.now()),
                timeId
        );
        ReservationEntity entityWithId = reservationsRepository.saveReservation(entity);

        //when
        reservationsRepository.deleteReservationById(entityWithId.id());

        //then
        assertThat(reservationsRepository.findAllReservationsWithTime()).isEmpty();
    }

    @DisplayName("id에 해당하는 예약이 없으면 예외가 발생한다.")
    @Test
    void deleteReservationById_fail() {
        //when & then
        assertThatThrownBy(() -> reservationsRepository.deleteReservationById(timeId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 예약은 존재하지 않습니다.");
    }
}
