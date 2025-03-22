package com.Truskovski.testTask.DataBase;

import com.Truskovski.testTask.Objects.CDRDataClass;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CDRRepository extends JpaRepository<CDRDataClass, Long> {
}
