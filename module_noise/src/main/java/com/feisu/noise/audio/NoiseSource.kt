package com.feisu.noise.audio

import com.feisu.noise.R
import com.feisu.noise.bean.MusicFileBean

val recommendSource= arrayOf(
    MusicFileBean().also {
        it.name="暴雨"
        it.musicDes="Torrential rain"
        it.picIcon
        it.picVirtualBg=R.mipmap.bg_torrential_rain_v
        it.picBg= R.mipmap.bg_sound_big_waves_v
        it.fileSource= MediaPlayerWrapper.AudioFileSource.assets.name
        it.assetsFileName="暴雨.mp3"
        it.allPathString="https://yinping1.oss-cn-beijing.aliyuncs.com/baizaoyin/vip/%E5%85%A5%E7%9C%A0%E9%9B%A8%E5%A3%B0_real_VIP.mp3"
        it.pathString="vip/%E5%85%A5%E7%9C%A0%E9%9B%A8%E5%A3%B0_real_VIP.mp3"
        it.picUrl="https://xiaotupian.oss-cn-beijing.aliyuncs.com/baizaoyin/20.png"

    },
    MusicFileBean().also {
        it.name="大浪声"
        it.musicDes="The sound of big waves"
        it.picIcon
        it.picVirtualBg=R.mipmap.bg_sound_big_waves_v
        it.picBg= R.mipmap.bg_sound_big_waves_v
        it.fileSource= MediaPlayerWrapper.AudioFileSource.assets.name
        it.assetsFileName="大浪声.mp3"
        it.allPathString="https://yinping1.oss-cn-beijing.aliyuncs.com/baizaoyin/vip/%E6%B5%B7%E6%B5%AA_real_VIP.mp3"
        it.pathString="vip/%E6%B5%B7%E6%B5%AA_real_VIP.mp3"
        it.picUrl="https://xiaotupian.oss-cn-beijing.aliyuncs.com/baizaoyin/50.png"
    },
    MusicFileBean().also {
        it.name="风声"
        it.musicDes="Wind sound"
        it.picIcon
        it.picVirtualBg=R.mipmap.bg_wind_sound_v
        it.picBg= R.mipmap.bg_wind_sound_v
        it.fileSource= MediaPlayerWrapper.AudioFileSource.assets.name
        it.assetsFileName="风声.mp3"
        it.allPathString="https://yinping1.oss-cn-beijing.aliyuncs.com/baizaoyin/vip/4%E7%BA%A7%E9%A3%8E%E5%A3%B0_real_VIP.mp3"
        it.pathString="vip/4%E7%BA%A7%E9%A3%8E%E5%A3%B0_real_VIP.mp3"
        it.picUrl="https://xiaotupian.oss-cn-beijing.aliyuncs.com/baizaoyin/00.png"
    },
    MusicFileBean().also {
        it.name="海浪声"
        it.musicDes="The sound of the waves"
        it.picIcon
        it.picVirtualBg=R.mipmap.bg_sound_waves_v
        it.picBg= R.mipmap.bg_sound_waves_v
        it.fileSource= MediaPlayerWrapper.AudioFileSource.assets.name
        it.assetsFileName="海浪声.mp3"
        it.allPathString="https://yinping1.oss-cn-beijing.aliyuncs.com/baizaoyin/vip/%E6%B5%B7%E6%B5%AA2_real_VIP.mp3"
        it.pathString="vip/%E6%B5%B7%E6%B5%AA2_real_VIP.mp3"
        it.picUrl="https://xiaotupian.oss-cn-beijing.aliyuncs.com/baizaoyin/40.png"
    },
    MusicFileBean().also {
        it.name="雷声"
        it.musicDes="Thunder"
        it.picIcon
        it.picVirtualBg=R.mipmap.bg_thunder_v
        it.picBg= R.mipmap.bg_thunder_v
        it.fileSource= MediaPlayerWrapper.AudioFileSource.assets.name
        it.assetsFileName="雷声.mp3"
        it.allPathString="https://yinping1.oss-cn-beijing.aliyuncs.com/baizaoyin/vip/%E9%9B%B7%E5%A3%B0-%E5%AE%8C%E6%95%B4%E6%A8%A1%E6%8B%9F_VIP.mp3"
        it.pathString="vip/%E9%9B%B7%E5%A3%B0-%E5%AE%8C%E6%95%B4%E6%A8%A1%E6%8B%9F_VIP.mp3"
        it.picUrl="https://xiaotupian.oss-cn-beijing.aliyuncs.com/baizaoyin/30.png"
    },
    MusicFileBean().also {
        it.name="雨声"
        it.musicDes="The sound of rain"
        it.picIcon
        it.picVirtualBg=R.mipmap.bg_sound_rain_v
        it.picBg= R.mipmap.bg_sound_rain_v
        it.fileSource= MediaPlayerWrapper.AudioFileSource.assets.name
        it.assetsFileName="雨声.mp3"
        it.allPathString="https://yinping1.oss-cn-beijing.aliyuncs.com/baizaoyin/vip/%E6%9C%89%E8%B5%B7%E4%BC%8F%E7%9A%84%E9%9B%A8%E5%A3%B0_VIP.mp3"
        it.pathString="vip/%E6%9C%89%E8%B5%B7%E4%BC%8F%E7%9A%84%E9%9B%A8%E5%A3%B0_VIP.mp3"
        it.picUrl="https://xiaotupian.oss-cn-beijing.aliyuncs.com/baizaoyin/10.png"
    }
)

val allSource= arrayOf(
    MusicFileBean().apply {
        name="青蛙叫"
        musicDes="The frog barks"
        picIcon= R.mipmap.ic_frog
        picBg
        fileSource= MediaPlayerWrapper.AudioFileSource.assets.name
        allPathString="https://yinping1.oss-cn-beijing.aliyuncs.com/baizaoyin/putong/%E9%9D%92%E8%9B%99%E5%8F%AB.mp3"
        pathString="putong/%E9%9D%92%E8%9B%99%E5%8F%AB.mp3"
        assetsFileName="青蛙叫.mp3"
    },
    MusicFileBean().also {
        it.name="踩水"
        it.musicDes="Tread on water"
        it.picIcon= R.mipmap.ic_tread_water
        it.picBg
        it.fileSource= MediaPlayerWrapper.AudioFileSource.assets.name
        it.allPathString="https://yinping1.oss-cn-beijing.aliyuncs.com/baizaoyin/putong/%E8%B8%A9%E6%B0%B4.mp3"
        it.pathString="putong/%E8%B8%A9%E6%B0%B4.mp3"
        it.assetsFileName="踩水.mp3"
    },
    MusicFileBean().also {
        it.name="电视无信号"
        it.musicDes="There is no signal on TV."
        it.picIcon= R.mipmap.ic_tv_signal
        it.picBg
        it.fileSource= MediaPlayerWrapper.AudioFileSource.assets.name
        it.allPathString="https://yinping1.oss-cn-beijing.aliyuncs.com/baizaoyin/putong/%E7%94%B5%E8%A7%86%E6%97%A0%E4%BF%A1%E5%8F%B7.mp3"
        it.pathString="putong/%E7%94%B5%E8%A7%86%E6%97%A0%E4%BF%A1%E5%8F%B7.mp3"
        it.assetsFileName="电视无信号.mp3"
    },
    MusicFileBean().also {
        it.name="沙滩行走"
        it.musicDes="Walking on the beach"
        it.picIcon= R.mipmap.ic_walking_beach
        it.picBg
        it.fileSource= MediaPlayerWrapper.AudioFileSource.assets.name
        it.allPathString="https://yinping1.oss-cn-beijing.aliyuncs.com/baizaoyin/putong/%E6%B2%99%E6%BB%A9%E8%A1%8C%E8%B5%B0.mp3"
        it.pathString="putong/%E6%B2%99%E6%BB%A9%E8%A1%8C%E8%B5%B0.mp3"
        it.assetsFileName="沙滩行走.mp3"
    },
    MusicFileBean().also {
        it.name="鼓风机"
        it.musicDes="Air blower"
        it.picIcon= R.mipmap.ic_fan
        it.picBg
        it.fileSource= MediaPlayerWrapper.AudioFileSource.assets.name
        it.allPathString="https://yinping1.oss-cn-beijing.aliyuncs.com/baizaoyin/putong/%E9%BC%93%E9%A3%8E%E6%9C%BA.mp3"
        it.pathString="putong/%E9%BC%93%E9%A3%8E%E6%9C%BA.mp3"
        it.assetsFileName="鼓风机.mp3"
    },
    MusicFileBean().also {
        it.name="火车经过"
        it.musicDes="The train passed by"
        it.picIcon= R.mipmap.ic_train
        it.picBg
        it.fileSource= MediaPlayerWrapper.AudioFileSource.assets.name
        it.allPathString="https://yinping1.oss-cn-beijing.aliyuncs.com/baizaoyin/putong/%E7%81%AB%E8%BD%A6%E7%BB%8F%E8%BF%87.mp3"
        it.pathString="putong/%E7%81%AB%E8%BD%A6%E7%BB%8F%E8%BF%87.mp3"
        it.assetsFileName="火车经过.mp3"
    },
    MusicFileBean().also {
        it.name="吹风机-1"
        it.musicDes="Hair Dryer-1"
        it.picIcon= R.mipmap.ic_hair_dryer
        it.picBg
        it.fileSource= MediaPlayerWrapper.AudioFileSource.assets.name
        it.allPathString="https://yinping1.oss-cn-beijing.aliyuncs.com/baizaoyin/putong/%E5%90%B9%E9%A3%8E%E6%9C%BA.mp3"
        it.pathString="putong/%E5%90%B9%E9%A3%8E%E6%9C%BA.mp3"
        it.assetsFileName="吹风机-1.mp3"
    },
    MusicFileBean().also {
        it.name="吹风机-2"
        it.musicDes="Hair Dryer-2"
        it.picIcon= R.mipmap.ic_hair_dryer
        it.picBg
        it.fileSource= MediaPlayerWrapper.AudioFileSource.assets.name
        it.allPathString="https://yinping1.oss-cn-beijing.aliyuncs.com/baizaoyin/putong/%E5%90%B9%E9%A3%8E%E6%9C%BA2.mp3"
        it.pathString="putong/%E5%90%B9%E9%A3%8E%E6%9C%BA2.mp3"
        it.assetsFileName="吹风机-2.mp3"
    },
    MusicFileBean().also {
        it.name="吹风机-3"
        it.musicDes="Hair Dryer-3"
        it.picIcon= R.mipmap.ic_hair_dryer
        it.picBg
        it.fileSource= MediaPlayerWrapper.AudioFileSource.assets.name
        it.allPathString="https://yinping1.oss-cn-beijing.aliyuncs.com/baizaoyin/putong/%E5%90%B9%E9%A3%8E%E6%9C%BA3.mp3"
        it.pathString="putong/%E5%90%B9%E9%A3%8E%E6%9C%BA3.mp3"
        it.assetsFileName="吹风机-3.mp3"
    },
    MusicFileBean().also {
        it.name="游泳-1"
        it.musicDes="Swimming-1"
        it.picIcon= R.mipmap.ic_swimming
        it.picBg
        it.fileSource= MediaPlayerWrapper.AudioFileSource.assets.name
        it.allPathString="https://yinping1.oss-cn-beijing.aliyuncs.com/baizaoyin/putong/%E6%B8%B8%E6%B3%B3.mp3"
        it.pathString="putong/%E6%B8%B8%E6%B3%B3.mp3"
        it.assetsFileName="游泳-1.mp3"
    },
    MusicFileBean().also {
        it.name="游泳-2"
        it.musicDes="Swimming-2"
        it.picIcon= R.mipmap.ic_swimming
        it.picBg
        it.fileSource= MediaPlayerWrapper.AudioFileSource.assets.name
        it.allPathString="https://yinping1.oss-cn-beijing.aliyuncs.com/baizaoyin/putong/%E6%B8%B8%E6%B3%B32.mp3"
        it.pathString="putong/%E6%B8%B8%E6%B3%B32.mp3"
        it.assetsFileName="游泳-2.mp3"
    },
    MusicFileBean().also {
        it.name="摩托车"
        it.musicDes="Motorbike"
        it.picIcon= R.mipmap.ic_motorbike
        it.picBg
        it.fileSource= MediaPlayerWrapper.AudioFileSource.assets.name
        it.allPathString="https://yinping1.oss-cn-beijing.aliyuncs.com/baizaoyin/putong/%E6%91%A9%E6%89%98%E8%BD%A6.mp3"
        it.pathString="putong/%E6%91%A9%E6%89%98%E8%BD%A6.mp3"
        it.assetsFileName="摩托车.mp3"
    },
    MusicFileBean().also {
        it.name="钢琴"
        it.musicDes="Piano"
        it.picIcon= R.mipmap.ic_piano
        it.picBg
        it.fileSource= MediaPlayerWrapper.AudioFileSource.assets.name
        it.allPathString="https://yinping1.oss-cn-beijing.aliyuncs.com/baizaoyin/putong/%E9%92%A2%E7%90%B4.mp3"
        it.pathString="putong/%E9%92%A2%E7%90%B4.mp3"
        it.assetsFileName="钢琴.mp3"
    },
    MusicFileBean().also {
        it.name="下雨-有雷"
        it.musicDes="-Rain.-Thunder."
        it.picIcon= R.mipmap.ic_rain_thunder
        it.picBg
        it.fileSource= MediaPlayerWrapper.AudioFileSource.assets.name
        it.allPathString="https://yinping1.oss-cn-beijing.aliyuncs.com/baizaoyin/putong/%E4%B8%8B%E9%9B%A8-%E6%9C%89%E9%9B%B7.mp3"
        it.pathString="putong/%E4%B8%8B%E9%9B%A8-%E6%9C%89%E9%9B%B7.mp3"
        it.assetsFileName="下雨-有雷.mp3"
    },
    MusicFileBean().apply {
       name="海浪"
       musicDes="Waves"
       picIcon= R.mipmap.ic_waves
       picBg
       fileSource= MediaPlayerWrapper.AudioFileSource.assets.name
        allPathString="https://yinping1.oss-cn-beijing.aliyuncs.com/baizaoyin/putong/%E6%B5%B7%E6%B5%AA.mp3"
        pathString="putong/%E6%B5%B7%E6%B5%AA.mp3"
       assetsFileName="海浪.mp3"
    },
    MusicFileBean().apply {
        name="雷声-1"
        musicDes="Thunder-1"
        picIcon= R.mipmap.ic_thunder
        picBg
        fileSource= MediaPlayerWrapper.AudioFileSource.assets.name
        allPathString="https://yinping1.oss-cn-beijing.aliyuncs.com/baizaoyin/putong/%E9%9B%B7%E5%A3%B04.mp3"
        pathString="putong/%E9%9B%B7%E5%A3%B04.mp3"
        assetsFileName="雷声-1.mp3"
    },
    MusicFileBean().also {
        it.name="雷声-2"
        it.musicDes="Thunder-2"
        it.picIcon= R.mipmap.ic_thunder
        it.picBg
        it.fileSource= MediaPlayerWrapper.AudioFileSource.assets.name
        it.allPathString="https://yinping1.oss-cn-beijing.aliyuncs.com/baizaoyin/putong/%E9%9B%B7%E5%A3%B02.mp3"
        it.pathString="putong/%E9%9B%B7%E5%A3%B02.mp3"
        it.assetsFileName="雷声-2.mp3"
    },
    MusicFileBean().also {
        it.name="雷声-3"
        it.musicDes="Thunder-3"
        it.picIcon= R.mipmap.ic_thunder
        it.picBg
        it.fileSource= MediaPlayerWrapper.AudioFileSource.assets.name
        it.allPathString="https://yinping1.oss-cn-beijing.aliyuncs.com/baizaoyin/putong/%E9%9B%B7%E5%A3%B03.mp3"
        it.pathString="putong/%E9%9B%B7%E5%A3%B03.mp3"
        it.assetsFileName="雷声-3.mp3"
    },
    MusicFileBean().also {
        it.name="打字"
        it.musicDes="Typing"
        it.picIcon= R.mipmap.ic_typing
        it.picBg
        it.fileSource= MediaPlayerWrapper.AudioFileSource.assets.name
        it.allPathString=" https://yinping1.oss-cn-beijing.aliyuncs.com/baizaoyin/putong/%E6%89%93%E5%AD%97.mp3"
        it.pathString="putong/%E6%89%93%E5%AD%97.mp3"
        it.assetsFileName="打字.mp3"
    },
    MusicFileBean().also {
        it.name="水落玻璃"
        it.musicDes="Waterfall glass"
        it.picIcon= R.mipmap.ic_waterfall_glass
        it.picBg
        it.fileSource= MediaPlayerWrapper.AudioFileSource.assets.name
        it.allPathString=" https://yinping1.oss-cn-beijing.aliyuncs.com/baizaoyin/putong/%E6%B0%B4%E8%90%BD%E7%8E%BB%E7%92%83.mp3"
        it.pathString="putong/%E6%B0%B4%E8%90%BD%E7%8E%BB%E7%92%83.mp3"
        it.assetsFileName="水落玻璃.mp3"
    },
    MusicFileBean().also {
        it.name="心跳"
        it.musicDes="heartbeat"
        it.picIcon= R.mipmap.ic_heartbeat
        it.picBg
        it.fileSource= MediaPlayerWrapper.AudioFileSource.assets.name
        it.allPathString="https://yinping1.oss-cn-beijing.aliyuncs.com/baizaoyin/putong/%E5%BF%83%E8%B7%B3.mp3"
        it.pathString="putong/%E5%BF%83%E8%B7%B3.mp3"
        it.assetsFileName="心跳.mp3"
    },
    MusicFileBean().also {
        it.name="钟摆"
        it.musicDes="Pendulum"
        it.picIcon= R.mipmap.ic_pendulum
        it.picBg
        it.fileSource= MediaPlayerWrapper.AudioFileSource.assets.name
        it.allPathString=" https://yinping1.oss-cn-beijing.aliyuncs.com/baizaoyin/putong/%E9%92%9F%E6%91%86.mp3"
        it.pathString="putong/%E9%92%9F%E6%91%86.mp3"
        it.assetsFileName="钟摆.mp3"
    },
    MusicFileBean().also {
        it.name="小呼噜-猫"
        it.musicDes="-Little purr.-Cat."
        it.picIcon= R.mipmap.ic_little_purr_cat
        it.picBg
        it.fileSource= MediaPlayerWrapper.AudioFileSource.assets.name
        it.allPathString="https://yinping1.oss-cn-beijing.aliyuncs.com/baizaoyin/putong/%E5%B0%8F%E5%91%BC%E5%99%9C-%E7%8C%AB.mp3"
        it.pathString="putong/%E5%B0%8F%E5%91%BC%E5%99%9C-%E7%8C%AB.mp3"
        it.assetsFileName="小呼噜-猫.mp3"
    },
    MusicFileBean().also {
        it.name="呼噜-未知"
        it.musicDes="Snore"
        it.picIcon= R.mipmap.ic_snore
        it.picBg
        it.fileSource= MediaPlayerWrapper.AudioFileSource.assets.name
        it.allPathString="https://yinping1.oss-cn-beijing.aliyuncs.com/baizaoyin/putong/%E5%91%BC%E5%99%9C-%E6%9C%AA%E7%9F%A5.mp3"
        it.pathString="putong/%E5%91%BC%E5%99%9C-%E6%9C%AA%E7%9F%A5.mp3"
        it.assetsFileName="呼噜-未知.mp3"
    },
    MusicFileBean().also {
        it.name="夏天-鸟鸣1"
        it.musicDes="Summer-birdsong"
        it.picIcon= R.mipmap.ic_summer_birdsong
        it.picBg
        it.fileSource= MediaPlayerWrapper.AudioFileSource.assets.name
        it.allPathString="https://yinping1.oss-cn-beijing.aliyuncs.com/baizaoyin/putong/%E5%A4%8F%E5%A4%A9-%E9%B8%9F%E9%B8%A3.mp3"
        it.pathString="putong/%E5%A4%8F%E5%A4%A9-%E9%B8%9F%E9%B8%A3.mp3"
        it.assetsFileName="夏天-鸟鸣1.mp3"
    },
    MusicFileBean().also {
        it.name="夏天-鸟鸣2"
        it.musicDes="夏天-鸟鸣2"
        it.picIcon= R.mipmap.ic_summer_birdsong
        it.picBg
        it.fileSource= MediaPlayerWrapper.AudioFileSource.assets.name
        it.allPathString="https://yinping1.oss-cn-beijing.aliyuncs.com/baizaoyin/putong/%E5%A4%8F%E5%A4%A9-%E9%B8%9F%E9%B8%A32.mp3"
        it.pathString="putong/%E5%A4%8F%E5%A4%A9-%E9%B8%9F%E9%B8%A32.mp3"
        it.assetsFileName="夏天-鸟鸣2.mp3"
    },
    MusicFileBean().also {
        it.name="夏天-自然"
        it.musicDes="Summer-Nature"
        it.picIcon= R.mipmap.ic_summer_nature
        it.picBg
        it.fileSource= MediaPlayerWrapper.AudioFileSource.assets.name
        it.allPathString="https://yinping1.oss-cn-beijing.aliyuncs.com/baizaoyin/putong/%E5%A4%8F%E5%A4%A9-%E8%87%AA%E7%84%B6.mp3"
        it.pathString="putong/%E5%A4%8F%E5%A4%A9-%E8%87%AA%E7%84%B6.mp3"
        it.assetsFileName="夏天-自然.mp3"
    },
    MusicFileBean().also {
        it.name="零星小雨1"
        it.musicDes="Sporadic drizzle"
        it.picIcon= R.mipmap.ic_sporadic_drizzle
        it.picBg
        it.fileSource= MediaPlayerWrapper.AudioFileSource.assets.name
        it.allPathString=" https://yinping1.oss-cn-beijing.aliyuncs.com/baizaoyin/putong/%E9%9B%B6%E6%98%9F%E5%B0%8F%E9%9B%A8.mp3"
        it.pathString="putong/%E9%9B%B6%E6%98%9F%E5%B0%8F%E9%9B%A8.mp3"
        it.assetsFileName="零星小雨1.mp3"
    },
    MusicFileBean().also {
        it.name="零星小雨2"
        it.musicDes="零星小雨2"
        it.picIcon= R.mipmap.ic_sporadic_drizzle
        it.picBg
        it.fileSource= MediaPlayerWrapper.AudioFileSource.assets.name
        it.allPathString="https://yinping1.oss-cn-beijing.aliyuncs.com/baizaoyin/putong/%E9%9B%B6%E6%98%9F%E5%B0%8F%E9%9B%A82.mp3"
        it.pathString="putong/%E9%9B%B6%E6%98%9F%E5%B0%8F%E9%9B%A82.mp3"
        it.assetsFileName="零星小雨2.mp3"
    },
    MusicFileBean().also {
        it.name="刮风"
        it.musicDes="Windy"
        it.picIcon= R.mipmap.ic_windy
        it.picBg
        it.fileSource= MediaPlayerWrapper.AudioFileSource.assets.name
        it.allPathString="https://yinping1.oss-cn-beijing.aliyuncs.com/baizaoyin/putong/%E5%88%AE%E9%A3%8E.mp3"
        it.pathString="putong/%E5%88%AE%E9%A3%8E.mp3"
        it.assetsFileName="刮风.mp3"
    },
    MusicFileBean().also {
        it.name="流水1"
        it.musicDes="Running water"
        it.picIcon= R.mipmap.ic_running_water
        it.picBg
        it.fileSource= MediaPlayerWrapper.AudioFileSource.assets.name
        it.allPathString=" https://yinping1.oss-cn-beijing.aliyuncs.com/baizaoyin/putong/%E6%B5%81%E6%B0%B4.mp3"
        it.pathString="putong/%E6%B5%81%E6%B0%B4.mp3"
        it.assetsFileName="流水1.mp3"
    },
    MusicFileBean().also {
        it.name="流水2"
        it.musicDes="流水2"
        it.picIcon= R.mipmap.ic_running_water
        it.picBg
        it.fileSource= MediaPlayerWrapper.AudioFileSource.assets.name
        it.allPathString="https://yinping1.oss-cn-beijing.aliyuncs.com/baizaoyin/putong/%E6%B5%81%E6%B0%B42.mp3"
        it.pathString="putong/%E6%B5%81%E6%B0%B42.mp3"
        it.assetsFileName="流水2.mp3"
    },
    MusicFileBean().also {
        it.name="流水3"
        it.musicDes="流水3"
        it.picIcon= R.mipmap.ic_running_water
        it.picBg
        it.fileSource= MediaPlayerWrapper.AudioFileSource.assets.name
        it.allPathString="https://yinping1.oss-cn-beijing.aliyuncs.com/baizaoyin/putong/%E6%B5%81%E6%B0%B43.mp3"
        it.pathString="putong/%E6%B5%81%E6%B0%B43.mp3"
        it.assetsFileName="流水3.mp3"
    },
    MusicFileBean().also {
        it.name="鼓声"
        it.musicDes="Drums"
        it.picIcon= R.mipmap.ic_drums
        it.picBg
        it.fileSource= MediaPlayerWrapper.AudioFileSource.assets.name
        it.allPathString="https://yinping1.oss-cn-beijing.aliyuncs.com/baizaoyin/putong/%E9%BC%93%E5%A3%B0.mp3"
        it.pathString="putong/%E9%BC%93%E5%A3%B0.mp3"
        it.assetsFileName="鼓声.mp3"
    },
    MusicFileBean().also {
        it.name="海浪-风"
        it.musicDes="Wave-wind"
        it.picIcon= R.mipmap.ic_wave_wind
        it.picBg
        it.fileSource= MediaPlayerWrapper.AudioFileSource.assets.name
        it.allPathString="https://yinping1.oss-cn-beijing.aliyuncs.com/baizaoyin/putong/%E6%B5%B7%E6%B5%AA-%E9%A3%8E.mp3"
        it.pathString="putong/%E6%B5%B7%E6%B5%AA-%E9%A3%8E.mp3"
        it.assetsFileName="海浪-风.mp3"
    },
    MusicFileBean().also {
        it.name="风声加车声"
        it.musicDes="The sound of the wind and the sound of cars"
        it.picIcon= R.mipmap.ic_wind_cars_sound
        it.picBg
        it.fileSource= MediaPlayerWrapper.AudioFileSource.assets.name
        it.allPathString=" https://yinping1.oss-cn-beijing.aliyuncs.com/baizaoyin/putong/%E9%A3%8E%E5%A3%B0%E5%8A%A0%E8%BD%A6%E5%A3%B0.mp3"
        it.pathString="putong/%E9%A3%8E%E5%A3%B0%E5%8A%A0%E8%BD%A6%E5%A3%B0.mp3"
        it.assetsFileName="风声加车声.mp3"
    },
    MusicFileBean().also {
        it.name="发动机-轻"
        it.musicDes="Engine-Light"
        it.picIcon= R.mipmap.ic_engine_light
        it.picBg
        it.fileSource= MediaPlayerWrapper.AudioFileSource.assets.name
        it.allPathString="https://yinping1.oss-cn-beijing.aliyuncs.com/baizaoyin/putong/%E5%8F%91%E5%8A%A8%E6%9C%BA-%E8%BD%BB.mp3"
        it.pathString="putong/%E5%8F%91%E5%8A%A8%E6%9C%BA-%E8%BD%BB.mp3"
        it.assetsFileName="发动机-轻.mp3"
    }
)