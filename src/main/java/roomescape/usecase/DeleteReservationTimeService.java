package roomescape.usecase;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class DeleteReservationTimeService implements DeleteReservationTimeUsecase {
    private final ReservationTimeRepository reservationTimeRepository;
    private final GetReservationTimeService getReservationTimeService;

    public DeleteReservationTimeService(final ReservationTimeRepository reservationTimeRepository,
                                        final GetReservationTimeService getReservationTimeService) {
        this.reservationTimeRepository = reservationTimeRepository;
        this.getReservationTimeService = getReservationTimeService;
    }

    @Override
    public void deleteReservationTime(long id) {
        List<ReservationTimeOutput> reservationTimeOutputs = getReservationTimeService.getReservationTime();
        reservationTimeOutputs.stream()
                .filter(reservationTimeOutput -> reservationTimeOutput.id().equals(id))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("해당 시간이 존재하지 않습니다"));
        reservationTimeRepository.deleteReservationTime(id);
    }
}
