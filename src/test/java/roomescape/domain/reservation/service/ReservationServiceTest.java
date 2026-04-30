package roomescape.domain.reservation.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import roomescape.domain.reservation.domain.Reservation;
import roomescape.domain.reservation.dto.request.ReservationCreateRequestDTO;
import roomescape.domain.reservation.dto.response.ReservationResponseDTO;
import roomescape.domain.reservation.repository.InMemoryReservationRepository;
import roomescape.domain.reservation.repository.ReservationRepository;

class ReservationServiceTest {

    private final ReservationService reservationService;
    private final ReservationRepository reservationRepository;

    ReservationServiceTest() {
        this.reservationRepository = new InMemoryReservationRepository();
        this.reservationService = new ReservationService(reservationRepository);
    }

    @Nested
    class GetReservationTest {

        @Test
        void 성공() {

            // given
            LocalDate date = LocalDate.of(2026, 4, 30);
            LocalTime time = LocalTime.of(10, 0);

            reservationRepository.save(new Reservation("제이콥", date, time));
            reservationRepository.save(new Reservation("라이", date.plusDays(1), time.plusHours(1)));
            reservationRepository.save(new Reservation("티모", date.plusDays(2), time.plusHours(2)));

            // when
            List<ReservationResponseDTO> actual = reservationService.getReservations();

            // then
            assertAll(
                () -> assertEquals(3, actual.size()),
                () -> assertEquals(new ReservationResponseDTO(1L, "제이콥", date, time), actual.get(0)),
                () -> assertEquals(new ReservationResponseDTO(2L, "라이", date.plusDays(1), time.plusHours(1)),
                    actual.get(1)),
                () -> assertEquals(new ReservationResponseDTO(3L, "티모", date.plusDays(2), time.plusHours(2)),
                    actual.get(2))
            );
        }
    }

    @Nested
    class SaveReservationTest {

        @Test
        void 성공() {

            // given
            ReservationCreateRequestDTO request = new ReservationCreateRequestDTO(
                "보예",
                LocalDate.of(2026, 5, 1),
                LocalTime.of(15, 30)
            );

            // when
            ReservationResponseDTO actual = reservationService.saveReservation(request);

            // then
            assertAll(
                () -> assertEquals(1L, actual.id()),
                () -> assertEquals("보예", actual.name()),
                () -> assertEquals(LocalDate.of(2026, 5, 1), actual.date()),
                () -> assertEquals(LocalTime.of(15, 30), actual.time()),
                () -> assertEquals(List.of(actual), reservationService.getReservations())
            );
        }
    }

    @Nested
    class DeleteReservationByIdTest {

        @Test
        void 성공() {

            // given
            Reservation savedReservation = reservationRepository.save(
                new Reservation("제이슨", LocalDate.of(2026, 5, 2), LocalTime.of(12, 0))
            );
            reservationRepository.save(new Reservation("시오", LocalDate.of(2026, 5, 3), LocalTime.of(13, 0)));

            // when
            reservationService.deleteReservationById(savedReservation.getId());

            // then
            List<ReservationResponseDTO> actual = reservationService.getReservations();
            assertAll(
                () -> assertEquals(1, actual.size()),
                () -> assertEquals("시오", actual.getFirst().name())
            );

        }
    }
}
