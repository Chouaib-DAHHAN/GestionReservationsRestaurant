package res.restau.resres.service;

import res.restau.resres.model.TableRestaurant;
import res.restau.resres.repository.TableRestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TableService {

    @Autowired
    private TableRestaurantRepository tableRepository;

    public List<TableRestaurant> findAll() {
        return tableRepository.findAll();
    }

    public TableRestaurant save(TableRestaurant table) {
        return tableRepository.save(table);
    }

    public Optional<TableRestaurant> findById(Long id) {
        return tableRepository.findById(id);
    }

    public void deleteById(Long id) {
        tableRepository.deleteById(id);
    }
}
