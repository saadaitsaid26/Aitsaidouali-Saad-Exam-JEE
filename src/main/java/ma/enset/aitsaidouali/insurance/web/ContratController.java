package ma.enset.aitsaidouali.insurance.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import ma.enset.aitsaidouali.insurance.dtos.ContratAutoRequestDTO;
import ma.enset.aitsaidouali.insurance.dtos.ContratHabitationRequestDTO;
import ma.enset.aitsaidouali.insurance.dtos.ContratResponseDTO;
import ma.enset.aitsaidouali.insurance.dtos.ContratSanteRequestDTO;
import ma.enset.aitsaidouali.insurance.enums.StatutContrat;
import ma.enset.aitsaidouali.insurance.services.InsuranceService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Contrats", description = "Manage insurance contracts")
@RestController
@RequestMapping("/api/contrats")
public class ContratController {

    private final InsuranceService insuranceService;

    public ContratController(InsuranceService insuranceService) {
        this.insuranceService = insuranceService;
    }

    @Operation(summary = "List all contracts", description = "Retrieve all contracts with pagination.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Contracts retrieved"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @GetMapping
    public Page<ContratResponseDTO> getAllContrats(
            @Parameter(description = "Page index") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "10") int size) {
        return insuranceService.getAllContrats(page, size);
    }

    @Operation(summary = "Get contract by id", description = "Retrieve a contract by id.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Contract found", content = @Content(schema = @Schema(implementation = ContratResponseDTO.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Contract not found")
    })
    @GetMapping("/{id}")
    public ContratResponseDTO getContrat(
            @Parameter(description = "Contract id", required = true) @PathVariable Long id) {
        return insuranceService.getContrat(id);
    }

    @Operation(summary = "Create auto contract", description = "Create a new auto insurance contract.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Contract created", content = @Content(schema = @Schema(implementation = ContratResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Validation error"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Client not found")
    })
    @PostMapping("/auto")
    public ContratResponseDTO saveContratAuto(@Valid @RequestBody ContratAutoRequestDTO dto) {
        return insuranceService.saveContratAuto(dto);
    }

    @Operation(summary = "Create housing contract", description = "Create a new housing insurance contract.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Contract created", content = @Content(schema = @Schema(implementation = ContratResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Validation error"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Client not found")
    })
    @PostMapping("/habitation")
    public ContratResponseDTO saveContratHabitation(@Valid @RequestBody ContratHabitationRequestDTO dto) {
        return insuranceService.saveContratHabitation(dto);
    }

    @Operation(summary = "Create health contract", description = "Create a new health insurance contract.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Contract created", content = @Content(schema = @Schema(implementation = ContratResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Validation error"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Client not found")
    })
    @PostMapping("/sante")
    public ContratResponseDTO saveContratSante(@Valid @RequestBody ContratSanteRequestDTO dto) {
        return insuranceService.saveContratSante(dto);
    }

    @Operation(summary = "Update contract status", description = "Update the status of a contract.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Status updated", content = @Content(schema = @Schema(implementation = ContratResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid status"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Contract not found")
    })
    @PutMapping("/{id}/statut")
    public ContratResponseDTO updateStatut(
            @Parameter(description = "Contract id", required = true) @PathVariable Long id,
            @Parameter(description = "New status", required = true) @RequestParam StatutContrat statut) {
        return insuranceService.updateStatut(id, statut);
    }

    @Operation(summary = "Delete contract", description = "Delete a contract by id.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Contract deleted"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Contract not found")
    })
    @DeleteMapping("/{id}")
    public void deleteContrat(
            @Parameter(description = "Contract id", required = true) @PathVariable Long id) {
        insuranceService.deleteContrat(id);
    }
}
