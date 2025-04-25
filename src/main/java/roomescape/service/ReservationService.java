package roomescape.service;

import java.time.LocalDate;
import java.util.List;
import roomescape.dto.CreateReservationDto;
import roomescape.dto.ReservationResponseDto;
import roomescape.entity.Reservation;
import roomescape.entity.ReservationTime;
import roomescape.repository.reservation.ReservationRepository;
import roomescape.repository.reservationtime.ReservationTimeRepository;
import roomescape.util.DateTimeFormatUtils;

public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationService(ReservationRepository reservationRepository,
                              ReservationTimeRepository reservationTimeRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<ReservationResponseDto> getAllReservations() {
        return reservationRepository.findAll().stream()
                .map(ReservationResponseDto::from)
                .toList();
    }

    public ReservationResponseDto createReservation(CreateReservationDto createReservationDto) {
        ReservationTime time = reservationTimeRepository.findById(createReservationDto.timeId());
        Reservation reservation = new Reservation(
                createReservationDto.name(),
                LocalDate.parse(createReservationDto.date(), DateTimeFormatUtils.dateFormatter),
                time
        );

        Long id = reservationRepository.addAndGetId(reservation);
        Reservation resultReservation = reservationRepository.findById(id);
        return ReservationResponseDto.from(resultReservation);
    }

    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }
}
