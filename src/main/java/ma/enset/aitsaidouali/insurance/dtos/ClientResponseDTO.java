package ma.enset.aitsaidouali.insurance.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClientResponseDTO {

    private Long id;
    private String nom;
    private String email;
    private int nombreContrats;
}
