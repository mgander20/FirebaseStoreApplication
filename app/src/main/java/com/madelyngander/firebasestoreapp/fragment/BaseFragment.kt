package com.madelyngander.firebasestoreapp.fragment

import android.app.Dialog
import androidx.fragment.app.Fragment
import com.madelyngander.firebasestoreapp.R
import com.madelyngander.firebasestoreapp.utils.TextViewRegular

open class BaseFragment : Fragment(){

    private lateinit var mProgressDialog: Dialog

    fun showProgressDialog(text: String){
        mProgressDialog = Dialog(requireActivity())

        mProgressDialog.setContentView(R.layout.dialog_progress)
        mProgressDialog.findViewById<TextViewRegular>(R.id.tv_progress_text).text = text

        mProgressDialog.setCancelable(false)
        mProgressDialog.setCanceledOnTouchOutside(false)

        mProgressDialog.show()
    }

    fun hideProgressDialog(){
        mProgressDialog.dismiss()
    }

}