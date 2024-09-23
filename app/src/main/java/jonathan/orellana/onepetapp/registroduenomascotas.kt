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
import android.widget.EditText
import android.widget.ImageButton
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.ClaseConexion
import java.io.ByteArrayOutputStream
import java.security.MessageDigest
import java.util.UUID

class  registroduenomascotas : AppCompatActivity() {

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
        setContentView(R.layout.activity_registroduenomascotas)
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
        imageView = findViewById(R.id.imgftperfilduenomas)
        val  txtnombreduenomas = findViewById<EditText>(R.id.txtnombreduenomas)
        val  txtcorreoduenomas = findViewById<EditText>(R.id.txtcorreoduenomas)
        val  txtcontraduenomas = findViewById<EditText>(R.id.txtcontraduenomas)
        val  subirft = findViewById<Button>(R.id.btnsubirftduenomas)
        val tomarft = findViewById<Button>(R.id.btntomarftduenomas)
        val contraconfirm = findViewById<EditText>(R.id.contraconfirm)
        val  btnsiguiente = findViewById<TextView>(R.id.btnSiguienteDuenoMascota)
        val ojomascotas = findViewById<ImageButton>(R.id.btnojomascotas1)
        val ojomascota2 = findViewById<ImageButton>(R.id.btnmascotas2)
        val  btnVolver = findViewById<ImageButton>(R.id.btnVolverDM)

        btnVolver.setOnClickListener {
            val pantallaAnterior = Intent(this, registrarse::class.java)
            startActivity(pantallaAnterior)
        }

       ojomascotas.setOnClickListener{
            if (txtcontraduenomas.inputType == InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD){
               txtcontraduenomas.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            } else {
                txtcontraduenomas.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD

            }

           }

           ojomascota2.setOnClickListener{
               if (contraconfirm.inputType == InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD){
                   contraconfirm.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
               } else {
                   contraconfirm.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD

               }

           }

        //obtenemos el uuid

        fun obtenerUuidRol(): String? {
            val objConexion = ClaseConexion().cadenaConexion()
            val statement = objConexion?.createStatement()
            val resulSet = statement?.executeQuery("SELECT UUID_rol FROM tbRolesUsuarios WHERE nombre_rol = 'Dueno Mascota'")!!
            var uuidRol: String? = null

            if (resulSet.next()) {
                uuidRol = resulSet.getString("UUID_rol")
                println("este es el uuid traido desde el if $uuidRol")
            }

            println("este es el uuid traido desde la funcion $uuidRol")
            return uuidRol
        }


        //fumciom para guardar usuarios
        fun guardarUsuarioconft(imageUri: String){
            GlobalScope.launch(Dispatchers.IO){

                val objConexion = ClaseConexion().cadenaConexion()
                val contraencriptada = hashSHA256(txtcontraduenomas.text.toString())

                val uuidTraido = obtenerUuidRol()

                val crearusuario = objConexion?.prepareStatement("insert into tbUsuariosOne (UUID_usuario, nombre_usuario, contra_usuario, correo_usuario, foto_usuario, rol) values (?, ?, ?, ?, ?, ?)")!!
                crearusuario.setString(1, uuid)
                crearusuario.setString(2, txtnombreduenomas.text.toString())
                crearusuario.setString(3, contraencriptada)
                crearusuario.setString(4, txtcorreoduenomas.text.toString())
                crearusuario.setString(5, imageUri)
                crearusuario.setString(6, uuidTraido)
                println("este es el uuid traido antes del execute  $uuidTraido")
                crearusuario.executeUpdate()
                withContext(Dispatchers.Main){
                    //mostrar mensaje y limpiar campos
                    Toast.makeText(this@registroduenomascotas, "Usuario registrado", Toast.LENGTH_SHORT).show()
                    txtnombreduenomas.setText("")
                    txtcontraduenomas.setText("")
                    contraconfirm.setText("")
                    txtcorreoduenomas.setText("")
                    imageView.setImageResource(0)
                    imageView.tag = null
                    val login = Intent(this@registroduenomascotas, iniciarsesion::class.java)
                    startActivity(login)

                }
            }
        }

        btnsiguiente.setOnClickListener{
            val nombre = txtnombreduenomas.text.toString()
            val correo = txtcorreoduenomas.text.toString()
            val contra = txtcontraduenomas.text.toString()
            val confirmcontra = contraconfirm.text.toString()
            var hayerrores = false

            //validaciones
            if (nombre.isEmpty()) {
                txtnombreduenomas.error = "Complete todos lo campos"
                hayerrores = true
            } else {
                txtnombreduenomas.error = null
            }

            if (correo.isEmpty()) {
                txtcorreoduenomas.error = "Complete todos lo campos"
                hayerrores = true
            } else {
                txtcorreoduenomas.error = null
            }

            if (contra.isEmpty()) {
                txtcontraduenomas.error = "Complete todos lo campos"
                hayerrores = true
            } else {
                txtcontraduenomas.error = null
            }

            if (!correo.matches(Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}\$"))){
                txtcorreoduenomas.error = "Ingrese un correo valido"
                hayerrores = true
            } else {
                txtcorreoduenomas.error = null
            }

            if ( confirmcontra == txtcontraduenomas.text.toString()){
                contraconfirm.error = null
            } else {

            contraconfirm.error = "Las contraseñas no coinciden"
            hayerrores = true
        }

            if (contra.length <= 8) {
                txtcontraduenomas.error = "La contraseña debe tener más de 8 caracteres"
                hayerrores = true
            } else {
                txtcontraduenomas.error = null
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
//subimos la imagen con firebase
    private fun subirimagenFirebase (bitmap: Bitmap, onSuccess: (String) -> Unit) {
        val storageRef = Firebase.storage.reference
        val imageRef = storageRef.child("images/${uuid}.jpg")
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()
        val uploadTask = imageRef.putBytes(data)

        uploadTask.addOnFailureListener{
            Toast.makeText(this@registroduenomascotas,"Error la subir la imagen", Toast.LENGTH_SHORT).show()
        } .addOnSuccessListener { taskSnapshot ->
            imageRef.downloadUrl.addOnSuccessListener {uri ->
                onSuccess(uri.toString())
            }

        }
    }

    //funciones de permisos
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

            //validaciones
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
