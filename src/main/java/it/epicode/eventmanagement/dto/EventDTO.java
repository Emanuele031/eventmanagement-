package it.epicode.eventmanagement.dto;

import lombok.Data;

@Data
public class EventDTO {
    private String title;
    private String description;
    private String location;
    private int availableSeats;
    private Long organizerId;
}

