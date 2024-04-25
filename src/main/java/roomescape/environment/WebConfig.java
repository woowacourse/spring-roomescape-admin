package roomescape.environment;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;

@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(
        basePackages = "roomescape",
        useDefaultFilters = false,
        includeFilters = {
                @Filter(type = FilterType.REGEX, pattern = "roomescape\\.controller\\.web\\..*Controller"),
                @Filter(type = FilterType.REGEX, pattern = "roomescape\\.service\\..*Service"),
                @Filter(type = FilterType.REGEX, pattern = "roomescape\\.repository\\..*Repository"),
                @Filter(type = FilterType.REGEX, pattern = "roomescape\\.config\\..*Config"),
                @Filter(type = FilterType.REGEX, pattern = "roomescape\\.environment\\..*Environment")
        }
)
public class WebConfig {
}
