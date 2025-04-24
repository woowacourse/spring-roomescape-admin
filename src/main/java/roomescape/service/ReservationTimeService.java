package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationTimeDAO;
import roomescape.dto.ReservationTimeReqDto;
import roomescape.dto.ReservationTimeResDto;
import roomescape.model.ReservationTime;

@Service
public class ReservationTimeService {

    private final ReservationTimeDAO reservationTimeDAO;

    public ReservationTimeService(ReservationTimeDAO reservationTimeDAO) {
        this.reservationTimeDAO = reservationTimeDAO;
    }

    public List<ReservationTimeResDto> findAll() {
        List<ReservationTime> reservationTimes = reservationTimeDAO.findAll();
        return reservationTimes.stream()
                .map(this::convertToReservationTimeResDto)
                .toList();
    }

    public ReservationTimeResDto addAndGet(ReservationTimeReqDto dto) {
        ReservationTime newReservationTime = reservationTimeDAO.addAndGet(dto.startAt());
        return convertToReservationTimeResDto(newReservationTime);
    }

    public void deleteById(Long id) {
        reservationTimeDAO.deleteById(id);
    }

    private ReservationTimeResDto convertToReservationTimeResDto(ReservationTime reservationTime) {
        return new ReservationTimeResDto(reservationTime.getId(), reservationTime.getTime());
    }
}
