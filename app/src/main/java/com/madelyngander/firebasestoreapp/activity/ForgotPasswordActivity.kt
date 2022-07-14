package com.madelyngander.firebasestoreapp.activity


import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import com.google.firebase.auth.FirebaseAuth
import com.madelyngander.firebasestoreapp.databinding.ActivityForgotPasswordBinding

class ForgotPasswordActivity : BaseActivity() {
    private var binding : ActivityForgotPasswordBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        @Suppress("DEPRECATION")
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        }else{
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        binding?.btnSubmit?.setOnClickListener{
            val email: String = binding?.etEmailForgotPw.toString().trim{ it<= ' ' }
            if(email.isEmpty()){
                showErrorSnackBar("Please enter email.",true)
            }else{
                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                    .addOnCompleteListener{task ->
                        if(task.isSuccessful){
                            showErrorSnackBar("Email sent successfully.",false)
                        }else{
                            showErrorSnackBar(task.exception!!.message.toString(),true)
                        }
                    }
            }
        }
    }
}