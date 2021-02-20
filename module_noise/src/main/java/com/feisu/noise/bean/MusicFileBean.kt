package com.feisu.noise.bean

import android.os.Parcel
import android.os.Parcelable
import com.feisu.noise.R

class MusicFileBean() :Parcelable {
    var name:String?=null
    var musicDes:String?=null
    var picIcon:Int=R.mipmap.ic_wind_cars_sound
    var picBg:Int= R.mipmap.bg_sound_rain
    var picVirtualBg:Int=R.mipmap.bg_sound_rain_v

    var fileSource:String?=null
    var assetsFileName:String?=null

    var rawResId:Int?=null

    constructor(parcel: Parcel) : this() {
        name = parcel.readString()
        musicDes = parcel.readString()
        picIcon = parcel.readInt()
        picBg = parcel.readInt()
        picVirtualBg = parcel.readInt()
        fileSource = parcel.readString()
        assetsFileName = parcel.readString()
        rawResId = parcel.readValue(Int::class.java.classLoader) as? Int
    }

    override fun equals(other: Any?): Boolean {
        if (other !is MusicFileBean) return false
        if (other.name!=name) return false
        return true
    }

    override fun hashCode(): Int {
        var result = name?.hashCode() ?: 0
        result = 31 * result + (musicDes?.hashCode() ?: 0)
        result = 31 * result + picIcon
        result = 31 * result + picBg
        result = 31 * result + (fileSource?.hashCode() ?: 0)
        result = 31 * result + (assetsFileName?.hashCode() ?: 0)
        result = 31 * result + (rawResId ?: 0)
        return result
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(musicDes)
        parcel.writeInt(picIcon)
        parcel.writeInt(picBg)
        parcel.writeInt(picVirtualBg)
        parcel.writeString(fileSource)
        parcel.writeString(assetsFileName)
        parcel.writeValue(rawResId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MusicFileBean> {
        override fun createFromParcel(parcel: Parcel): MusicFileBean {
            return MusicFileBean(parcel)
        }

        override fun newArray(size: Int): Array<MusicFileBean?> {
            return arrayOfNulls(size)
        }
    }
}