package fp.api.ApiBarWaiter.api.repository;

import fp.api.ApiBarWaiter.api.model.Bebida;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BebidaRepository extends JpaRepository<Bebida, Long> {
}
