package roomescape.service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.Name;
import roomescape.domain.Reservation;
import roomescape.dto.request.ReservationRequest;
import roomescape.domain.ReservationTime;
import roomescape.dto.response.ReservationResponse;
import roomescape.dto.response.ReservationTimeResponse;

@Service
public class ReservationService {
    private final ReservationDao reservationDao;
    private final ReservationTimeDao reservationTimeDao;

    public ReservationService(ReservationDao reservationDao, ReservationTimeDao reservationTimeDao) {
        this.reservationDao = reservationDao;
        this.reservationTimeDao = reservationTimeDao;
    }

    public List<ReservationResponse> findAllReservations() {
        List<Reservation> reservations = reservationDao.findAll();

        return reservations.stream()
                .map(this::convertToResponse)
                .toList();
    }

    public ReservationResponse createReservation(ReservationRequest request) {
        ReservationTime reservationTime = reservationTimeDao.findById(request.timeId());

        Reservation newReservation = new Reservation(
                null,
                Name.parse(request.name()),
                LocalDate.parse(request.date()),
                reservationTime
        );

        Long generatedId = reservationDao.insertReservation(newReservation);

        Reservation savedReservation = new Reservation(
                generatedId,
                newReservation.getName(),
                newReservation.getDate(),
                reservationTime
        );

        return convertToResponse(savedReservation);
    }

    public void deleteReservation(Long id) {
        reservationDao.deleteById(id);
    }

    private ReservationResponse convertToResponse(Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getName().toString(),
                reservation.getDate().toString(),
                convertToResponse(reservation.getTime())
        );
    }

    private ReservationTimeResponse convertToResponse(ReservationTime reservationTime) {
        return new ReservationTimeResponse(
                reservationTime.getId(),
                reservationTime.getStartAt().toString()
        );
    }
}
