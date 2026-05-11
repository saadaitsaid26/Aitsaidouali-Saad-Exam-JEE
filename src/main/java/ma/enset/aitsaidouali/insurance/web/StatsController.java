package ma.enset.aitsaidouali.insurance.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Map;
import ma.enset.aitsaidouali.insurance.services.InsuranceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Stats", description = "Dashboard statistics")
@RestController
@RequestMapping("/api/stats")
public class StatsController {

    private final InsuranceService insuranceService;

    public StatsController(InsuranceService insuranceService) {
        this.insuranceService = insuranceService;
    }

    @Operation(summary = "Contracts by type", description = "Get count of contracts per type.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Stats retrieved"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @GetMapping("/contrats-by-type")
    public Map<String, Long> getContractCountByType() {
        return insuranceService.getContractCountByType();
    }

    @Operation(summary = "Contracts by status", description = "Get count of contracts per status.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Stats retrieved"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @GetMapping("/contrats-by-statut")
    public Map<String, Long> getContractCountByStatut() {
        return insuranceService.getContractCountByStatut();
    }
}
