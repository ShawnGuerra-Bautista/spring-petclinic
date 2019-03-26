package org.springframework.samples.petclinic.db.helper;

public final class SqliteRepositoryHelper {

    public static String getAllColumns(ColumnProvider[] columnProvider) {
        StringBuilder columns = new StringBuilder("(");

        for(ColumnProvider col : columnProvider) {
            columns.append(col.toString()).append(", ");
        }

        columns.replace(columns.length() - 2, columns.length(), ")");
        return columns.toString();
    }
}
