package net.jemsit.Click;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

@Service
@AllArgsConstructor
public class ClickService {

    private final ClickRepository clickRepository;

    public List<Click> getAll(String username) {
        return clickRepository
                .findByUsername(username)
                .stream()
                .sorted(Comparator.comparing(ClickEntity::getClickedAt).reversed())
                .map(Click::toClick)
                .toList();
    }

    public Click save(ClickRequest request) {
        var entity = ClickEntity.builder()
                .username(request.getUsername())
                .XCoordinate(request.getXCoordinate())
                .YCoordinate(request.getYCoordinate())
                .clickedAt(OffsetDateTime.now())
                .build();
        var saved = clickRepository.save(entity);
        return Click.toClick(saved);
    }
}
