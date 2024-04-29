package roomescape.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(basePackages = {
        "roomescape.controller.web",
        "roomescape.repository.h2",
        "roomescape.service",})
public class WebConfig {
}
