package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import roomescape.controller.dto.ReservationCreateRequest;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationMemoryRepository;
import roomescape.util.CustomDateTimeFormatter;

class ReservationServiceTest {

    private static final ReservationTime DEFAULT_RESERVATION_TIME = new ReservationTime(1L, LocalTime.of(10, 0));
    private static final ReservationCreateRequest DEFAULT_RESERVATION_REQUEST = new ReservationCreateRequest(
            "2024-11-26",
            "미르",
            1L
    );
    private final ReservationMemoryRepository reservationRepository = new ReservationMemoryRepository(
            new ArrayList<>(),
            List.of(DEFAULT_RESERVATION_TIME),
            new AtomicLong(1)
    );

    private final ReservationService reservationService = new ReservationService(reservationRepository);

    @AfterEach
    void cleanUp() {
        reservationRepository.cleanUp();
    }

    @DisplayName("예약 전체 조회 테스트")
    @Nested
    class FindAllReservationsTest {
        @DisplayName("존재하는 모든 예약을 조회할 수 있다.")
        @Test
        void findAllReservationTest() {
            ReservationCreateRequest reservationRequest1 = new ReservationCreateRequest(
                    "2024-04-22", "미르", 1
            );
            ReservationCreateRequest reservationRequest2 = new ReservationCreateRequest(
                    "2024-04-23", "미르", 1
            );
            ReservationCreateRequest reservationRequest3 = new ReservationCreateRequest(
                    "2024-04-24", "미르", 1
            );
            Long reservationId1 = reservationRepository.createReservation(reservationRequest1);
            Long reservationId2 = reservationRepository.createReservation(reservationRequest2);
            Long reservationId3 = reservationRepository.createReservation(reservationRequest3);

            List<Reservation> reservations = reservationService.findReservations();

            org.junit.jupiter.api.Assertions.assertAll(
                    () -> assertThat(reservations).hasSize(3),
                    () -> assertThat(reservations).containsExactly(
                            new Reservation(
                                    reservationId1,
                                    reservationRequest1.name(),
                                    CustomDateTimeFormatter.getLocalDate(reservationRequest1.date()),
                                    DEFAULT_RESERVATION_TIME
                            ),
                            new Reservation(
                                    reservationId2,
                                    reservationRequest2.name(),
                                    CustomDateTimeFormatter.getLocalDate(reservationRequest2.date()),
                                    DEFAULT_RESERVATION_TIME
                            ),
                            new Reservation(
                                    reservationId3,
                                    reservationRequest3.name(),
                                    CustomDateTimeFormatter.getLocalDate(reservationRequest3.date()),
                                    DEFAULT_RESERVATION_TIME
                            )
                    )
            );
        }
    }

    @DisplayName("예약 단건 조회 테스트")
    @Nested
    class FindReservationTest {
        @DisplayName("존재하는 예약을 Id로 조회할 수 있다.")
        @Test
        void findReservationTest() {
            Long createdReservationId = reservationRepository.createReservation(DEFAULT_RESERVATION_REQUEST);

            Optional<Reservation> reservationById = reservationService.findReservationById(createdReservationId);
            Assertions.assertAll(
                    () -> assertThat(reservationById.isPresent()).isTrue(),
                    () -> assertThat(reservationById.get()).isEqualTo(
                            new Reservation(createdReservationId,
                                    DEFAULT_RESERVATION_REQUEST.name(),
                                    CustomDateTimeFormatter.getLocalDate(DEFAULT_RESERVATION_REQUEST.date()),
                                    DEFAULT_RESERVATION_TIME)
                    )
            );
        }
    }

    @DisplayName("예약을 추가할 수 있다.")
    @Nested
    class CreateReservationTest {
        @DisplayName("예약을 정상적으로 추가할 수 있다.")
        @Test
        void createReservationTest() {
            Long createdReservationId = reservationService.createReservation(DEFAULT_RESERVATION_REQUEST);
            Optional<Reservation> createdReservation = reservationRepository.findReservationById(createdReservationId);
            Assertions.assertAll(
                    () -> assertThat(createdReservation.isPresent()).isTrue(),
                    () -> assertThat(createdReservation.get()).isEqualTo(
                            new Reservation(createdReservationId,
                                    DEFAULT_RESERVATION_REQUEST.name(),
                                    CustomDateTimeFormatter.getLocalDate(DEFAULT_RESERVATION_REQUEST.date()),
                                    DEFAULT_RESERVATION_TIME)
                    )
            );
        }
    }

    @DisplayName("예약을 삭제할 수 있다.")
    @Nested
    class DeleteReservationTest {
        @DisplayName("존재하는 예약을 Id로 삭제할 수 있다.")
        @Test
        void deleteReservationByIdTest() {
            Long deleteReservationId = reservationRepository.createReservation(DEFAULT_RESERVATION_REQUEST);

            reservationService.deleteReservationById(deleteReservationId);
            assertThat(reservationService.findReservationById(deleteReservationId).isPresent()).isFalse();
        }
    }
}
