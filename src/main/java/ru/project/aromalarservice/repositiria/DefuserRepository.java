package ru.project.aromalarservice.repositiria;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.project.aromalarservice.entity.Diffuser;

@Repository
public interface DefuserRepository extends JpaRepository<Diffuser,Long> {

}
