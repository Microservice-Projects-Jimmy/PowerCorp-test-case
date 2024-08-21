package net.jemsit.Click;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ClickService {

    private final ClickRepository clickRepository;

    public List<Click> getAll(String username) {
        return clickRepository.findByUsername(username).stream().map(Click::toClick).toList();
    }

    public Click save(ClickRequest request) {
        var entity = ClickEntity.builder()
                .username(request.getUsername())
                .XCoordinate(request.getXCoordinate())
                .YCoordinate(request.getYCoordinate())
                .build();
        var saved = clickRepository.save(entity);
        return Click.toClick(saved);
    }
}
