package roomescape.config;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import roomescape.time.dao.TimeJdbcDao;

@Configuration
public class BeanConfig {

    @Bean
    public TimeJdbcDao timeJdbcDao(DataSource dataSource) {
        return new TimeJdbcDao(dataSource);
    }
}
