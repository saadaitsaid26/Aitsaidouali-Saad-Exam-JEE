package ma.enset.aitsaidouali.insurance.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import ma.enset.aitsaidouali.insurance.dtos.PaiementRequestDTO;
import ma.enset.aitsaidouali.insurance.dtos.PaiementResponseDTO;
import ma.enset.aitsaidouali.insurance.services.InsuranceService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Paiements", description = "Manage contract payments")
@RestController
@RequestMapping("/api/paiements")
public class PaiementController {

    private final InsuranceService insuranceService;

    public PaiementController(InsuranceService insuranceService) {
        this.insuranceService = insuranceService;
    }

    @Operation(summary = "Get payments for contract", description = "List all payments for a contract.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Payments retrieved"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Contract not found")
    })
    @GetMapping("/contrat/{contratId}")
    public List<PaiementResponseDTO> getPaiementsForContrat(
            @Parameter(description = "Contract id", required = true) @PathVariable Long contratId) {
        return insuranceService.getPaiementsForContrat(contratId);
    }

    @Operation(summary = "Add payment", description = "Create a new payment for a contract.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Payment created", content = @Content(schema = @Schema(implementation = PaiementResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Validation error"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Contract not found")
    })
    @PostMapping
    public PaiementResponseDTO addPaiement(@Valid @RequestBody PaiementRequestDTO dto) {
        return insuranceService.addPaiement(dto);
    }

    @Operation(summary = "Delete payment", description = "Delete a payment by id.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Payment deleted"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Payment not found")
    })
    @DeleteMapping("/{id}")
    public void deletePaiement(
            @Parameter(description = "Payment id", required = true) @PathVariable Long id) {
        insuranceService.deletePaiement(id);
    }
}
