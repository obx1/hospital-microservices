package ma.you.hospital.billing.repositories;

import ma.you.hospital.billing.domain.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillRepository extends JpaRepository<Bill, Long> {
}
