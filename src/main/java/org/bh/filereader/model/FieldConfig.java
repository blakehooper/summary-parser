package org.bh.filereader.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.function.BiFunction;
import java.util.function.Function;

@Builder
@Getter
public class FieldConfig {
    private final String fieldName;
    private final BiFunction<Line.LineBuilder, String, Line.LineBuilder> builderFn;
    private final Integer length;
    private final Integer offset;

    @Setter
    private Function validator;

    public Integer getEndIndex() {
        return offset + length;
    }

    public static FieldConfig of(String fieldName,
                                 BiFunction<Line.LineBuilder, String, Line.LineBuilder> builderFn,
                                 Integer length,
                                 Integer offset) {
        return FieldConfig.builder()
                .fieldName(fieldName)
                .builderFn(builderFn)
                .length(length)
                .offset(offset)
                .build();

    }

    public static FieldConfig of(String fieldName,
                                 BiFunction<Line.LineBuilder, String, Line.LineBuilder> builderFn,
                                 Integer length,
                                 Integer offset,
                                 Function validator) {
        var fieldConfig = of(fieldName, builderFn, length, offset);
        fieldConfig.setValidator(validator);
        return fieldConfig;
    }
}
