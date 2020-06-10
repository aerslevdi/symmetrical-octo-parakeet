package com.cristal.crypto.repositories;

import com.cristal.crypto.entities.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;


public interface WalletRepository extends JpaRepository <Wallet, Long> {
}
