package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dto.CreateReservationDto;
import roomescape.dto.ReservationResponseDto;
import roomescape.entity.Reservation;
import roomescape.repository.ReservationRepository;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<ReservationResponseDto> getAllReservations() {
        return reservationRepository.findAll().stream()
                .map(ReservationResponseDto::from)
                .toList();
    }

    /*
    TODO: dto를 repository에 넘겨주면 왜 안될까? -> 난 dto가 view와 근접한 객체라고 생각했음
    근데 repository에서 컬럼 별 필드 매핑 해줄거면 repository에 객체 말고 dto 넘기는게 오히려 일 덜하는거 아닌가?
    service는 by-pass만 해주면 되니까
    또 service 계층에선 reservation id를 못만드네 생각해보니까
     */
    public Reservation createReservation(CreateReservationDto createReservationDto) {
        Long id = reservationRepository.addAndGetId(createReservationDto);
        return reservationRepository.findById(id);
    }

    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }
}
