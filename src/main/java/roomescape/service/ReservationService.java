package roomescape.service;

import java.time.LocalDate;
import java.util.List;
import roomescape.entity.Reservation;
import roomescape.entity.ReservationTime;
import roomescape.persistence.repository.reservation.ReservationRepository;
import roomescape.persistence.repository.reservationtime.ReservationTimeRepository;
import roomescape.presentation.dto.CreateReservationDto;
import roomescape.presentation.dto.ReservationResponseDto;
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
        LocalDate date = DateTimeFormatUtils.formatDateFrom(createReservationDto.date());
        Reservation reservation = new Reservation(createReservationDto.name(), date, time);

        Long id = reservationRepository.addAndGetId(reservation);
        return ReservationResponseDto.fromIdAndReservation(id, reservation);
    }

    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }
}
