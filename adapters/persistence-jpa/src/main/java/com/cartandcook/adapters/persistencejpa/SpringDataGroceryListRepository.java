package com.cartandcook.adapters.persistencejpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataGroceryListRepository extends JpaRepository<GroceryListEntity, Long> {
    // No methods required — JpaRepository provides save(), findById(), findAll(), delete(), etc.
}
