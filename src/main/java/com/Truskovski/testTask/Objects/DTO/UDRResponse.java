package com.Truskovski.testTask.Objects.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UDRResponse {

    private final String msisdn;

    @JsonProperty("incomingCall")
    private String incomingCallTotalTime;

    @JsonProperty("outgoingCall")
    private String outgoingCallTotalTime;

    public UDRResponse(String msisdn, String incomingCallTotalTime, String outgoingCallTotalTime) {
        this.msisdn = msisdn;
        this.incomingCallTotalTime = incomingCallTotalTime;
        this.outgoingCallTotalTime = outgoingCallTotalTime;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public String getIncomingCallTotalTime() {
        return incomingCallTotalTime;
    }

    public String getOutgoingCallTotalTime() {
        return outgoingCallTotalTime;
    }

    @JsonProperty("incomingCall")
    private Object getIncomingCall() {
        return new TotalTimeWrapper(incomingCallTotalTime);
    }

    @JsonProperty("outgoingCall")
    private Object getOutgoingCall() {
        return new TotalTimeWrapper(outgoingCallTotalTime);
    }

    private record TotalTimeWrapper(@JsonProperty("totalTime") String totalTime) {
            private TotalTimeWrapper(String totalTime) {
                this.totalTime = totalTime;
            }
        }
}