package com.peknight.security.signature

import com.peknight.security.digest.{MessageDigestAlgorithm, `SHA-1`}
import com.peknight.security.error.{InvalidECDSASignatureFormat, SecurityError}
import com.peknight.security.format.Format
import scodec.bits.ByteVector

/**
 * Elliptic Curve Digital Signature Algorithm
 */
trait ECDSA extends SignatureAlgorithm with DSA with ECDSAPlatform:
  def digest: MessageDigestAlgorithm
  def format: Option[Format] = None
  def signature: DigestWithEncryption = digest.withEncryption(ECDSA, format = format)
  override def algorithm: String = "ECDSA"
object ECDSA extends ECDSA:
  def digest: MessageDigestAlgorithm = `SHA-1`
  private case class ECDSA(digest: MessageDigestAlgorithm, override val format: Option[Format])
    extends com.peknight.security.signature.ECDSA
  def apply(digest: MessageDigestAlgorithm, format: Option[Format] = None): com.peknight.security.signature.ECDSA =
    ECDSA(digest, format)

  def convertDERToConcatenated(derEncodedBytes: ByteVector, outputLength: Int): Either[SecurityError, ByteVector] =
    for
      // 1. 验证输入有效性
      // 首先检查输入的DER编码字节长度是否至少为8，以及字节数组的第一个字节（derEncodedBytes[0]）是否为48（表示SEQUENCE标签），以确保输入是一个有效的DER编码的ECDSA签名。
      _ <- if derEncodedBytes.length < 8 || derEncodedBytes.head != 48 then Left(InvalidECDSASignatureFormat) else Right(())
      // 2. 计算偏移量（offset）
      // 根据DER编码规则，长度字段可能占用1或2个字节。如果长度字节大于0，则偏移量为2；如果长度字节为0x81，则偏移量为3，因为这表示接下来有一个字节来指示实际长度。
      second = derEncodedBytes(1)
      offset <- if second > 0 then Right(2) else if second == 0x81.byteValue then Right(3) else Left(InvalidECDSASignatureFormat)
      // 3. 解析R值的长度和前导零
      // 计算R值的长度（rLength），并去除R值前面的无意义前导零，找到实际数据的起始位置。这是通过从R值结束位置反向遍历并找到第一个非零字节来完成的。
      rLength = derEncodedBytes(offset + 1)
      rBytes = derEncodedBytes.drop(offset + 2).take(rLength).dropWhile(_ == 0)
      // 4. 解析S值的长度和前导零
      // 同样地，计算S值的长度（sLength）并去除其前导零。
      sLength = derEncodedBytes(offset + rLength + 3)
      sBytes = derEncodedBytes.drop(offset + rLength + 4).take(sLength).dropWhile(_ == 0)
      // 5. 确定输出字节的长度（rawLen）
      // 确定最终输出的每个整数（R和S）的最小需要长度，取R和S的实际无前导零长度中的较大者，并确保它至少是outputLength/2，以满足输出长度需求。
      rawLen = rBytes.length.max(sBytes.length).max(outputLength / 2)
      // 6. 验证DER编码结构的正确性
      // 检查DER编码的结构，包括前一个字节表示的后续数据长度是否与计算出的总长度匹配，以及R和S部分的编码是否符合预期格式。
      flag = (derEncodedBytes(offset - 1) & 0xff) != derEncodedBytes.length - offset ||
        (derEncodedBytes(offset - 1) & 0xff) != rLength + sLength + 4 ||
        derEncodedBytes(offset) != 2 ||
        derEncodedBytes(offset + rLength + 2) != 2
      _ <- if flag then Left(InvalidECDSASignatureFormat) else Right(())
    yield
      // 7. 创建并填充输出数组
      ByteVector.fill(rawLen - rBytes.length)(0) ++ rBytes ++ ByteVector.fill(rawLen - sBytes.length)(0) ++ sBytes

  // Convert the concatenation of R and S into DER encoding
  def convertConcatenatedToDER(concatenatedSignatureBytes: ByteVector): Either[SecurityError, ByteVector] =
    def getBytes(rawBytes: ByteVector): ByteVector = rawBytes.init.dropWhile(_ == 0) :+ rawBytes.last
    def getLength(bytes: ByteVector): Long = if bytes.head < 0 then bytes.length + 1 else bytes.length
    val rBytes = getBytes(leftHalf(concatenatedSignatureBytes))
    val sBytes = getBytes(rightHalf(concatenatedSignatureBytes))
    val rLength = getLength(rBytes)
    val sLength = getLength(sBytes)
    val len = rLength + sLength + 4
    if len > 255 then Left(InvalidECDSASignatureFormat)
    else
      Right(48.byteValue +: ((if len < 128 then ByteVector.empty else ByteVector(0x81.byteValue)) ++ (len.byteValue +:
        2.byteValue +: rLength.byteValue +: (ByteVector.fill(rLength - rBytes.length)(0) ++ rBytes ++
        (2.byteValue +: sLength.byteValue +: (ByteVector.fill(sLength - sBytes.length)(0) ++ sBytes)))
        )))

  private[security] def leftHalf(bytes: ByteVector): ByteVector = bytes.take(bytes.length / 2)
  private[security] def rightHalf(bytes: ByteVector): ByteVector =
    val half = bytes.length / 2
    bytes.drop(half).take(half)

end ECDSA
