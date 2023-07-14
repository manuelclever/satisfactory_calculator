package de.manuelclever.satisfactorycalculator.json_reader;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.List;

public class Reader {

    public static void main( String[] args ) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {

            String absolutePath = FileSystems.getDefault().getPath("src", "main", "java",
                    "de", "manuelclever", "satisfactorycalculator", "json_reader", "Docs.json").toAbsolutePath().toString();

            SimpleModule module = new SimpleModule();
            module.addDeserializer(List.class, new CustomDeserializer());
            objectMapper.registerModule(module);

            List<NativeClass> nativeClasses = objectMapper.readValue(new File(absolutePath), List.class);

            for(NativeClass nativeClass : nativeClasses) {
//                System.out.println(nativeClass.toString());
//                if(nativeClass.getNativeClass().toLowerCase().contains("descriptor")) {
//                    for(ItemDescriptor itemDescriptor : (List<ItemDescriptor>) nativeClass.getList()) {
//                        System.out.println(itemDescriptor.getmDisplayName() + ": " + itemDescriptor.getmDescription().replace("\r", " ").replace("\n", " "));
//                    }
//                } else {
//                    for(TestClass testClass : (List<TestClass>) nativeClass.getList()) {
//                        System.out.println(testClass.getName());
//                    }
//
//                }
//                System.out.println("===");
            }

//            List<NativeClass> nativeClasses = Arrays.asList(objectMapper.readValue(new File(absolutePath),
//                    NativeClass[].class));

//            nativeClasses.forEach(nativeClass -> {
//                Arrays.stream(nativeClass.getClasses()).forEach(item -> {
//                    System.out.println(item.getmDisplayName());
//                });
//            });


//            NativeClass[] nativeClasses = objectMapper.readValue(new File(absolutePath), NativeClass[].class);
//
//            Arrays.stream(nativeClasses).sequential().forEach(nativeClass -> {
//                System.out.println(nativeClass.getNativeClass());
//                Arrays.stream(nativeClass.getClasses()).sequential().forEach(item -> {
//                    System.out.println(item.getClassName() + ", " + item.getmDisplayName() + ", " + item.getmDescription());
//                });
//            });

            ItemMapTest.get().forEach((id, item) -> {
                System.out.println(item.getmDisplayName());
                System.out.println("\t" + item.getmDescription().replace("\n", "").replace("\r", " "));
                System.out.println("\t" + item.getmProducedIn());
                System.out.println("\tEdges:");
                item.getEdges().forEach(edge -> {
                    System.out.println("\t" + edge.getResourceId() + ", " + edge.getWeight());
                });
                System.out.println("\t");
            });



        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
