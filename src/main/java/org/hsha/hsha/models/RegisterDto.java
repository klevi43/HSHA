package org.hsha.hsha.models;

import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.BatchSize;

@Data
public class RegisterDto {

    @NotNull
    private String firstName;
    @NotNull
    private String lastName;

    @NotNull
    private String email;

    private String password;

    private String confirmPassword;
}
