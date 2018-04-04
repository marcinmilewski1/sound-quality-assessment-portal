import com.sqap.api.domain.PermissionType
import org.apache.commons.math3.distribution.BinomialDistribution
import spock.lang.Specification

class AdHocTester extends Specification {

    def "getClass in runtime"() {
        given:
        def a = new BigInteger("5000")

        when:
        def b = (Object)a

        then:
        b.getClass().equals(BigInteger.class)
    }

    def "should parse enum"() {
        when:
        def sut = PermissionType.valueOf("OWNER")

        then:
        notThrown(Exception)
    }

    def "shuld not parse enum"() {
        when:
        def sut = PermissionType.valueOf("owner")

        then:
        thrown(IllegalArgumentException)
    }

    def "Should calculate binomial probability from cdf"() {
        when:
        def binomialDistribution = new BinomialDistribution(30, 0.5)
        def pValue = binomialDistribution.cumulativeProbability(30) - binomialDistribution.cumulativeProbability(19)

        double expected = 0.49
        then:
        (pValue - expected) <= 0.01
    }
}