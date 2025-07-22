package io.github.a5b84.helditeminfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;

public final class Util {
   public static final int FONT_HEIGHT = 9;
   private static final double CHARACTER_WIDTH = (double)6.0F;

   private Util() {
   }

   public static void setAllToDefaultColor(List<Component> lines) {
      for(Component line : lines) {
         if (line instanceof MutableComponent mutableLine) {
            mutableLine.withStyle(TooltipBuilder.DEFAULT_COLOR);
         }
      }

   }

   public static boolean isBlank(FormattedText visitable) {
      Optional<Boolean> result = visitable.visit((asString) -> asString.isBlank() ? Optional.empty() : Optional.of(false));
      return result.isEmpty();
   }

   public static List<MutableComponent> wrapLines(String s, int maxLines) {
      if (maxLines <= 0) {
         return Collections.emptyList();
      } else {
         double maxLength = (double)1.25F * (double)maxLines * (double) HeldItemInfoClient.config.maxLineLength();
         if (maxLength <= (double)0.0F) {
            return Collections.emptyList();
         } else {
            if (maxLength > (double)Integer.MAX_VALUE) {
               maxLength = (double)Integer.MAX_VALUE;
            }

            boolean wasShortened = (double)s.length() > maxLength;
            if (wasShortened) {
               s = s.substring(0, (int)maxLength);
            }

            List<MutableComponent> lines = new ArrayList(maxLines);
            double maxWidth = (double) HeldItemInfoClient.config.maxLineLength() * (double)6.0F;
            if (maxWidth > (double)Integer.MAX_VALUE) {
               maxWidth = (double)Integer.MAX_VALUE;
            }

            Font textRenderer = Minecraft.getInstance().font;
            String finalS = s;
            textRenderer.getSplitter().splitLines(s, (int)maxWidth, Style.EMPTY, false, (style, start, end) -> lines.add(Component.literal(finalS.substring(start, end))));
            if (lines.size() > maxLines) {
               lines.subList(maxLines, lines.size()).clear();
               wasShortened = true;
            }

            if (wasShortened) {
               ((MutableComponent)lines.getLast()).append("...");
            }

            return lines;
         }
      }
   }

   public static List<MutableComponent> wrapLines(FormattedText s, int maxLines) {
      if (maxLines <= 0) {
         return Collections.emptyList();
      } else {
         double maxWidth = (double) HeldItemInfoClient.config.maxLineLength() * (double)6.0F;
         if (maxWidth > (double)Integer.MAX_VALUE) {
            maxWidth = (double)Integer.MAX_VALUE;
         }

         Font textRenderer = Minecraft.getInstance().font;
         List<FormattedText> strings = textRenderer.getSplitter().splitLines(s, (int)maxWidth, Style.EMPTY);
         List<MutableComponent> lines = new ArrayList(maxLines);

         for(FormattedText visitable : strings) {
            lines.add(Component.literal(visitable.getString()));
            if (lines.size() >= maxLines) {
               if (strings.size() > maxLines) {
                  ((MutableComponent)lines.get(maxLines - 1)).append("...");
               }
               break;
            }
         }

         return lines;
      }
   }
}
