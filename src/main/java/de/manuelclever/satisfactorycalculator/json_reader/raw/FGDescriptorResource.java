package de.manuelclever.satisfactorycalculator.json_reader.raw;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class FGDescriptorResource extends FGDescriptor {
    private SimpleDoubleProperty mDecalSize;
    private SimpleStringProperty mPingColor;
    private SimpleDoubleProperty mCollectSpeedMultiplier;

    public FGDescriptorResource() {
        super();
        this.mDecalSize = new SimpleDoubleProperty();
        this.mPingColor = new SimpleStringProperty();
        this.mCollectSpeedMultiplier = new SimpleDoubleProperty();
    }

    public double getmDecalSize() {
        return mDecalSize.get();
    }

    public SimpleDoubleProperty mDecalSizeProperty() {
        return mDecalSize;
    }

    public void setmDecalSize(double mDecalSize) {
        this.mDecalSize.set(mDecalSize);
    }

    public String getmPingColor() {
        return mPingColor.get();
    }

    public SimpleStringProperty mPingColorProperty() {
        return mPingColor;
    }

    public void setmPingColor(String mPingColor) {
        this.mPingColor.set(mPingColor);
    }

    public double getmCollectSpeedMultiplier() {
        return mCollectSpeedMultiplier.get();
    }

    public SimpleDoubleProperty mCollectSpeedMultiplierProperty() {
        return mCollectSpeedMultiplier;
    }

    public void setmCollectSpeedMultiplier(double mCollectSpeedMultiplier) {
        this.mCollectSpeedMultiplier.set(mCollectSpeedMultiplier);
    }
}
