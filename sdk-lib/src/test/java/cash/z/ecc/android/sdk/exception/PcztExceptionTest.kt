package cash.z.ecc.android.sdk.exception

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class PcztExceptionTest {
    @Test
    fun addSaplingKeysFailureDoesNotExposeBackendMessage() {
        val sensitiveBackendMessage = "backend accidentally included secret key material"
        val exception =
            PcztException.AddSaplingProofGenerationKeysException(
                PcztException.AddSaplingProofGenerationKeysFailure.NO_MATCHING_KEY,
                IllegalStateException(sensitiveBackendMessage)
            )

        assertEquals(
            "Failed to add Sapling proof generation keys: NO_MATCHING_KEY",
            exception.message
        )
        assertFalse(exception.message.orEmpty().contains(sensitiveBackendMessage))
    }
}
