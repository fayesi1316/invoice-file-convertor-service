package com.pfworkshop

import com.google.inject.Inject
import com.google.inject.Provides
import com.google.inject.name.Named
import ru.vyarus.dropwizard.guice.module.support.DropwizardAwareModule

class AppModule : DropwizardAwareModule<InvoiceFileConvertorServiceConfiguration>() {
    override fun configure() {
        bootstrap()
    }

    @Provides
    @Inject
    @Named("customMap")
    fun customMap(configuration: InvoiceFileConvertorServiceConfiguration) : Map<String, Any>{
        return configuration.customMap
    }
}