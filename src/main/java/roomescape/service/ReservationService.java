    package roomescape.service;

    import java.util.List;
    import org.springframework.stereotype.Service;
    import org.springframework.transaction.annotation.Transactional;
    import roomescape.domain.Reservation;
    import roomescape.domain.ReservationTime;
    import roomescape.repository.ReservationRepository;
    import roomescape.service.command.ReservationCreateCommand;

    @Service
    public class ReservationService {

        private final ReservationRepository reservationRepository;
        private final ReservationTimeService timeService;

        public ReservationService(
                ReservationRepository reservationRepository,
                ReservationTimeService timeService
        ) {
            this.reservationRepository = reservationRepository;
            this.timeService = timeService;
        }

        @Transactional
        public Reservation create(
                ReservationCreateCommand createCommand
        ) {
            ReservationTime time = timeService.findById(createCommand.timeId());
            Reservation reservation = Reservation.create(
                    createCommand.name(),
                    createCommand.date(),
                    time
            );

            return reservationRepository.create(reservation);
        }

        @Transactional
        public List<Reservation> findAll() {
            return reservationRepository.findAll();
        }

        @Transactional
        public void delete(long id) {
            reservationRepository.delete(id);
        }
    }
