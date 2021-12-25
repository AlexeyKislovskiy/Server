package fertdt.helpers;

import com.google.gson.Gson;
import fertdt.entities.Character;
import fertdt.entities.*;

import java.util.List;

public class EffectHelper {
    public static void addEffectsToCharacters(boolean isNormalSkill, int player, int[] my, int[] opponents, Character character, Character[] myCharacters, Character[] opponentsCharacters) {
        Effect[] effects;
        if (isNormalSkill) effects = character.getNormalSkill().getEffects();
        else effects = character.getSpecialSkill().getEffects();
        for (Effect el : effects) {
            if (el.getTargetEntity() == Effect.TARGET_CHARACTER) {
                if (el.getTargetPlayer() == Effect.TARGET_YOU) {
                    effectsToCharacters(el, myCharacters, player, my);
                } else {
                    effectsToCharacters(el, opponentsCharacters, player, opponents);
                }
            }
        }
    }

    private static void effectsToCharacters(Effect el, Character[] characters, int player, int[] targetCharacters) {
        Gson gson = new Gson();
        if (el.getTargetQuantity() == Effect.TARGET_ALL) {
            for (Character ch : characters) {
                if (!effectsResistCharacterCheck(ch, el.getEffectStatus())) {
                    el.setPlayer(player);
                    ch.getEffects().add(gson.fromJson(gson.toJson(el), Effect.class));
                }
            }
        } else {
            for (int j : targetCharacters) {
                if (!effectsResistCharacterCheck(characters[j], el.getEffectStatus())) {
                    el.setPlayer(player);
                    characters[j].getEffects().add(gson.fromJson(gson.toJson(el), Effect.class));
                }
            }
        }
    }

    public static void addEffectsToBlocks(boolean isNormalSkill, int player, int[] xMy, int[] yMy, int[] xOpponent, int[] yOpponent, Character character, Field myField, Field opponentsField) {
        Effect[] effects;
        Gson gson = new Gson();
        if (isNormalSkill) effects = character.getNormalSkill().getEffects();
        else effects = character.getSpecialSkill().getEffects();
        for (Effect el : effects) {
            if (el.getTargetEntity() == Effect.TARGET_BLOCK) {
                if (el.getTargetPlayer() == Effect.TARGET_YOU) {
                    effectsToBlocks(myField, el, player, xMy, yMy);
                } else {
                    effectsToBlocks(opponentsField, el, player, xOpponent, yOpponent);
                }
            }
        }
    }

    private static void effectsToBlocks(Field field, Effect el, int player, int[] x, int[] y) {
        Gson gson = new Gson();
        Block[] blocks = field.getBlocks();
        if (el.getTargetQuantity() == Effect.TARGET_ALL) {
            for (Block block : blocks) {
                if (!effectsResistBlockCheck(block, el.getEffectStatus())) {
                    el.setPlayer(player);
                    block.getEffects().add(gson.fromJson(gson.toJson(el), Effect.class));
                }
            }
        } else {
            for (Block block : blocks) {
                int num = block.getId();
                for (int i = 0; i < x.length; i++) {
                    if (field.getG()[x[i]][y[i]] == num) {
                        if (!effectsResistBlockCheck(block, el.getEffectStatus())) {
                            el.setPlayer(player);
                            block.getEffects().add(gson.fromJson(gson.toJson(el), Effect.class));
                            break;
                        }
                    }
                }
            }
        }
    }

    public static void recalculateEffects(Game game) {
        int current = game.getCurrentTurn();
        recalculateCharactersEffects(game.getFirstCharacters(), current);
        recalculateCharactersEffects(game.getSecondCharacters(), current);
        recalculateBlocksEffects(game.getFirstField().getBlocks(), current);
        recalculateBlocksEffects(game.getSecondField().getBlocks(), current);
    }

    private static void recalculateCharactersEffects(Character[] characters, int current) {
        for (Character ch : characters) {
            for (int i = 0; i < ch.getEffects().size(); i++) {
                Effect ef = ch.getEffects().get(i);
                if (ef.getPlayer() != current && ef.getTurns() != null) {
                    ef.setTurns(ef.getTurns() - 1);
                    if (ef.getTurns() == 0) {
                        ch.getEffects().remove(i);
                        i--;
                    }
                }
            }
        }
    }

    private static void recalculateBlocksEffects(Block[] blocks, int current) {
        for (Block block : blocks) {
            for (int i = 0; i < block.getEffects().size(); i++) {
                Effect ef = block.getEffects().get(i);
                if (ef.getPlayer() != current && ef.getTurns() != null) {
                    ef.setTurns(ef.getTurns() - 1);
                    if (ef.getTurns() == 0) {
                        block.getEffects().remove(i);
                        i--;
                    }
                }
            }
        }
    }

    public static void addEffect(List<Integer> list, Effect effect) {
        list.add(effect.getId());
        list.add(effect.getPlayer());
        if (effect.getTimes() != null) list.add(effect.getTimes());
        else list.add(-1);
        if (effect.getTurns() != null) list.add(effect.getTurns());
        else list.add(-1);
        if (effect.getValue() != null) list.add(effect.getValue());
    }

    public static int damageWithEffects(int defaultDamage, Character character, Block block, Field field) {
        List<Effect> characterEffects = character.getEffects(), blockEffects = block.getEffects();
        int valueChange = 0, percentageChange = 0;
        boolean invisible = false, ignoreInvisible = false, reverseDamage = false;
        for (int i = 0; i < characterEffects.size(); i++) {
            Effect effect = characterEffects.get(i);
            if (effect.getTimes() != null) effect.setTimes(effect.getTimes() - 1);
            if (effect.getId() == 1) valueChange += effect.getValue();
            else if (effect.getId() == 2) percentageChange += effect.getValue();
            else if (effect.getId() == 3) valueChange -= effect.getValue();
            else if (effect.getId() == 4) percentageChange -= effect.getValue();
            else if (effect.getId() == 10) ignoreInvisible = true;
            else if (effect.getTimes() != null) effect.setTimes(effect.getTimes() + 1);
            if (effect.getTimes() != null && effect.getTimes() == 0) {
                characterEffects.remove(i);
                i--;
            }
        }
        for (int i = 0; i < blockEffects.size(); i++) {
            Effect effect = blockEffects.get(i);
            if (effect.getTimes() != null) effect.setTimes(effect.getTimes() - 1);
            if (effect.getId() == 5) valueChange -= effect.getValue();
            else if (effect.getId() == 6) percentageChange -= effect.getValue();
            else if (effect.getId() == 7) valueChange += effect.getValue();
            else if (effect.getId() == 8) percentageChange += effect.getValue();
            else if (effect.getId() == 9) invisible = true;
            else if (effect.getId() == 13) reverseDamage = true;
            else if (effect.getTimes() != null) effect.setTimes(effect.getTimes() + 1);
            if (effect.getTimes() != null && effect.getTimes() == 0) {
                blockEffects.remove(i);
                i--;
            }
        }
        int damage = defaultDamage * (100 + percentageChange) / 100 + valueChange;
        if (damage < 0) damage = 0;
        if (invisible && !ignoreInvisible) damage = 0;
        if (reverseDamage) {
            damage = -damage;
            if (block.getHp() - damage > field.getBlockStateInitial().get(block.getId() - 1).get(1))
                damage = block.getHp() - field.getBlockStateInitial().get(block.getId() - 1).get(1);
        }
        return damage;
    }

    public static void handleInstantEffects(Game game) {
        Character[] firstCharacters = game.getFirstCharacters(), secondCharacters = game.getSecondCharacters();
        Block[] firstBlocks = game.getFirstField().getBlocks(), secondBlocks = game.getSecondField().getBlocks();
        for (Character character : firstCharacters) {
            handleCharacterInstantEffects(character);
        }
        for (Character character : secondCharacters) {
            handleCharacterInstantEffects(character);
        }
        for (Block block : firstBlocks) {
            handleBlockInstantEffects(block, game.getFirstField(), game.getFirstPoints());
        }
        for (Block block : secondBlocks) {
            handleBlockInstantEffects(block, game.getSecondField(), game.getSecondPoints());
        }
    }

    private static void handleCharacterInstantEffects(Character character) {
        List<Effect> effects = character.getEffects();
        for (int i = 0; i < effects.size(); i++) {
            Effect effect = effects.get(i);
            if (effect.getId() == 21) {
                effects.remove(i);
                characterRemoveEffects(effects, Effect.DEBUFF);
                i = -1;
            } else if (effect.getId() == 23) {
                effects.remove(i);
                characterRemoveEffects(effects, Effect.BUFF);
                i = -1;
            }
        }
    }

    private static void characterRemoveEffects(List<Effect> effects, int effectStatus) {
        for (int j = 0; j < effects.size(); j++) {
            Effect innerEffect = effects.get(j);
            if (innerEffect.getEffectStatus() == effectStatus && (innerEffect.getTimes() != null || innerEffect.getTurns() != null)) {
                effects.remove(j);
                j--;
            }
        }
    }

    private static void handleBlockInstantEffects(Block block, Field field, int[] points) {
        int initialHp = field.getBlockStateInitial().get(block.getId() - 1).get(1);
        List<Effect> effects = block.getEffects();
        for (int i = 0; i < effects.size(); i++) {
            Effect effect = effects.get(i);
            if (effect.getId() == 11) {
                block.setHp(0);
                effects.remove(i);
                i--;
                int pointsForBlock = 0;
                for (int j = 0; j < field.getX().length; j++) {
                    if (field.getX()[j][1] == block.getId()) pointsForBlock++;
                }
                points[0] += pointsForBlock;
                points[1] += pointsForBlock;
                int broken = 0;
                for (Block bl : field.getBlocks()) {
                    if (bl.getHp() == 0) broken++;
                }
                if (broken == field.getBlocks().length) field.regenerateField();
            } else if (effect.getId() == 12) {
                block.setHp(block.getHp() + effect.getValue());
                if (block.getHp() > initialHp) block.setHp(initialHp);
                effects.remove(i);
                i--;
            } else if (effect.getId() == 20) {
                effects.remove(i);
                for (int j = 0; j < effects.size(); j++) {
                    Effect innerEffect = effects.get(j);
                    if (innerEffect.getEffectStatus() == Effect.DEBUFF && (innerEffect.getTimes() != null || innerEffect.getTurns() != null)) {
                        effects.remove(j);
                        j--;
                    }
                }
                i = -1;
            } else if (effect.getId() == 22) {
                effects.remove(i);
                for (int j = 0; j < effects.size(); j++) {
                    Effect innerEffect = effects.get(j);
                    if (innerEffect.getEffectStatus() == Effect.BUFF && (innerEffect.getTimes() != null || innerEffect.getTurns() != null)) {
                        effects.remove(j);
                        j--;
                    }
                }
                i = -1;
            }
        }
    }

    public static void blockMoveCheck(Character[] characters) {
        for (Character character : characters) {
            List<Effect> effects = character.getEffects();
            for (Effect effect : effects) {
                if (effect.getId() == 15) {
                    character.setMadeMove(true);
                    break;
                }
            }
        }
    }

    public static boolean blockSkillUseCheck(Character character) {
        List<Effect> effects = character.getEffects();
        for (Effect effect : effects) {
            if (effect.getId() == 14) {
                return true;
            }
        }
        return false;
    }

    public static boolean effectsResistCharacterCheck(Character character, int effectStatus) {
        List<Effect> effects = character.getEffects();
        for (int i = 0; i < effects.size(); i++) {
            Effect effect = effects.get(i);
            if (effect.getId() == 17 && effectStatus == Effect.DEBUFF) {
                if (effect.getTimes() != null) {
                    effect.setTimes(effect.getTimes() - 1);
                    if (effect.getTimes() == 0) {
                        effects.remove(i);
                    }
                }
                return true;
            } else if (effect.getId() == 19 && effectStatus == Effect.BUFF) {
                if (effect.getTimes() != null) {
                    effect.setTimes(effect.getTimes() - 1);
                    if (effect.getTimes() == 0) {
                        effects.remove(i);
                    }
                }
                return true;
            }
        }
        return false;
    }

    public static boolean effectsResistBlockCheck(Block block, int effectStatus) {
        List<Effect> effects = block.getEffects();
        for (int i = 0; i < effects.size(); i++) {
            Effect effect = effects.get(i);
            if (effect.getId() == 16 && effectStatus == Effect.DEBUFF) {
                if (effect.getTimes() != null) {
                    effect.setTimes(effect.getTimes() - 1);
                    if (effect.getTimes() == 0) {
                        effects.remove(i);
                    }
                }
                return true;
            } else if (effect.getId() == 18 && effectStatus == Effect.BUFF) {
                if (effect.getTimes() != null) {
                    effect.setTimes(effect.getTimes() - 1);
                    if (effect.getTimes() == 0) {
                        effects.remove(i);
                    }
                }
                return true;
            }
        }
        return false;
    }
}
