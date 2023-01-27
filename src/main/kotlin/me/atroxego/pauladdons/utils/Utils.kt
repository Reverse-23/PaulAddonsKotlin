package me.atroxego.pauladdons.utils

import PaulAddons.Companion.mc
import gg.essential.universal.wrappers.message.UTextComponent
import me.atroxego.pauladdons.events.impl.MainReceivePacketEvent
import me.atroxego.pauladdons.events.impl.PacketEvent
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiScreen
import net.minecraft.client.gui.inventory.GuiChest
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.EnumPlayerModelParts
import net.minecraft.inventory.ContainerChest
import net.minecraft.item.ItemStack
import net.minecraft.network.play.server.S02PacketChat
import net.minecraft.util.ChatComponentText
import net.minecraft.util.EnumChatFormatting
import net.minecraft.util.MathHelper
import net.minecraftforge.client.event.ClientChatReceivedEvent
import net.minecraftforge.common.MinecraftForge
import java.io.File
import java.util.*


object Utils {
    private val STRIP_COLOR_PATTERN = Regex("(?i)§[\\dA-FK-OR]")

    var skyblock = false

    val inSkyblock: Boolean
//        get() = true
        get() = SBInfo.mode == "SKYBLOCK" || skyblock
    val inDungeon
        get() = SBInfo.mode == "dungeon"

    fun sendItemTags(){
        val tags = mc.thePlayer.heldItem.tagCompound
        mc.thePlayer.addChatMessage(ChatComponentText("$tags"))
    }

    public fun getGuiName(gui: GuiScreen?): String? {
        return if (gui is GuiChest) {
            (gui.inventorySlots as ContainerChest).lowerChestInventory.displayName.unformattedText
        } else ""
    }

    fun getItemLore(itemStack: ItemStack): List<String?> {
        val NBT_INTEGER = 3
        val NBT_STRING = 8
        val NBT_LIST = 9
        val NBT_COMPOUND = 10
        if (itemStack.hasTagCompound() && itemStack.tagCompound.hasKey("display", NBT_COMPOUND)) {
            val display = itemStack.tagCompound.getCompoundTag("display")
            if (display.hasKey("Lore", NBT_LIST)) {
                val lore = display.getTagList("Lore", NBT_STRING)
                val loreAsList: MutableList<String> = ArrayList()
                for (lineNumber in 0 until lore.tagCount()) {
                    loreAsList.add(lore.getStringTagAt(lineNumber))
                }
                return Collections.unmodifiableList(loreAsList)
            }
        }
        return Collections.emptyList()
    }
    fun scoreboardData(){
        if(getScoreboardLines()[3].contains("F7") || getScoreboardLines()[3].contains("M7")) addMessage("True")
//        for (line in getScoreboardLines()){
//            mc.thePlayer.addChatMessage(ChatComponentText(line))
//        }
    }

    fun addMessage(message: String){
        mc.thePlayer.addChatMessage(ChatComponentText(message))
    }

    fun getScoreboardLines(): List<String> {
        val lines = ScoreboardUtil.fetchScoreboardLines().map { it.stripControlCodes() }
        return lines
    }

    fun String?.stripControlCodes(): String = UTextComponent.stripFormatting(this ?: "")

    fun checkThreadAndQueue(run: () -> Unit) {
        if (!mc.isCallingFromMinecraftThread) {
            mc.addScheduledTask(run)
        } else run()
    }

    fun cancelChatPacket(ReceivePacketEvent: PacketEvent.ReceiveEvent) {
        if (ReceivePacketEvent.packet !is S02PacketChat) return
        ReceivePacketEvent.isCanceled = true
        val packet = ReceivePacketEvent.packet
        checkThreadAndQueue {
            MinecraftForge.EVENT_BUS.post(MainReceivePacketEvent(mc.netHandler, ReceivePacketEvent.packet))
            MinecraftForge.EVENT_BUS.post(ClientChatReceivedEvent(packet.type, packet.chatComponent))
        }
    }

    fun interpolateRotation(par1: Float, par2: Float, par3: Float): Float {
        var f: Float = par2 - par1
        while (f < -180.0f) {
            f += 360.0f
        }
        while (f >= 180.0f) {
            f -= 360.0f
        }
        return par1 + par3 * f
    }

    fun rotateCorpse(bat: EntityLivingBase, p_77043_2_: Float, p_77043_3_: Float, partialTicks: Float) {
        GlStateManager.rotate(180.0f - p_77043_3_, 0.0f, 1.0f, 0.0f)
        if (bat.deathTime > 0) {
            var f: Float = (bat.deathTime.toFloat() + partialTicks - 1.0f) / 20.0f * 1.6f
            f = MathHelper.sqrt_float(f)
            if (f > 1.0f) {
                f = 1.0f
            }
            GlStateManager.rotate(f * 90.0f, 0.0f, 0.0f, 1.0f)
        } else {
            val s = EnumChatFormatting.getTextWithoutFormattingCodes(bat.getName())
            if (s != null && s == "Dinnerbone" || s == "Grumm" && (bat !is EntityPlayer || (bat as EntityPlayer).isWearing(
                    EnumPlayerModelParts.CAPE
                ))
            ) {
                GlStateManager.translate(0.0f, bat.height + 0.1f, 0.0f)
                GlStateManager.rotate(180.0f, 0.0f, 0.0f, 1.0f)
            }
        }
    }
    fun getRenderPartialTicks(): Float {
        val minecraft = Minecraft.getMinecraft()
        try {
            val timerField = Minecraft::class.java.getDeclaredField("timer")
            timerField.isAccessible = true
            val timer = timerField.get(minecraft)
            val renderPartialTicksField = timer::class.java.getDeclaredField("renderPartialTicks")
            renderPartialTicksField.isAccessible = true
            return renderPartialTicksField.getFloat(timer)
        } catch (e: NoSuchFieldException) {
            // Handle the exception
        } catch (e: IllegalAccessException) {
            // Handle the exception
        }
        return 0.0f
    }

    @JvmStatic
    fun String.stripColor(): String = STRIP_COLOR_PATTERN.replace(this, "")

    fun File.ensureFile() = (parentFile.exists() || parentFile.mkdirs()) && createNewFile()
    }


