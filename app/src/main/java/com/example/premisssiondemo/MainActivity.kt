package com.example.premisssiondemo
import android.Manifest
import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {

    //*****This is used & work only for single permission
    /*var cameraResultLauncher : ActivityResultLauncher<String> =
        registerForActivityResult(
        ActivityResultContracts.RequestPermission()){
            isGranted->
            run {
                if (isGranted) {
                    Toast.makeText(this, "granted", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "not grnated", Toast.LENGTH_LONG).show()
                }
            }
    }*/

    //*****This is used & work  for multiple permissions
    private var cameraAndLocationResultLauncher : ActivityResultLauncher<Array<String>> =
        registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()){
            permissions->
            permissions.entries.forEach {
                val permissionName = it.key
                val isGranted = it.value
                if (isGranted){
                    if (permissionName == Manifest.permission.ACCESS_FINE_LOCATION){
                        Toast.makeText(this,"Permission  Granted for Fine Location",Toast.LENGTH_LONG).show()
                    }else if (permissionName == Manifest.permission.ACCESS_COARSE_LOCATION){
                        Toast.makeText(this,"Permission Granted for Coarse Location",Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(this,"Permission Granted for Camera",Toast.LENGTH_LONG).show()
                    }
                }else{
                    if (permissionName == Manifest.permission.ACCESS_FINE_LOCATION){
                        Toast.makeText(this,"Permission  Denied for Fine Location",Toast.LENGTH_LONG).show()
                    }else if (permissionName == Manifest.permission.ACCESS_COARSE_LOCATION){
                        Toast.makeText(this,"Permission Denied for Course Location",Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(this,"Permission Denied for Camera",Toast.LENGTH_LONG).show()
                    }
                }
            }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnCameraPermission : Button = findViewById(R.id.btnCameraPermission)
        btnCameraPermission.setOnClickListener {
            if (this.shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)){
                " Permission Demo requires camera access".showRationaleDialog("Camera cannot be used because Camera access is denied")

            }else
                //** This is for Single Permission
                //cameraResultLauncher.launch(Manifest.permission.CAMERA)
            cameraAndLocationResultLauncher.launch(arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ))

        }
    }

private fun String.showRationaleDialog(message: String) {
    val builder: AlertDialog.Builder = AlertDialog.Builder(this@MainActivity)
    builder.setTitle(this)
        .setMessage(message)
        .setPositiveButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }
    builder.create().show()
}

}