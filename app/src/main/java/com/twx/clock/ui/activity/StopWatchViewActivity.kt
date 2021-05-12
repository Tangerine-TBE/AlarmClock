package com.twx.clock.ui.activity


import androidx.recyclerview.widget.LinearLayoutManager
import com.twx.clock.R
import com.twx.clock.databinding.ActivityStopWatchBinding
import com.twx.clock.ui.adapter.recyclerview.SignItemAdapter
import com.twx.clock.util.BtState
import com.twx.clock.viewmodel.StopWatchViewModel
import com.twx.module_base.base.BaseVmViewViewActivity
import com.twx.module_base.util.MarginStatusBarUtil
import kotlinx.android.synthetic.main.activity_stop_watch.*
import kotlinx.android.synthetic.main.layout_watch_begin_time.*
import kotlinx.android.synthetic.main.layout_watch_current_time.*
import java.util.*

class StopWatchViewActivity : BaseVmViewViewActivity<ActivityStopWatchBinding,StopWatchViewModel>(){
    private val mAdapter by lazy {
        SignItemAdapter()
    }

    private var mCurrentState=BtState.NONE
    override fun getViewModelClass(): Class<StopWatchViewModel> {
        return StopWatchViewModel::class.java
    }
    override fun getLayoutView(): Int =R.layout.activity_stop_watch
    override fun initView() {
        //设置顶部距离
        MarginStatusBarUtil.setStatusBar(this, binding.relativeLayout, 2)
        itemContainer.layoutManager=LinearLayoutManager(this)
        itemContainer.adapter=mAdapter

    }

    override fun observerData() {
        viewModel.apply {
            btState.observe(this@StopWatchViewActivity,{
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
                    BtState.PAUSE->{
                        continue_pause.setImageResource(R.mipmap.icon_watch_begin)
                        sign_stop.setImageResource(R.mipmap.icon_watch_stop)
                    }

                }
            })

            number.observe(this@StopWatchViewActivity, {
                val min = String.format("%02d", it.min)
                val sec = String.format("%02d", it.second)
                val mil = String.format("%02d", it.mil)
                minTime.text="$min:$sec.$mil"
            })

            itemSignContext.observe(this@StopWatchViewActivity,{
                mAdapter.setList(it)
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
                    viewModel.setBtState(BtState.PAUSE)
                }

                BtState.PAUSE->{
                    viewModel.startTimer()
                    viewModel.setBtState(BtState.BEGIN)
                }

            }

        }

        sign_stop.setOnClickListener {
            when(mCurrentState){
                BtState.BEGIN->{
                    viewModel.getItemSign()
                }

                BtState.PAUSE->{
                    viewModel.setBtState(BtState.STOP)
                    viewModel.cleanItemList()
                }
            }

        }

    }

}



