package res.restau.resres.repository;

import res.restau.resres.model.TableRestaurant;
import org.springframework.data.jpa.repository.JpaRepository;



public interface TableRestaurantRepository extends JpaRepository<TableRestaurant, Long> {
    boolean existsByNumeroTable(int numeroTable);
}