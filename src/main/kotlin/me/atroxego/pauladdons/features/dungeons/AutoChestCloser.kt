package me.atroxego.pauladdons.features.dungeons

import PaulAddons.Companion.mc
import me.atroxego.pauladdons.config.Config
import me.atroxego.pauladdons.utils.Utils
import me.atroxego.pauladdons.utils.Utils.getGuiName
import net.minecraft.client.gui.inventory.GuiChest
import net.minecraftforge.client.event.GuiScreenEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent


object AutoChestCloser {

    @SubscribeEvent
    fun onGuiBackgroundRender(event: GuiScreenEvent.BackgroundDrawnEvent) {
        if (event.gui is GuiChest && Utils.inSkyblock) {
            if (Utils.inDungeon && Config.autoCloseChest && getGuiName(event.gui).equals("Chest")) {
                mc.thePlayer.closeScreen()
//                mc.netHandler.networkManager.sendPacket(C0DPacketCloseWindow(mc.thePlayer.openContainer.windowId))
//                mc.netHandler.networkManager.sendPacket(C0DPacketCloseWindow((event.gui as GuiChest).inventorySlots.windowId))
            }
        }
    }


//    @SubscribeEvent
//    fun onPlayerInteract(event: PlayerInteractEvent){
//        mc.thePlayer.sendChatMessage(SBInfo.lastOpenContainerName)
//            if (!Config.autoCloseChest) return
//            if (!Utils.inDungeon) return
//        if(event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK){
//            val block = mc.thePlayer.worldObj.getChunkFromBlockCoords(event.pos).getBlock(event.pos)
//            if (block == Blocks.chest || block == Blocks.trapped_chest){
//                mc.thePlayer.sendChatMessage("closing")
//                mc.thePlayer.closeScreen()
//            }
//
//        }
//    }

//    @SubscribeEvent
//    fun onPacket(event: PacketEvent.ReceiveEvent){
//        if (!Config.autoCloseChest || event.packet !is S2DPacketOpenWindow || !Utils.inDungeon || event.packet.windowTitle.unformattedText.stripColor() != "Chest") return
//        event.cancel()
//        mc.netHandler.networkManager.sendPacket(C0DPacketCloseWindow(event.packet.windowId))
//        mc.thePlayer.closeScreen()
//    }
}