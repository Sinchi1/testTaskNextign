package com.Truskovski.testTask.DataBase;

import com.Truskovski.testTask.Objects.CallerDataClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CallerRepository extends JpaRepository<CallerDataClass, Long> {

    @Query("SELECT c FROM CallerDataClass c WHERE (c.phoneNumber = :phoneNumber)")
    CallerDataClass findByPhoneNumber(String phoneNumber);
}
