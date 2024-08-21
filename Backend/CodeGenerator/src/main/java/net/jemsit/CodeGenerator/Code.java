package net.jemsit.CodeGenerator;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Code {
    private String code;

    public static Code toCode(CodeEntity entity) {
        return Code.builder().code(entity.getCode()).build();
    }
}
