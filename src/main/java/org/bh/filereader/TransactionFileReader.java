package org.bh.filereader;

import lombok.RequiredArgsConstructor;
import org.bh.filereader.model.Line;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

// Not tested. See SummaryServiceTest for test with dependencies example
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TransactionFileReader {
    private final LineParser lineParser;
    @Value("${input.filePath}")
    private String filePath;
    public List<Line> readFile() {
        try {
            return Files.lines(Path.of(filePath))
                    .map(lineParser::parseLine)
                    .collect(Collectors.toList());

        } catch (IOException ioe) {
            throw new RuntimeException("Unable to read file", ioe);
        }
    }
}
