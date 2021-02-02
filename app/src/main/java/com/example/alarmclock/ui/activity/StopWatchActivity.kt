package com.example.alarmclock.ui.activity


import android.os.Handler
import android.os.Looper
import android.os.Message
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.alarmclock.R
import com.example.alarmclock.util.BtState
import com.example.alarmclock.viewmodel.StopWatchViewModel
import com.example.module_base.util.MarginStatusBarUtil
import com.example.td_horoscope.base.MainBaseActivity
import kotlinx.android.synthetic.main.activity_stop_watch.*
import kotlinx.android.synthetic.main.layout_watch_begin_time.*
import kotlinx.android.synthetic.main.layout_watch_current_time.*
import java.util.*

class StopWatchActivity : MainBaseActivity(){



    private val viewModel  by lazy {
        ViewModelProvider(this).get(StopWatchViewModel::class.java)
    }
    private var mCurrentState=BtState.NONE

    override fun getLayoutView(): Int =R.layout.activity_stop_watch
    override fun initView() {
        //设置顶部距离
        MarginStatusBarUtil.setStatusBar(this, relativeLayout, 2)
    }

    override fun initLoadData() {
        viewModel.apply {
            btState.observe(this@StopWatchActivity,{
                mCurrentState=it
                when(it){
                    BtState.BEGIN->{
                        gone(beginInclude)
                        visible(currentInclude)
                        continue_pause.setImageResource(R.mipmap.icon_watch_pasue)
                        sign_stop.setImageResource(R.mipmap.icon_watch_sgin)
                    }

                    BtState.STOP->{
                        visible(beginInclude)
                        gone(currentInclude)
                        time.text="00:00.00"
                    }
                    BtState.PASUE->{
                        continue_pause.setImageResource(R.mipmap.icon_watch_begin)
                        sign_stop.setImageResource(R.mipmap.icon_watch_stop)
                    }

                }
            })

            number.observe(this@StopWatchActivity, {
                val min = String.format("%02d", it.min)
                val sec = String.format("%02d", it.second)
                val mil = String.format("%02d", it.mil)
                minTime.text="$min:$sec.$mil"
            })


        }

    }


    override fun initEvent() {
        back.setOnClickListener {
            finish()
        }


        begin.setOnClickListener {
            viewModel.startTimer()
            viewModel.setBtState(BtState.BEGIN)
        }


        continue_pause.setOnClickListener {
            when(mCurrentState){
                BtState.BEGIN->{
                    viewModel.stopTimer()
                    viewModel.setBtState(BtState.PASUE)
                }

                BtState.PASUE->{
                    viewModel.startTimer()
                    viewModel.setBtState(BtState.BEGIN)
                }

            }

        }

        sign_stop.setOnClickListener {
            when(mCurrentState){
                BtState.BEGIN->{

                }

                BtState.PASUE->{
                    viewModel.setBtState(BtState.STOP)
                }
            }



        }



    }



}



