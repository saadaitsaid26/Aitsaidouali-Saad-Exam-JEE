package ma.enset.aitsaidouali.insurance.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClientRequestDTO {

    @NotBlank
    private String nom;

    @NotBlank
    @Email
    private String email;
}
