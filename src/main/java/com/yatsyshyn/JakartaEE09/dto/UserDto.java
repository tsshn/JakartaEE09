package com.yatsyshyn.JakartaEE09.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Setter
@Getter
@ToString
@AllArgsConstructor
public class UserDto {
    private Integer id;

    @NotEmpty(message = "login can`t be empty")
    @Pattern(regexp = "^(?=.{1,30}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$", message = "Incorrect login format")
    private String login;

    @NotEmpty(message = "password can`t be empty")
    @Pattern(regexp = "^(?=.{8,20}$)[a-zA-Z0-9._]+(?<![_.])$", message = "Incorrect password format")
    private String password;
}