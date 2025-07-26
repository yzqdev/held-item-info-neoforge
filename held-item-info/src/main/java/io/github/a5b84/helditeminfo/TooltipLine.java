package io.github.a5b84.helditeminfo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;

public class TooltipLine {
   public final Component text;
   public final int width;

   public TooltipLine(Font textRenderer, Component text) {
      this.text = text;
      this.width = textRenderer.width(text);
   }

   public static List<TooltipLine> from(List<Component> texts) {
      List<TooltipLine> result = new ArrayList<>(texts.size());
      Font textRenderer = Minecraft.getInstance().font;

      for(Component text : texts) {
         result.add(new TooltipLine(textRenderer, text));
      }

      return result;
   }

   public static boolean areEquivalent(List<TooltipLine> lines, List<Component> texts) {
      if (lines.size() != texts.size()) {
         return false;
      } else {
         Iterator<TooltipLine> lineIt = lines.iterator();
         Iterator<Component> textIt = texts.iterator();

         while(lineIt.hasNext()) {
            if (!((TooltipLine)lineIt.next()).text.equals(textIt.next())) {
               return false;
            }
         }

         return true;
      }
   }
}
