package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationDateTime;
import roomescape.entity.ReservationEntity;

class MemoryReservationRepositoryTest {
    private final LocalDate RESERVATION_DATE = LocalDate.of(2025, 1, 2);
    private final LocalTime RESERVATION_TIME = LocalTime.of(9, 0);

    private ReservationDateTime RESERVATION_DATE_TIME;
    private MemoryReservationRepository memoryReservationRepository;

    @BeforeEach
    void setUp() {
        memoryReservationRepository = new MemoryReservationRepository();
        RESERVATION_DATE_TIME = ReservationDateTime.of(RESERVATION_DATE, RESERVATION_TIME);
    }

    @Test
    void 예약을_조회한다() {
        // given
        Reservation reservation = new Reservation("name1", RESERVATION_DATE_TIME);
        memoryReservationRepository.add(reservation);
        // when
        List<ReservationEntity> reservations = memoryReservationRepository.getReservations();

        // then
        assertThat(reservations).contains(new ReservationEntity(
                1L,
                "name1",
                RESERVATION_DATE,
                RESERVATION_TIME
        ));
    }

    @Test
    void 예약이_존재하는지_확인한다() {
        // given
        Reservation reservation = new Reservation("name1", RESERVATION_DATE_TIME);
        memoryReservationRepository.add(reservation);
        // when & then
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(memoryReservationRepository.existReservation(1L)).isTrue();
        softly.assertThat(memoryReservationRepository.existReservation(2L)).isFalse();
        softly.assertAll();
    }

    @Test
    void 예약을_한개_추가한다() {
        // given
        Reservation reservation = new Reservation("name1", RESERVATION_DATE_TIME);
        // when
        memoryReservationRepository.add(reservation);
        // then
        List<ReservationEntity> reservations = memoryReservationRepository.getReservations();
        assertThat(reservations).hasSize(1);
    }

    @Test
    void 예약을_여러개_추가한다() {
        // given
        Reservation reservation1 = new Reservation("name1", RESERVATION_DATE_TIME);
        Reservation reservation2 = new Reservation("name2", RESERVATION_DATE_TIME);
        // when
        memoryReservationRepository.add(reservation1);
        memoryReservationRepository.add(reservation2);
        // then
        List<ReservationEntity> reservations = memoryReservationRepository.getReservations();
        assertThat(reservations).contains(
                new ReservationEntity(1L, "name1", RESERVATION_DATE, RESERVATION_TIME),
                new ReservationEntity(2L, "name2", RESERVATION_DATE, RESERVATION_TIME)
        );
    }

    @Test
    void 예약을_삭제한다() {
        // given
        Reservation reservation = new Reservation("name1", RESERVATION_DATE_TIME);
        memoryReservationRepository.add(reservation);
        // when
        memoryReservationRepository.deleteById(1L);

        // then
        assertThat(memoryReservationRepository.getReservations()).hasSize(0);
    }
}