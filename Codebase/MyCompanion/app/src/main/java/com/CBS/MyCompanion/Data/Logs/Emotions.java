package com.CBS.MyCompanion.Data.Logs;

public enum Emotions {
    SAD, HAPPY, ANXIOUS, STRESSED, ANGRY, LONELY, WITHDRAWN, FEARFUL;

    public Integer getIndex() {
        return this.ordinal();
    }

}
