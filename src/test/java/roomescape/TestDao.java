package roomescape;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.jdbc.core.RowMapper;

import roomescape.model.Entity;
import roomescape.repository.Dao;
import roomescape.repository.PreparedStatementProvider;

public class TestDao extends Dao {
        
        private Long autoIncrement = 1L;
        private final Map<Long, Object> map = new HashMap<>();

        public TestDao() {
            super(null);
        }

        @Override
        public <T> List<T> getAll(String query, RowMapper<T> rowMapper) {
            return (List<T>)map.values().stream().toList();
        }

        @Override
        public <T> Optional<T> findById(String query, Long id, RowMapper<T> rowMapper) {
            return Optional.ofNullable((T)map.get(id));
        }

        @Override
        public <T extends Entity<T>> T save(String query, T object, PreparedStatementProvider<T> psProvider) {
            Long id = autoIncrement++;
            map.put(id, object.withId(id));
            return (T)map.get(id);
        }

        @Override
        public void remove(String query, Long id) {
            map.remove(id);
        }
    }
