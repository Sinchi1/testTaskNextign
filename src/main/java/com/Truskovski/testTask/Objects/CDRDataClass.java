package com.Truskovski.testTask.Objects;

import jakarta.persistence.*;

@Entity
@Table(name = "CDR_WR")
public class CDRDataClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "in_come_number", nullable = false)
    private String inComeNumber;

    @Column(name = "out_come_number", nullable = false)
    private String outComeNumber;

    @Column(name = "time_started", nullable = false)
    private String timeStarted;

    @Column(name = "time_ended", nullable = false)
    private String timeEnded;

    @Column(nullable = false)
    private String type;

    public CDRDataClass(String type, String outComeNumber, String inComeNumber, String timeStarted, String timeEnded) {
        this.type = type;
        this.outComeNumber = outComeNumber;
        this.inComeNumber = inComeNumber;
        this.timeStarted = timeStarted;
        this.timeEnded = timeEnded;
    }

    public CDRDataClass() {
    }

    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getOutComeNumber() {
        return outComeNumber;
    }


    public String getInComeNumber() {
        return inComeNumber;
    }


    public String getTimeStarted() {
        return timeStarted;
    }


    public String getTimeEnded() {
        return timeEnded;
    }

    @Override
    public String toString() {
        return "CRD Абонента" +
                "id=" + id +
                ", Тип звонка='" + type + '\'' +
                ", Исходящий номер='" + outComeNumber + '\'' +
                ", Входящий номер='" + inComeNumber + '\'' +
                ", Время начала='" + timeStarted + '\'' +
                ", Время конца='" + timeEnded + '\'' +
                '}';
    }
}