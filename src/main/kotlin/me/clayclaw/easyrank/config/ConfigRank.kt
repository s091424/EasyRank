package me.clayclaw.easyrank.config

class ConfigRank {

    var profileMap : HashMap<String, RankProfile> = hashMapOf()

    class RankProfile {
        var name : String = "unknown"
        var displayMaterial : String = "STONE"
        var displayLore : List<String> = listOf()
        var attributes : List<String> = listOf()
    }

}
