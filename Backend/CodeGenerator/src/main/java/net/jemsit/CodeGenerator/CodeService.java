package net.jemsit.CodeGenerator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CodeService {

    private final CodeRepository codeRepository;

    public String getCode(String username) {
        String randomCode = String.valueOf((int)(Math.random() * 10000));
        var code = CodeEntity.builder()
                .code(randomCode)
                .username(username)
                .build();
        codeRepository.save(code);
        return randomCode;
    }
}
