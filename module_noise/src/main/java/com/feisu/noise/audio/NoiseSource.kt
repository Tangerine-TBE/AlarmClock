package com.feisu.noise.audio

import com.feisu.noise.R
import com.feisu.noise.bean.MusicFileBean

val recommendSource= arrayOf(
    MusicFileBean().also {
        it.name="暴雨"
        it.musicDes="Torrential rain"
        it.picIcon
        it.picVirtualBg=R.mipmap.bg_torrential_rain_v
        it.picBg= R.mipmap.bg_torrential_rain
        it.fileSource= MediaPlayerWrapper.AudioFileSource.assets.name
        it.assetsFileName="暴雨.mp3"
    },
    MusicFileBean().also {
        it.name="大浪声"
        it.musicDes="The sound of big waves"
        it.picIcon
        it.picVirtualBg=R.mipmap.bg_sound_big_waves_v
        it.picBg= R.mipmap.bg_sound_big_waves
        it.fileSource= MediaPlayerWrapper.AudioFileSource.assets.name
        it.assetsFileName="大浪声.mp3"
    },
    MusicFileBean().also {
        it.name="风声"
        it.musicDes="Wind sound"
        it.picIcon
        it.picVirtualBg=R.mipmap.bg_wind_sound_v
        it.picBg= R.mipmap.bg_wind_sound
        it.fileSource= MediaPlayerWrapper.AudioFileSource.assets.name
        it.assetsFileName="风声.mp3"
    },
    MusicFileBean().also {
        it.name="海浪声"
        it.musicDes="The sound of the waves"
        it.picIcon
        it.picVirtualBg=R.mipmap.bg_sound_waves_v
        it.picBg= R.mipmap.bg_sound_waves
        it.fileSource= MediaPlayerWrapper.AudioFileSource.assets.name
        it.assetsFileName="海浪声.mp3"
    },
    MusicFileBean().also {
        it.name="雷声"
        it.musicDes="Thunder"
        it.picIcon
        it.picVirtualBg=R.mipmap.bg_thunder_v
        it.picBg= R.mipmap.bg_thunder
        it.fileSource= MediaPlayerWrapper.AudioFileSource.assets.name
        it.assetsFileName="雷声.mp3"
    },
    MusicFileBean().also {
        it.name="雨声"
        it.musicDes="The sound of rain"
        it.picIcon
        it.picVirtualBg=R.mipmap.bg_sound_rain_v
        it.picBg= R.mipmap.bg_sound_rain
        it.fileSource= MediaPlayerWrapper.AudioFileSource.assets.name
        it.assetsFileName="雨声.mp3"
    }
)

val allSource= arrayOf(
    MusicFileBean().also {
        it.name="青蛙叫"
        it.musicDes="The frog barks"
        it.picIcon= R.mipmap.ic_frog
        it.picBg
        it.fileSource= MediaPlayerWrapper.AudioFileSource.assets.name
        it.assetsFileName="青蛙叫.mp3"
    },
    MusicFileBean().also {
        it.name="踩水"
        it.musicDes="Tread on water"
        it.picIcon= R.mipmap.ic_tread_water
        it.picBg
        it.fileSource= MediaPlayerWrapper.AudioFileSource.assets.name
        it.assetsFileName="踩水.mp3"
    },
    MusicFileBean().also {
        it.name="电视无信号"
        it.musicDes="There is no signal on TV."
        it.picIcon= R.mipmap.ic_tv_signal
        it.picBg
        it.fileSource= MediaPlayerWrapper.AudioFileSource.assets.name
        it.assetsFileName="电视无信号.mp3"
    },
    MusicFileBean().also {
        it.name="沙滩行走"
        it.musicDes="Walking on the beach"
        it.picIcon= R.mipmap.ic_walking_beach
        it.picBg
        it.fileSource= MediaPlayerWrapper.AudioFileSource.assets.name
        it.assetsFileName="沙滩行走.mp3"
    },
    MusicFileBean().also {
        it.name="鼓风机"
        it.musicDes="Air blower"
        it.picIcon= R.mipmap.ic_fan
        it.picBg
        it.fileSource= MediaPlayerWrapper.AudioFileSource.assets.name
        it.assetsFileName="鼓风机.mp3"
    },
    MusicFileBean().also {
        it.name="火车经过"
        it.musicDes="The train passed by"
        it.picIcon= R.mipmap.ic_train
        it.picBg
        it.fileSource= MediaPlayerWrapper.AudioFileSource.assets.name
        it.assetsFileName="火车经过.mp3"
    },
    MusicFileBean().also {
        it.name="吹风机-1"
        it.musicDes="Hair Dryer-1"
        it.picIcon= R.mipmap.ic_hair_dryer
        it.picBg
        it.fileSource= MediaPlayerWrapper.AudioFileSource.assets.name
        it.assetsFileName="吹风机-1.mp3"
    },
    MusicFileBean().also {
        it.name="吹风机-2"
        it.musicDes="Hair Dryer-2"
        it.picIcon= R.mipmap.ic_hair_dryer
        it.picBg
        it.fileSource= MediaPlayerWrapper.AudioFileSource.assets.name
        it.assetsFileName="吹风机-2.mp3"
    },
    MusicFileBean().also {
        it.name="吹风机-3"
        it.musicDes="Hair Dryer-3"
        it.picIcon= R.mipmap.ic_hair_dryer
        it.picBg
        it.fileSource= MediaPlayerWrapper.AudioFileSource.assets.name
        it.assetsFileName="吹风机-3.mp3"
    },
    MusicFileBean().also {
        it.name="游泳-1"
        it.musicDes="Swimming-1"
        it.picIcon= R.mipmap.ic_swimming
        it.picBg
        it.fileSource= MediaPlayerWrapper.AudioFileSource.assets.name
        it.assetsFileName="游泳-1.mp3"
    },
    MusicFileBean().also {
        it.name="游泳-2"
        it.musicDes="Swimming-2"
        it.picIcon= R.mipmap.ic_swimming
        it.picBg
        it.fileSource= MediaPlayerWrapper.AudioFileSource.assets.name
        it.assetsFileName="游泳-2.mp3"
    },
    MusicFileBean().also {
        it.name="摩托车"
        it.musicDes="Motorbike"
        it.picIcon= R.mipmap.ic_motorbike
        it.picBg
        it.fileSource= MediaPlayerWrapper.AudioFileSource.assets.name
        it.assetsFileName="摩托车.mp3"
    },
    MusicFileBean().also {
        it.name="钢琴"
        it.musicDes="Piano"
        it.picIcon= R.mipmap.ic_piano
        it.picBg
        it.fileSource= MediaPlayerWrapper.AudioFileSource.assets.name
        it.assetsFileName="钢琴.mp3"
    },
    MusicFileBean().also {
        it.name="下雨-有雷"
        it.musicDes="-Rain.-Thunder."
        it.picIcon= R.mipmap.ic_rain_thunder
        it.picBg
        it.fileSource= MediaPlayerWrapper.AudioFileSource.assets.name
        it.assetsFileName="下雨-有雷.mp3"
    },
    MusicFileBean().also {
        it.name="海浪"
        it.musicDes="Waves"
        it.picIcon= R.mipmap.ic_waves
        it.picBg
        it.fileSource= MediaPlayerWrapper.AudioFileSource.assets.name
        it.assetsFileName="海浪.mp3"
    },
    MusicFileBean().also {
        it.name="雷声-1"
        it.musicDes="Thunder-1"
        it.picIcon= R.mipmap.ic_thunder
        it.picBg
        it.fileSource= MediaPlayerWrapper.AudioFileSource.assets.name
        it.assetsFileName="雷声-1.mp3"
    },
    MusicFileBean().also {
        it.name="雷声-2"
        it.musicDes="Thunder-2"
        it.picIcon= R.mipmap.ic_thunder
        it.picBg
        it.fileSource= MediaPlayerWrapper.AudioFileSource.assets.name
        it.assetsFileName="雷声-2.mp3"
    },
    MusicFileBean().also {
        it.name="雷声-3"
        it.musicDes="Thunder-3"
        it.picIcon= R.mipmap.ic_thunder
        it.picBg
        it.fileSource= MediaPlayerWrapper.AudioFileSource.assets.name
        it.assetsFileName="雷声-3.mp3"
    },
    MusicFileBean().also {
        it.name="打字"
        it.musicDes="Typing"
        it.picIcon= R.mipmap.ic_typing
        it.picBg
        it.fileSource= MediaPlayerWrapper.AudioFileSource.assets.name
        it.assetsFileName="打字.mp3"
    },
    MusicFileBean().also {
        it.name="水落玻璃"
        it.musicDes="Waterfall glass"
        it.picIcon= R.mipmap.ic_waterfall_glass
        it.picBg
        it.fileSource= MediaPlayerWrapper.AudioFileSource.assets.name
        it.assetsFileName="水落玻璃.mp3"
    },
    MusicFileBean().also {
        it.name="心跳"
        it.musicDes="heartbeat"
        it.picIcon= R.mipmap.ic_heartbeat
        it.picBg
        it.fileSource= MediaPlayerWrapper.AudioFileSource.assets.name
        it.assetsFileName="心跳.mp3"
    },
    MusicFileBean().also {
        it.name="钟摆"
        it.musicDes="Pendulum"
        it.picIcon= R.mipmap.ic_pendulum
        it.picBg
        it.fileSource= MediaPlayerWrapper.AudioFileSource.assets.name
        it.assetsFileName="钟摆.mp3"
    },
    MusicFileBean().also {
        it.name="小呼噜-猫"
        it.musicDes="-Little purr.-Cat."
        it.picIcon= R.mipmap.ic_little_purr_cat
        it.picBg
        it.fileSource= MediaPlayerWrapper.AudioFileSource.assets.name
        it.assetsFileName="小呼噜-猫.mp3"
    },
    MusicFileBean().also {
        it.name="呼噜-未知"
        it.musicDes="Snore"
        it.picIcon= R.mipmap.ic_snore
        it.picBg
        it.fileSource= MediaPlayerWrapper.AudioFileSource.assets.name
        it.assetsFileName="呼噜-未知.mp3"
    },
    MusicFileBean().also {
        it.name="夏天-鸟鸣1"
        it.musicDes="Summer-birdsong"
        it.picIcon= R.mipmap.ic_summer_birdsong
        it.picBg
        it.fileSource= MediaPlayerWrapper.AudioFileSource.assets.name
        it.assetsFileName="夏天-鸟鸣1.mp3"
    },
    MusicFileBean().also {
        it.name="夏天-鸟鸣2"
        it.musicDes="夏天-鸟鸣2"
        it.picIcon= R.mipmap.ic_summer_birdsong
        it.picBg
        it.fileSource= MediaPlayerWrapper.AudioFileSource.assets.name
        it.assetsFileName="夏天-鸟鸣2.mp3"
    },
    MusicFileBean().also {
        it.name="夏天-自然"
        it.musicDes="Summer-Nature"
        it.picIcon= R.mipmap.ic_summer_nature
        it.picBg
        it.fileSource= MediaPlayerWrapper.AudioFileSource.assets.name
        it.assetsFileName="夏天-自然.mp3"
    },
    MusicFileBean().also {
        it.name="零星小雨1"
        it.musicDes="Sporadic drizzle"
        it.picIcon= R.mipmap.ic_sporadic_drizzle
        it.picBg
        it.fileSource= MediaPlayerWrapper.AudioFileSource.assets.name
        it.assetsFileName="零星小雨1.mp3"
    },
    MusicFileBean().also {
        it.name="零星小雨2"
        it.musicDes="零星小雨2"
        it.picIcon= R.mipmap.ic_sporadic_drizzle
        it.picBg
        it.fileSource= MediaPlayerWrapper.AudioFileSource.assets.name
        it.assetsFileName="零星小雨2.mp3"
    },
    MusicFileBean().also {
        it.name="刮风"
        it.musicDes="Windy"
        it.picIcon= R.mipmap.ic_windy
        it.picBg
        it.fileSource= MediaPlayerWrapper.AudioFileSource.assets.name
        it.assetsFileName="刮风.mp3"
    },
    MusicFileBean().also {
        it.name="流水1"
        it.musicDes="Running water"
        it.picIcon= R.mipmap.ic_running_water
        it.picBg
        it.fileSource= MediaPlayerWrapper.AudioFileSource.assets.name
        it.assetsFileName="流水1.mp3"
    },
    MusicFileBean().also {
        it.name="流水2"
        it.musicDes="流水2"
        it.picIcon= R.mipmap.ic_running_water
        it.picBg
        it.fileSource= MediaPlayerWrapper.AudioFileSource.assets.name
        it.assetsFileName="流水2.mp3"
    },
    MusicFileBean().also {
        it.name="流水3"
        it.musicDes="流水3"
        it.picIcon= R.mipmap.ic_running_water
        it.picBg
        it.fileSource= MediaPlayerWrapper.AudioFileSource.assets.name
        it.assetsFileName="流水3.mp3"
    },
    MusicFileBean().also {
        it.name="鼓声"
        it.musicDes="Drums"
        it.picIcon= R.mipmap.ic_drums
        it.picBg
        it.fileSource= MediaPlayerWrapper.AudioFileSource.assets.name
        it.assetsFileName="鼓声.mp3"
    },
    MusicFileBean().also {
        it.name="海浪-风"
        it.musicDes="Wave-wind"
        it.picIcon= R.mipmap.ic_wave_wind
        it.picBg
        it.fileSource= MediaPlayerWrapper.AudioFileSource.assets.name
        it.assetsFileName="海浪-风.mp3"
    },
    MusicFileBean().also {
        it.name="风声加车声"
        it.musicDes="The sound of the wind and the sound of cars"
        it.picIcon= R.mipmap.ic_wind_cars_sound
        it.picBg
        it.fileSource= MediaPlayerWrapper.AudioFileSource.assets.name
        it.assetsFileName="风声加车声.mp3"
    },
    MusicFileBean().also {
        it.name="发动机-轻"
        it.musicDes="Engine-Light"
        it.picIcon= R.mipmap.ic_engine_light
        it.picBg
        it.fileSource= MediaPlayerWrapper.AudioFileSource.assets.name
        it.assetsFileName="发动机-轻.mp3"
    }
)