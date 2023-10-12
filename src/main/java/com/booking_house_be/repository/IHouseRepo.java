package com.booking_house_be.repository;

import com.booking_house_be.entity.House;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface IHouseRepo extends JpaRepository<House, Integer> {


    @Query("SELECT h.id AS id, h.name AS name, h.thumbnail AS thumbnail, h.price AS price, h.address AS address, h.province AS province, h.status AS status, " +
            "SUM(CASE WHEN b.status = 'CONFIRMED' THEN b.total ELSE 0 END) AS revenue " +
            "FROM House h " +
            "LEFT JOIN Booking b ON h.id = b.house.id " +
            "WHERE h.owner.id = :ownerId " +
            "AND h.name LIKE CONCAT('%', :nameSearch, '%') " +
            "AND h.status LIKE CONCAT('%', :status, '%') " +
            "GROUP BY h.id, h.name, h.thumbnail, h.price, h.address, h.status")
    Page<IHouseRepo.HouseInfo> findByOwnerIdAndNameAndStatus(@Param("ownerId") int ownerId, @Param("nameSearch") String nameSearch, @Param("status") String status, Pageable pageable);


    interface HouseInfo {
        int getId();

        String getName();

        String getThumbnail();

        double getPrice();

        String getAddress();
        String getProvince();

        double getRevenue();

        String getStatus();
    }


    @Query("SELECT h FROM House h WHERE h.province LIKE concat('%', :province, '%') AND h.name LIKE concat('%', :nameSearch, '%') AND (h.price - h.price * h.sale / 100) BETWEEN :minPrice AND :maxPrice")
    Page<House> findHousesByNameAndPriceRangeAndLocal(Pageable pageable, @Param("nameSearch") String nameSearch, @Param("province") String province, @Param("minPrice") double minPrice, @Param("maxPrice") double maxPrice);

    @Query("SELECT h FROM House h WHERE (h.price - h.price * h.sale / 100)  BETWEEN :minPrice AND :maxPrice")
    Page<House> findAllByPriceRange(Pageable pageable, @Param("minPrice") double minPrice, @Param("maxPrice") double maxPrice);

    @Query("SELECT h FROM House h WHERE h.name LIKE concat('%', :nameSearch, '%') AND (h.price - h.price * h.sale / 100) BETWEEN :minPrice AND :maxPrice")
    Page<House> findHousesByNameAndPriceRange(Pageable pageable, @Param("nameSearch") String nameSearch, @Param("minPrice") double minPrice, @Param("maxPrice") double maxPrice);


    House findByIdAndOwnerId(int houseId, int ownerId);
}


