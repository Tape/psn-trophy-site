package org.openpsn.api.db;

import lombok.RequiredArgsConstructor;

import java.sql.ResultSet;
import java.sql.SQLException;

@RequiredArgsConstructor
public final class ValueHandler<T> implements RecordHandler<T> {
    public static final ValueHandler<Boolean> BOOLEAN = new ValueHandler<>(Boolean.class);

    private final Class<T> type;

    @Override
    public T toRecord(ResultSet rs) throws SQLException {
        return rs.getObject(1, type);
    }
}
