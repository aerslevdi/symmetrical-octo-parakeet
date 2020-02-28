package com.cristal.Crypto.repositories;

import com.cristal.Crypto.entities.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository <Wallet, Long> {
}
