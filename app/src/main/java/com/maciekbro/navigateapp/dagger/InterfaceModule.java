package com.maciekbro.navigateapp.dagger;

import com.maciekbro.navigateapp.utils.shared_preferences.SharedPreferencesFacade;
import com.maciekbro.navigateapp.utils.shared_preferences.SharedPreferencesFacadeImpl;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

/**
 *
 */
@Module
public abstract class InterfaceModule {

    @Binds
    @Singleton
    public abstract SharedPreferencesFacade provideSharedPreferencesFacade(SharedPreferencesFacadeImpl sharedPreferencesFacade);
}
