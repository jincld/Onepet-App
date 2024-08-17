package jonathan.orellana.onepetapp

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.InputType
import android.widget.Button
import android.widget.ImageButton
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import dataclassusuarios
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.ClaseConexion
import java.io.ByteArrayOutputStream
import java.security.MessageDigest

import java.util.UUID

class registroduenovet : AppCompatActivity() {

    companion object VariablesGlobalesRegistroDuenio{
        lateinit var txtcorreoadminvetGlobal: String
    }
    val codigo_opcion_galeria = 102
    val codigo_opcion_tomar_foto = 103
    val CAMERA_REQUEST_CODE = 0
    val STORAGE_REQUEST_CODE =1

    lateinit var imageView: ImageView
    lateinit var miPath: String

    val uuid = UUID.randomUUID().toString()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registroduenovet)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        supportActionBar?.hide();

        fun hashSHA256(contraescrita: String): String {
            val bytes = MessageDigest.getInstance("SHA-256").digest(contraescrita.toByteArray())
            return bytes.joinToString("") {"%02x".format(it)}

        }
        imageView = findViewById(R.id.imgftduevet)
        val txtnombreadminvet =findViewById<EditText>(R.id.txtnombreadminvet)
        val txtcorreoadminvet =findViewById<EditText>(R.id.txtcorreodminvet)
        val txtcontraadminvet =findViewById<EditText>(R.id.txtcontraadminvet)
        val  subirft = findViewById<Button>(R.id.btnsubirftadvet)
        val tomarft = findViewById<Button>(R.id.btntomarftadvet)
        val contraconfirm = findViewById<EditText>(R.id.txtcontraconfirm)
        val btninicarsesionvet = findViewById<TextView>(R.id.btniniciarsesionvet)
        val ojo = findViewById<ImageButton>(R.id.btnojovet1)
        val ojo2 = findViewById<ImageButton>(R.id.btnojovet2)
        val  btnVolver = findViewById<ImageButton>(R.id.btnVolverAV)

        btnVolver.setOnClickListener {
            val pantallaAnterior = Intent(this, registrarse::class.java)
            startActivity(pantallaAnterior)
        }

        ojo.setOnClickListener{
            if (txtcontraadminvet.inputType == InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD){
                txtcontraadminvet.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            } else {
                txtcontraadminvet.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD

            }

        }

       ojo2.setOnClickListener{
            if (contraconfirm.inputType == InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD){
                contraconfirm.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            } else {
                contraconfirm.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD

            }

        }

        fun obtenerUuidRol(): String? {
            val objConexion = ClaseConexion().cadenaConexion()
            val statement = objConexion?.createStatement()
            val resulSet = statement?.executeQuery("Select UUID_rol from tbRolesUsuarios where nombre_rol = 'Admin Vet'")!!
            val usuarios = mutableListOf<dataclassusuarios>()
            var uuidRol: String? = null

            if (resulSet?.next() == true) {
                uuidRol = resulSet.getString("UUID_rol")
                println("este es el uuid traido desde el if $uuidRol")
            }

            println("este es el uuid traido desde la funcion $uuidRol")
            return uuidRol
        }


        fun guardarUsuarioconft(imageUri: String){
            GlobalScope.launch(Dispatchers.IO){

                val objConexion = ClaseConexion().cadenaConexion()
                val contraencriptada = hashSHA256(txtcontraadminvet.text.toString())

                val uuidTraido = obtenerUuidRol()

                val crearusuario = objConexion?.prepareStatement("insert into tbUsuariosOne (UUID_usuario, nombre_usuario, contra_usuario, correo_usuario, foto_usuario, rol) values (?, ?, ?, ?, ?, ?)")!!
                crearusuario.setString(1, uuid)
                crearusuario.setString(2, txtnombreadminvet.text.toString())
                crearusuario.setString(3, contraencriptada)
                crearusuario.setString(4, txtcorreoadminvet.text.toString())
                crearusuario.setString(5, imageUri)
                crearusuario.setString(6, uuidTraido)
                println("este es el uuid traido antes del execute  $uuidTraido")
                crearusuario.executeUpdate()
                withContext(Dispatchers.Main){
                    //mostrar mensaje y limpiar campos
                    Toast.makeText( this@registroduenovet, "Usuario registrado", Toast.LENGTH_SHORT).show()
                    txtnombreadminvet.setText("")
                  txtcontraadminvet.setText("")
                   txtcorreoadminvet.setText("")
                    imageView.setImageResource(0)
                    imageView.tag = null
                    val login = Intent(this@registroduenovet, iniciarsesion::class.java)
                    startActivity(login)

                }
            }
        }


        btninicarsesionvet.setOnClickListener{

            val nombre = txtnombreadminvet.text.toString()
            val correo = txtcorreoadminvet.text.toString()
            val contra = txtcontraadminvet.text.toString()
            val contraconfrimada = contraconfirm.text.toString()
            var hayerrores = false


            if (nombre.isEmpty()) {
               txtnombreadminvet.error = "Complete todos lo campos"
                hayerrores = true
            } else {
                txtnombreadminvet.error = null
            }

            if (correo.isEmpty()) {
                txtcorreoadminvet.error = "Complete todos lo campos"
                hayerrores = true
            } else {
                txtcorreoadminvet.error = null
            }

            if (contraconfrimada.isEmpty()) {
                contraconfirm.error = "Complete todos lo campos"
                hayerrores = true
            } else {
                contraconfirm.error = null
            }

            if (contra.isEmpty()) {
                txtcontraadminvet.error = "Complete todos lo campos"
                hayerrores = true
            } else {
                txtcontraadminvet.error = null
            }
            if (contraconfrimada == txtcontraadminvet.text.toString()) {

                contraconfirm.error = null
            } else {

               contraconfirm.error = "Las contraseñas no coinciden"
                hayerrores = true
            }
            if (!correo.matches(Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"))){

                txtcorreoadminvet.error = "Ingrese un correo valido"
                hayerrores = true
            } else {
                txtcorreoadminvet.error = null
            }

            if (contra.length <= 7) {
                txtcontraadminvet.error = "La contraseña debe tener más de 8 caracteres"
                hayerrores = true
            } else {
               txtcontraadminvet.error = null
            }

            if (hayerrores){
            } else {

                guardarUsuarioconft(imageView.toString())
            }


            }
        subirft.setOnClickListener{
            checkStoragePermission()
        }

        tomarft.setOnClickListener{
            checkCameraPermission()
        }
    }
    private fun subirimagenFirebase (bitmap: Bitmap, onSuccess: (String) -> Unit) {
        val storageRef = Firebase.storage.reference
        val imageRef = storageRef.child("images/${uuid}.jpg")
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()
        val uploadTask = imageRef.putBytes(data)

        uploadTask.addOnFailureListener{
            Toast.makeText(this@registroduenovet,"Error la subir la imagen", Toast.LENGTH_SHORT).show()
        } .addOnSuccessListener { taskSnapshot ->
            imageRef.downloadUrl.addOnSuccessListener {uri ->
                onSuccess(uri.toString())
            }

        }
    }
    private fun checkStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            pedirpermisocamara()
        }else {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, codigo_opcion_tomar_foto)
        }
    }

    private fun checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
            pedirpermisoalmacenamiento()
        else {
            val intent = Intent (Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, codigo_opcion_galeria)
        }
    }

    private fun pedirpermisocamara() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.CAMERA)
        ){

        } else {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), CAMERA_REQUEST_CODE
            )}
    }

    private fun pedirpermisoalmacenamiento(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),STORAGE_REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            CAMERA_REQUEST_CODE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    startActivityForResult(intent, codigo_opcion_tomar_foto)
                } else {
                    Toast.makeText(this, "Permiso de cámara denegado", Toast.LENGTH_SHORT).show()
                }
                return
            }
            STORAGE_REQUEST_CODE -> {
                if ((grantResults.isNotEmpty()&& grantResults[0] == PackageManager.PERMISSION_GRANTED)){
                    val intent = Intent(Intent.ACTION_PICK)
                    intent.type = "image/*"
                    startActivityForResult(intent,codigo_opcion_galeria)
                } else {
                    Toast.makeText(this, "Permiso de almacenamiento denegado", Toast.LENGTH_SHORT).show()
                }

            }
            else -> {

            }

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK){
            when (requestCode){
                codigo_opcion_galeria-> {
                    val imageUri: Uri? = data?.data
                    imageUri?.let {
                        val imageBitmap = MediaStore.Images.Media.getBitmap(contentResolver, it)
                        subirimagenFirebase(imageBitmap){ url ->
                            miPath = url
                            imageView.setImageURI(it)
                        }
                    }
                }

                codigo_opcion_tomar_foto -> {
                    val imageBitmap = data?.extras?.get("data")as? Bitmap
                    imageBitmap?.let {
                        subirimagenFirebase(it) { url ->
                            miPath = url
                            imageView.setImageBitmap(it)

                        }
                    }
                }

            }

        }
    }
}


