package de.manuelclever.satisfactorycalculator.json_reader.raw;

 interface IPotential {

     double getmPowerConsumption();
     void setmPowerConsumption(double mPowerConsumption);
     double getmPowerConsumptionExponent();
     void setmPowerConsumptionExponent(double mPowerConsumptionExponent);
     double getmMinimumProducingTime();
     void setmMinimumProducingTime(double mMinimumProducingTime);
     double getmMinimumStoppedTime();
     void setmMinimumStoppedTime(double mMinimumStoppedTime);
     double getmNumCyclesForProductivity();
     void setmNumCyclesForProductivity(double mNumCyclesForProductivity);
     boolean ismCanChangePotential();
     void setmCanChangePotential(boolean mCanChangePotential);
     double getmMinPotential();
     void setmMinPotential(double mMinPotential);
     double getmMaxPotential();
     void setmMaxPotential(double mMaxPotential);
     double getmMaxPotentialIncreasePerCrystal();
     void setmMaxPotentialIncreasePerCrystal(double mMaxPotentialIncreasePerCrystal);
     String getmFluidStackSizeDefault();
     void setmFluidStackSizeDefault(String mFluidStackSizeDefault);
     double getmFluidStackSizeMultiplier();
     void setmFluidStackSizeMultiplier(double mFluidStackSizeMultiplier);
}
