package com.example.popularmovie

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login_firebase.*
import java.util.*

class LoginFirebase : AppCompatActivity() {

    lateinit var providers : List<AuthUI.IdpConfig>

    val MY_REQUEST_CODE: Int = 7117 // Qualquer valor desejado

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_firebase)

        //Inicializando os providers
        /*
        providers = Arrays.asList<AuthUI.IdpConfig>{
            AuthUI.IdpConfig.EmailBuilder().build(), // Login com email
            //AuthUI.IdpConfig.FacebookBuilder().build(), // login com Facebook
            AuthUI.IdpConfig.GoogleBuilder().build(),  // login do Google
            AuthUI.IdpConfig.PhoneBuilder().build() // Login com o celular
        }
        */
        providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.PhoneBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
        )

        showSignInOptions()

        //Event
        /*
        btn_sigh_out.setOnClickListener {

            AuthUI.getInstance().signOut(this@LoginFirebase)
                .addOnCompleteListener{
                    btn_sigh_out.isEnabled = false
                    showSignInOptions()
                }
                .addOnFailureListener {
                    e->Toast.makeText(this@LoginFirebase,e.message,Toast.LENGTH_SHORT).show()
                }
        }
         */
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if( requestCode == MY_REQUEST_CODE ){
            val response = IdpResponse.fromResultIntent(data)

            if(resultCode == Activity.RESULT_OK){
                val user = FirebaseAuth.getInstance().currentUser  // get current user
                Toast.makeText(this,""+user!!.email,Toast.LENGTH_SHORT).show()

                //btn_sigh_out.isEnabled = true
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            else
            {
                Toast.makeText(this,""+response!!.error!!.message,Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showSignInOptions() {
        startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setTheme(R.style.MyTheme)
                .build(), MY_REQUEST_CODE
        )
    }

}
