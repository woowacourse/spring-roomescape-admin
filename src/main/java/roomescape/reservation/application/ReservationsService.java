package roomescape.reservation.application;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.reservation.application.dto.Reservation;
import roomescape.reservation.application.dto.ReservationRequest;
import roomescape.reservation.repository.ReservationEntity;
import roomescape.reservation.repository.ReservationsRepository;
import roomescape.time.repository.TimesRepository;

@Service
public class ReservationsService {

    private final ReservationsRepository reservationsRepository;
    private final TimesRepository timesRepository;

    @Autowired
    public ReservationsService(
            ReservationsRepository reservationsRepository,
            TimesRepository timesRepository
    ) {
        this.reservationsRepository = reservationsRepository;
        this.timesRepository = timesRepository;
    }

    public List<Reservation> getReservations() {
        List<ReservationEntity> reservationEntities = reservationsRepository.getReservations();

        return reservationEntities.stream()
                .map(e ->
                        Reservation.from(
                                e,
                                timesRepository.getTimeEntityById(e.timeId())
                        )
                ).toList();
    }

    @Transactional
    public Reservation register(ReservationRequest request) {
        ReservationEntity entity = request.to();
        ReservationEntity entityWithId =
                reservationsRepository.saveReservation(entity);

        return Reservation.from(
                entityWithId,
                timesRepository.getTimeEntityById(entityWithId.timeId())
        );
    }

    @Transactional
    public void deleteReservationById(Long id) {
        reservationsRepository.deleteReservationById(id);
    }
}
