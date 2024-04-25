package roomescape.reservation.service;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.reservation.dao.ReservationDao;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.dto.ReservationRequestDto;
import roomescape.reservation.dto.ReservationResponseDto;
import roomescape.reservation.dto.ResponseCode;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ReservationService {

    private final ReservationDao reservationDao;

    public ReservationService(final ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public List<ReservationResponseDto> findAll() {
        final List<Reservation> reservations = reservationDao.findAll();
        return reservations.stream()
                           .map(ReservationResponseDto::new)
                           .toList();
    }

    @Transactional
    public ReservationResponseDto save(final ReservationRequestDto requestDto) {
        final long reservationId = reservationDao.save(requestDto);
        final Reservation reservation = reservationDao.findById(reservationId);
        return new ReservationResponseDto(reservation);
    }

    @Transactional
    public ResponseCode deleteById(final long id) {
        try {
            if (reservationDao.deleteById(id) > 0) {
                return ResponseCode.SUCCESS_DELETE;
            }
            return ResponseCode.NOT_FOUND;
        } catch (final DataAccessException dataAccessException) {
            return ResponseCode.FAILED_DELETE;
        }
    }
}
