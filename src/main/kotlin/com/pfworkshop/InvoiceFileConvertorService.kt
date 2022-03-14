package com.pfworkshop

import com.pfworkshop.resources.FileResource
import io.dropwizard.Application
import io.dropwizard.setup.Bootstrap
import io.dropwizard.setup.Environment
import org.glassfish.jersey.media.multipart.MultiPartFeature
import ru.vyarus.dropwizard.guice.GuiceBundle


class InvoiceFileConvertorService : Application<InvoiceFileConvertorServiceConfiguration>() {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            InvoiceFileConvertorService().run(*args)
        }
    }

    override fun initialize(bootstrap: Bootstrap<InvoiceFileConvertorServiceConfiguration>) {
        // Don't do anything
        bootstrap.addBundle(
            GuiceBundle.builder()
            .enableAutoConfig(javaClass.`package`.name)
            .modules(AppModule())
            .build())
    }

    override fun run(config: InvoiceFileConvertorServiceConfiguration, env: Environment) {

        env.jersey().register(MultiPartFeature::class.java)
        env.jersey().register(FileResource::class.java)

    }

}