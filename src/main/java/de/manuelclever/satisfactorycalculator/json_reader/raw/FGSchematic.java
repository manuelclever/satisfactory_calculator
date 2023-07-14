package de.manuelclever.satisfactorycalculator.json_reader.raw;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FGSchematic extends Element {
    @JsonProperty("FullName")
    private String fullName;
    private String mType;
    private String mSubCategories;
    private double mMenuPriority;
    private double mTechTier;
    private String mCost;
    private double mTimeToComplete;
    private String mRelevantShopSchematics;
    private String mUnlocks;
    private String mSchematicIcon;
    private String mSmallSchematicIcon;
    private String mSchematicDependencies;
    private String mIncludeInBuilds;
    @JsonProperty("Class")
    private String classOfE;
    private String mRecipes;
    private double mNumInventorySlotsToUnlock;
    private String mResourcesToAddToScanner;
    private String mResourcePairsToAddToScanner;
    private String mSchematics;
    private boolean mRequireAllSchematicsToBePurchased;
    private double mNumArmEquipmentSlotsToUnlock;
    private String mItemsToGive;
    private String mGamePhase;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getmType() {
        return mType;
    }

    public void setmType(String mType) {
        this.mType = mType;
    }

    public String getmSubCategories() {
        return mSubCategories;
    }

    public void setmSubCategories(String mSubCategories) {
        this.mSubCategories = mSubCategories;
    }

    public double getmMenuPriority() {
        return mMenuPriority;
    }

    public void setmMenuPriority(double mMenuPriority) {
        this.mMenuPriority = mMenuPriority;
    }

    public double getmTechTier() {
        return mTechTier;
    }

    public void setmTechTier(double mTechTier) {
        this.mTechTier = mTechTier;
    }

    public String getmCost() {
        return mCost;
    }

    public void setmCost(String mCost) {
        this.mCost = mCost;
    }

    public double getmTimeToComplete() {
        return mTimeToComplete;
    }

    public void setmTimeToComplete(double mTimeToComplete) {
        this.mTimeToComplete = mTimeToComplete;
    }

    public String getmRelevantShopSchematics() {
        return mRelevantShopSchematics;
    }

    public void setmRelevantShopSchematics(String mRelevantShopSchematics) {
        this.mRelevantShopSchematics = mRelevantShopSchematics;
    }

    public String getmUnlocks() {
        return mUnlocks;
    }

    public void setmUnlocks(String mUnlocks) {
        this.mUnlocks = mUnlocks;
    }

    public String getmSchematicIcon() {
        return mSchematicIcon;
    }

    public void setmSchematicIcon(String mSchematicIcon) {
        this.mSchematicIcon = mSchematicIcon;
    }

    public String getmSmallSchematicIcon() {
        return mSmallSchematicIcon;
    }

    public void setmSmallSchematicIcon(String mSmallSchematicIcon) {
        this.mSmallSchematicIcon = mSmallSchematicIcon;
    }

    public String getmSchematicDependencies() {
        return mSchematicDependencies;
    }

    public void setmSchematicDependencies(String mSchematicDependencies) {
        this.mSchematicDependencies = mSchematicDependencies;
    }

    public String getmIncludeInBuilds() {
        return mIncludeInBuilds;
    }

    public void setmIncludeInBuilds(String mIncludeInBuilds) {
        this.mIncludeInBuilds = mIncludeInBuilds;
    }

    public String getClassOfE() {
        return classOfE;
    }

    public void setClassOfE(String classOfE) {
        this.classOfE = classOfE;
    }

    public String getmRecipes() {
        return mRecipes;
    }

    public void setmRecipes(String mRecipes) {
        this.mRecipes = mRecipes;
    }

    public double getmNumInventorySlotsToUnlock() {
        return mNumInventorySlotsToUnlock;
    }

    public void setmNumInventorySlotsToUnlock(double mNumInventorySlotsToUnlock) {
        this.mNumInventorySlotsToUnlock = mNumInventorySlotsToUnlock;
    }

    public String getmResourcesToAddToScanner() {
        return mResourcesToAddToScanner;
    }

    public void setmResourcesToAddToScanner(String mResourcesToAddToScanner) {
        this.mResourcesToAddToScanner = mResourcesToAddToScanner;
    }

    public String getmResourcePairsToAddToScanner() {
        return mResourcePairsToAddToScanner;
    }

    public void setmResourcePairsToAddToScanner(String mResourcePairsToAddToScanner) {
        this.mResourcePairsToAddToScanner = mResourcePairsToAddToScanner;
    }

    public String getmSchematics() {
        return mSchematics;
    }

    public void setmSchematics(String mSchematics) {
        this.mSchematics = mSchematics;
    }

    public boolean ismRequireAllSchematicsToBePurchased() {
        return mRequireAllSchematicsToBePurchased;
    }

    public void setmRequireAllSchematicsToBePurchased(boolean mRequireAllSchematicsToBePurchased) {
        this.mRequireAllSchematicsToBePurchased = mRequireAllSchematicsToBePurchased;
    }

    public double getmNumArmEquipmentSlotsToUnlock() {
        return mNumArmEquipmentSlotsToUnlock;
    }

    public void setmNumArmEquipmentSlotsToUnlock(double mNumArmEquipmentSlotsToUnlock) {
        this.mNumArmEquipmentSlotsToUnlock = mNumArmEquipmentSlotsToUnlock;
    }

    public String getmItemsToGive() {
        return mItemsToGive;
    }

    public void setmItemsToGive(String mItemsToGive) {
        this.mItemsToGive = mItemsToGive;
    }

    public String getmGamePhase() {
        return mGamePhase;
    }

    public void setmGamePhase(String mGamePhase) {
        this.mGamePhase = mGamePhase;
    }
}
