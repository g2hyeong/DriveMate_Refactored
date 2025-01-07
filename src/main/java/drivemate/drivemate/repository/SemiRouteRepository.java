package drivemate.drivemate.repository;

import drivemate.drivemate.domain.SemiRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SemiRouteRepository extends JpaRepository<SemiRoute, Long> {
}
