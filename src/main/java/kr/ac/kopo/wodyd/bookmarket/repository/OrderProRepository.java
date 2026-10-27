package kr.ac.kopo.wodyd.bookmarket.repository;

import kr.ac.kopo.wodyd.bookmarket.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProRepository extends JpaRepository<Order,Long> {
}
