package com.maciekbro.navigateapp.dagger;

import javax.inject.Singleton;

import dagger.Component;

/**
 *
 */

@Singleton
@Component(modules = {MainAppModule.class,InterfaceModule.class})
public interface SearchComponent {
}
