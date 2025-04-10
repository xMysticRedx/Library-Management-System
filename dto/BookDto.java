package com.library.management.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {

    private Long id;

    @NotBlank(message = "Title is required")
    @Size(max = 255, message = "Title must be less than 255 characters")
    private String title;

    @NotBlank(message = "Author is required")
    @Size(max = 255, message = "Author name must be less than 255 characters")
    private String author;

    @NotBlank(message = "Genre is required")
    @Size(max = 100, message = "Genre must be less than 100 characters")
    private String genre;

    @Min(value = 0, message = "Available copies cannot be negative")
    private int availableCopies;
}