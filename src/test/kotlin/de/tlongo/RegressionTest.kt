import io.quarkus.test.common.QuarkusTestResourceLifecycleManager
import io.quarkus.test.common.WithTestResource
import io.quarkus.test.junit.QuarkusTest
import io.quarkus.test.junit.QuarkusTestProfile
import io.quarkus.test.junit.TestProfile
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import org.eclipse.microprofile.config.inject.ConfigProperty
import org.junit.jupiter.api.Test

@ApplicationScoped
class RegressionBean(
    @ConfigProperty(name = "test-property") val testProperty: String,
) {
}

@QuarkusTest
@WithTestResource(RegresseionLifeCycleManager::class, restrictToAnnotatedClass = false)
@TestProfile(RegressionTestProfile::class)
class RegressionExampleTest {

    @Inject
    lateinit var bean: RegressionBean

    @Test
    fun test() {
        println("value: ${bean.testProperty}")
        assert(bean.testProperty == "value-from-test-profile")
    }
}

class RegressionTestProfile : QuarkusTestProfile {
    override fun getConfigOverrides(): MutableMap<String, String> {
        return mutableMapOf("test-property" to "value-from-test-profile")
    }
}

class RegresseionLifeCycleManager : QuarkusTestResourceLifecycleManager {
    override fun start(): MutableMap<String, String> {
        return mutableMapOf("test-property" to "value-from-lifecycle-manager")
    }

    override fun stop() {
    }
}
