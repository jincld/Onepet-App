package jonathan.orellana.onepetapp

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import modelo.ClaseConexion
import org.w3c.dom.Text
import java.io.ByteArrayOutputStream
import java.security.MessageDigest
import java.util.UUID

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ajustesdv.newInstance] factory method to
 * create an instance of this fragment.
 */
class ajustesdv : Fragment() {
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

    val codigo_opcion_galeria = 102
    val codigo_opcion_tomar_foto = 103
    val CAMERA_REQUEST_CODE = 0
    val STORAGE_REQUEST_CODE =1

    lateinit var imageView: ImageView
    lateinit var miPath: String

    val uuid = UUID.randomUUID().toString()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_ajustesdv, container, false)

var txtNombreAjustes: TextView =  root.findViewById(R.id.txtNombreAjustes)
        imageView = root.findViewById(R.id.imgftajustes)
        val  subirfotoAjustes = root.findViewById<Button>(R.id.btnSubirftAjustes)
        val tomarfotoAjustes = root.findViewById<Button>(R.id.btnTomarftAjustes)
        var txtContraAjustes: TextView =  root.findViewById(R.id.txtContraAjustes)
        var txtCorreoAjustes: TextView =  root.findViewById(R.id.txtCorreoAjustes)
        val btnCerrar = root.findViewById<Button>(R.id.btnCerrarSesion)
        val btnActualizarDatos = root.findViewById<Button>(R.id.btnActualizarUser)




        txtCorreoAjustes.text = iniciarsesion.variablesLogin.correo_admin
        txtContraAjustes.text = iniciarsesion.variablesLogin.contra_sinincriptar
 txtNombreAjustes.text =  MainActivity.variablesMainActivity.nombre_user


        fun hashSHA256(contraescrita: String): String {
            val bytes = MessageDigest.getInstance("SHA-256").digest(contraescrita.toByteArray())
            return bytes.joinToString("") { "%02x".format(it) }
        }

        fun updateUser(nombreNuevoUser: String, contraNuevaUser: String, CorreoNuevoUser: String) {
            GlobalScope.launch(Dispatchers.IO) {

                val contraIncriptada = hashSHA256(contraNuevaUser)

                ///1 - creo un objeto de la clase conexion
                val objConexion = ClaseConexion().cadenaConexion()

                //2 - Creo una variable que tenga un prepareStatement
                val updateUser =
                    objConexion?.prepareStatement(
                        "UPDATE tbUsuariosOne set nombre_usuario = ?, contra_usuario = ?, correo_usuario = ? where correo_usuario = ?"
                    )!!
                updateUser.setString(1, nombreNuevoUser)
                updateUser.setString(2, contraIncriptada)
                updateUser.setString(3, CorreoNuevoUser)
                updateUser.setString(4, iniciarsesion.variablesLogin.correo_admin)
                println(" --------- este es el nombre de vet que quiero usar ${iniciarsesion.variablesLogin.correo_admin}")

                updateUser.executeUpdate()
            }
        }
        fun isValid(vararg editTexts: EditText): Boolean {
            for (editText in editTexts) {
                if (editText.text.toString().isEmpty()) {
                    Toast.makeText(context, "Porfavor llene todos los datos", Toast.LENGTH_SHORT).show()
                    return false
                }
            }
            return true
        }


        btnActualizarDatos.setOnClickListener{
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Editar")
            builder.setMessage("Estas seguro que quieres editar?")

            val nombrenuevo = EditText(context)
            nombrenuevo.setText(txtNombreAjustes.text.toString())

            val correonuevo = EditText(context)
            correonuevo.setText(txtCorreoAjustes.text.toString())

            val contranueva = EditText(context)
            contranueva.setText(txtContraAjustes.text.toString())

            val layout = LinearLayout(context).apply {
                orientation = LinearLayout.VERTICAL
                addView(nombrenuevo)
                addView(correonuevo)
                addView(contranueva)
            }

            builder.setView(layout)

            builder.setPositiveButton("Si") { dialog, which ->
                if (isValid(nombrenuevo, contranueva, correonuevo )) {
                    val contraIncriptada = hashSHA256(contranueva.text.toString())
                    updateUser(
                        nombrenuevo.text.toString(),
                        contraIncriptada,
                        correonuevo.text.toString(),
                        )
                    println(" --------- este es el nombre de vet que quiero usar ${nombrenuevo.text.toString()}")
                    println("---------- este es el nombre de vet que quiero usar ${contraIncriptada}")
                    println("---------- este es el nombre de vet que quiero usar ${correonuevo.text}")

                    Toast.makeText(context, "Datos actualizados", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                    txtNombreAjustes.text = nombrenuevo.text.toString()
                    txtContraAjustes.text = contranueva.text.toString()
                    txtCorreoAjustes.text = correonuevo.text.toString()
                }
            }
            builder.setNegativeButton("no") { dialog, which ->
                dialog.dismiss()
            }
            builder.show()
        }

        subirfotoAjustes.setOnClickListener{
            checkStoragePermission()
        }

        tomarfotoAjustes.setOnClickListener{
            checkCameraPermission()

        }

        btnCerrar.setOnClickListener {
            val cerrar = Intent(context, login::class.java)
            startActivity(cerrar)
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
            Toast.makeText(requireContext(),"Error al subir la imagen", Toast.LENGTH_SHORT).show()
        } .addOnSuccessListener { taskSnapshot ->
            imageRef.downloadUrl.addOnSuccessListener {uri ->
                onSuccess(uri.toString())
            }

        }
    }
    private fun checkStoragePermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            pedirpermisocamara()
        }else {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, codigo_opcion_tomar_foto)
        }
    }

    private fun checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
            pedirpermisoalmacenamiento()
        else {
            val intent = Intent (Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, codigo_opcion_galeria)
        }
    }

    private fun pedirpermisocamara() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), android.Manifest.permission.CAMERA)
        ){

        } else {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.CAMERA), CAMERA_REQUEST_CODE
            )}
    }

    private fun pedirpermisoalmacenamiento(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
        } else {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),STORAGE_REQUEST_CODE)
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
                    Toast.makeText(requireContext(), "Permiso de cámara denegado", Toast.LENGTH_SHORT).show()
                }
                return
            }
            STORAGE_REQUEST_CODE -> {
                if ((grantResults.isNotEmpty()&& grantResults[0] == PackageManager.PERMISSION_GRANTED)){
                    val intent = Intent(Intent.ACTION_PICK)
                    intent.type = "image/*"
                    startActivityForResult(intent,codigo_opcion_galeria)
                } else {
                    Toast.makeText(requireContext(), "Permiso de almacenamiento denegado", Toast.LENGTH_SHORT).show()
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
                        val imageBitmap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, it)
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


companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ajustesdv.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ajustesdv().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}