package fertdt.helpers;

import fertdt.entities.Character;
import fertdt.entities.Effect;
import fertdt.entities.Field;

import java.util.List;

public class EffectHelper {
    public static void addEffectsToCharacters(boolean isNormalSkill, int[] my, int[] opponents, Character character, Character[] myCharacters, Character[] opponentsCharacters) {
        Effect[] effects;
        if (isNormalSkill) effects = character.getNormalSkill().getEffects();
        else effects = character.getSpecialSkill().getEffects();
        for (Effect el : effects) {
            if (el.getTargetEntity() == Effect.TARGET_CHARACTER) {
                if (el.getTargetPlayer() == Effect.TARGET_YOU) {
                    for (int j : my) {
                        myCharacters[j].getEffects().add(el);
                    }
                } else {
                    for (int j : opponents) {
                        opponentsCharacters[j].getEffects().add(el);
                    }
                }
            }
        }
    }

    public static void addEffectsToBlocks(boolean isNormalSkill, int[] xMy, int[] yMy, int[] xOpponent, int[] yOpponent, Character character, Field myField, Field opponentsField) {
        Effect[] effects;
        if (isNormalSkill) effects = character.getNormalSkill().getEffects();
        else effects = character.getSpecialSkill().getEffects();
        for (Effect el : effects) {
            if (el.getTargetEntity() == Effect.TARGET_BLOCK) {
                if (el.getTargetPlayer() == Effect.TARGET_YOU) {
                    List<List<Integer>> blocks = myField.getRawBlockState();
                    for (List<Integer> block : blocks) {
                        int num = block.get(0);
                        for (int i = 0; i < xMy.length; i++) {
                            if (myField.getG()[xMy[i]][yMy[i]] == num) {
                                addEffect(block, el);
                                break;
                            }
                        }
                    }
                } else {
                    List<List<Integer>> blocks = opponentsField.getRawBlockState();
                    for (List<Integer> block : blocks) {
                        int num = block.get(0);
                        for (int i = 0; i < xOpponent.length; i++) {
                            if (opponentsField.getG()[xOpponent[i]][yOpponent[i]] == num) {
                                addEffect(block, el);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    public static void addEffect(List<Integer> list, Effect effect) {
        list.add(effect.getId());
        if (effect.getTimes() != null) list.add(effect.getTimes());
        else list.add(0);
        if (effect.getTurns() != null) list.add(effect.getTurns());
        else list.add(0);
        if (effect.getValue() != null) list.add(effect.getValue());
    }
}
