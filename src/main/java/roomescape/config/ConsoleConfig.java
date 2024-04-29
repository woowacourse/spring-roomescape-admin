package roomescape.config;

import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {
        "roomescape.controller.console",
        "roomescape.repository.memory",
        "roomescape.service",
        "roomescape.view"})
public class ConsoleConfig {

}
