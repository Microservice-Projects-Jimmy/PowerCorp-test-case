package net.jemsit.CodeGenerator;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("code")
@AllArgsConstructor
public class CodeController {

    private final CodeService codeService;

    @GetMapping("get-code/{username}")
    public ResponseEntity<Code> getConfirmationCode(@PathVariable String username) {
        return ResponseEntity.ok().body(
                Code.builder()
                        .code(codeService.getCode(username))
                        .build()
        );
    }
}
