package purchase.api

import grails.boot.GrailsApp
import grails.boot.config.GrailsAutoConfiguration
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean
import org.springframework.context.EnvironmentAware
import org.springframework.core.env.Environment
import org.springframework.core.env.PropertiesPropertySource
import org.springframework.core.io.FileSystemResource
import org.springframework.core.io.Resource

class Application extends GrailsAutoConfiguration implements EnvironmentAware {
    static void main(String[] args) {
        GrailsApp.run(Application, args)
    }

    @Override
    void setEnvironment(Environment environment) {
        Resource resourceConfig = new FileSystemResource('app-config.yml')

        YamlPropertiesFactoryBean entries = new YamlPropertiesFactoryBean()

        entries.setResources([resourceConfig] as Resource[])
        entries.afterPropertiesSet()

        Properties properties = entries.getObject()

        environment.propertySources.addFirst(new PropertiesPropertySource("local.config.location", properties))
    }
}