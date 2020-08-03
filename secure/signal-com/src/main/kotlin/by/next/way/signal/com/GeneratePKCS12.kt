package by.next.way.signal.com

import org.apache.commons.io.IOUtils
import org.apache.logging.log4j.LogManager
import org.bouncycastle.jce.provider.BouncyCastleProvider
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.security.*
import java.security.cert.CertificateException
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import java.security.spec.InvalidKeySpecException
import java.security.spec.KeySpec
import java.security.spec.PKCS8EncodedKeySpec

object GeneratePKCS12 {
    private val log = LogManager.getLogger()

    private const val KEY_PATH = "etoken/keys/00000001.key"
    private const val PRIVATE_CERT = "CA/prod.cer"
    private const val RESULT_PATH = "etoken/store.pfx"
    private const val CA_ROOT_NAME = "CA root eNotary"
    private const val CA_ROOT_CERT_PATH = "CA/root1.cer"
    private const val EQUIFAX_CERT_NAME = "CA Equifax"
    private const val EQUIFAX_CERT_PATH = "CA/root2.cer"
    private const val ENCRYPT_CERT_NAME = "encrypt cert"
    private const val ENCRYPT_CERT_PATH = "CA/root3.cer"
    private const val PASSWD = "changeit"

    @Throws(IOException::class, NoSuchAlgorithmException::class, CertificateException::class, NoSuchProviderException::class, KeyStoreException::class, InvalidKeySpecException::class)
    @JvmStatic
    fun main(args: Array<String>) {

        if (args.isEmpty()) {
            log.error("add cert alias by argument!")
            return
        }

        // Инициализация хранилища
        val store = KeyStore.getInstance("PKCS#12", "SC")
        store.load(null, null)

        // Чтение секретного ключа PKCS#8
        val keyFac = KeyFactory.getInstance("PKCS#8", "SC")
        val encoded: ByteArray = IOUtils.toByteArray(FileInputStream(KEY_PATH))
        val privkeySpec: KeySpec = PKCS8EncodedKeySpec(encoded)
        val priv = keyFac.generatePrivate(privkeySpec)

        // Чтение сертификата УЦ
        val cf = CertificateFactory.getInstance("X.509", "SC")
        var `in` = FileInputStream(CA_ROOT_CERT_PATH) // CA 7
        val cacert = cf.generateCertificate(`in`) as X509Certificate
        `in`.close()
        `in` = FileInputStream(EQUIFAX_CERT_PATH) // CA 7
        val cacert2 = cf.generateCertificate(`in`) as X509Certificate
        `in`.close()
        `in` = FileInputStream(ENCRYPT_CERT_PATH) // CA 7
        val encryptcert = cf.generateCertificate(`in`) as X509Certificate
        `in`.close()

        // Чтение собственного сертификата
        `in` = FileInputStream(PRIVATE_CERT) // CA 7
        val cert = cf.generateCertificate(`in`) as X509Certificate
        `in`.close()

        // Формирование цепочки сертификатов
        val chain = arrayOfNulls<X509Certificate>(4)
        chain[0] = cert
        chain[1] = cacert
        chain[2] = cacert2
        chain[3] = encryptcert

        // Помещение в хранилище секретного ключа с цепочкой сертификатов
        store.setKeyEntry(args[0], priv, PASSWD.toCharArray(), chain)
        // Помещение в хранилище сертификата УЦ
        store.setCertificateEntry(CA_ROOT_NAME, cacert)
        store.setCertificateEntry(EQUIFAX_CERT_NAME, cacert2)
        store.setCertificateEntry(ENCRYPT_CERT_NAME, encryptcert)

        // Запись хранилища
        val out = FileOutputStream(File(RESULT_PATH))
        store.store(out, PASSWD.toCharArray())
        out.close()
    }

    init {
        Security.addProvider(BouncyCastleProvider())
    }
}