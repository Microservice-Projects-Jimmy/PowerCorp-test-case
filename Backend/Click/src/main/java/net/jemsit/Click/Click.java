package net.jemsit.Click;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Click {
    private float X;
    private float Y;

    public static Click toClick(ClickEntity entity) {
        return Click.builder().X(entity.getXCoordinate()).Y(entity.getYCoordinate()).build();
    }
}
