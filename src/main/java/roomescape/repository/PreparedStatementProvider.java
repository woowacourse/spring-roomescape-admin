package roomescape.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface PreparedStatementProvider<T> {

    PreparedStatement provide(PreparedStatement preparedStatement, T object) throws SQLException;
}
