package roomescape;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import roomescape.config.ConsoleConfig;
import roomescape.controller.console.ReservationConsoleController;

public class ConsoleApplication {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ConsoleConfig.class);
        ReservationConsoleController console = applicationContext.getBean(ReservationConsoleController.class);
        console.start();
    }
}
