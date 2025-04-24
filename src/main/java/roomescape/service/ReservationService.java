package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDAO;
import roomescape.dto.ReservationReqDto;
import roomescape.dto.ReservationResDto;
import roomescape.dto.ReservationTimeResDto;
import roomescape.model.Reservation;
import roomescape.model.ReservationTime;

@Service
public class ReservationService {

    private final ReservationDAO reservationDAO;

    public ReservationService(ReservationDAO reservationDAO) {
        this.reservationDAO = reservationDAO;
    }

    public List<ReservationResDto> findAll() {
        List<Reservation> reservations = reservationDAO.findAll();
        return reservations.stream()
                .map(this::convertToReservationResDto)
                .toList();
    }

    public ReservationResDto addAndGet(ReservationReqDto dto) {
        Reservation newReservation = reservationDAO.addAndGet(dto.name(), dto.date(), dto.timeId());
        return convertToReservationResDto(newReservation);
    }

    public void deleteById(Long id) {
        reservationDAO.deleteById(id);
    }

    private ReservationResDto convertToReservationResDto(Reservation reservation) {
        return new ReservationResDto(reservation.getId(), reservation.getName(), reservation.getDate(), convertToReservationTimeResDto(reservation.getTime()));
    }

    private ReservationTimeResDto convertToReservationTimeResDto(ReservationTime reservationTime) {
        return new ReservationTimeResDto(reservationTime.getId(), reservationTime.getTime());
    }
}
