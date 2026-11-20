# AE2UEL Chef Leopard Patches

AE2UEL has some critical bugs in 0.56.7, so this addon is a patch with mixins for this version. This is only to patch bugs & add optimizations, not add features. Hopefully a new AE2UEL fork is maintained soon.

## Fixes

- [Fix Fluid Storage Bus Reset Spam](https://github.com/AE2-UEL/Applied-Energistics-2/pull/588/changes/e4bffc79cb9ab50b3b2714610be5d7a7ae49463a), originally by kurrycat
- Fix fluid storage bus not reconnecting after a fluid interface is broken and replaced. PR 588 skipped handler rebuilds when the hash was unchanged, but the fluid bus hashed only the tile and accessor, so the first rebuild after place could lock in a null handler before the interface grid was ready. The hash now includes the resolved fluid inventory, matching the item storage bus.
- [Improve partition list handling to avoid semi-expensive isEmpty](https://github.com/MasonD/Applied-Energistics-2/commit/75fd8e458a7b46b9331e1b648a567e3784399d9c), originally by MasonD. Precise and fuzzy partition lists cache their empty state at construction instead of building an iterator on every insert/extract check, merged lists skip empty sub-lists, and inventory handlers share one empty partition list.
- [Fix fluid P2P attunement producing an FE P2P](https://github.com/MasonD/Applied-Energistics-2/commit/55d3f10aa84399e0e9e13460e134192b171443e8), originally by MasonD. Fixes [Fluid P2P Error](https://github.com/AE2-UEL/Applied-Energistics-2/issues/546).
- [Don't crash when fluid bus placed on empty fluid drawer](https://github.com/MasonD/Applied-Energistics-2/commit/ffe8561c9a40dd432366d312e27f0e22aabe2f19), originally by MasonD. In Extractable Only mode the fluid storage bus called `drain(null)` on empty tanks, which crashes fluid drawers; empty tanks are now skipped.
- [Revert 724f3ba](https://github.com/AE2-UEL/Applied-Energistics-2/commit/844ae6e78a797f1f48d72afd7530bcd0b8323e05), originally by kurrycat. The shared item stack registry was keyed only by hash code, so different items with colliding hashes were merged into the same shared stack. It is now keyed by `AESharedItemStack` equality with weak references.

## Configuration

Every fix can be turned off in `config/ae2uelchefleopardpatches.cfg`.

## Dependencies

- Obviously [AE2UEL 0.56.7](https://github.com/AE2-UEL/Applied-Energistics-2)
- Requires MixinBooter

## Credits

- [AE2UEL](https://github.com/AE2-UEL/Applied-Energistics-2) by the AE2-UEL team, licensed under LGPLv3.0.
- kurrycat, for the fluid storage bus reset spam fix ([AE2-UEL PR 588](https://github.com/AE2-UEL/Applied-Energistics-2/pull/588)) and more from [kurrycat2004/Applied-Energistics-2](https://github.com/kurrycat2004/Applied-Energistics-2).
- MasonD, for fixes from [MasonD/Applied-Energistics-2](https://github.com/MasonD/Applied-Energistics-2).

## Development Notes

Parts of the code were written with the help of an AI assistant.