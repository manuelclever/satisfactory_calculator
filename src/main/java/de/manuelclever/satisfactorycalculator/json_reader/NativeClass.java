package de.manuelclever.satisfactorycalculator.json_reader;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NativeClass<T> {
    @JsonProperty("NativeClass")
    private String nativeClass;
    private T list;

    public NativeClass(String nativeClass, T list) {
        this.nativeClass = nativeClass;
        this.list = list;
    }

    public String getNativeClass() {
        return nativeClass;
    }

    public void setNativeClass(String nativeClass) {
        this.nativeClass = nativeClass;
    }

    public T getList() {
        return list;
    }

    public void setList(T list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return nativeClass;
    }
}
