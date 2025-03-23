package com.Truskovski.testTask.DataBase;

import com.Truskovski.testTask.Objects.CallerDataClass;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CallerRepository extends JpaRepository<CallerDataClass, Long> {
}
