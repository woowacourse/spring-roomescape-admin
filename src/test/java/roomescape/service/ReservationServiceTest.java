package roomescape.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationCreateRequestDto;
import roomescape.dto.ReservationResponseDto;
import roomescape.dto.ReservationTimeResponseDto;
import roomescape.repository.MemoryReservationRepository;
import roomescape.repository.MemoryReservationTimeRepository;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ReservationServiceTest {

    ReservationService reservationService;

    ReservationRepository reservationRepository = new MemoryReservationRepository(new ArrayList<>());
    ReservationTimeRepository reservationTimeRepository = new MemoryReservationTimeRepository(List.of(new ReservationTime(1L, LocalTime.now())));

    @DisplayName("요청에 따라 Reservation을 생성 할 수 있다")
    @Test
    void createReservationTest() {
        reservationService = new ReservationService(reservationRepository, reservationTimeRepository);
        ReservationCreateRequestDto requestDto = new ReservationCreateRequestDto("가이온", LocalDate.now(), 1L);

        ReservationResponseDto responseDto = reservationService.createReservation(requestDto);

        Long id = responseDto.id();
        LocalDate date = responseDto.date();
        String name = requestDto.name();

        ReservationTimeResponseDto time = responseDto.time();
        Long timeId = time.id();

        Assertions.assertAll(
                () -> assertThat(id).isEqualTo(1L),
                () -> assertThat(date).isEqualTo(requestDto.date()),
                () -> assertThat(name).isEqualTo("가이온"),
                () -> assertThat(timeId).isEqualTo(1L)
        );
    }
}
