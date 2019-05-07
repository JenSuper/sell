package com.jensuper.sell.repository;

import com.jensuper.sell.entity.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderMatserRepository extends JpaRepository<OrderMaster,String> {

    Page<OrderMaster> findAllByBuyerOpenid(String buyerOpenid, Pageable pageable);
}
