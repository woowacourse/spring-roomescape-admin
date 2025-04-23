package roomescape.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import roomescape.model.Entity;

public abstract class Repository<T extends Entity<T>> {

    protected final Dao dao;

    protected Repository(Dao dao) {
        this.dao = dao;
    }

    protected abstract String getAllQuery();

    protected abstract String saveQuery();

    protected abstract String removeQuery();

    protected abstract T rowMapper(ResultSet resultSet, int rowNum) throws SQLException;

    protected abstract PreparedStatement preparedStatementProvider(PreparedStatement preparedStatement,
        T object) throws SQLException;

    public List<T> getAll() {
        return dao.getAll(getAllQuery(), this::rowMapper);
    }

    public T save(T object) {
        return dao.save(saveQuery(), object, this::preparedStatementProvider);
    }

    public void remove(Long id) {
        dao.remove(removeQuery(), id);
    }
}
