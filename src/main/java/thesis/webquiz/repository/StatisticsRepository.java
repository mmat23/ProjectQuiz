package thesis.webquiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import thesis.webquiz.model.Statistics;

public interface StatisticsRepository extends JpaRepository<Statistics, Long> {
    
}
