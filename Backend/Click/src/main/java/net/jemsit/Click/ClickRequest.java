package net.jemsit.Click;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class ClickRequest {
    private String username;
    private float XCoordinate;
    private float YCoordinate;
}
