package my.app.repository;

import my.app.model.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    Page<Booking> findByResourceID(Long resourceID, Pageable pageable);

    Page<Booking> findByUserID(Long userID, Pageable pageable);
}
