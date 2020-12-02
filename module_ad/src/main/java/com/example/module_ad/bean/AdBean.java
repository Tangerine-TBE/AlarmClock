package com.example.module_ad.bean;

import com.example.module_ad.base.IBaseAdBean;
import com.example.module_ad.base.IBaseXXBean;

/**
 * @author wujinming QQ:1245074510
 * @name td_horoscope
 * @class name：com.example.module_ad.bean
 * @class describe
 * @time 2020/11/25 20:27
 * @class describe
 */
public class AdBean {

    /**
     * code : 0
     * message : 成功
     * data : {"start_page":{"spread_screen":{"status":false,"ad_origin":"gdt_toutiao","times":1,"change_times":300,"ad_percent":"0_100"}},"home_page":{"native_screen":{"status":false,"ad_origin":"gdt_toutiao","times":0,"change_times":3,"ad_percent":"0_100"},"insert_screen":{"status":false,"ad_origin":"gdt_toutiao","times":300,"change_times":3,"ad_percent":"0_100"},"banner_screen":{"status":false,"ad_origin":"gdt_toutiao","times":100,"change_times":300,"ad_percent":"0_100"}},"almanac_page":{"native_screen":{"status":false,"ad_origin":"gdt_toutiao","times":0,"change_times":3,"ad_percent":"0_100"},"insert_screen":{"status":false,"ad_origin":"gdt_toutiao","times":300,"change_times":3,"ad_percent":"0_100"},"banner_screen":{"status":false,"ad_origin":"gdt_toutiao","times":100,"change_times":300,"ad_percent":"0_100"}},"today_in_history_page":{"native_screen":{"status":false,"ad_origin":"gdt_toutiao","times":0,"change_times":10,"ad_percent":"0_100"},"banner_screen":{"status":false,"ad_origin":"gdt_toutiao","times":100,"change_times":300,"ad_percent":"0_100"}},"my_page":{"native_screen":{"status":false,"ad_origin":"gdt_toutiao","times":0,"change_times":10,"ad_percent":"0_100"}},"exit_page":{"native_screen":{"status":false,"ad_origin":"gdt_toutiao","times":5,"change_times":10,"ad_percent":"0_100"}},"setting_page":{"incentive_video":{"status":false,"ad_origin":"gdt_toutiao","times":5,"change_times":10,"ad_percent":"0_100"}},"Advertisement":{"kTouTiaoAppKey":"5122509","kTouTiaoKaiPing":"887406868","kTouTiaoBannerKey":"945636720","kTouTiaoChaPingKey":"945636721","kTouTiaoJiLiKey":"945636723","kTouTiaoSeniorKey":"945636718","kGDTMobSDKAppKey":"1111238878","kGDTMobSDKChaPingKey":"8051343358666012","kGDTMobSDKKaiPingKey":"6071640378059906","kGDTMobSDKBannerKey":"2011843338769080","kGDTMobSDKNativeKey":"5061546308954968","kGDTMobSDKJiLiKey":"9011047308351959"}}
     */

    private int code;
    private String message;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * start_page : {"spread_screen":{"status":false,"ad_origin":"gdt_toutiao","times":1,"change_times":300,"ad_percent":"0_100"}}
         * home_page : {"native_screen":{"status":false,"ad_origin":"gdt_toutiao","times":0,"change_times":3,"ad_percent":"0_100"},"insert_screen":{"status":false,"ad_origin":"gdt_toutiao","times":300,"change_times":3,"ad_percent":"0_100"},"banner_screen":{"status":false,"ad_origin":"gdt_toutiao","times":100,"change_times":300,"ad_percent":"0_100"}}
         * almanac_page : {"native_screen":{"status":false,"ad_origin":"gdt_toutiao","times":0,"change_times":3,"ad_percent":"0_100"},"insert_screen":{"status":false,"ad_origin":"gdt_toutiao","times":300,"change_times":3,"ad_percent":"0_100"},"banner_screen":{"status":false,"ad_origin":"gdt_toutiao","times":100,"change_times":300,"ad_percent":"0_100"}}
         * today_in_history_page : {"native_screen":{"status":false,"ad_origin":"gdt_toutiao","times":0,"change_times":10,"ad_percent":"0_100"},"banner_screen":{"status":false,"ad_origin":"gdt_toutiao","times":100,"change_times":300,"ad_percent":"0_100"}}
         * my_page : {"native_screen":{"status":false,"ad_origin":"gdt_toutiao","times":0,"change_times":10,"ad_percent":"0_100"}}
         * exit_page : {"native_screen":{"status":false,"ad_origin":"gdt_toutiao","times":5,"change_times":10,"ad_percent":"0_100"}}
         * setting_page : {"incentive_video":{"status":false,"ad_origin":"gdt_toutiao","times":5,"change_times":10,"ad_percent":"0_100"}}
         * Advertisement : {"kTouTiaoAppKey":"5122509","kTouTiaoKaiPing":"887406868","kTouTiaoBannerKey":"945636720","kTouTiaoChaPingKey":"945636721","kTouTiaoJiLiKey":"945636723","kTouTiaoSeniorKey":"945636718","kGDTMobSDKAppKey":"1111238878","kGDTMobSDKChaPingKey":"8051343358666012","kGDTMobSDKKaiPingKey":"6071640378059906","kGDTMobSDKBannerKey":"2011843338769080","kGDTMobSDKNativeKey":"5061546308954968","kGDTMobSDKJiLiKey":"9011047308351959"}
         */

        private StartPageBean start_page;
        private HomePageBean home_page;
        private AlmanacPageBean almanac_page;
        private TodayInHistoryPageBean today_in_history_page;
        private MyPageBean my_page;
        private ExitPageBean exit_page;
        private SettingPageBean setting_page;
        private AdvertisementBean Advertisement;

        public StartPageBean getStart_page() {
            return start_page;
        }

        public void setStart_page(StartPageBean start_page) {
            this.start_page = start_page;
        }

        public HomePageBean getHome_page() {
            return home_page;
        }

        public void setHome_page(HomePageBean home_page) {
            this.home_page = home_page;
        }

        public AlmanacPageBean getAlmanac_page() {
            return almanac_page;
        }

        public void setAlmanac_page(AlmanacPageBean almanac_page) {
            this.almanac_page = almanac_page;
        }

        public TodayInHistoryPageBean getToday_in_history_page() {
            return today_in_history_page;
        }

        public void setToday_in_history_page(TodayInHistoryPageBean today_in_history_page) {
            this.today_in_history_page = today_in_history_page;
        }

        public MyPageBean getMy_page() {
            return my_page;
        }

        public void setMy_page(MyPageBean my_page) {
            this.my_page = my_page;
        }

        public ExitPageBean getExit_page() {
            return exit_page;
        }

        public void setExit_page(ExitPageBean exit_page) {
            this.exit_page = exit_page;
        }

        public SettingPageBean getSetting_page() {
            return setting_page;
        }

        public void setSetting_page(SettingPageBean setting_page) {
            this.setting_page = setting_page;
        }

        public AdvertisementBean getAdvertisement() {
            return Advertisement;
        }

        public void setAdvertisement(AdvertisementBean Advertisement) {
            this.Advertisement = Advertisement;
        }

        public static class StartPageBean {
            /**
             * spread_screen : {"status":false,"ad_origin":"gdt_toutiao","times":1,"change_times":300,"ad_percent":"0_100"}
             */

            private SpreadScreenBean spread_screen;

            public SpreadScreenBean getSpread_screen() {
                return spread_screen;
            }

            public void setSpread_screen(SpreadScreenBean spread_screen) {
                this.spread_screen = spread_screen;
            }

            public static class SpreadScreenBean {
                /**
                 * status : false
                 * ad_origin : gdt_toutiao
                 * times : 1
                 * change_times : 300
                 * ad_percent : 0_100
                 */

                private boolean status;
                private String ad_origin;
                private int times;
                private int change_times;
                private String ad_percent;

                public boolean isStatus() {
                    return status;
                }

                public void setStatus(boolean status) {
                    this.status = status;
                }

                public String getAd_origin() {
                    return ad_origin;
                }

                public void setAd_origin(String ad_origin) {
                    this.ad_origin = ad_origin;
                }

                public int getTimes() {
                    return times;
                }

                public void setTimes(int times) {
                    this.times = times;
                }

                public int getChange_times() {
                    return change_times;
                }

                public void setChange_times(int change_times) {
                    this.change_times = change_times;
                }

                public String getAd_percent() {
                    return ad_percent;
                }

                public void setAd_percent(String ad_percent) {
                    this.ad_percent = ad_percent;
                }
            }
        }

        public static class HomePageBean implements IBaseAdBean {
            /**
             * native_screen : {"status":false,"ad_origin":"gdt_toutiao","times":0,"change_times":3,"ad_percent":"0_100"}
             * insert_screen : {"status":false,"ad_origin":"gdt_toutiao","times":300,"change_times":3,"ad_percent":"0_100"}
             * banner_screen : {"status":false,"ad_origin":"gdt_toutiao","times":100,"change_times":300,"ad_percent":"0_100"}
             */

            private NativeScreenBean native_screen;
            private InsertScreenBean insert_screen;
            private BannerScreenBean banner_screen;

            public NativeScreenBean getNative_screen() {
                return native_screen;
            }

            public void setNative_screen(NativeScreenBean native_screen) {
                this.native_screen = native_screen;
            }

            public InsertScreenBean getInsert_screen() {
                return insert_screen;
            }

            public void setInsert_screen(InsertScreenBean insert_screen) {
                this.insert_screen = insert_screen;
            }

            public BannerScreenBean getBanner_screen() {
                return banner_screen;
            }

            public void setBanner_screen(BannerScreenBean banner_screen) {
                this.banner_screen = banner_screen;
            }

            @Override
            public IBaseXXBean getBaseBanner_screen() {
                return banner_screen;
            }

            @Override
            public IBaseXXBean getBaseNative_screen() {
                return native_screen;
            }

            @Override
            public IBaseXXBean getBaseInsert_screen() {
                return native_screen;
            }

            public static class NativeScreenBean implements IBaseXXBean {
                /**
                 * status : false
                 * ad_origin : gdt_toutiao
                 * times : 0
                 * change_times : 3
                 * ad_percent : 0_100
                 */

                private boolean status;
                private String ad_origin;
                private int times;
                private int change_times;
                private String ad_percent;

                public boolean isStatus() {
                    return status;
                }

                public void setStatus(boolean status) {
                    this.status = status;
                }

                public String getAd_origin() {
                    return ad_origin;
                }

                public void setAd_origin(String ad_origin) {
                    this.ad_origin = ad_origin;
                }

                public int getTimes() {
                    return times;
                }

                public void setTimes(int times) {
                    this.times = times;
                }

                public int getChange_times() {
                    return change_times;
                }

                public void setChange_times(int change_times) {
                    this.change_times = change_times;
                }

                public String getAd_percent() {
                    return ad_percent;
                }

                public void setAd_percent(String ad_percent) {
                    this.ad_percent = ad_percent;
                }

                @Override
                public String getBaseAd_percent() {
                    return ad_percent;
                }

                @Override
                public boolean isBaseStatus() {
                    return status;
                }
            }

            public static class InsertScreenBean implements IBaseXXBean {
                /**
                 * status : false
                 * ad_origin : gdt_toutiao
                 * times : 300
                 * change_times : 3
                 * ad_percent : 0_100
                 */

                private boolean status;
                private String ad_origin;
                private int times;
                private int change_times;
                private String ad_percent;

                public boolean isStatus() {
                    return status;
                }

                public void setStatus(boolean status) {
                    this.status = status;
                }

                public String getAd_origin() {
                    return ad_origin;
                }

                public void setAd_origin(String ad_origin) {
                    this.ad_origin = ad_origin;
                }

                public int getTimes() {
                    return times;
                }

                public void setTimes(int times) {
                    this.times = times;
                }

                public int getChange_times() {
                    return change_times;
                }

                public void setChange_times(int change_times) {
                    this.change_times = change_times;
                }

                public String getAd_percent() {
                    return ad_percent;
                }

                public void setAd_percent(String ad_percent) {
                    this.ad_percent = ad_percent;
                }

                @Override
                public String getBaseAd_percent() {
                    return ad_percent;
                }

                @Override
                public boolean isBaseStatus() {
                    return status;
                }
            }

            public static class BannerScreenBean implements IBaseXXBean {
                /**
                 * status : false
                 * ad_origin : gdt_toutiao
                 * times : 100
                 * change_times : 300
                 * ad_percent : 0_100
                 */

                private boolean status;
                private String ad_origin;
                private int times;
                private int change_times;
                private String ad_percent;

                public boolean isStatus() {
                    return status;
                }

                public void setStatus(boolean status) {
                    this.status = status;
                }

                public String getAd_origin() {
                    return ad_origin;
                }

                public void setAd_origin(String ad_origin) {
                    this.ad_origin = ad_origin;
                }

                public int getTimes() {
                    return times;
                }

                public void setTimes(int times) {
                    this.times = times;
                }

                public int getChange_times() {
                    return change_times;
                }

                public void setChange_times(int change_times) {
                    this.change_times = change_times;
                }

                public String getAd_percent() {
                    return ad_percent;
                }

                public void setAd_percent(String ad_percent) {
                    this.ad_percent = ad_percent;
                }

                @Override
                public String getBaseAd_percent() {
                    return ad_percent;
                }

                @Override
                public boolean isBaseStatus() {
                    return status;
                }
            }
        }

        public static class AlmanacPageBean implements IBaseAdBean {
            /**
             * native_screen : {"status":false,"ad_origin":"gdt_toutiao","times":0,"change_times":3,"ad_percent":"0_100"}
             * insert_screen : {"status":false,"ad_origin":"gdt_toutiao","times":300,"change_times":3,"ad_percent":"0_100"}
             * banner_screen : {"status":false,"ad_origin":"gdt_toutiao","times":100,"change_times":300,"ad_percent":"0_100"}
             */

            private NativeScreenBean native_screen;
            private InsertScreenBean insert_screen;
            private BannerScreenBean banner_screen;

            public NativeScreenBean getNative_screen() {
                return native_screen;
            }

            public void setNative_screen(NativeScreenBean native_screen) {
                this.native_screen = native_screen;
            }

            public InsertScreenBean getInsert_screen() {
                return insert_screen;
            }

            public void setInsert_screen(InsertScreenBean insert_screen) {
                this.insert_screen = insert_screen;
            }

            public BannerScreenBean getBanner_screen() {
                return banner_screen;
            }

            public void setBanner_screen(BannerScreenBean banner_screen) {
                this.banner_screen = banner_screen;
            }

            @Override
            public IBaseXXBean getBaseBanner_screen() {
                return banner_screen;
            }

            @Override
            public IBaseXXBean getBaseNative_screen() {
                return native_screen;
            }

            @Override
            public IBaseXXBean getBaseInsert_screen() {
                return insert_screen;
            }

            public static class NativeScreenBean implements IBaseXXBean{
                /**
                 * status : false
                 * ad_origin : gdt_toutiao
                 * times : 0
                 * change_times : 3
                 * ad_percent : 0_100
                 */

                private boolean status;
                private String ad_origin;
                private int times;
                private int change_times;
                private String ad_percent;

                public boolean isStatus() {
                    return status;
                }

                public void setStatus(boolean status) {
                    this.status = status;
                }

                public String getAd_origin() {
                    return ad_origin;
                }

                public void setAd_origin(String ad_origin) {
                    this.ad_origin = ad_origin;
                }

                public int getTimes() {
                    return times;
                }

                public void setTimes(int times) {
                    this.times = times;
                }

                public int getChange_times() {
                    return change_times;
                }

                public void setChange_times(int change_times) {
                    this.change_times = change_times;
                }

                public String getAd_percent() {
                    return ad_percent;
                }

                public void setAd_percent(String ad_percent) {
                    this.ad_percent = ad_percent;
                }

                @Override
                public String getBaseAd_percent() {
                    return ad_percent;
                }

                @Override
                public boolean isBaseStatus() {
                    return status;
                }
            }

            public static class InsertScreenBean implements IBaseXXBean{
                /**
                 * status : false
                 * ad_origin : gdt_toutiao
                 * times : 300
                 * change_times : 3
                 * ad_percent : 0_100
                 */

                private boolean status;
                private String ad_origin;
                private int times;
                private int change_times;
                private String ad_percent;

                public boolean isStatus() {
                    return status;
                }

                public void setStatus(boolean status) {
                    this.status = status;
                }

                public String getAd_origin() {
                    return ad_origin;
                }

                public void setAd_origin(String ad_origin) {
                    this.ad_origin = ad_origin;
                }

                public int getTimes() {
                    return times;
                }

                public void setTimes(int times) {
                    this.times = times;
                }

                public int getChange_times() {
                    return change_times;
                }

                public void setChange_times(int change_times) {
                    this.change_times = change_times;
                }

                public String getAd_percent() {
                    return ad_percent;
                }

                public void setAd_percent(String ad_percent) {
                    this.ad_percent = ad_percent;
                }

                @Override
                public String getBaseAd_percent() {
                    return ad_percent;
                }

                @Override
                public boolean isBaseStatus() {
                    return status;
                }
            }

            public static class BannerScreenBean implements IBaseXXBean{
                /**
                 * status : false
                 * ad_origin : gdt_toutiao
                 * times : 100
                 * change_times : 300
                 * ad_percent : 0_100
                 */

                private boolean status;
                private String ad_origin;
                private int times;
                private int change_times;
                private String ad_percent;

                public boolean isStatus() {
                    return status;
                }

                public void setStatus(boolean status) {
                    this.status = status;
                }

                public String getAd_origin() {
                    return ad_origin;
                }

                public void setAd_origin(String ad_origin) {
                    this.ad_origin = ad_origin;
                }

                public int getTimes() {
                    return times;
                }

                public void setTimes(int times) {
                    this.times = times;
                }

                public int getChange_times() {
                    return change_times;
                }

                public void setChange_times(int change_times) {
                    this.change_times = change_times;
                }

                public String getAd_percent() {
                    return ad_percent;
                }

                public void setAd_percent(String ad_percent) {
                    this.ad_percent = ad_percent;
                }

                @Override
                public String getBaseAd_percent() {
                    return ad_percent;
                }

                @Override
                public boolean isBaseStatus() {
                    return status;
                }
            }
        }

        public static class TodayInHistoryPageBean implements IBaseAdBean{
            /**
             * native_screen : {"status":false,"ad_origin":"gdt_toutiao","times":0,"change_times":10,"ad_percent":"0_100"}
             * banner_screen : {"status":false,"ad_origin":"gdt_toutiao","times":100,"change_times":300,"ad_percent":"0_100"}
             */

            private NativeScreenBean native_screen;
            private BannerScreenBean banner_screen;

            public NativeScreenBean getNative_screen() {
                return native_screen;
            }

            public void setNative_screen(NativeScreenBean native_screen) {
                this.native_screen = native_screen;
            }

            public BannerScreenBean getBanner_screen() {
                return banner_screen;
            }

            public void setBanner_screen(BannerScreenBean banner_screen) {
                this.banner_screen = banner_screen;
            }

            @Override
            public IBaseXXBean getBaseBanner_screen() {
                return banner_screen;
            }

            @Override
            public IBaseXXBean getBaseNative_screen() {
                return native_screen;
            }

            @Override
            public IBaseXXBean getBaseInsert_screen() {
                return native_screen;
            }

            public static class NativeScreenBean implements IBaseXXBean{
                /**
                 * status : false
                 * ad_origin : gdt_toutiao
                 * times : 0
                 * change_times : 10
                 * ad_percent : 0_100
                 */

                private boolean status;
                private String ad_origin;
                private int times;
                private int change_times;
                private String ad_percent;

                public boolean isStatus() {
                    return status;
                }

                public void setStatus(boolean status) {
                    this.status = status;
                }

                public String getAd_origin() {
                    return ad_origin;
                }

                public void setAd_origin(String ad_origin) {
                    this.ad_origin = ad_origin;
                }

                public int getTimes() {
                    return times;
                }

                public void setTimes(int times) {
                    this.times = times;
                }

                public int getChange_times() {
                    return change_times;
                }

                public void setChange_times(int change_times) {
                    this.change_times = change_times;
                }

                public String getAd_percent() {
                    return ad_percent;
                }

                public void setAd_percent(String ad_percent) {
                    this.ad_percent = ad_percent;
                }

                @Override
                public String getBaseAd_percent() {
                    return ad_percent;
                }

                @Override
                public boolean isBaseStatus() {
                    return status;
                }
            }

            public static class BannerScreenBean implements IBaseXXBean {
                /**
                 * status : false
                 * ad_origin : gdt_toutiao
                 * times : 100
                 * change_times : 300
                 * ad_percent : 0_100
                 */

                private boolean status;
                private String ad_origin;
                private int times;
                private int change_times;
                private String ad_percent;

                public boolean isStatus() {
                    return status;
                }

                public void setStatus(boolean status) {
                    this.status = status;
                }

                public String getAd_origin() {
                    return ad_origin;
                }

                public void setAd_origin(String ad_origin) {
                    this.ad_origin = ad_origin;
                }

                public int getTimes() {
                    return times;
                }

                public void setTimes(int times) {
                    this.times = times;
                }

                public int getChange_times() {
                    return change_times;
                }

                public void setChange_times(int change_times) {
                    this.change_times = change_times;
                }

                public String getAd_percent() {
                    return ad_percent;
                }

                public void setAd_percent(String ad_percent) {
                    this.ad_percent = ad_percent;
                }

                @Override
                public String getBaseAd_percent() {
                    return ad_percent;
                }

                @Override
                public boolean isBaseStatus() {
                    return status;
                }
            }
        }

        public static class MyPageBean implements IBaseAdBean{
            /**
             * native_screen : {"status":false,"ad_origin":"gdt_toutiao","times":0,"change_times":10,"ad_percent":"0_100"}
             */

            private NativeScreenBean native_screen;

            public NativeScreenBean getNative_screen() {
                return native_screen;
            }

            public void setNative_screen(NativeScreenBean native_screen) {
                this.native_screen = native_screen;
            }

            @Override
            public IBaseXXBean getBaseBanner_screen() {
                return native_screen;
            }

            @Override
            public IBaseXXBean getBaseNative_screen() {
                return native_screen;
            }

            @Override
            public IBaseXXBean getBaseInsert_screen() {
                return native_screen;
            }

            public static class NativeScreenBean  implements IBaseXXBean{
                /**
                 * status : false
                 * ad_origin : gdt_toutiao
                 * times : 0
                 * change_times : 10
                 * ad_percent : 0_100
                 */

                private boolean status;
                private String ad_origin;
                private int times;
                private int change_times;
                private String ad_percent;

                public boolean isStatus() {
                    return status;
                }

                public void setStatus(boolean status) {
                    this.status = status;
                }

                public String getAd_origin() {
                    return ad_origin;
                }

                public void setAd_origin(String ad_origin) {
                    this.ad_origin = ad_origin;
                }

                public int getTimes() {
                    return times;
                }

                public void setTimes(int times) {
                    this.times = times;
                }

                public int getChange_times() {
                    return change_times;
                }

                public void setChange_times(int change_times) {
                    this.change_times = change_times;
                }

                public String getAd_percent() {
                    return ad_percent;
                }

                public void setAd_percent(String ad_percent) {
                    this.ad_percent = ad_percent;
                }

                @Override
                public String getBaseAd_percent() {
                    return ad_percent;
                }

                @Override
                public boolean isBaseStatus() {
                    return status;
                }
            }
        }

        public static class ExitPageBean implements IBaseAdBean{
            /**
             * native_screen : {"status":false,"ad_origin":"gdt_toutiao","times":5,"change_times":10,"ad_percent":"0_100"}
             */

            private NativeScreenBean native_screen;

            public NativeScreenBean getNative_screen() {
                return native_screen;
            }

            public void setNative_screen(NativeScreenBean native_screen) {
                this.native_screen = native_screen;
            }

            @Override
            public IBaseXXBean getBaseBanner_screen() {
                return native_screen;
            }

            @Override
            public IBaseXXBean getBaseNative_screen() {
                return native_screen;
            }

            @Override
            public IBaseXXBean getBaseInsert_screen() {
                return native_screen;
            }

            public static class NativeScreenBean implements IBaseXXBean{
                /**
                 * status : false
                 * ad_origin : gdt_toutiao
                 * times : 5
                 * change_times : 10
                 * ad_percent : 0_100
                 */

                private boolean status;
                private String ad_origin;
                private int times;
                private int change_times;
                private String ad_percent;

                public boolean isStatus() {
                    return status;
                }

                public void setStatus(boolean status) {
                    this.status = status;
                }

                public String getAd_origin() {
                    return ad_origin;
                }

                public void setAd_origin(String ad_origin) {
                    this.ad_origin = ad_origin;
                }

                public int getTimes() {
                    return times;
                }

                public void setTimes(int times) {
                    this.times = times;
                }

                public int getChange_times() {
                    return change_times;
                }

                public void setChange_times(int change_times) {
                    this.change_times = change_times;
                }

                public String getAd_percent() {
                    return ad_percent;
                }

                public void setAd_percent(String ad_percent) {
                    this.ad_percent = ad_percent;
                }

                @Override
                public String getBaseAd_percent() {
                    return ad_percent;
                }

                @Override
                public boolean isBaseStatus() {
                    return status;
                }
            }
        }

        public static class SettingPageBean implements IBaseAdBean{
            /**
             * incentive_video : {"status":false,"ad_origin":"gdt_toutiao","times":5,"change_times":10,"ad_percent":"0_100"}
             */

            private IncentiveVideoBean incentive_video;

            public IncentiveVideoBean getIncentive_video() {
                return incentive_video;
            }

            public void setIncentive_video(IncentiveVideoBean incentive_video) {
                this.incentive_video = incentive_video;
            }

            @Override
            public IBaseXXBean getBaseBanner_screen() {
                return incentive_video;
            }

            @Override
            public IBaseXXBean getBaseNative_screen() {
                return incentive_video;
            }

            @Override
            public IBaseXXBean getBaseInsert_screen() {
                return incentive_video;
            }

            public static class IncentiveVideoBean implements IBaseXXBean{
                /**
                 * status : false
                 * ad_origin : gdt_toutiao
                 * times : 5
                 * change_times : 10
                 * ad_percent : 0_100
                 */

                private boolean status;
                private String ad_origin;
                private int times;
                private int change_times;
                private String ad_percent;

                public boolean isStatus() {
                    return status;
                }

                public void setStatus(boolean status) {
                    this.status = status;
                }

                public String getAd_origin() {
                    return ad_origin;
                }

                public void setAd_origin(String ad_origin) {
                    this.ad_origin = ad_origin;
                }

                public int getTimes() {
                    return times;
                }

                public void setTimes(int times) {
                    this.times = times;
                }

                public int getChange_times() {
                    return change_times;
                }

                public void setChange_times(int change_times) {
                    this.change_times = change_times;
                }

                public String getAd_percent() {
                    return ad_percent;
                }

                public void setAd_percent(String ad_percent) {
                    this.ad_percent = ad_percent;
                }

                @Override
                public String getBaseAd_percent() {
                    return ad_percent;
                }

                @Override
                public boolean isBaseStatus() {
                    return status;
                }
            }
        }

        public static class AdvertisementBean {
            /**
             * kTouTiaoAppKey : 5122509
             * kTouTiaoKaiPing : 887406868
             * kTouTiaoBannerKey : 945636720
             * kTouTiaoChaPingKey : 945636721
             * kTouTiaoJiLiKey : 945636723
             * kTouTiaoSeniorKey : 945636718
             * kGDTMobSDKAppKey : 1111238878
             * kGDTMobSDKChaPingKey : 8051343358666012
             * kGDTMobSDKKaiPingKey : 6071640378059906
             * kGDTMobSDKBannerKey : 2011843338769080
             * kGDTMobSDKNativeKey : 5061546308954968
             * kGDTMobSDKJiLiKey : 9011047308351959
             */

            private String kTouTiaoAppKey;
            private String kTouTiaoKaiPing;
            private String kTouTiaoBannerKey;
            private String kTouTiaoChaPingKey;
            private String kTouTiaoJiLiKey;
            private String kTouTiaoSeniorKey;
            private String kGDTMobSDKAppKey;
            private String kGDTMobSDKChaPingKey;
            private String kGDTMobSDKKaiPingKey;
            private String kGDTMobSDKBannerKey;
            private String kGDTMobSDKNativeKey;
            private String kGDTMobSDKJiLiKey;

            public String getKTouTiaoAppKey() {
                return kTouTiaoAppKey;
            }

            public void setKTouTiaoAppKey(String kTouTiaoAppKey) {
                this.kTouTiaoAppKey = kTouTiaoAppKey;
            }

            public String getKTouTiaoKaiPing() {
                return kTouTiaoKaiPing;
            }

            public void setKTouTiaoKaiPing(String kTouTiaoKaiPing) {
                this.kTouTiaoKaiPing = kTouTiaoKaiPing;
            }

            public String getKTouTiaoBannerKey() {
                return kTouTiaoBannerKey;
            }

            public void setKTouTiaoBannerKey(String kTouTiaoBannerKey) {
                this.kTouTiaoBannerKey = kTouTiaoBannerKey;
            }

            public String getKTouTiaoChaPingKey() {
                return kTouTiaoChaPingKey;
            }

            public void setKTouTiaoChaPingKey(String kTouTiaoChaPingKey) {
                this.kTouTiaoChaPingKey = kTouTiaoChaPingKey;
            }

            public String getKTouTiaoJiLiKey() {
                return kTouTiaoJiLiKey;
            }

            public void setKTouTiaoJiLiKey(String kTouTiaoJiLiKey) {
                this.kTouTiaoJiLiKey = kTouTiaoJiLiKey;
            }

            public String getKTouTiaoSeniorKey() {
                return kTouTiaoSeniorKey;
            }

            public void setKTouTiaoSeniorKey(String kTouTiaoSeniorKey) {
                this.kTouTiaoSeniorKey = kTouTiaoSeniorKey;
            }

            public String getKGDTMobSDKAppKey() {
                return kGDTMobSDKAppKey;
            }

            public void setKGDTMobSDKAppKey(String kGDTMobSDKAppKey) {
                this.kGDTMobSDKAppKey = kGDTMobSDKAppKey;
            }

            public String getKGDTMobSDKChaPingKey() {
                return kGDTMobSDKChaPingKey;
            }

            public void setKGDTMobSDKChaPingKey(String kGDTMobSDKChaPingKey) {
                this.kGDTMobSDKChaPingKey = kGDTMobSDKChaPingKey;
            }

            public String getKGDTMobSDKKaiPingKey() {
                return kGDTMobSDKKaiPingKey;
            }

            public void setKGDTMobSDKKaiPingKey(String kGDTMobSDKKaiPingKey) {
                this.kGDTMobSDKKaiPingKey = kGDTMobSDKKaiPingKey;
            }

            public String getKGDTMobSDKBannerKey() {
                return kGDTMobSDKBannerKey;
            }

            public void setKGDTMobSDKBannerKey(String kGDTMobSDKBannerKey) {
                this.kGDTMobSDKBannerKey = kGDTMobSDKBannerKey;
            }

            public String getKGDTMobSDKNativeKey() {
                return kGDTMobSDKNativeKey;
            }

            public void setKGDTMobSDKNativeKey(String kGDTMobSDKNativeKey) {
                this.kGDTMobSDKNativeKey = kGDTMobSDKNativeKey;
            }

            public String getKGDTMobSDKJiLiKey() {
                return kGDTMobSDKJiLiKey;
            }

            public void setKGDTMobSDKJiLiKey(String kGDTMobSDKJiLiKey) {
                this.kGDTMobSDKJiLiKey = kGDTMobSDKJiLiKey;
            }
        }
    }
}
