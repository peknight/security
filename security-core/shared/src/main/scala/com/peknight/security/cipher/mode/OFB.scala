package com.peknight.security.cipher.mode

/**
 * Output Feedback Mode
 *
 * Using modes such as CFB and OFB, block ciphers can encrypt data in units smaller than the cipher's actual block size.
 * When requesting such a mode, you may optionally specify the number of bits to be processed at a time by appending
 * this number to the mode name as shown in the "DES/CFB8/NoPadding" and "DES/OFB32/PKCS5Padding" transformations.
 * If no such number is specified, a provider-specific default is used.
 * (For example, the SunJCE provider uses a default of 64 bits for DES.)
 * Thus, block ciphers can be turned into byte-oriented stream ciphers by using an 8-bit mode such as CFB8 or OFB8.
 */
trait OFB extends CipherAlgorithmMode:
  def mode: String = "OFB"
end OFB
object OFB extends OFB
