package roomescape.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;
import roomescape.dto.ReservationTimeResponseDto;
import roomescape.entity.Reservation;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    public List<ReservationResponseDto> getAllReservations() {
        final List<ReservationResponseDto> reservationResponseDtos = new ArrayList<>();
        for (final Reservation reservation : reservationRepository.findAll()) {
            final ReservationTimeResponseDto reservationTimeResponseDto =
                ReservationTimeResponseDto.from(reservationTimeRepository.findById(reservation.getId()));
            reservationResponseDtos.add(ReservationResponseDto.from(reservation, reservationTimeResponseDto));
        }

        return reservationResponseDtos;
    }

    public ReservationResponseDto createReservation(final ReservationRequestDto reservationRequestDto) {
        final Reservation reservation = Reservation.builder()
            .name(reservationRequestDto.name())
            .date(reservationRequestDto.date())
            .timeId(reservationRequestDto.timeId())
            .build();
        final ReservationTimeResponseDto reservationTimeResponseDto =
            ReservationTimeResponseDto.from(reservationTimeRepository.findById(reservationRequestDto.timeId()));


        return ReservationResponseDto.from(reservationRepository.save(reservation), reservationTimeResponseDto);
    }

    public void removeReservation(final long id) {
        reservationRepository.deleteById(id);
    }
}
