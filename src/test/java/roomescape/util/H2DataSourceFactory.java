package roomescape.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import javax.sql.DataSource;
import org.h2.tools.RunScript;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class H2DataSourceFactory {

    private static final String PROPERTY_FILE_PATH = "/test-application.properties";

    public static DataSource getDataSource() {
        Properties properties = new Properties();

        try (InputStream input = H2DataSourceFactory.class.getResourceAsStream(PROPERTY_FILE_PATH)) {
            properties.load(input);

            String url = properties.getProperty("test.db.url");
            String username = properties.getProperty("test.db.username");
            String password = properties.getProperty("test.db.password");

            return new DriverManagerDataSource(url, username, password);
        } catch (IOException e) {
            throw new RuntimeException("propereties 파일을 읽지 못했습니다.", e);
        }
    }

    public static void initializeTable(DataSource dataSource) {
        try (Connection connection = dataSource.getConnection()) {
            RunScript.execute(connection, new FileReader("src/test/resources/schema.sql"));
        } catch (SQLException e) {
            throw new RuntimeException("테이블 초기화에 실패했습니다.", e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("초기화 SQL 파일이 존재하지 않습니다.");
        }
    }
}
