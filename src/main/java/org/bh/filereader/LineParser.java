package org.bh.filereader;

import lombok.RequiredArgsConstructor;
import org.bh.filereader.model.Line;
import org.bh.filereader.model.FieldConfig;
import org.bh.filereader.model.LineConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LineParser {
    private final LineConfig lineConfig;

    public Line parseLine(String line) {
        var lineBuilder = Line.builder();

        Consumer<FieldConfig> callBuilderWithFieldConfig = (fieldConfig) ->
                fieldConfig.getBuilderFn().apply(lineBuilder, line.substring(fieldConfig.getOffset(), fieldConfig.getEndIndex()));

        lineConfig.allFields()
                .forEach(callBuilderWithFieldConfig);

        return lineBuilder.build();
    }
}
