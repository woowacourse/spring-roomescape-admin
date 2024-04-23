package roomescape.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.dto.ReservationResponse;
import roomescape.dto.ReservationSaveRequest;
import roomescape.exception.ResourceNotFoundException;
import roomescape.model.Reservation;
import roomescape.model.ReservationTime;
import roomescape.repository.ReservationDao;
import roomescape.repository.ReservationTimeDao;
import roomescape.repository.dto.ReservationSaveDto;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationDao reservationDao;
    private final ReservationTimeDao reservationTimeDao;

    public ReservationService(final ReservationDao reservationDao, final ReservationTimeDao reservationTimeDao) {
        this.reservationDao = reservationDao;
        this.reservationTimeDao = reservationTimeDao;
    }

    public List<ReservationResponse> getReservations() {
        return reservationDao.findAll()
                .stream()
                .map(ReservationResponse::from)
                .toList();
    }

    @Transactional
    public ReservationResponse saveReservation(final ReservationSaveRequest reservationSaveRequest) {
        final ReservationTime reservationTime = reservationTimeDao.findById(reservationSaveRequest.timeId())
                .orElseThrow(() -> new ResourceNotFoundException("존재하지 않는 예약 시간입니다."));
        final ReservationSaveDto reservationSaveDto = ReservationSaveDto.of(reservationSaveRequest, reservationTime);
        final Reservation savedReservation = reservationDao.save(reservationSaveDto);
        return ReservationResponse.from(savedReservation);
    }

    public void deleteReservation(final Long id) {
        final boolean isDeleted = reservationDao.deleteById(id);
        if (isDeleted) {
            return;
        }
        throw new ResourceNotFoundException("존재하지 않는 예약 시간입니다.");
    }
}
