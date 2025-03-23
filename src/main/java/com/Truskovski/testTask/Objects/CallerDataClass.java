package com.Truskovski.testTask.Objects;


import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "Caller_WR")
public class CallerDataClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private  String phoneNumber;

    public CallerDataClass() {
    }

    public CallerDataClass(String number) {
        this.phoneNumber = number;
    }

    public String getNumber() {
        return phoneNumber;
    }


    @Override
    public String toString() {
        return "Данные абонента из базы данных \n" +
                "id=" + id +
                "\n number='" + phoneNumber  +
                '\n';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CallerDataClass that = (CallerDataClass) o;
        return Objects.equals(phoneNumber, that.phoneNumber);
    }

}
