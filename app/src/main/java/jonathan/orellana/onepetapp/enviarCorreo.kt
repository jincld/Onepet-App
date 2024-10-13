package jonathan.orellana.onepetapp



import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.security.Security
import java.util.Properties
import javax.mail.Message
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

       val mensaje = """

<html>
<head>
    <style>
        @import url('https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&display=swap');
        body { font-family: 'Poppins', sans-serif; background-color: #f0f2f5; margin: 0; padding: 0; }
        .container { max-width: 600px; margin: 40px auto; background-color: #ffffff; border-radius: 16px; box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1); overflow: hidden; }
        .header { background-color: #4a90e2; color: #ffffff; padding: 30px; text-align: center; }
        .content { padding: 40px; }
        h1 { margin: 0; font-size: 28px; font-weight: 600; }
        h2 { color: #333; font-size: 24px; margin-bottom: 20px; }
        p { color: #555; line-height: 1.6; margin-bottom: 20px; }
        .code-container { background-color: #f7f9fc; border: 2px dashed #4a90e2; border-radius: 8px; padding: 20px; text-align: center; margin-bottom: 30px; }
        .code { font-size: 32px; font-weight: 600; color: #4a90e2; letter-spacing: 5px; }
        .footer { background-color: #f7f9fc; padding: 20px; text-align: center; font-size: 14px; color: #888; }
    </style>
</head>
<body>
    <div class='container'>
        <div class='header'>
            <h1>OnePet</h1>
        </div>
        <div class='content'>
            <h2>Recuperación de Contraseña</h2>
            <p>Hola,</p>
            <p>Has solicitado la recuperación de tu contraseña. Utiliza el siguiente código de verificación:</p>
            <div class='code-container'>
                <div class='code'>$mensaje</div>
            </div>
            <p>Por favor, ingresa este código en la aplicación para continuar con el proceso de recuperación.</p>
            <p>Si no has solicitado este cambio, por favor ignora este correo o contacta con nuestro soporte.</p>
        </div>
        <div class='footer'>
            <p>Este es un correo automático, por favor no responda.</p>
            <p>&copy; 2024 OnePet. Todos los derechos reservados.</p>
        </div>
    </div>
</body>
</html>
""".trimIndent()
        message.setContent(mensaje, "text/html; charset=utf-8")



        Transport.send(message)
        println("Correo enviado satisfactoriamente")
    } catch (e: Exception) {
        e.printStackTrace()
        println("CORREO NO ENVIADO EXE $e")
        }
}