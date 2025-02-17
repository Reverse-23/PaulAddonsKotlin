/*
 * Paul Addons - Hypixel Skyblock QOL Mod
 * Copyright (C) 2023  AtroxEGO
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */


package me.atroxego.pauladdons.features.kuudra

import PaulAddons
import PaulAddons.Companion.prefix
import me.atroxego.pauladdons.gui.GuiElement
import me.atroxego.pauladdons.render.RenderUtils
import me.atroxego.pauladdons.utils.Utils.addMessage
import me.atroxego.pauladdons.utils.Utils.stripColor
import net.minecraft.client.Minecraft
import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.event.RenderWorldLastEvent
import net.minecraftforge.event.world.WorldEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

object ChaosmiteCounter {
    init {
        ChaosmiteCounterGuiElement()
    }
    var chaosmiteAmount = 0
    class ChaosmiteCounterGuiElement : GuiElement("Chaosmite Counter"){
        override fun render() {
            if (toggled){
                RenderUtils.renderTexture(ResourceLocation("pauladdons/endermiteBasic.png"), 1, 0)
                fr.drawString(chaosmiteAmount.toString(), 19f, 5f, 0xFFFFFF, true)
            }
        }

        override fun demoRender() {
            RenderUtils.renderTexture(ResourceLocation("pauladdons/endermiteBasic.png"), 1, 0)
//            FontUtils.smartTexturePlacement(0f, this, "pauladdons/endermiteBasic.png", "pauladdons/endermiteMirror.png")
            fr.drawString("0", 19f, 5f, 0xFFFFFF, true)
        }



        override val toggled: Boolean
            get() = chaosCounter
        override val height: Int
            get() = fr.FONT_HEIGHT
        override val width: Int
            get() = 20 + fr.getStringWidth("0")

        init {
            PaulAddons.guiManager.registerElement(this)
        }
    }
    var chaosCounter = false
    @SubscribeEvent
    fun onWorldLoad(event: WorldEvent.Load){
        if (chaosmiteAmount != 0) addMessage("$prefix Last run §6§l$chaosmiteAmount§r Chaosmites spawned")
        seenChaosmites.clear()
        chaosmiteAmount = 0
    }

    var seenChaosmites = arrayListOf<Int>()

    @SubscribeEvent
    fun onWorldRender(event: RenderWorldLastEvent){
        val world = Minecraft.getMinecraft().theWorld
        val entityList = world.loadedEntityList
        for (entity in entityList){
            if (!entity.hasCustomName()) continue
            if (!entity.customNameTag.stripColor().contains("Chaosmite")) continue
            if (seenChaosmites.contains(entity.entityId)) continue
            seenChaosmites.add(entity.entityId)
            chaosmiteAmount++
        }
    }
}