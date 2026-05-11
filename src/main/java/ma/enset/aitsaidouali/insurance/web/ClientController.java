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
import ma.enset.aitsaidouali.insurance.dtos.ClientRequestDTO;
import ma.enset.aitsaidouali.insurance.dtos.ClientResponseDTO;
import ma.enset.aitsaidouali.insurance.dtos.ContratResponseDTO;
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

@Tag(name = "Clients", description = "Manage clients and their contracts")
@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final InsuranceService insuranceService;

    public ClientController(InsuranceService insuranceService) {
        this.insuranceService = insuranceService;
    }

    @Operation(summary = "Search clients", description = "Search clients by keyword with pagination.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Clients retrieved"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @GetMapping
    public Page<ClientResponseDTO> searchClients(
            @Parameter(description = "Search keyword (name filter)") @RequestParam(defaultValue = "") String keyword,
            @Parameter(description = "Page index") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "10") int size) {
        return insuranceService.searchClients(keyword, page, size);
    }

    @Operation(summary = "Get client by id", description = "Retrieve a client by id.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Client found", content = @Content(schema = @Schema(implementation = ClientResponseDTO.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Client not found")
    })
    @GetMapping("/{id}")
    public ClientResponseDTO getClient(
            @Parameter(description = "Client id", required = true) @PathVariable Long id) {
        return insuranceService.getClient(id);
    }

    @Operation(summary = "Create client", description = "Create a new client.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Client created", content = @Content(schema = @Schema(implementation = ClientResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Validation error"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PostMapping
    public ClientResponseDTO saveClient(@Valid @RequestBody ClientRequestDTO dto) {
        return insuranceService.saveClient(dto);
    }

    @Operation(summary = "Update client", description = "Update client details by id.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Client updated", content = @Content(schema = @Schema(implementation = ClientResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Validation error"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Client not found")
    })
    @PutMapping("/{id}")
    public ClientResponseDTO updateClient(
            @Parameter(description = "Client id", required = true) @PathVariable Long id,
            @Valid @RequestBody ClientRequestDTO dto) {
        return insuranceService.updateClient(id, dto);
    }

    @Operation(summary = "Delete client", description = "Delete a client by id.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Client deleted"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Client not found")
    })
    @DeleteMapping("/{id}")
    public void deleteClient(
            @Parameter(description = "Client id", required = true) @PathVariable Long id) {
        insuranceService.deleteClient(id);
    }

    @Operation(summary = "Get client contracts", description = "List all contracts for a client.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Contracts retrieved"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Client not found")
    })
    @GetMapping("/{id}/contrats")
    public List<ContratResponseDTO> getContratsForClient(
            @Parameter(description = "Client id", required = true) @PathVariable Long id) {
        return insuranceService.getContratsForClient(id);
    }
}
