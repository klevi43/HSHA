package org.hsha.hsha.models;

import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.BatchSize;
import org.springframework.lang.NonNull;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegisterDto {

    @NonNull
    private String firstName;
    @NonNull
    private String lastName;

    @NonNull
    private String email;

    private String password;

    private String confirmPassword;
}
