package org.ak80.taskbuddy

import com.tngtech.archunit.core.domain.JavaClasses
import com.tngtech.archunit.core.importer.ClassFileImporter
import org.junit.Test
import com.tngtech.archunit.library.Architectures.layeredArchitecture
import com.tngtech.archunit.lang.ArchRule



class ArchitectureTest {

    val importedClasses: JavaClasses = ClassFileImporter().importPackages("org.ak80.taskbuddy")

    @Test
    fun some_architecture_rule() {
        val layersRule: ArchRule = layeredArchitecture()
            .layer("BusinessModel").definedBy("org.ak80.taskbuddy.core.model..")
            .layer("Controllers").definedBy("org.ak80.taskbuddy.core.controllers..")
            .layer("Persistence").definedBy("org.ak80.taskbuddy.persistence..")
            .layer("UI").definedBy("org.ak80.taskbuddy.ui..")
            .layer("DI").definedBy("org.ak80.taskbuddy.di..")

            .whereLayer("UI").mayOnlyBeAccessedByLayers("DI")
            .whereLayer("Persistence").mayOnlyBeAccessedByLayers("DI")
            .whereLayer("Controllers").mayOnlyBeAccessedByLayers("UI")

        layersRule.check(importedClasses)
    }



}