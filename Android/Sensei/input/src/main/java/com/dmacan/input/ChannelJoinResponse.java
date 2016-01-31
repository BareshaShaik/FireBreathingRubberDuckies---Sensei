package com.dmacan.input;

public class ChannelJoinResponse {

    private long channelId;
    private long paidRows;

    public long getChannelId() {
        return channelId;
    }

    public void setChannelId(long channelId) {
        this.channelId = channelId;
    }

    public long getPaidRows() {
        return paidRows;
    }

    public void setPaidRows(long paidRows) {
        this.paidRows = paidRows;
    }
}
