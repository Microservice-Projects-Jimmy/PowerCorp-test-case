package net.jemsit.CodeGenerator;

import lombok.Builder;
import lombok.Data;

import java.time.format.DateTimeFormatter;

@Data
@Builder
public class Code {
    private String code;
    private String username;
    private String createdAt;

    public static Code toCode(CodeEntity entity) {
        return Code.builder()
                .username(entity.getUsername())
                .createdAt(entity.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-dd-MM HH:mm:ss")))
                .code(entity.getCode())
                .build();
    }
}
