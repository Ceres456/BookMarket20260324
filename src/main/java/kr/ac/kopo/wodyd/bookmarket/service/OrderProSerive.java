package kr.ac.kopo.wodyd.bookmarket.service;

import kr.ac.kopo.wodyd.bookmarket.domain.Order;
import kr.ac.kopo.wodyd.bookmarket.repository.OrderProRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class OrderProSerive {
    @Autowired
    private OrderProRepository orderProRepository;

    public void save(Order order){
        orderProRepository.save(order);
    }

    public Order get(Long id){
        return orderProRepository.findById(id).get();
    }

    public void delete(Long id) {
        orderProRepository.deleteById(id);
    }

    public void deleteAll(){
        orderProRepository.deleteAll();
    }

    public Page<Order> listAll(int pageNum, String sortField, String sortDir){

        return null;
    }
}
