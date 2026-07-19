# VillagerMarket
*The shop plugin you've been looking for! Perfect for semi-vanilla servers.*

VillagerMarket is the all-in-one shop plugin. With VillagerMarket you can let player setup 
their own shops in a predefined shopping district, or let them place their own shops using 
shop items. **Sell**, **buy** and **trade** items, in both permanent and **rentable shops**.

> This is a personalized fork of [VillagerMarket by Bestem0r](https://github.com/Bestem0r/VillagerMarket).

## Changes in this fork

### New Commands
- `/vm edit <UUID>` — opens the edit menu for any villager shop via command (requires `villagermarket.command.edit`, op-only)
- `/vm movehere <UUID>` — now requires full UUID (no longer accepts prefix shorthand)

### Sound System
- Custom namespaced sounds (e.g. `minecraft:clavate.sell`) now work in `config.yml` sounds section
- Added missing `menu_click` sound when clicking items in customer shopfront
- Added `menu_click` sound on increase/decrease buttons in buy/sell menu

### Buy/Sell Menu (`BuyItemMenu`)
- Added a **YES** confirm button (LIME_STAINED_GLASS_PANE) — transactions now require explicit confirmation
- Fixed amount overflow: increase buttons are now capped at item max stack size; decrease buttons are capped at 1
- Back button changed to RED_STAINED_GLASS_PANE

### Shopfront Titles
- Titles for customer view, editor view, and detailed view are now fully customizable in language files
- Separate keys for single-page (`title`) and multi-page (`title_multipage`) with `%page%` and `%total%` placeholders
- Detailed view title no longer appends `(Details)` suffix by default

### Misc
- Removed hardcoded space between prefix and message; trailing space is now part of `plugin_prefix` in `config.yml`
- GUI items support `model` key for custom model data in language yml configs
