package roomescape;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import roomescape.controller.ReservationConsoleController;
import roomescape.repository.ReservationMemoryRepository;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeMemoryRepository;
import roomescape.repository.ReservationTimeRepository;
import roomescape.service.ReservationService;
import roomescape.service.ReservationTimeService;
import roomescape.view.InputView;
import roomescape.view.OutputView;

@Configuration
public class ConsoleConfiguration {

    @Bean
    public ReservationConsoleController reservationConsoleController() {
        return new ReservationConsoleController(
                inputView(),
                outputView(),
                reservationService(),
                reservationTimeService());
    }

    @Bean
    public InputView inputView() {
        return new InputView();
    }

    @Bean
    public OutputView outputView() {
        return new OutputView();
    }

    @Bean
    public ReservationService reservationService() {
        return new ReservationService(reservationRepository(), reservationTimeRepository());
    }

    @Bean
    public ReservationTimeService reservationTimeService() {
        return new ReservationTimeService(reservationTimeRepository());
    }

    @Bean
    public ReservationRepository reservationRepository() {
        return new ReservationMemoryRepository();
    }

    @Bean
    public ReservationTimeRepository reservationTimeRepository() {
        return new ReservationTimeMemoryRepository();
    }
}
