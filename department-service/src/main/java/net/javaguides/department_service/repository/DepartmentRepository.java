package net.javaguides.department_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.javaguides.department_service.entity.Department;

@Repository  // ← OBRIGATÓRIO (faltava no tutorial original!)
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    // JpaRepository já fornece: save(), findById(), findAll(), deleteById()...
}