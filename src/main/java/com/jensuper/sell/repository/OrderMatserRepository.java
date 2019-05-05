package com.jensuper.sell.repository;

import com.jensuper.sell.entity.OrderMaster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderMatserRepository extends JpaRepository<OrderMaster,String> {
    List<OrderMaster> findAllByBuyerOpenid(String buyerOpenid);
}
