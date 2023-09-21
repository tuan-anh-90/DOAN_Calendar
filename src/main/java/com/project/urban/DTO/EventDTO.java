package com.project.urban.DTO;


import java.time.LocalDateTime;

import lombok.Data;

@Data
public class EventDTO {
	private String summary;

    private String description;

    private LocalDateTime startTime;

    private LocalDateTime endTime;
}
