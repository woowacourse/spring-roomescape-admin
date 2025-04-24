package roomescape.service;

import java.util.List;
import roomescape.dto.CreateReservationDto;
import roomescape.entity.Reservation;
import roomescape.entity.ReservationTime;
import roomescape.repository.reservation.ReservationRepository;
import roomescape.repository.reservationtime.ReservationTimeRepository;

public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationService(ReservationRepository reservationRepository,
                              ReservationTimeRepository reservationTimeRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation createReservation(CreateReservationDto createReservationDto) {
        ReservationTime time = reservationTimeRepository.findById(createReservationDto.timeId());
        Reservation reservation = new Reservation(
                createReservationDto.name(),
                createReservationDto.date(),
                time
        );

        Long id = reservationRepository.addAndGetId(reservation);
        return reservationRepository.findById(id);
    }

    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }
}
