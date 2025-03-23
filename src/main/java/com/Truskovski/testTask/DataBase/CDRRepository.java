package com.Truskovski.testTask.DataBase;

import com.Truskovski.testTask.Objects.CDRDataClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CDRRepository extends JpaRepository<CDRDataClass, Long> {

    @Query("SELECT c FROM CDRDataClass c WHERE (c.outComeNumber = :msisdn OR c.inComeNumber = :msisdn) AND SUBSTRING(c.timeStarted, 1, 7) = :yearMonth")
    List<CDRDataClass> findByMsisdnAndMonth(String msisdn, String yearMonth);

    @Query("SELECT c FROM CDRDataClass c WHERE c.outComeNumber = :msisdn OR c.inComeNumber = :msisdn")
    List<CDRDataClass> findByMsisdn(String msisdn);

    @Query("SELECT c FROM CDRDataClass c WHERE SUBSTRING(c.timeStarted, 1, 7) = :yearMonth")
    List<CDRDataClass> findAllByMonth(String yearMonth);
}
