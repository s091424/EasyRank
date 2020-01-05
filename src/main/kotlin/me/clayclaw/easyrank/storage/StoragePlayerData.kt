package me.clayclaw.easyrank.storage

class StoragePlayerData {

    var players : ArrayList<PlayerRankDataProfile> = arrayListOf()

    class PlayerRankDataProfile {

        var id : String = "unknown"

        var selectedRank : String = "unknown"

    }

}
