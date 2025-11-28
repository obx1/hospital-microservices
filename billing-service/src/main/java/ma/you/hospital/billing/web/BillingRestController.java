package ma.you.hospital.billing.web;

import lombok.RequiredArgsConstructor;
import ma.you.hospital.billing.services.BillingService;
import ma.you.hospital.billing.web.dto.BillRequestDTO;
import ma.you.hospital.billing.web.dto.BillResponseDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bills")
@RequiredArgsConstructor
public class BillingRestController {

    private final BillingService billingService;

    @PostMapping
    public BillResponseDTO createBill(@RequestBody BillRequestDTO request) {
        return billingService.createBill(request);
    }

    @GetMapping
    public List<BillResponseDTO> getAllBills() {
        return billingService.getAllBills();
    }

    @GetMapping("/{id}")
    public BillResponseDTO getBillById(@PathVariable Long id) {
        return billingService.getBillById(id);
    }
}
