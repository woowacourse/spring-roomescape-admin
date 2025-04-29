package roomescape.console.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import roomescape.console.controller.ConsoleRoomEscapeController;
import roomescape.console.view.InputView;
import roomescape.console.view.OutputView;
import roomescape.repository.MemoryReservationRepository;
import roomescape.repository.MemoryReservationTimeRepository;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;
import roomescape.service.ReservationService;

@Configuration
public class ConsoleConfig {

    @Bean
    public ReservationRepository reservationRepository() {
        return new MemoryReservationRepository();
    }

    @Bean
    public ReservationTimeRepository reservationTimeRepository() {
        return new MemoryReservationTimeRepository();
    }

    @Bean
    public ReservationService reservationService() {
        return new ReservationService(reservationRepository(), reservationTimeRepository());
    }

    @Bean
    public ConsoleRoomEscapeController consoleRoomEscapeController(ReservationService reservationService,
                                                                   OutputView outputView, InputView inputView) {
        return new ConsoleRoomEscapeController(reservationService, outputView, inputView);
    }
}
