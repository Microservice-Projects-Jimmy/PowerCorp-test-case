package net.jemsit.CodeGenerator;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@AllArgsConstructor
public class CodeService extends HelperFunctions {

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

    public Page<Code> getHistory(String username, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Code> codeHistory = codeRepository.findByUsername(username)
                .stream()
                .sorted(Comparator.comparing(CodeEntity::getId).reversed())
                .map(Code::toCode).toList();
        return makingPagination(codeHistory, pageable);
    }

    public String confirm(String username, long code) {
        var codeFromRedis = redisService.getData(username);
        if (codeFromRedis == null) throw new CodeIsExpiredException();
        return code == (long) codeFromRedis?"Confirmed":"Not Confirmed";
    }

}
