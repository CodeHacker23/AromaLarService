package ru.project.aromalarservice.repositiria;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.project.aromalarservice.model.entity.Diffuser;

@Repository
public interface DiffuserRepository extends JpaRepository<Diffuser,Long> {

}
