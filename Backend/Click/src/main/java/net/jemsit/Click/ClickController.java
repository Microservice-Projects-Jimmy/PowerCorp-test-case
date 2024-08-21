package net.jemsit.Click;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("click")
@AllArgsConstructor
public class ClickController {
    private final ClickService clickService;

    @GetMapping("get-all/{username}")
    public ResponseEntity<List<Click>> getAll(@PathVariable String username) {
        return ResponseEntity.ok().body(clickService.getAll(username));
    }

    @PostMapping("save")
    public ResponseEntity<Click> saveClick(@RequestBody ClickRequest request) {
        System.out.println(request);
        return ResponseEntity.ok().body(clickService.save(request));
    }
}
