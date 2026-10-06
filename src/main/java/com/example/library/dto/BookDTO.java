package com.example.library.dto;

import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {

    private Long id;

    @NotBlank(message = "Book title is required")
    @Size(min = 2,max = 200,message = "Title must be between 2 and 200 characters")
    private String title;


    @NotBlank(message = "ISBN is required")
    private String isbn;

    @NotNull(message = "Published date is required")
    private LocalDate publishedDate;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than zero")
    private BigDecimal price;

    @NotNull(message = "Author must not be null")
    private Long authorId;


}
