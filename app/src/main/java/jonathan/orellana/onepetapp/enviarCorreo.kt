package jonathan.orellana.onepetapp



import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.security.Security
import java.util.Properties
import javax.mail.Message
import javax.mail.MessagingException
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage


suspend fun enviarCorreo (receptor: String, sujeto: String, mensaje: String) = withContext(Dispatchers.IO) {

    val props = Properties().apply {
        put("mail.smtp.host", "smtp.gmail.com")
        put("mail.smtp.socketFactory.port", "465")
        put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory")
        put("mail.smtp.auth", "true")
        put("mail.smtp.port", "465")
        Security.setProperty("jdk.tls.disabledAlgorithms", "")
        Security.setProperty("jdk.tls.client.protocols", "TLSv1.2")
        put("mail.smtp.ssl.protocols","TLSv1.2" )
    }

    // Iniciamos Sesión
    val session = Session.getInstance(props, object : javax.mail.Authenticator() {
        override fun getPasswordAuthentication(): PasswordAuthentication {
            return PasswordAuthentication("onepettapp@gmail.com", "luab sshe vihu fdxr")
            props["mail.smtp.socketFactory.class"] = "javax.net.ssl.SSLSocketFactory"
            props["mail.smtp.socketFactory.fallback"] = "false"

        }
    })

    // Hacemos el envío
    try {
        val message = MimeMessage(session).apply {
            //Con que correo enviaré el mensaje
            setFrom(InternetAddress("onepettapp@gmail.com"))
            addRecipient(Message.RecipientType.TO, InternetAddress(receptor))
            subject = sujeto
            setText(mensaje)
        }
        Transport.send(message)
        println("Correo enviado satisfactoriamente")
    } catch (e: Exception) {
        e.printStackTrace()
        println("CORREO NO ENVIADO EXE $e")
    }
}