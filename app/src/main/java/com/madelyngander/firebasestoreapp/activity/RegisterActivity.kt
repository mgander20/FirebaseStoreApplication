package com.madelyngander.firebasestoreapp.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.madelyngander.firebasestoreapp.R
import com.madelyngander.firebasestoreapp.databinding.ActivityRegisterBinding
import com.madelyngander.firebasestoreapp.firestore.FirestoreClass
import com.madelyngander.firebasestoreapp.models.User

class RegisterActivity : BaseActivity() {
    private var binding : ActivityRegisterBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
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
        binding?.tvLogin?.setOnClickListener{
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        setupActionBar()

        binding?.btnRegister?.setOnClickListener {
            registerUser()
        }
    }

    private fun setupActionBar(){
        setSupportActionBar(binding?.toolbarRegisterActivity)
        val actionBar = supportActionBar
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)
        }
        binding?.toolbarRegisterActivity?.setNavigationOnClickListener{onBackPressed()}
    }

    private fun registerUser(){
        if(validateRegisterDetails()){

            showProgressDialog(resources.getString(R.string.please_wait))

            val email: String = binding?.etEmail?.text.toString().trim{it<=' '}
            val password: String = binding?.etPassword?.text.toString().trim{it<=' '}

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val firebaseUser: FirebaseUser = task.result!!.user!!
                        val user = User(
                            firebaseUser.uid,
                            binding?.etFirstName!!.text.toString(),
                            binding?.etLastName!!.text.toString(),
                            binding?.etEmail!!.text.toString()
                        )
                        FirestoreClass().registerUser(this@RegisterActivity,user)
//                        FirebaseAuth.getInstance().signOut()
//                        finish()
                    } else {
                        hideProgressDialog()
                        showErrorSnackBar(task.exception!!.message.toString(),true)
                    }
                }
        }
    }

    private fun validateRegisterDetails() : Boolean {
        return when {
            TextUtils.isEmpty(binding?.etFirstName?.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_first_name), true)
                false
            }

            TextUtils.isEmpty(binding?.etLastName?.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_last_name), true)
                false
            }

            TextUtils.isEmpty(binding?.etEmail?.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_email), true)
                false
            }

            TextUtils.isEmpty(binding?.etPassword?.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_password), true)
                false
            }

            TextUtils.isEmpty(binding?.etConfirmPassword?.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(
                    resources.getString(R.string.err_msg_enter_confirm_password),
                    true
                )
                false
            }

            binding?.etPassword?.text.toString()
                .trim { it <= ' ' } != binding?.etConfirmPassword?.text.toString()
                .trim { it <= ' ' } -> {
                showErrorSnackBar(
                    resources.getString(R.string.err_msg_password_and_confirm_password_mismatch),
                    true
                )
                false
            }
            !binding?.cbTermsAndCondition?.isChecked!! -> {
                showErrorSnackBar(
                    resources.getString(R.string.err_msg_agree_terms_and_condition),
                    true
                )
                false
            }
            else -> {
                true
            }
        }
    }

    fun userRegistrationSuccess(){
        hideProgressDialog()
        Toast.makeText(this@RegisterActivity,R.string.register_success,Toast.LENGTH_SHORT).show()
    }
}