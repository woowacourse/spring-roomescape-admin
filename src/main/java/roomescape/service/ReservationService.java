package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.Name;
import roomescape.domain.Reservation;
import roomescape.dto.request.ReservationRequest;
import roomescape.domain.ReservationTime;
import roomescape.dto.response.ReservationResponse;
import roomescape.service.mapper.ReservationMapper;

@Service
public class ReservationService {
    private final ReservationDao reservationDao;
    private final ReservationTimeDao reservationTimeDao;
    private final ReservationMapper reservationMapper;

    public ReservationService(ReservationDao reservationDao, ReservationTimeDao reservationTimeDao, ReservationMapper reservationMapper) {
        this.reservationDao = reservationDao;
        this.reservationTimeDao = reservationTimeDao;
        this.reservationMapper = reservationMapper;
    }

    public List<ReservationResponse> findAllReservations() {
        List<Reservation> reservations = reservationDao.findAll();

        return reservations.stream()
                .map(reservationMapper::toResponse)
                .toList();
    }

    public ReservationResponse createReservation(ReservationRequest request) {
        ReservationTime reservationTime = reservationTimeDao.findById(request.timeId());

        Reservation newReservation = new Reservation(
                null,
                Name.parse(request.name()),
                request.date(),
                reservationTime
        );

        Reservation savedReservation = reservationDao.insertReservation(newReservation);

        return reservationMapper.toResponse(savedReservation);
    }

    public void deleteReservation(Long id) {
        reservationDao.deleteById(id);
    }
}
