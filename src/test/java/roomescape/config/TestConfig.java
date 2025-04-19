package roomescape.config;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class TestConfig {

    public static JdbcTemplate getJdbcTemplate() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:test_database;DB_CLOSE_DELAY=-1");
        dataSource.setUsername("sa");

        jdbcTemplate.setDataSource(dataSource);

        jdbcTemplate.execute(getSchema());

        return jdbcTemplate;
    }

    public static String getSchema() {
        try {
            ClassPathResource resource = new ClassPathResource("schema.sql");

            return Files.readString(resource.getFile().toPath(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("파일을 가져오는 데 문제가 생겼습니다!");
        }
    }

}
