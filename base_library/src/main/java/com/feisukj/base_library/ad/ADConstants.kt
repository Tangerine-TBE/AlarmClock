package com.feisukj.base_library.ad

import com.feisukj.base_library.utils.SPUtil

object ADConstants {
    /************************页面命名 存储广告显示配置 */
    const val START_PAGE = "start_page"
    const val HOME_PAGE = "home_page"
    const val ALL_NEW = "all_new"
    const val SETTING_PAGE = "setting_page"
    const val EXIT_PAGE = "exit_page"
    /************************页面命名*************************/

    /************************页面命名 */
    /************************开屏广告 */
    const val AD_APP_BACKGROUND_TIME = "ad_app_background_time" //App退到后台时间

    const val AD_SPREAD_PERIOD = "ad_spread_period" //开屏后台设置的时间间隔

    const val AD_SPLASH_STATUS = "ad_splash_status" //开屏开关

    /************************开屏广告*************************/


    /************************开屏广告 */
    /************************插屏广告 */
    const val AD_INSERT_SHOW_PERIOD = "ad_insert_change_period" //插屏广告显示间隔

    const val AD_INSERT_LAST_SHOW = "ad_insert_last_origin" //插屏广告上展示时间

    /************************插屏广告*************************/
    /************************插屏广告 */
    /**
     * 是否开启了页面banner定时器
     */
    const val AD_BANNER_IS_TIMER = "ad_banner_is_timer"

    const val AD_BANNER_LAST_CHANGE = "AD_BANNER_LAST_CHANGE"

    /************************原生广告 */
    const val AD_NATIVE_SHOW_PERIOD = "ad_native_change_period" //原生广告显示间隔

    const val AD_NATIVE_LAST_SHOW = "ad_native_last_origin" //原生广告上ci展示时间

    /************************原生广告 */
    val kTouTiaoAppKey by lazy { SPUtil.instance.getString("kTouTiaoAppKey","5054951")?:"5054951" }
    val kTouTiaoKaiPing by lazy { SPUtil.instance.getString("kTouTiaoKaiPing","887308046")?:"887308046" }
    val kTouTiaoBannerKey by lazy { SPUtil.instance.getString("kTouTiaoBannerKey","945092940")?:"945092940" }
    val kTouTiaoChaPingKey by lazy { SPUtil.instance.getString("kTouTiaoChaPingKey","945092941")?:"945092941" }
    val kTouTiaoJiLiKey by lazy { SPUtil.instance.getString("kTouTiaoJiLiKey","945092942")?:"945092942" }
    val kTouTiaoSeniorKey by lazy { SPUtil.instance.getString("kTouTiaoSeniorKey","945092939")?:"945092939" }

    val kGDTMobSDKAppKey by lazy { SPUtil.instance.getString("kGDTMobSDKAppKey","1110257261")?:"1110257261" }
    val kGDTMobSDKChaPingKey by lazy { SPUtil.instance.getString("kGDTMobSDKChaPingKey","5021700424697302")?:"5021700424697302" }
    val kGDTMobSDKKaiPingKey by lazy { SPUtil.instance.getString("kGDTMobSDKKaiPingKey","8011702484093279")?:"8011702484093279" }
    val kGDTMobSDKBannerKey by lazy { SPUtil.instance.getString("kGDTMobSDKBannerKey","7001409434498232")?:"7001409434498232" }
    val kGDTMobSDKNativeKey by lazy { SPUtil.instance.getString("kGDTMobSDKNativeKey","7041901474795189")?:"7041901474795189" }
    val kGDTMobSDKJiLiKey by lazy { SPUtil.instance.getString("kGDTMobSDKJiLiKey","7081807404092384")?:"7081807404092384" }
}