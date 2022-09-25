package com.esportsarena.esafzzymod

import com.esportsarena.esafzzymod.util.TimeFormat
import com.mojang.blaze3d.platform.GlStateManager
import com.mojang.blaze3d.systems.RenderSystem
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawableHelper
import net.minecraft.util.Identifier
import org.lwjgl.opengl.GL11
import java.awt.Color

@Suppress("UNUSED")
object ESAFzzyMod : ModInitializer {
    private const val MOD_ID = "esafzzymod"

    private var currentTime = 0L
    private var timerRunning = true
    override fun onInitialize() {
        println("Example mod has been initialized.")

        ClientTickEvents.START_WORLD_TICK.register(ClientTickEvents.StartWorldTick { world ->
            run {
                if (world.time < 20) timerRunning = true
                if (timerRunning) currentTime = world.time - (20 * 2)
                for (item in MinecraftClient.getInstance().player!!.inventory.main) {
                    if (item.registryEntry.key.get().value.toString() == "minecraft:diamond") {
                        timerRunning = false
                    }
                }
            }
        })

        HudRenderCallback.EVENT.register(HudRenderCallback { matrixStack, tickDelta ->
            run {
                val client = MinecraftClient.getInstance()
                matrixStack.push()
                matrixStack.scale(2F, 2F, 2F)
                val size =
                    client.textRenderer.draw(
                        matrixStack,
                        TimeFormat.timerFormat(currentTime * 50, timerRunning),
                        27F,
                        10F,
                        if (timerRunning) Color.WHITE.rgb else Color.GREEN.rgb
                    )
                DrawableHelper.fill(matrixStack, 0, 0, 10 + size, 27, Color(0, 0, 0, 100).rgb)
                matrixStack.scale(0.5F, 0.5F, 0.5F)

                RenderSystem.setShaderFogColor(1F, 1F, 1F, 1F)
                client.textureManager.bindTexture(Identifier(MOD_ID, "textures/gui/esalogo.png"))
                RenderSystem.setShaderTexture(0, Identifier(MOD_ID, "textures/gui/esalogo.png"))
                DrawableHelper.drawTexture(matrixStack, 10, 10, 0F, 0F, 32, 35, 32, 35)

                matrixStack.scale(2F, 2F, 2F)
                client.textRenderer.draw(
                    matrixStack,
                    TimeFormat.timerFormat(currentTime * 50, timerRunning),
                    27F,
                    10F,
                    if (timerRunning) Color.WHITE.rgb else Color.GREEN.rgb
                )
                matrixStack.pop()
            }
        })
    }
}