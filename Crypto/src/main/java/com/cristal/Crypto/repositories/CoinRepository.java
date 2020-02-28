package com.cristal.Crypto.repositories;

import com.cristal.Crypto.entities.CryptoCoin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoinRepository extends JpaRepository<CryptoCoin, Long> {
}
