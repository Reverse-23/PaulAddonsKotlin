package me.atroxego.pauladdons.config

import PaulAddons
import gg.essential.vigilance.Vigilant
import gg.essential.vigilance.data.Category
import gg.essential.vigilance.data.SortingBehavior
import me.atroxego.pauladdons.gui.LocationEditGui
import java.awt.Color
import java.io.File


object Config : Vigilant(
    File(PaulAddons.configDirectory, "config.toml"),
    "Paul Addons",
    sortingBehavior = ConfigSorting()
) {
    var betterLootShare = false
    var glowOnMob = false
    var glowColor : Color = Color.BLUE
    var disableVisible = false
    var starCultTimer = false
    var espSelector = 2
    var mobNotification = false
    var customESPMobs = "MobOne, MobTwo"
    var thunderNotification = false
    var jawbusNotification = false
    var gwSharkNotification = false
    var hydraNotification = false
    var grimNotification = false
    var empNotification = false
    var nutterNotification = false
    var yetiNotification = false
    var espOnNotifiedMobs = false
    var gcStarCultNofification = false
    var cStarCultNotification = false
    var screenStarCultNotification = false
    var autoFriendHi = false
    var autoHiFriends = "PlayerOne, PlayerTwo"
    var autoFriendHiCooldown = 3
    var autoFriendHiType = 0
    var autoHiCustomCommand = "/msg [IGN] Hi [IGN]!"
    var autoGuildHi = false
    var lastGuildHi = 0
    var autoGuildHiCustomMessage = "Hi Guild!"
    var autoGuildHiFrequency = 0
    var autoThankYou = false
    var thankYouMessage = "Thank you [IGN]! <3"

    init {
        category("Better Loot Share") {
            subcategory("Better Loot Share") {
                switch(
                    Config::betterLootShare,
                    name = "Better Loot Share"
                )
            }
            subcategory("ESP Options") {
                switch(
                    Config::glowOnMob,
                    name = "ESP",
                    description = "Entity Glowing On Mobs",
                )
                switch(
                    Config::espOnNotifiedMobs,
                    name = "ESP On Notified Mobs",
                    description = "Renders ESP on mobs that were selected in Mob Notification subcategory"
                )
                text(
                    Config::customESPMobs,
                    name = "Custom ESP Mobs",
                    description = "Type names of mobs separated by ', '"
                )
                selector(
                    Config::espSelector,
                    name = "Type Of ESP",
                    description = "Changes the type of ESP",
                    options = listOf("Chams", "Box", "Outline"),
                )
                color(
                    Config::glowColor,
                    name = "Glow Color",
                )
                switch(
                    Config::disableVisible,
                    name = "Disable on Visible",
                    description = "Disables ESP Rendering On Mobs Visible By Player",

                    )
            }
            subcategory("Mob Notification"){
                switch(
                    Config::mobNotification,
                    name = "Display Notification",
                    description = "Displays notification on the screen when mob spawns",
                )
                switch(
                    Config::thunderNotification,
                    name = "Thunder Notification",
                )
                switch(
                    Config::jawbusNotification,
                    name = "Jawbus Notification",
                )
                switch(
                    Config::gwSharkNotification,
                    name = "Great White Shark Notification",
                )
                switch(
                    Config::hydraNotification,
                    name = "Hydra Notification",
                )
                switch(
                    Config::grimNotification,
                    name = "Grim Reaper Notification",
                )
                switch(
                    Config::empNotification,
                    name = "Sea Emperior Notification",
                )
                switch(
                    Config::nutterNotification,
                    name = "Nutcracker Notification",
                )
                switch(
                    Config::yetiNotification,
                    name = "Yeti Notification",
                )

            }
        }

        category("Star Cult") {
            switch(
                Config::starCultTimer,
                name = "Star Cult Timer",
                description = "Turns On and Off Cult Timer",
            )
            subcategory("Notification Options"){
                switch(
                    Config::cStarCultNotification,
                    name = "Chat Notification",
                    description = "Displays a message in chat about star cult being active"
                )
                switch(
                    Config::gcStarCultNofification,
                    name = "Guild Chat Notification",
                    description = "Sends a text in guild chat about star cult being active"
                )
                switch(
                    Config::screenStarCultNotification,
                    name = "Screen Notification",
                    description = "Displays notification on the screen about star cult being active"
                )
            }

        }
        category("Auto Hi"){
//            subcategory("Auto Hi"){
                switch(
                    Config::autoFriendHi,
                    name = "Auto Friend Hi",
                    description = "Automatically sends Hi message to selected friends when they join"
                )
                switch(
                    Config::autoGuildHi,
                    name = "Auto Guild Hi",
                    description = "Automatically sends Hi message to guild"
                )
//            }
            subcategory("Auto Friend Hi Options"){
                text(
                    Config::autoHiFriends,
                    name = "Auto Hi Friends",
                    description = "Type IGN's of friends separated by ', '"
                )
                slider(
                    Config::autoFriendHiCooldown,
                    name = "Auto Hi Cooldown",
                    description = "Cooldown between messages per player in seconds",
                    min = 0,
                    max = 600,
                )
                selector(
                    Config::autoFriendHiType,
                    name = "Message Type",
                    description = "Change Between MSG and Boop",
                    options = listOf("Msg", "Boop")
                )
                text(
                    Config::autoHiCustomCommand,
                    name = "Custom Command",
                    description = "Type custom command, for ign use [IGN], leave empty for default"
                )
            }
            subcategory("Auto Guild Hi Options"){
                text(
                    Config::autoGuildHiCustomMessage,
                    name = "Custom Guild Hi Message",
                )
                selector(
                    Config::autoGuildHiFrequency,
                    name = "Auto Guild Hi Frequency",
                    options = listOf("Once Per Day", "Every Skyblock Join")
                )
                slider(
                    Config::lastGuildHi,
                    name = "Last Guild Hi",
                    min = 0,
                    max = 31,
//                    hidden = true
                )
            }
        }
        category("Auto Thank You"){
            switch(
                Config::autoThankYou,
                name = "Auto Thank You",
                description = "Automatically thanks for a splash :D"
            )
            subcategory("Auto Thank You Options"){
                text(
                    Config::thankYouMessage,
                    name = "Thank You Message",
                    description = "For Splashers IGN use [IGN]"
                )
            }
        }
        category("GUI Locations"){
            button(
                name = "Edit GUI Locations",
                description = "Also /pa gui",
                buttonText = "Edit",
            ) {
                PaulAddons.currentGui = LocationEditGui()
            }
        }
        addDependency(Config::mobNotification, Config::betterLootShare)
        addDependency(Config::glowColor, Config::glowOnMob)
        addDependency(Config::espSelector, Config::glowOnMob)
        addDependency(Config::disableVisible, Config::glowOnMob)
        addDependency(Config::gcStarCultNofification, Config::starCultTimer)
        addDependency(Config::cStarCultNotification, Config::starCultTimer)
        addDependency(Config::screenStarCultNotification, Config::starCultTimer)
        addDependency(Config::customESPMobs, Config::glowOnMob)
        addDependency(Config::glowOnMob, Config::betterLootShare)
        addDependency(Config::espOnNotifiedMobs, Config::glowOnMob)
        addDependency(Config::thunderNotification, Config::mobNotification)
        addDependency(Config::thunderNotification, Config::mobNotification)
        addDependency(Config::jawbusNotification, Config::mobNotification)
        addDependency(Config::gwSharkNotification, Config::mobNotification)
        addDependency(Config::hydraNotification, Config::mobNotification)
        addDependency(Config::grimNotification, Config::mobNotification)
        addDependency(Config::empNotification , Config::mobNotification)
        addDependency(Config::yetiNotification, Config::mobNotification)
        addDependency(Config::nutterNotification, Config::mobNotification)
        addDependency(Config::autoHiFriends, Config::autoFriendHi)
        addDependency(Config::autoFriendHiType, Config::autoFriendHi)
        addDependency(Config::autoHiCustomCommand, Config::autoFriendHi)
        addDependency(Config::autoFriendHiCooldown, Config::autoFriendHi)
        addDependency(Config::autoGuildHiCustomMessage, Config::autoGuildHi)
        addDependency(Config::autoGuildHiFrequency, Config::autoGuildHi)
        addDependency(Config::thankYouMessage, Config::autoThankYou)
        markDirty()
    }

    private class ConfigSorting : SortingBehavior() {
        private val categories = listOf(
            "Better Loot Share",
            "Star Cult",
            "Auto Hi",
            "Auto Thank You",
            "GUI Locations"
        )
        override fun getCategoryComparator(): Comparator<in Category> =
            Comparator.comparingInt { category: Category -> categories.indexOf(category.name) }
    }

}