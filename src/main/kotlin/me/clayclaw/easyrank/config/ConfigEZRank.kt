package me.clayclaw.easyrank.config

class ConfigEZRank {

    var UUIDMode : Boolean = true
    var Locale : ConfigLocale = ConfigLocale()

    class ConfigLocale {
        var UI_Name : String = "稱號一覽"
        var UI_PreviousPage : String = "上一頁"
        var UI_NextPage : String = "下一頁"
        var UI_PlayerDisplay_Name : String = "&c{player} 的稱號表"
        var UI_PageNumber : String = "&c第{page}頁"
    }

}
