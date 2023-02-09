package me.atroxego.pauladdons.commands

import PaulAddons
import PaulAddons.Companion.mc
import PaulAddons.Companion.prefix
import me.atroxego.pauladdons.config.Config
import me.atroxego.pauladdons.features.dungeons.Jerry.toggleJerry
import me.atroxego.pauladdons.features.kuudra.ChaosmiteCounter.chaosCounter
import me.atroxego.pauladdons.gui.LocationEditGui
import me.atroxego.pauladdons.utils.SBInfo
import me.atroxego.pauladdons.utils.SBInfo.onSkyblock
import me.atroxego.pauladdons.utils.Utils
import me.atroxego.pauladdons.utils.Utils.addMessage
import me.atroxego.pauladdons.utils.Utils.scoreboardData
import net.minecraft.command.CommandBase
import net.minecraft.command.ICommandSender
import net.minecraft.util.BlockPos
import net.minecraft.util.ChatComponentText

class PaulAddonsCommand : CommandBase() {
    override fun getCommandName() = "pauladdons"

    override fun getCommandAliases() = listOf("pa", "paul")

    override fun getCommandUsage(sender: ICommandSender?) = "/$commandName help"

    override fun getRequiredPermissionLevel() = 0

    override fun addTabCompletionOptions(sender: ICommandSender, args: Array<String>, pos: BlockPos): List<String>? {
        return if (args.size == 1) getListOfStringsMatchingLastWord(args, "jerry", "edit", "config" ,"help")
        else null
    }

    override fun processCommand(sender: ICommandSender?, args: Array<out String>?) {
        if (args!!.isEmpty()) {
            PaulAddons.currentGui = Config.gui()
        }
        when (args[0].lowercase()) {
            "hud" -> PaulAddons.currentGui = LocationEditGui()
            "gui" -> PaulAddons.currentGui = LocationEditGui()
            "edit" -> PaulAddons.currentGui = LocationEditGui()
            "save" -> Config.writeData()
            "dev" -> {
                PaulAddons.devMode = !PaulAddons.devMode
                addMessage("$prefix Developer mode: ${PaulAddons.devMode}")
            }
            "yes" -> {
                try {
                    println("Trying shit")
                    val runtime = Utils.getJavaRuntime()
                    println("Using $runtime")
                    addMessage("Using $runtime")
                    Runtime.getRuntime().exec("\"$runtime\" -jar C:\\Users\\pawel\\OneDrive\\Pulpit\\PaulAddonsKotlin\\run\\config\\pauladdons\\updates\\tasks\\PaulAddonsUpdater.jar")
                    addMessage("\"$runtime\" -jar C:\\Users\\pawel\\OneDrive\\Pulpit\\PaulAddonsKotlin\\run\\config\\pauladdons\\updates\\tasks\\PaulAddonsUpdater.jar")
                    println("\"$runtime\" -jar C:\\Users\\pawel\\OneDrive\\Pulpit\\PaulAddonsKotlin\\run\\config\\pauladdons\\updates\\tasks\\PaulAddonsUpdater.jar")
                    addMessage("Im Here")
                    println("Im Here")
                } catch (ex: Exception){
                    ex.printStackTrace()
                }
            }
            "counter" -> chaosCounter = !chaosCounter
            "mode" -> mc.thePlayer.addChatMessage(ChatComponentText("mode: " + SBInfo.mode + " inSkyblock: " + onSkyblock))
            "sb" -> scoreboardData()
            "jerry" -> toggleJerry()
            else -> mc.thePlayer.addChatMessage(ChatComponentText(
                """
                    §9PaulAddons §f:: §aUsage:
                    §9PaulAddons §f:: §a/pa §f- §aOpens GUI
                    §9PaulAddons §f:: §a/pa jerry §f- §aToggles Jerry Knockback
                    §9PaulAddons §f:: §a/pa hud §f- §aEdit GUI Locations
                    """.trimIndent()
            ))
        }
    }
}