package fp.api.ApiBarWaiter.api.repository;

import fp.api.ApiBarWaiter.api.model.Postre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostreRepository extends JpaRepository<Postre, Long> {
}
