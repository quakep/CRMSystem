package kz.quakep.CRMSystem.repositories;

import jakarta.transaction.Transactional;
import kz.quakep.CRMSystem.entities.Course;
import kz.quakep.CRMSystem.entities.Operator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public interface OperatorRepository extends JpaRepository<Operator, Long> {

}