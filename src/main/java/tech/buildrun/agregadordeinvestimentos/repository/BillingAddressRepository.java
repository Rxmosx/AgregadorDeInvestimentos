package tech.buildrun.agregadordeinvestimentos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.buildrun.agregadordeinvestimentos.entity.BillingAddress;
import tech.buildrun.agregadordeinvestimentos.entity.Stock;

import java.util.UUID;

@Repository
public interface BillingAddressRepository extends JpaRepository<BillingAddress, UUID> {
}
