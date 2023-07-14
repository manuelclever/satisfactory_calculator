package de.manuelclever.satisfactorycalculator.json_reader;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import de.manuelclever.satisfactorycalculator.content.items.Edge;
import de.manuelclever.satisfactorycalculator.json_reader.raw.*;
import de.manuelclever.satisfactorycalculator.json_reader.utils.KeyGenerator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomDeserializer extends StdDeserializer<List<NativeClass>> {

    public CustomDeserializer() {
        this(null);
    }

    public CustomDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public List<NativeClass> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException, JsonProcessingException {
        JsonNode arrNode = jsonParser.getCodec().readTree(jsonParser);
        List<NativeClass> nativeClasses = new ArrayList<>();

        for(JsonNode objNode : arrNode) {
            String nativeClassName = objNode.get("NativeClass").asText();
            JsonNode arrNodeClasses = objNode.get("Classes");

            if(nativeClassName.contains("FGResourceDescriptor")) {

                List<FGDescriptorResource> fgDescriptorResourceList = new ArrayList<>();
                for(JsonNode node : arrNodeClasses) {
                    FGDescriptorResource fgDescriptorResource = new FGDescriptorResource();
                    setClass(fgDescriptorResource, node);
                    setDescriptor(fgDescriptorResource, node);
                    fgDescriptorResource.setmDecalSize(node.get("mDecalSize").asDouble());
                    fgDescriptorResource.setmPingColor(node.get("mPingColor").asText());
                    fgDescriptorResource.setmCollectSpeedMultiplier(node.get("mCollectSpeedMultiplier").asDouble());

                    fgDescriptorResource.setId(KeyGenerator.getInstance().createNewKey());

                    fgDescriptorResourceList.add(fgDescriptorResource);
                    ItemMapTest.put(fgDescriptorResource);
                }

                nativeClasses.add(new NativeClass<>(nativeClassName, fgDescriptorResourceList));
            } else if(nativeClassName.contains("FGItemDescriptor")) {

                List<FGDescriptor> fgDescriptorList = new ArrayList<>();
                for(JsonNode node : arrNodeClasses) {
                    FGDescriptor fgDescriptor = new FGDescriptor();
                    setClass(fgDescriptor, node);
                    fgDescriptor.setmAbbreviatedDisplayName(node.get("mAbbreviatedDisplayName").asText());
                    fgDescriptor.setmStackSize(node.get("mStackSize").asText());
                    fgDescriptor.setmEnergyValue(node.get("mEnergyValue").asDouble());
                    fgDescriptor.setmRadioactiveDecay(node.get("mRadioactiveDecay").asDouble());
                    fgDescriptor.setmForm(node.get("mForm").asText());
                    fgDescriptor.setmSmallIcon(node.get("mSmallIcon").asText());
                    fgDescriptor.setmPersistentBigIcon(node.get("mPersistentBigIcon").asText());
                    fgDescriptor.setmFluidColor(node.get("mFluidColor").asText());
                    fgDescriptor.setmGasColor(node.get("mGasColor").asText());
                    fgDescriptor.setmResourceSinkPoints(node.get("mResourceSinkPoints").asDouble());

                    fgDescriptor.setId(KeyGenerator.getInstance().createNewKey());

                    fgDescriptorList.add(fgDescriptor);
                    ItemMapTest.put(fgDescriptor);
                }
                nativeClasses.add(new NativeClass<>(nativeClassName, fgDescriptorList));
            } else if(nativeClassName.contains("FGRecipe")){
                List<FGRecipe> fgRecipeList = new ArrayList<>();
                for(JsonNode node : arrNodeClasses) {
                    FGRecipe fgRecipe = new FGRecipe();
                    setClass(fgRecipe, node);
                    fgRecipe.setFullName(node.get("FullName").asText());
                    fgRecipe.setmManufacturingMenuPriority(node.get("mManufacturingMenuPriority").asDouble());
                    fgRecipe.setmManufactoringDuration(node.get("mManufactoringDuration").asDouble());
                    fgRecipe.setmManualManufacturingMultiplier(node.get("mManualManufacturingMultiplier").asDouble());
                    fgRecipe.setmProducedIn(node.get("mProducedIn").asText());
                    fgRecipe.setmVariablePowerConsumptionConstant(node.get("mVariablePowerConsumptionConstant").asDouble());
                    fgRecipe.setmVariablePowerConsumptionFactor(node.get("mVariablePowerConsumptionFactor").asDouble());

                    try {
                        String [] classNameArr = fgRecipe.getClassNameOnlyName().split(("(?=[A-Z])"));
                        if(classNameArr.length == 1) {
                            fgRecipe.setId(ItemMapTest.getItem(classNameArr[0]).getId());
                        } else {
                            fgRecipe.setId(ItemMapTest.getItem(classNameArr).getId());
                        }
                    } catch(NullPointerException e) {
                        fgRecipe.setId(-1);
                    }
//                    fgRecipe.setmProducedIn();

                    String mIngredientsString = node.get("mIngredients").asText();

                    Pattern pattern = Pattern.compile("[.][^\\W_]+\\w([\\w]+)[_][C][\\d\\w.\"',]+[=](\\d)+");
                    Matcher matcher = pattern.matcher(mIngredientsString);

                    LinkedList<Edge> edges = fgRecipe.getEdges();
                    while(matcher.find()) {
                        String resourceString = matcher.group(1);
                        int amount = 0;
//                        try {
                            amount = Integer.parseInt(matcher.group(2));
//                        } catch (NumberFormatException ignored) {}

                        //get Resource ID from ItemMap
                        int resourceID = -1;
                        try {
                            resourceID = ItemMapTest.getItem(resourceString).getId();

                            String [] ressourceStringArr = resourceString.split(("(?=[A-Z])"));
                            if(ressourceStringArr.length == 1) {
                                resourceID = ItemMapTest.getItem(ressourceStringArr[0]).getId();
                            } else {
                                resourceID = ItemMapTest.getItem(ressourceStringArr).getId();
                            }
                        } catch(NullPointerException ignore) {}

                        edges.add(new Edge(fgRecipe.getId(), resourceID, amount));
                    }
                    fgRecipe.setEdges(edges);
                    fgRecipeList.add(fgRecipe);
                }
                fgRecipeList.forEach(fgRecipe -> {
                    Element element = ItemMapTest.getItem(fgRecipe.getId());
                    if(element != null) {
                        fgRecipe.convertToElement(element);
                    }
                });
            }
        }

        return nativeClasses;
    }

    public void setClass(Element element, JsonNode node) {
        element.setClassName(node.get("ClassName").asText());
        element.setmDisplayName(node.get("mDisplayName").asText());

        try {
            element.setmDescription(node.get("mDescription").asText());
        } catch (NullPointerException ignore){}
    }

    public void setDescriptor(Element element, JsonNode node) {
        ((IDescriptor) element).setmAbbreviatedDisplayName(node.get("mAbbreviatedDisplayName").asText());
        ((IDescriptor) element).setmEnergyValue(node.get("mEnergyValue").asDouble());
        ((IDescriptor) element).setmRadioactiveDecay(node.get("mRadioactiveDecay").asDouble());
        ((IDescriptor) element).setmForm(node.get("mForm").asText());
        ((IDescriptor) element).setmSmallIcon(node.get("mSmallIcon").asText());
        ((IDescriptor) element).setmPersistentBigIcon(node.get("mPersistentBigIcon").asText());
        ((IDescriptor) element).setmFluidColor(node.get("mFluidColor").asText());
        ((IDescriptor) element).setmGasColor(node.get("mGasColor").asText());
        ((IDescriptor) element).setmResourceSinkPoints(node.get("mResourceSinkPoints").asDouble());

        try {
            ((IDescriptor) element).setmStackSize(node.get("StackSize").asText());
        } catch(NullPointerException ignore) {}
    }
}
