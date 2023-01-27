package me.atroxego.pauladdons.features.dungeons

import PaulAddons.Companion.mc
import me.atroxego.pauladdons.config.Config
import me.atroxego.pauladdons.utils.Utils.inDungeon
import net.minecraft.potion.Potion
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent

object RemoveBlindness {

    @SubscribeEvent
    fun removeBlindness(event: PlayerTickEvent){
        if(!Config.removeBlindness) return
        if(!inDungeon) return
        mc.thePlayer.removePotionEffectClient(Potion.blindness.id)
    }
}