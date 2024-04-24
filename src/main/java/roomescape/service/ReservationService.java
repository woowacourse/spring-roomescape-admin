package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.model.ReservationDto;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;
import roomescape.model.Reservation;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationService(ReservationRepository reservationRepository,
                              ReservationTimeRepository reservationTimeRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<Reservation> findAllReservations() {
        return reservationRepository.findAllReservationDtos()
                .stream()
                .map(this::convertDtoToEntity)
                .toList();
    }

    public Reservation createReservation(final ReservationDto reservationDto) {
        return convertDtoToEntity(reservationRepository.createReservation(reservationDto));
    }

    private Reservation convertDtoToEntity(final ReservationDto reservationDto) {
        return reservationDto.toEntity(
                reservationTimeRepository.findReservationTimeById(reservationDto.timeId()));
    }

    public void deleteReservation(final Long id) {
        reservationRepository.deleteReservation(id);
    }

}
