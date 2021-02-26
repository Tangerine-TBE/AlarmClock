package com.feisu.noise.ui.popup


import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import com.example.module_base.base.BasePopup
import com.example.module_base.util.SPUtil
import com.feisu.noise.R
import com.feisu.noise.utils.Constants
import kotlinx.android.synthetic.main.popup_exit_noise_window.view.*


/**
 * @name Wifi_Manager
 * @class name：com.example.wifi_manager.ui.popup
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2021/1/28 13:42:27
 * @class describe
 */
class ExitNoisePopup(activity: FragmentActivity):BasePopup(activity, R.layout.popup_exit_noise_window,ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT) {



    init {
        view.apply {
            cancel.setOnClickListener {
                this@ExitNoisePopup.dismiss()
                sp.apply {
                    putBoolean(Constants.SP_CLOSE_POPUP,check.isChecked)
                    putBoolean(Constants.SP_CLOSE_NOISE,false)
                }
                mListener?.cancel()
            }



            sure.setOnClickListener {
                this@ExitNoisePopup.dismiss()
                sp.apply {
                    putBoolean(Constants.SP_CLOSE_POPUP,check.isChecked)
                    putBoolean(Constants.SP_CLOSE_NOISE,true)
                }
                mListener?.sure()

            }
        }

    }
}