package net.jemsit.CodeGenerator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CodeService {

    private final CodeRepository codeRepository;
    private final RedisService redisService;

    public String generateCode(String username) {
        String randomCode = String.valueOf((int)(Math.random() * 10000));
        var code = CodeEntity.builder()
                .code(randomCode)
                .username(username)
                .build();
        codeRepository.save(code);
        redisService.saveData(username, Long.parseLong(randomCode));
        return randomCode;
    }

    public List<Code> getHistory(String username) {
        return codeRepository.findByUsername(username).stream().map(Code::toCode).toList();
    }

    public String confirm(String username, long code) {
        var codeFromRedis = redisService.getData(username);
        if (codeFromRedis == null) throw new CodeIsExpiredException();
        return code == (long) codeFromRedis?"Confirmed":"Not Confirmed";
    }

}
