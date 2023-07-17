package co.com.bancolombia.factory;

import co.com.bancolombia.exceptions.ScreenPlayException;

import java.io.IOException;

public interface ModuleFactory {
    void buildModule(ModuleBuilder builder) throws IOException, ScreenPlayException;

}
