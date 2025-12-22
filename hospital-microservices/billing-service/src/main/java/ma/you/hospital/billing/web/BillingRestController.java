package ma.you.hospital.billing.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.you.hospital.billing.services.BillingService;
import ma.you.hospital.billing.web.dto.*;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bills")
@RequiredArgsConstructor
public class BillingRestController {

    private final BillingService billingService;

    // CREATE
    @PostMapping
    public ResponseEntity<BillResponseDTO> createBill(@RequestBody @Valid BillRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(billingService.createBill(request));
    }

    // LIST SIMPLE (comme tu avais)
    @GetMapping("/all")
    public List<BillResponseDTO> getAllBills() {
        return billingService.getAllBills();
    }

    // LIST PAGINÉ + FILTRES (recommandé)
    @GetMapping
    public Page<BillResponseDTO> searchBills(
            @RequestParam(required = false) Long patientId,
            @RequestParam(required = false) Long doctorId,
            @RequestParam(required = false) Long appointmentId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "billDate,desc") String sort
    ) {
        Pageable pageable = PageRequest.of(page, size, parseSort(sort));
        return billingService.searchBills(patientId, doctorId, appointmentId, pageable);
    }

    // GET BY ID
    @GetMapping("/{id}")
    public BillResponseDTO getBillById(@PathVariable Long id) {
        return billingService.getBillById(id);
    }

    // UPDATE
    @PutMapping("/{id}")
    public BillResponseDTO updateBill(@PathVariable Long id, @RequestBody @Valid BillUpdateDTO request) {
        // On réutilise ton service updateBill, mais il attend BillRequestDTO.
        // Pour éviter de casser ton code: on construit un BillRequestDTO sans appointmentId (inutile en update).
        BillRequestDTO req = new BillRequestDTO();
        req.setAppointmentId(0L); // ignoré par updateBill (car update ne touche pas appointmentId)
        req.setAmount(request.getAmount());
        req.setDescription(request.getDescription());
        return billingService.updateBill(id, req);
    }

    // DELETE
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBill(@PathVariable Long id) {
        billingService.deleteBill(id);
    }

    private Sort parseSort(String sort) {
        if (sort == null || sort.isBlank()) return Sort.by("billDate").descending();
        String[] parts = sort.split(",");
        String field = parts[0].trim();
        String dir = (parts.length > 1) ? parts[1].trim().toLowerCase() : "asc";
        return "desc".equals(dir) ? Sort.by(field).descending() : Sort.by(field).ascending();
    }
}
