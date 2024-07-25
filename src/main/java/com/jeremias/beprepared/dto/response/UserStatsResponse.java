package com.jeremias.beprepared.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserStatsResponse {
    public long citizens;
    public long totalAlerts;
    public long activeAlerts;
}
