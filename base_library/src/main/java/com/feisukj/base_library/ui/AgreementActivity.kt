package com.feisukj.base_library.ui

import android.content.Intent
import android.widget.Toast
import com.feisukj.base_library.ActivityEntrance
import com.feisukj.base_library.R
import com.feisukj.base_library.baseclass.BaseActivity
import com.feisukj.base_library.utils.SPUtil
import kotlinx.android.synthetic.main.activity_agreement_base.*

class AgreementActivity :BaseActivity(){
    companion object{
        const val key="agreement"
    }
    private var isToAct=false

    override fun getLayoutId()=R.layout.activity_agreement_base

    override fun initView() {

    }

    override fun initListener() {
        imageSelect.setOnClickListener {
            it.isSelected=!it.isSelected
        }
        open.setOnClickListener {
            if (imageSelect.isSelected){
                SPUtil.instance.putBoolean(key,true)
                if (!isToAct) {
                    startActivity(Intent(this, ActivityEntrance.HomeActivity.cls))
                    finish()
                }
                isToAct=true
            }else{
                Toast.makeText(this,"请先勾选同意隐私政策和用户协议",Toast.LENGTH_SHORT).show()
            }
        }
        xieyi.setOnClickListener {
            val intent=Intent(this,AgreementContentActivity::class.java)
            intent.putExtra(AgreementContentActivity.FLAG,AgreementContentActivity.FLAG_YINSI)
            startActivity(intent)
        }
        fuwu.setOnClickListener {
            val intent=Intent(this,AgreementContentActivity::class.java)
            intent.putExtra(AgreementContentActivity.FLAG,AgreementContentActivity.FLAG_FUWU)
            startActivity(intent)
        }
    }
}