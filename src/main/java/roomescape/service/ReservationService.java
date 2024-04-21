package roomescape.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;
import roomescape.dto.ReservationTimeResponseDto;
import roomescape.entity.Reservation;

@Service
public class ReservationService {

    @Autowired
    private ReservationDao reservationDao;

    public List<ReservationResponseDto> findReservations() {
        List<Reservation> reservations = reservationDao.findAllReservations();
        return reservations.stream()
                .map(ReservationResponseDto::from)
                .toList();
    }

    public ReservationResponseDto addReservation(ReservationRequestDto reservationDto) {
        long id = reservationDao.save(reservationDto);
        Reservation reservation = reservationDao.findById(id); // TODO: save + findById ???
        return new ReservationResponseDto(
                id,
                reservation.getName(),
                reservation.getDate(),
                ReservationTimeResponseDto.from(reservation.getTime())
        ); // TODO: 서비스 계층에서 DTO의 이름을 통해 요청과 응답임을 암시해도 괜찮을까?
    }

    public void deleteReservation(long id) {
        reservationDao.delete(id);
    }
}
