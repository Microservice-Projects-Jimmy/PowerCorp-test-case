package net.jemsit.CodeGenerator;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConfirmCodeRequest {
    private String username;
    private long code;
}
