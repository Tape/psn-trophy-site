package org.openpsn.api.db;

import org.apache.commons.dbutils.ResultSetHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@FunctionalInterface
public interface RecordHandler<T> extends ResultSetHandler<Optional<T>> {
    T toRecord(ResultSet rs) throws SQLException;

    @Override
    default Optional<T> handle(ResultSet rs) throws SQLException {
        return rs.next() ? Optional.of(toRecord(rs)) : Optional.empty();
    }
}
