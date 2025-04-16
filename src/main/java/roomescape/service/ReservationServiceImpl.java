package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.entity.ReservationEntity;
import roomescape.repository.ReservationRepository;

import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService{

    private final ReservationRepository reservationRepository;

    public ReservationServiceImpl(final ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public List<ReservationResponse> getReservations() {
        return ReservationResponse.from(reservationRepository.findAll());
    }

    @Override
    public ReservationResponse createReservation(final ReservationRequest reservationRequest) {
        ReservationEntity reservationEntity = ReservationEntity.createWithoutId(reservationRequest.name(),
                reservationRequest.date(),
                reservationRequest.time());

        return ReservationResponse.from(reservationRepository.save(reservationEntity));
    }

    @Override
    public void delete(final long id) {
        reservationRepository.delete(id);
    }
}
