package net.jemsit.CodeGenerator;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("code")
@AllArgsConstructor
public class CodeController {

    private final CodeService codeService;

    @GetMapping("get-code/{username}")
    public ResponseEntity<Code> getConfirmationCode(@PathVariable String username) {
        return ResponseEntity.ok().body(
                Code.builder()
                        .code(codeService.generateCode(username))
                        .build()
        );
    }

    @GetMapping("get-all/{username}")
    public ResponseEntity<List<Code>> getHistoryOfUser(@PathVariable String username) {
        return ResponseEntity.ok().body(codeService.getHistory(username));
    }

    @PostMapping("confirm")
    public ResponseEntity<String> confirmCode(@RequestBody ConfirmCodeRequest request) {
        try {

            return ResponseEntity.ok().body(codeService.confirm(request.getUsername(), request.getCode()));
        } catch (CodeIsExpiredException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Code is expired!");
        }
    }
}
