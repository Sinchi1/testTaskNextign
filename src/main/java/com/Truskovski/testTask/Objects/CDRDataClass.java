package com.Truskovski.testTask.Objects;

import jakarta.persistence.*;

@Entity
@Table(name="CDR_WR")
public class CDRDataClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private  String type;
    @Column(nullable = false)
    private  String outComeNumber;
    @Column(nullable = false)
    private  String inComeNumber;
    @Column(nullable = false)
    private  String timeStarted;
    @Column(nullable = false)
    private  String timeEnded;


    public CDRDataClass(String type, String outComeNumber, String inComeNumber, String timeStarted, String timeEnded) {
        this.type = type;
        this.outComeNumber = outComeNumber;
        this.inComeNumber = inComeNumber;
        this.timeStarted = timeStarted;
        this.timeEnded = timeEnded;
    }

    public CDRDataClass() {
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
        return "CDRDataClass{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", outComeNumber='" + outComeNumber + '\'' +
                ", inComeNumber='" + inComeNumber + '\'' +
                ", timeStarted='" + timeStarted + '\'' +
                ", timeEnded='" + timeEnded + '\'' +
                '}';
    }
}
