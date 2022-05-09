package io.uvera.eobrazovanje

import com.blazebit.persistence.Criteria
import com.blazebit.persistence.CriteriaBuilderFactory
import com.blazebit.persistence.integration.view.spring.EnableEntityViews
import com.blazebit.persistence.spi.CriteriaBuilderConfiguration
import com.blazebit.persistence.spring.data.impl.repository.BlazePersistenceRepositoryFactoryBean
import com.blazebit.persistence.view.EntityViewManager
import com.blazebit.persistence.view.impl.EntityViewConfigurationImpl
import com.blazebit.persistence.view.spi.EntityViewConfiguration
import io.uvera.eobrazovanje.configuration.properties.ConfigurationPropertiesMarker
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Scope
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.EnableScheduling
import javax.persistence.EntityManagerFactory
import javax.persistence.PersistenceUnit

@SpringBootApplication
@EnableAsync
@EnableScheduling
@EnableEntityViews(basePackages = ["io.uvera.eobrazovanje"])
@EnableJpaRepositories(repositoryFactoryBeanClass = BlazePersistenceRepositoryFactoryBean::class)
@ConfigurationPropertiesScan(basePackageClasses = [ConfigurationPropertiesMarker::class])
class KotlinSpringBootApp {

    @PersistenceUnit
    lateinit var entityManagerFactory: EntityManagerFactory

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    @org.springframework.context.annotation.Lazy(false)
    fun createCriteriaBuilderFactory(): CriteriaBuilderFactory {
        val config: CriteriaBuilderConfiguration = Criteria.getDefault()
        // do some configuration
        return config.createCriteriaBuilderFactory(entityManagerFactory)
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    fun entityViewConfiguration() = EntityViewConfigurationImpl()

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    @org.springframework.context.annotation.Lazy(false)
    fun createEntityViewManager(
        cbf: CriteriaBuilderFactory,
        entityViewConfiguration: EntityViewConfiguration,
    ): EntityViewManager? {
        return entityViewConfiguration.createEntityViewManager(cbf)
    }
}

fun main(args: Array<String>) {
    runApplication<KotlinSpringBootApp>(*args)
}
