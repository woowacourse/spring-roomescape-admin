package roomescape.environment;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import roomescape.controller.console.ConsoleNavigator;

@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(
        basePackages = "roomescape",
        useDefaultFilters = false,
        includeFilters = {
                @Filter(type = FilterType.REGEX, pattern = "roomescape\\.controller\\.console\\..*[(Controller)(Navigator)]"),
                @Filter(type = FilterType.REGEX, pattern = "roomescape\\.view\\..*View"),
                @Filter(type = FilterType.REGEX, pattern = "roomescape\\.service\\..*Service"),
                @Filter(type = FilterType.REGEX, pattern = "roomescape\\.repository\\..*Repository"),
                @Filter(type = FilterType.REGEX, pattern = "roomescape\\.config\\..*Config")
        }
)
public class ConsoleConfig {

    @Bean
    public CommandLineRunner executeOnStartUp(ConsoleNavigator navigator) {
        return args -> navigator.run();
    }
}
