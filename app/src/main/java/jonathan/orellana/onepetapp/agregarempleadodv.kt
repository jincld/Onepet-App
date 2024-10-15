package jonathan.orellana.onepetapp

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.ClaseConexion
import java.io.ByteArrayOutputStream
import java.security.MessageDigest
import java.util.UUID

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [agregarempleadodv.newInstance] factory method to
 * create an instance of this fragment.
 */
class
agregarempleadodv : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    //creamos variables globales




    val uuid = UUID.randomUUID().toString()


    companion object VariablesGlobalesEmpleado{
        lateinit var NombreEmpVG: String
        lateinit var CorreoEmVG: String
        lateinit var ContraEmpVG: String
        lateinit var RolEmpVG: String
        lateinit var correo_emp: String
    }

    val codigo_opcion_galeria = 102
    val codigo_opcion_tomar_foto = 103
    val CAMERA_REQUEST_CODE = 0
    val STORAGE_REQUEST_CODE =1

    lateinit var imageView: ImageView
    lateinit var miPath: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val root = inflater.inflate(R.layout.fragment_agregarempleadodv, container, false)
        miPath = "https://i.pinimg.com/736x/1b/f1/e3/1bf1e3ee658f2b7b6d513056280c0305.jpg"

     // val uuid_admin = 'Codigo para que mande a llamar el uuid del admin de veterinaria'

        val txtNombre_empleado = root.findViewById<TextView>(R.id.txtNombre_empleado)
        val txtContra_empleado = root.findViewById<TextView>(R.id.txtContra_empleado)
        val txtCorreoEmpleado = root.findViewById<TextView>(R.id.txtCorreo_empleado)
        val btnAgregarEmpleado = root.findViewById<Button>(R.id.btnAgregarEmpleado)
        imageView = root.findViewById(R.id.ftempleado)
        val  tomarft =root.findViewById<Button>(R.id.subirftemp)
        val subirft = root.findViewById<Button>(R.id.tomarftemp)


//funcion de encriptacion
        fun hashSHA256(contraescrita: String): String {
            val bytes = MessageDigest.getInstance("SHA-256").digest(contraescrita.toByteArray())
            return bytes.joinToString("") {"%02x".format(it)}

        }

        fun obtenerUuidRol(): String? {
            val objConexion = ClaseConexion().cadenaConexion()
            val statement = objConexion?.createStatement()
            val resulSet = statement?.executeQuery("SELECT UUID_rol FROM tbRolesUsuarios WHERE nombre_rol = 'Empleado'")!!
            var uuidRol: String? = null

            if (resulSet.next()) {
                uuidRol = resulSet.getString("UUID_rol")
                println("este es el uuid traido desde el if $uuidRol")
            }

            println("este es el uuid traido desde la funcion $uuidRol")
            return uuidRol
        }

        btnAgregarEmpleado.setOnClickListener {
            val correo = txtCorreoEmpleado.text.toString()
            val contra = txtContra_empleado.text.toString()
            val nombre = txtNombre_empleado.text.toString()
            var hayerrores = false

            //validaciones
            if (!correo.matches(Regex("[a-zA-Z0-9._-]+@[a-z]+[.][a-z]+"))){
                txtCorreoEmpleado.error = "Ingrese un correo válido"
                hayerrores = true
            } else {
                txtCorreoEmpleado.error = null
            }

            if (contra.length <= 8) {
                txtContra_empleado.error = "La contraseña debe tener más de 8 carácteres"
                hayerrores = true
            } else {
                txtContra_empleado.error = null
            }

            if (nombre.isEmpty()) {
                txtNombre_empleado.error = "Complete este campo"
                hayerrores = true
            } else {
                txtNombre_empleado.error = null
            }

            if (iniciarsesion.variablesLogin.uuid_Vet_real.matches(Regex("1"))) {
                CoroutineScope(Dispatchers.IO).launch {
                    withContext(Dispatchers.Main){
                        //mostrar mensaje
                        Toast.makeText(context, "Debe de registrar a su veterinaria antes de agregar empleados", Toast.LENGTH_LONG).show()
                    }
                }
                hayerrores = true
            } else {


            }

            if (hayerrores){
            } else{


                //corrutina para insertar usuario
                fun guardarUsuarioconft(imageUri: String){
            CoroutineScope(Dispatchers.IO).launch {

                val objConexion = ClaseConexion().cadenaConexion()
                val contraencriptada = hashSHA256(txtContra_empleado.text.toString())

                val uuidTraido = obtenerUuidRol()

                val crearEmpleado = objConexion?.prepareStatement("insert into tbUsuariosOne (UUID_usuario, nombre_usuario, contra_usuario, correo_usuario, foto_usuario, rol, vet) values (?, ?, ?, ?, ?, ?)")!!
                crearEmpleado.setString(1, UUID.randomUUID().toString())
                crearEmpleado.setString(2, txtNombre_empleado.text.toString())
                crearEmpleado.setString(3, contraencriptada)
                crearEmpleado.setString(4, txtCorreoEmpleado.text.toString())
                crearEmpleado.setString(5, uuidTraido)
                crearEmpleado.setString(6, imageUri)
                crearEmpleado.setString(7, iniciarsesion.variablesLogin.uuid_Vet_real)
                println("este es la UUID de vet que quiero usar ${iniciarsesion.variablesLogin.uuid_Vet_real}")
                println("este es el uuid traido antes del execute  $uuidTraido")
                correo_emp = txtCorreoEmpleado.text.toString()
                println("este es el correo del empleado traido antes del execute  $correo_emp")
                crearEmpleado.executeUpdate()

                withContext(Dispatchers.Main){
                    //mostrar mensaje y limpiar campos
                    Toast.makeText(context, "Empleado registrado", Toast.LENGTH_SHORT).show()
                    txtNombre_empleado.setText("")
                    txtCorreoEmpleado.setText("")
                    txtContra_empleado.setText("")

                }

            }


                }
                guardarUsuarioconft(miPath)
        }


        }





        subirft.setOnClickListener{
            checkStoragePermission()
        }

        tomarft.setOnClickListener{
            checkCameraPermission()
        }




        return root

    }


    private fun subirimagenFirebase (bitmap: Bitmap, onSuccess: (String) -> Unit) {
        val storageRef = Firebase.storage.reference
        val imageRef = storageRef.child("images/${uuid}.jpg")
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()
        val uploadTask = imageRef.putBytes(data)

        uploadTask.addOnFailureListener{
            Toast.makeText(context,"Error la subir la imagen", Toast.LENGTH_SHORT).show()
        } .addOnSuccessListener { taskSnapshot ->
            imageRef.downloadUrl.addOnSuccessListener {uri ->
                onSuccess(uri.toString())
            }

        }
    }

    //funciones de permisos
    private fun checkStoragePermission() {
        if (ContextCompat.checkSelfPermission(context as Activity, android.Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            pedirpermisocamara()
        }else {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, codigo_opcion_tomar_foto)
        }
    }

    private fun checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(context as Activity, android.Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
            pedirpermisoalmacenamiento()
        else {
            val intent = Intent (Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, codigo_opcion_galeria)
        }
    }

    private fun pedirpermisocamara() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(context as Activity, android.Manifest.permission.CAMERA)
        ){

        } else {
            ActivityCompat.requestPermissions(context as Activity, arrayOf(android.Manifest.permission.CAMERA), CAMERA_REQUEST_CODE
            )}
    }

    private fun pedirpermisoalmacenamiento(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(context as Activity, android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
        } else {
            ActivityCompat.requestPermissions(context as Activity, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),STORAGE_REQUEST_CODE)
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
                    Toast.makeText(context, "Permiso de cámara denegado", Toast.LENGTH_SHORT).show()
                }
                return
            }
            STORAGE_REQUEST_CODE -> {
                if ((grantResults.isNotEmpty()&& grantResults[0] == PackageManager.PERMISSION_GRANTED)){
                    val intent = Intent(Intent.ACTION_PICK)
                    intent.type = "image/*"
                    startActivityForResult(intent,codigo_opcion_galeria)
                } else {
                    Toast.makeText(context, "Permiso de almacenamiento denegado", Toast.LENGTH_SHORT).show()
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
                    if (imageUri != null) {
                        val contentResolver = requireActivity().contentResolver // Inicializa contentResolver
                        if (contentResolver != null) {
                            val imageBitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
                            subirimagenFirebase(imageBitmap){ url ->
                                miPath = url
                                imageView.setImageURI(imageUri)
                            }
                        } else {
                            // Maneja error: contentResolver es nulo
                        }
                    } else {
                        // Establece imagen predeterminada
                        imageView.setImageResource(R.drawable.usericonosocuro)
                        miPath = "https://i.pinimg.com/736x/1b/f1/e3/1bf1e3ee658f2b7b6d513056280c0305.jpg"
                    }
                }



                codigo_opcion_tomar_foto -> {
                    val imageBitmap = data?.extras?.get("data")as? Bitmap
                    if (imageBitmap != null) {
                        subirimagenFirebase(imageBitmap) { url ->
                            miPath = url
                            imageView.setImageBitmap(imageBitmap)
                        }
                    } else {
                        // Set default image
                        imageView.setImageResource(R.drawable.usericonosocuro)
                        miPath = "https://i.pinimg.com/736x/1b/f1/e3/1bf1e3ee658f2b7b6d513056280c0305.jpg"
                    }
                }

            }

        }
    }

}