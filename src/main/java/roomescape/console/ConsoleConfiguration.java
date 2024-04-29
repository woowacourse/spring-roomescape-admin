package roomescape.console;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration
@Conditional(ConsoleCondition.class)
public class ConsoleConfiguration {

    private final ApplicationContext applicationContext;

    public ConsoleConfiguration(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Bean
    public RoomEscapeConsoleRunner roomEscapeConsoleRunner() {
        return new RoomEscapeConsoleRunner(consoleCommandMatcher(), consoleInputScanner(), consoleView());
    }

    @Bean
    public ConsoleCommandMatcher consoleCommandMatcher() {
        return new ConsoleCommandMatcher(applicationContext);
    }

    @Bean
    public ConsoleInputView consoleInputScanner() {
        return new ConsoleInputView();
    }

    @Bean
    public ConsoleOutputView consoleView() {
        return new ConsoleOutputView();
    }
}
