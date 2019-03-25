package org.springframework.samples.petclinic.db.helper;

import java.sql.Date;
import java.time.LocalDate;

public class SqliteValuesBuilder {

    private String values;

    public SqliteValuesBuilder() {
        values = "(";
    }

    public String build() {
        int len = values.length();
        if (len < 2) {
            return "()";
        }

        return values.substring(0, len - 2) + ")";
    }

    public SqliteValuesBuilder withInt(Integer integer) {
        values += integer + ", ";
        return this;
    }

    public SqliteValuesBuilder withVarchar(String string) {
        values += "\"" + string + "\", ";
        return this;
    }

    public SqliteValuesBuilder withDate(LocalDate date) {
        values += "\"" + Date.valueOf(date) + "\", ";
        return this;
    }
}
