package net.jemsit.Click;

import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("click")
@AllArgsConstructor
public class ClickController {
    private final ClickService clickService;

    @GetMapping("get-all/{username}")
    public ResponseEntity<Page<Click>> getAll(@PathVariable String username, @PathParam("size") int size, @PathParam("page") int page) {
        return ResponseEntity.ok().body(clickService.getAll(username, page, size));
    }

    @PostMapping("save")
    public ResponseEntity<Click> saveClick(@RequestBody ClickRequest request) {
        System.out.println(request);
        return ResponseEntity.ok().body(clickService.save(request));
    }
}
