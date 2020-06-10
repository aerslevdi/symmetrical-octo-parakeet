package com.cristal.crypto.repositories;

import com.cristal.crypto.entities.CryptoCoin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoinRepository extends JpaRepository<CryptoCoin, Long> {
}
