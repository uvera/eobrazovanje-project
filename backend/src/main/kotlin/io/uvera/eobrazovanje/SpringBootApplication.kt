package io.uvera.eobrazovanje

import io.uvera.eobrazovanje.configuration.properties.ConfigurationPropertiesMarker
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableAsync
@EnableScheduling
@ConfigurationPropertiesScan(basePackageClasses = [ConfigurationPropertiesMarker::class])
class KotlinSpringBootApp

fun main(args: Array<String>) {
    runApplication<KotlinSpringBootApp>(*args)
}
