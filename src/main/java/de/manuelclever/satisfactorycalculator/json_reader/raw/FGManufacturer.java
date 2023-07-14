package de.manuelclever.satisfactorycalculator.json_reader.raw;

public class FGManufacturer extends Element implements IPowerConsumption {
    private double mManufacturingSpeed;
    private String mFactoryInputConnections;
    private String mPipeInputConnections;
    private String mFactoryOutputConnections;
    private String mPipeOutputConnections;
    private double mMinimumProducingTime;
    private double mMinimumStoppedTime;
    private double mNumCyclesForProductivity;
    private boolean mCanChangePotential;
    private double mMinPotential;
    private double mMaxPotential;
    private double mMaxPotentialIncreasePerCrystal;
    private String mFluidStackSizeDefault;
    private double mFluidStackSizeMultiplier;

    private double mPowerConsumption;
    private double mPowerConsumptionExponent;

    public double getmManufacturingSpeed() {
        return mManufacturingSpeed;
    }

    public void setmManufacturingSpeed(double mManufacturingSpeed) {
        this.mManufacturingSpeed = mManufacturingSpeed;
    }

    public String getmFactoryInputConnections() {
        return mFactoryInputConnections;
    }

    public void setmFactoryInputConnections(String mFactoryInputConnections) {
        this.mFactoryInputConnections = mFactoryInputConnections;
    }

    public String getmPipeInputConnections() {
        return mPipeInputConnections;
    }

    public void setmPipeInputConnections(String mPipeInputConnections) {
        this.mPipeInputConnections = mPipeInputConnections;
    }

    public String getmFactoryOutputConnections() {
        return mFactoryOutputConnections;
    }

    public void setmFactoryOutputConnections(String mFactoryOutputConnections) {
        this.mFactoryOutputConnections = mFactoryOutputConnections;
    }

    public String getmPipeOutputConnections() {
        return mPipeOutputConnections;
    }

    public void setmPipeOutputConnections(String mPipeOutputConnections) {
        this.mPipeOutputConnections = mPipeOutputConnections;
    }

    public double getmMinimumProducingTime() {
        return mMinimumProducingTime;
    }

    public void setmMinimumProducingTime(double mMinimumProducingTime) {
        this.mMinimumProducingTime = mMinimumProducingTime;
    }

    public double getmMinimumStoppedTime() {
        return mMinimumStoppedTime;
    }

    public void setmMinimumStoppedTime(double mMinimumStoppedTime) {
        this.mMinimumStoppedTime = mMinimumStoppedTime;
    }

    public double getmNumCyclesForProductivity() {
        return mNumCyclesForProductivity;
    }

    public void setmNumCyclesForProductivity(double mNumCyclesForProductivity) {
        this.mNumCyclesForProductivity = mNumCyclesForProductivity;
    }

    public boolean ismCanChangePotential() {
        return mCanChangePotential;
    }

    public void setmCanChangePotential(boolean mCanChangePotential) {
        this.mCanChangePotential = mCanChangePotential;
    }

    public double getmMinPotential() {
        return mMinPotential;
    }

    public void setmMinPotential(double mMinPotential) {
        this.mMinPotential = mMinPotential;
    }

    public double getmMaxPotential() {
        return mMaxPotential;
    }

    public void setmMaxPotential(double mMaxPotential) {
        this.mMaxPotential = mMaxPotential;
    }

    public double getmMaxPotentialIncreasePerCrystal() {
        return mMaxPotentialIncreasePerCrystal;
    }

    public void setmMaxPotentialIncreasePerCrystal(double mMaxPotentialIncreasePerCrystal) {
        this.mMaxPotentialIncreasePerCrystal = mMaxPotentialIncreasePerCrystal;
    }

    public String getmFluidStackSizeDefault() {
        return mFluidStackSizeDefault;
    }

    public void setmFluidStackSizeDefault(String mFluidStackSizeDefault) {
        this.mFluidStackSizeDefault = mFluidStackSizeDefault;
    }

    public double getmFluidStackSizeMultiplier() {
        return mFluidStackSizeMultiplier;
    }

    public void setmFluidStackSizeMultiplier(double mFluidStackSizeMultiplier) {
        this.mFluidStackSizeMultiplier = mFluidStackSizeMultiplier;
    }

    @Override
    public double getmPowerConsumption() {
        return mPowerConsumption;
    }

    @Override
    public void setmPowerConsumption(double mPowerConsumption) {
        this.mPowerConsumption = mPowerConsumption;
    }

    @Override
    public double getmPowerConsumptionExponent() {
        return mPowerConsumptionExponent;
    }

    @Override
    public void setmPowerConsumptionExponent(double mPowerConsumptionExponent) {
        this.mPowerConsumptionExponent = mPowerConsumptionExponent;
    }
}
