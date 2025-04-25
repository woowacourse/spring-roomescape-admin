package roomescape.usecase.ReservationTime;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class DeleteReservationTimeService implements DeleteReservationTimeUsecase {
    private final ReservationTimeRepository reservationTimeRepository;
    private final GetReservationTimeUseCase getReservationTimeUseCase;

    public DeleteReservationTimeService(final ReservationTimeRepository reservationTimeRepository,
                                        final GetReservationTimeService getReservationTimeUseCase) {
        this.reservationTimeRepository = reservationTimeRepository;
        this.getReservationTimeUseCase = getReservationTimeUseCase;
    }

    @Override
    public void deleteReservationTime(long id) {
        List<ReservationTimeOutput> reservationTimeOutputs = getReservationTimeUseCase.getReservationTime();
        reservationTimeOutputs.stream()
                .filter(reservationTimeOutput -> reservationTimeOutput.id().equals(id))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("해당 시간이 존재하지 않습니다"));
        reservationTimeRepository.deleteReservationTime(id);
    }
}
