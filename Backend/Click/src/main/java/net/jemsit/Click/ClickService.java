package net.jemsit.Click;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

@Service
@AllArgsConstructor
public class ClickService extends HelperFunctions {

    private final ClickRepository clickRepository;

    public Page<Click> getAll(String username, int page, int size) {
        Pageable pageable =  PageRequest.of(page, size);
        List<Click> coordinates = clickRepository
                .findByUsername(username)
                .stream()
                .sorted(Comparator.comparing(ClickEntity::getClickedAt).reversed())
                .map(Click::toClick)
                .toList();
        return makingPagination(coordinates, pageable);
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
