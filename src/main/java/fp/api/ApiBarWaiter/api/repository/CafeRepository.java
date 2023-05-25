package fp.api.ApiBarWaiter.api.repository;

import fp.api.ApiBarWaiter.api.model.Cafe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CafeRepository extends JpaRepository<Cafe, Long> {
}
