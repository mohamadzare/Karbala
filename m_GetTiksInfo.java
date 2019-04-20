package com.zare.karbala;

public class m_GetTiksInfo {
    public int tickID ;
    public String IsOpen ;
    public String ClientName;
    public String MasolName ;
    public int Rank ;
    public String dateTime ;

    public void setTickID(int tickID) {
        this.tickID = tickID;
    }

    public void setMasolName(String masolName) {
        MasolName = masolName;
    }

    public void setClientName(String clientName) {
        ClientName = clientName;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public void setIsOpen(String isOpen) {
        IsOpen = isOpen;
    }

    public void setRank(int rank) {
        Rank = rank;
    }

    public String getMasolName() {
        return MasolName;
    }

    public int getRank() {
        return Rank;
    }

    public int getTickID() {
        return tickID;
    }

    public String getClientName() {
        return ClientName;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getIsOpen() {
        return IsOpen;
    }
}
