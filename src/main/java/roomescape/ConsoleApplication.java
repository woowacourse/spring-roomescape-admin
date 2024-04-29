package roomescape;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import roomescape.controller.ReservationConsoleController;

public class ConsoleApplication {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ConsoleConfiguration.class);
        ReservationConsoleController console = applicationContext.getBean(ReservationConsoleController.class);
        console.start();
    }
}
