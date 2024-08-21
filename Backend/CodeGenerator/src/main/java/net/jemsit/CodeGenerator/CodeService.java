package net.jemsit.CodeGenerator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
public class CodeService {

    private final CodeRepository codeRepository;

    public CodeService(CodeRepository codeRepository) {
        this.codeRepository = codeRepository;
    }

    public String getCode(String username) {
        String randomCode = String.valueOf(Math.random() * 1000);
        var code = CodeEntity.builder()
                .code(randomCode)
                .username(username)
                .build();
        codeRepository.save(code);
        return randomCode;
    }
}
