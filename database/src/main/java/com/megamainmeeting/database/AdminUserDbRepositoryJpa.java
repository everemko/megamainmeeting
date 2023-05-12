package com.megamainmeeting.database;


import com.megamainmeeting.database.dto.AdminUserDb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface AdminUserDbRepositoryJpa extends JpaRepository<AdminUserDb, Long> {
}
