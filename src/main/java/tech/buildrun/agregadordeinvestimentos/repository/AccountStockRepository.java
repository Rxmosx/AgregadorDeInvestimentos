package tech.buildrun.agregadordeinvestimentos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.buildrun.agregadordeinvestimentos.entity.Account;
import tech.buildrun.agregadordeinvestimentos.entity.AccountStock;
import tech.buildrun.agregadordeinvestimentos.entity.AccountStockId;

import java.util.UUID;

@Repository
public interface AccountStockRepository extends JpaRepository<AccountStock, AccountStockId> {
}
