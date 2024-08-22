package net.jemsit.Click;

import lombok.Builder;
import lombok.Data;

import java.time.format.DateTimeFormatter;

@Data
@Builder
public class Click {
    private float X;
    private float Y;
    private String clickedAt;

    public static Click toClick(ClickEntity entity) {
        return Click.builder()
                .X(entity.getXCoordinate())
                .Y(entity.getYCoordinate())
                .clickedAt(entity.getClickedAt().format(DateTimeFormatter.ofPattern("yyyy-dd-MM HH:mm:ss")))
                .build();
    }
}
