package de.manuelclever.satisfactorycalculator.json_reader.raw;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public interface IDescriptor {

    String getmAbbreviatedDisplayName();
    SimpleStringProperty mAbbreviatedDisplayNameProperty();
    void setmAbbreviatedDisplayName(String mAbbreviatedDisplayName);
    String getmStackSize();
    SimpleStringProperty mStackSizeProperty();
    void setmStackSize(String mStackSize);
    double getmEnergyValue();
    SimpleDoubleProperty mEnergyValueProperty();
    void setmEnergyValue(double mEnergyValue);
    double getmRadioactiveDecay();
    SimpleDoubleProperty mRadioactiveDecayProperty();
    void setmRadioactiveDecay(double mRadioactiveDecay);
    String getmForm();
    SimpleStringProperty mFormProperty();
    void setmForm(String mForm);
    String getmSmallIcon();
    SimpleStringProperty mSmallIconProperty();
    void setmSmallIcon(String mSmallIcon);
    String getmPersistentBigIcon();
    SimpleStringProperty mPersistentBigIconProperty();
    void setmPersistentBigIcon(String mPersistentBigIcon);
    String getmFluidColor();
    SimpleStringProperty mFluidColorProperty();
    void setmFluidColor(String mFluidColor);
    String getmGasColor();
    SimpleStringProperty mGasColorProperty();
    void setmGasColor(String mGasColor);
    double getmResourceSinkPoints();
    SimpleDoubleProperty mResourceSinkPointsProperty();
    void setmResourceSinkPoints(double mResourceSinkPoints);
}
