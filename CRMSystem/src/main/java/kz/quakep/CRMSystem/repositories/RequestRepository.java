package kz.quakep.CRMSystem.repositories;

import kz.quakep.CRMSystem.entities.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface RequestRepository extends JpaRepository<Request, Long> {

}