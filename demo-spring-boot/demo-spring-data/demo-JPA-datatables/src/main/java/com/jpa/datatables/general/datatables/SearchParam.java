package com.jpa.datatables.general.datatables;

import lombok.Data;

import java.util.Objects;

/**
 * Created by dongsilin on 2016/11/1.
 */
@Data
public class SearchParam{
    private String value;
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchParam that = (SearchParam) o;
        return Objects.equals(value, that.value) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, name);
    }
}
