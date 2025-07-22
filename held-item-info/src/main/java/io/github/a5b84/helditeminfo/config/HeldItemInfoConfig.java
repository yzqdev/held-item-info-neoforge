package io.github.a5b84.helditeminfo.config;

import java.util.Collections;
import java.util.List;

import io.github.a5b84.helditeminfo.HeldItemInfoClient;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry.BoundedDiscrete;
import me.shedaniel.autoconfig.annotation.ConfigEntry.Category;
import me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.PrefixText;
import me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.Tooltip;

public class HeldItemInfoConfig {
   public int maxLines() {
      return 6;
   }

   public int maxLineLength() {
      return 48;
   }

   public boolean respectHideFlags() {
      return true;
   }

   public int lineHeight() {
      return 8;
   }

   public float offsetPerExtraLine() {
      return 0.3334F;
   }

   public int itemNameSpacing() {
      return 2;
   }

   public int verticalOffset() {
      return 0;
   }

   public float baseFadeDuration() {
      return 2.0F;
   }

   public float fadeDurationPerExtraLine() {
      return 0.2F;
   }

   public boolean showName() {
      return true;
   }

   public boolean showEnchantments() {
      return true;
   }

   public boolean showPotionEffects() {
      return true;
   }

   public boolean showContainerContent() {
      return true;
   }

   public boolean mergeSimilarContainerItems() {
      return true;
   }

   public boolean showFireworkAttributes() {
      return true;
   }

   public boolean showCommandBlockInfo() {
      return true;
   }

   public int maxCommandLines() {
      return 2;
   }

   public boolean showBeehiveContent() {
      return true;
   }

   public boolean showSpawnerEntity() {
      return true;
   }

   public boolean showCrossbowProjectiles() {
      return true;
   }

   public boolean showLore() {
      return true;
   }

   public int maxLoreLines() {
      return 3;
   }

   public boolean showUnbreakable() {
      return true;
   }

   public boolean showSignText() {
      return true;
   }

   public boolean showMusicDiscDescription() {
      return true;
   }

   public boolean showPaintingDescription() {
      return true;
   }

   public boolean showGoatHornInstrument() {
      return true;
   }

   public boolean showBookMeta() {
      return true;
   }

   public boolean showFilledMapId() {
      return false;
   }

   public boolean showBannerPatternName() {
      return true;
   }

   public boolean showBannerPatterns() {
      return false;
   }

   public boolean showEntityBucketContent() {
      return true;
   }

   public boolean showHiddenLinesCount() {
      return true;
   }

   public boolean showOnlyFilteredEnchantments() {
      return false;
   }

   @Config(
      name = HeldItemInfoClient.MOD_ID
   )
   public static class HeldItemInfoAutoConfig extends HeldItemInfoConfig implements ConfigData {
      @BoundedDiscrete(
         min = 0L,
         max = 40L
      )
      @Tooltip
      private int maxLines = super.maxLines();
      @BoundedDiscrete(
         max = 240L
      )
      @Tooltip(
         count = 2
      )
      private int maxLineLength = super.maxLineLength();
      @Tooltip
      private boolean respectHideFlags = super.respectHideFlags();
      @Tooltip
      private int lineHeight = super.lineHeight();
      @Tooltip(
         count = 2
      )
      private float offsetPerExtraLine = super.offsetPerExtraLine();
      @Tooltip
      private int itemNameSpacing = super.itemNameSpacing();
      private int verticalOffset = super.verticalOffset();
      @Tooltip
      private float baseFadeDuration = super.baseFadeDuration();
      @Tooltip
      private float fadeDurationPerExtraLine = super.fadeDurationPerExtraLine();
      @PrefixText
      private boolean showName = super.showName();
      private boolean showEnchantments = super.showEnchantments();
      private boolean showPotionEffects = super.showPotionEffects();
      private boolean showContainerContent = super.showContainerContent();
      private boolean mergeSimilarContainerItems = super.mergeSimilarContainerItems();
      private boolean showFireworkAttributes = super.showFireworkAttributes();
      private boolean showCommandBlockInfo = super.showCommandBlockInfo();
      @BoundedDiscrete(
         max = 40L
      )
      @Tooltip
      private int maxCommandLines = super.maxCommandLines();
      private boolean showBeehiveContent = super.showBeehiveContent();
      private boolean showSpawnerEntity = super.showSpawnerEntity();
      private boolean showCrossbowProjectiles = super.showCrossbowProjectiles();
      private boolean showLore = super.showLore();
      @BoundedDiscrete(
         max = 40L
      )
      private int maxLoreLines = super.maxLoreLines();
      private boolean showUnbreakable = super.showUnbreakable();
      private boolean showSignText = super.showSignText();
      private boolean showMusicDiscDescription = super.showMusicDiscDescription();
      private boolean showPaintingDescription = super.showPaintingDescription();
      private boolean showGoatHornInstrument = super.showGoatHornInstrument();
      private boolean showBookMeta = super.showBookMeta();
      private boolean showFilledMapId = super.showFilledMapId();
      @Tooltip
      private boolean showBannerPatternName = super.showBannerPatternName();
      @Tooltip
      private boolean showBannerPatterns = super.showBannerPatterns();
      private boolean showEntityBucketContent = super.showEntityBucketContent();
      private boolean showHiddenLinesCount = super.showHiddenLinesCount();
      @Category("enchantments")
      @Tooltip(
         count = 2
      )
      private boolean showOnlyFilteredEnchantments = super.showOnlyFilteredEnchantments();
      @Category("enchantments")
      @Tooltip(
         count = 2
      )
      private List<String> filteredEnchantments = Collections.emptyList();

      public int maxLines() {
         return this.maxLines;
      }

      public int maxLineLength() {
         return this.maxLineLength;
      }

      public boolean respectHideFlags() {
         return this.respectHideFlags;
      }

      public int lineHeight() {
         return this.lineHeight;
      }

      public float offsetPerExtraLine() {
         return this.offsetPerExtraLine;
      }

      public int itemNameSpacing() {
         return this.itemNameSpacing;
      }

      public int verticalOffset() {
         return this.verticalOffset;
      }

      public float baseFadeDuration() {
         return this.baseFadeDuration;
      }

      public float fadeDurationPerExtraLine() {
         return this.fadeDurationPerExtraLine;
      }

      public boolean showName() {
         return this.showName;
      }

      public boolean showEnchantments() {
         return this.showEnchantments;
      }

      public boolean showPotionEffects() {
         return this.showPotionEffects;
      }

      public boolean showContainerContent() {
         return this.showContainerContent;
      }

      public boolean mergeSimilarContainerItems() {
         return this.mergeSimilarContainerItems;
      }

      public boolean showFireworkAttributes() {
         return this.showFireworkAttributes;
      }

      public boolean showCommandBlockInfo() {
         return this.showCommandBlockInfo;
      }

      public int maxCommandLines() {
         return this.maxCommandLines;
      }

      public boolean showBeehiveContent() {
         return this.showBeehiveContent;
      }

      public boolean showSpawnerEntity() {
         return this.showSpawnerEntity;
      }

      public boolean showCrossbowProjectiles() {
         return this.showCrossbowProjectiles;
      }

      public boolean showLore() {
         return this.showLore;
      }

      public int maxLoreLines() {
         return this.maxLoreLines;
      }

      public boolean showUnbreakable() {
         return this.showUnbreakable;
      }

      public boolean showSignText() {
         return this.showSignText;
      }

      public boolean showMusicDiscDescription() {
         return this.showMusicDiscDescription;
      }

      public boolean showPaintingDescription() {
         return this.showPaintingDescription;
      }

      public boolean showGoatHornInstrument() {
         return this.showGoatHornInstrument;
      }

      public boolean showBookMeta() {
         return this.showBookMeta;
      }

      public boolean showFilledMapId() {
         return this.showFilledMapId;
      }

      public boolean showBannerPatternName() {
         return this.showBannerPatternName;
      }

      public boolean showBannerPatterns() {
         return this.showBannerPatterns;
      }

      public boolean showEntityBucketContent() {
         return this.showEntityBucketContent;
      }

      public boolean showHiddenLinesCount() {
         return this.showHiddenLinesCount;
      }

      public boolean showOnlyFilteredEnchantments() {
         return this.showOnlyFilteredEnchantments;
      }

      public List<String> filteredEnchantments() {
         return this.filteredEnchantments;
      }
   }
}
