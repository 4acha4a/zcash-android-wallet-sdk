package cash.z.ecc.android.sdk.model

import cash.z.ecc.android.sdk.internal.jni.RustBackend

/**
 * A canonical 32-byte little-endian encoding of a Sapling nullifier private key scalar.
 *
 * This key is part of the Sapling proof generation key. It does not grant spend-signing
 * authority, but it is secret wallet key material and must not be logged or persisted by the SDK.
 */
class SaplingNsk private constructor(
    private val bytes: ByteArray
) {
    internal fun copyBytes(): ByteArray = bytes.copyOf()

    override fun toString(): String = "SaplingNsk(bytes=***)"

    companion object {
        /** Validates [bytes] in Rust and returns a secret-safe semantic key value. */
        suspend fun new(bytes: ByteArray): SaplingNsk {
            val copy = bytes.copyOf()
            RustBackend.loadLibrary()
            when (RustBackend.validateSaplingNskBytes(copy)) {
                0 -> return SaplingNsk(copy)
                1 -> throw IllegalArgumentException("Sapling nsk must be exactly 32 bytes")
                else -> throw IllegalArgumentException("Sapling nsk is not a canonical scalar encoding")
            }
        }
    }
}
