package de.manuelclever.satisfactorycalculator.json_reader.raw;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class FGDescriptor extends Element implements IDescriptor {
    private SimpleStringProperty mAbbreviatedDisplayName;
    private SimpleStringProperty mStackSize;
    private SimpleDoubleProperty mEnergyValue;
    private SimpleDoubleProperty mRadioactiveDecay;
    private SimpleStringProperty mForm;
    private SimpleStringProperty mSmallIcon;
    private SimpleStringProperty mPersistentBigIcon;
    private SimpleStringProperty mFluidColor;
    private SimpleStringProperty mGasColor;
    private SimpleDoubleProperty mResourceSinkPoints;

    public FGDescriptor() {
        super();
        this.mAbbreviatedDisplayName = new SimpleStringProperty();
        this.mStackSize = new SimpleStringProperty();
        this.mEnergyValue = new SimpleDoubleProperty();
        this.mRadioactiveDecay = new SimpleDoubleProperty();
        this.mForm = new SimpleStringProperty();
        this.mSmallIcon = new SimpleStringProperty();
        this.mPersistentBigIcon = new SimpleStringProperty();
        this.mFluidColor = new SimpleStringProperty();
        this.mGasColor = new SimpleStringProperty();
        this.mResourceSinkPoints = new SimpleDoubleProperty();
    }

    @Override
    public String getmAbbreviatedDisplayName() {
        return mAbbreviatedDisplayName.get();
    }

    @Override
    public SimpleStringProperty mAbbreviatedDisplayNameProperty() {
        return mAbbreviatedDisplayName;
    }

    public void setmAbbreviatedDisplayName(String mAbbreviatedDisplayName) {
        this.mAbbreviatedDisplayName.set(mAbbreviatedDisplayName);
    }

    @Override
    public String getmStackSize() {
        return mStackSize.get();
    }

    @Override
    public SimpleStringProperty mStackSizeProperty() {
        return mStackSize;
    }

    public void setmStackSize(String mStackSize) {
        this.mStackSize.set(mStackSize);
    }

    @Override
    public double getmEnergyValue() {
        return mEnergyValue.get();
    }

    @Override
    public SimpleDoubleProperty mEnergyValueProperty() {
        return mEnergyValue;
    }

    public void setmEnergyValue(double mEnergyValue) {
        this.mEnergyValue.set(mEnergyValue);
    }

    @Override
    public double getmRadioactiveDecay() {
        return mRadioactiveDecay.get();
    }

    @Override
    public SimpleDoubleProperty mRadioactiveDecayProperty() {
        return mRadioactiveDecay;
    }

    public void setmRadioactiveDecay(double mRadioactiveDecay) {
        this.mRadioactiveDecay.set(mRadioactiveDecay);
    }

    @Override
    public String getmForm() {
        return mForm.get();
    }

    @Override
    public SimpleStringProperty mFormProperty() {
        return mForm;
    }

    public void setmForm(String mForm) {
        this.mForm.set(mForm);
    }

    @Override
    public String getmSmallIcon() {
        return mSmallIcon.get();
    }

    @Override
    public SimpleStringProperty mSmallIconProperty() {
        return mSmallIcon;
    }

    public void setmSmallIcon(String mSmallIcon) {
        this.mSmallIcon.set(mSmallIcon);
    }

    @Override
    public String getmPersistentBigIcon() {
        return mPersistentBigIcon.get();
    }

    @Override
    public SimpleStringProperty mPersistentBigIconProperty() {
        return mPersistentBigIcon;
    }

    public void setmPersistentBigIcon(String mPersistentBigIcon) {
        this.mPersistentBigIcon.set(mPersistentBigIcon);
    }

    @Override
    public String getmFluidColor() {
        return mFluidColor.get();
    }

    @Override
    public SimpleStringProperty mFluidColorProperty() {
        return mFluidColor;
    }

    public void setmFluidColor(String mFluidColor) {
        this.mFluidColor.set(mFluidColor);
    }

    @Override
    public String getmGasColor() {
        return mGasColor.get();
    }

    @Override
    public SimpleStringProperty mGasColorProperty() {
        return mGasColor;
    }

    public void setmGasColor(String mGasColor) {
        this.mGasColor.set(mGasColor);
    }

    @Override
    public double getmResourceSinkPoints() {
        return mResourceSinkPoints.get();
    }

    @Override
    public SimpleDoubleProperty mResourceSinkPointsProperty() {
        return mResourceSinkPoints;
    }

    public void setmResourceSinkPoints(double mResourceSinkPoints) {
        this.mResourceSinkPoints.set(mResourceSinkPoints);
    }
}
