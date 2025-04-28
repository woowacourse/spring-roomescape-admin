package roomescape.config;

import javax.sql.DataSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

public class JdbcTemplateInitializer {

    public static JdbcTemplate jdbcTemplate = null;

    public static JdbcTemplate getJdbcTemplate() {
        if (jdbcTemplate != null) {
            return jdbcTemplate;
        }
        DataSource dataSource = new DriverManagerDataSource("jdbc:h2:mem:database;DB_CLOSE_DELAY=-1", "sa", "");
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("schema.sql"));
        DatabasePopulatorUtils.execute(populator, dataSource);
        jdbcTemplate = new JdbcTemplate(dataSource);
        return jdbcTemplate;
    }
}
