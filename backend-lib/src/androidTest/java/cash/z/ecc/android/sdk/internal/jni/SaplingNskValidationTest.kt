package cash.z.ecc.android.sdk.internal.jni

import kotlinx.coroutines.runBlocking
import org.junit.BeforeClass
import org.junit.Test
import kotlin.test.assertEquals

class SaplingNskValidationTest {
    companion object {
        @JvmStatic
        @BeforeClass
        fun loadNativeLibrary() {
            runBlocking {
                RustBackend.loadLibrary()
            }
        }
    }

    @Test
    fun canonicalScalarIsAccepted() {
        assertEquals(0, RustBackend.validateSaplingNskBytes(ByteArray(32)))
    }

    @Test
    fun invalidLengthsAreRejected() {
        assertEquals(1, RustBackend.validateSaplingNskBytes(ByteArray(31)))
        assertEquals(1, RustBackend.validateSaplingNskBytes(ByteArray(33)))
    }

    @Test
    fun noncanonicalScalarIsRejected() {
        assertEquals(2, RustBackend.validateSaplingNskBytes(ByteArray(32) { 0xff.toByte() }))
    }
}
