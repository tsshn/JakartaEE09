package com.yatsyshyn.JakartaEE09.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class BookDto {
    @NotEmpty(message = "Title needs to be filled")
    @Size(min = 1)
    private String title;

    @NotEmpty(message = "ISBN needs to be filled")
    @Pattern(regexp = "^(?:ISBN(?:-10)?:? )?(?=[0-9X]{10}$|(?=(?:[0-9]+[- ]){3})[- 0-9X]{13}$)[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9X]$",
            message = "Incorrect ISBN format")
    private String isbn;

    @NotEmpty(message = "Author needs to be filled")
    private String author;
}
