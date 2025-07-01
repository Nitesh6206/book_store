package com.niteshkr.online_book_store.Repository;

import com.niteshkr.online_book_store.entity.PurchaseHistory;
import com.niteshkr.online_book_store.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PurchaseHistoryRepository extends JpaRepository<PurchaseHistory,Long> {
//    List<PurchaseHistory> findbyUserId(int id);
    List<PurchaseHistory> findByCustomerUser(User user);

}
