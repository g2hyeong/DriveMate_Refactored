package drivemate.drivemate.repository;

import drivemate.drivemate.domain.SemiRouteInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SemiRouteInfoRepository extends JpaRepository<SemiRouteInfo, Long> {
}
