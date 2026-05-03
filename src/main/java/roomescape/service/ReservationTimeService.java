package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDAO;
import roomescape.dao.ReservationTimeDAO;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequestDTO;
import roomescape.exception.ReservationTimeInUseException;
import roomescape.exception.ReservationTimeNotFoundException;

import java.util.List;

@Service
public class ReservationTimeService {
    private final ReservationDAO reservationDAO;
    private final ReservationTimeDAO reservationTimeDAO;

    public ReservationTimeService(ReservationDAO reservationDAO, ReservationTimeDAO reservationTimeDAO) {
        this.reservationDAO = reservationDAO;
        this.reservationTimeDAO = reservationTimeDAO;
    }

    public List<ReservationTime> findAllReservationTime() {
        return reservationTimeDAO.findAllReservationTime();
    }

    public ReservationTime createReservationTime(ReservationTimeRequestDTO requestDTO) {
        ReservationTime reservationTime = new ReservationTime(requestDTO.getStartAt());
        Long generatedId = reservationTimeDAO.insertWithKeyHolder(reservationTime);
        return new ReservationTime(generatedId, reservationTime.getStartAt());
    }

    public void deleteReservationTime(Long id) {
        boolean isReservationExists = reservationDAO.existsByTimeId(id);
        if (isReservationExists) {
            throw new ReservationTimeInUseException("[ERROR] 해당 시간에 이미 예약이 존재하여 삭제할 수 없습니다.");
        }

        int deletedNum = reservationTimeDAO.delete(id);
        if (deletedNum == 0) {
            throw new ReservationTimeNotFoundException("[ERROR] 존재하지 않는 예약 시간 ID입니다.");
        }
    }
}
