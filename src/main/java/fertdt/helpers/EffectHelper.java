package fertdt.helpers;

import com.google.gson.Gson;
import fertdt.entities.Character;
import fertdt.entities.Effect;
import fertdt.entities.Field;
import fertdt.entities.Game;

import java.util.ArrayList;
import java.util.List;

public class EffectHelper {
    public static void addEffectsToCharacters(boolean isNormalSkill, int player, int[] my, int[] opponents, Character character, Character[] myCharacters, Character[] opponentsCharacters) {
        Effect[] effects;
        Gson gson = new Gson();
        if (isNormalSkill) effects = character.getNormalSkill().getEffects();
        else effects = character.getSpecialSkill().getEffects();
        for (Effect el : effects) {
            if (el.getTargetEntity() == Effect.TARGET_CHARACTER) {
                if (el.getTargetPlayer() == Effect.TARGET_YOU) {
                    if (el.getTargetQuantity() == Effect.TARGET_ALL) {
                        for (Character ch : myCharacters) {
                            el.setPlayer(player);
                            ch.getEffects().add(gson.fromJson(gson.toJson(el), Effect.class));
                        }
                    } else {
                        for (int j : my) {
                            el.setPlayer(player);
                            myCharacters[j].getEffects().add(gson.fromJson(gson.toJson(el), Effect.class));
                        }
                    }
                } else {
                    if (el.getTargetQuantity() == Effect.TARGET_ALL) {
                        for (Character ch : opponentsCharacters) {
                            el.setPlayer(player);
                            ch.getEffects().add(gson.fromJson(gson.toJson(el), Effect.class));
                        }
                    } else {
                        for (int j : opponents) {
                            el.setPlayer(player);
                            opponentsCharacters[j].getEffects().add(gson.fromJson(gson.toJson(el), Effect.class));
                        }
                    }
                }
            }
        }
    }

    public static void addEffectsToBlocks(boolean isNormalSkill, int player, int[] xMy, int[] yMy, int[] xOpponent, int[] yOpponent, Character character, Field myField, Field opponentsField) {
        Effect[] effects;
        if (isNormalSkill) effects = character.getNormalSkill().getEffects();
        else effects = character.getSpecialSkill().getEffects();
        for (Effect el : effects) {
            if (el.getTargetEntity() == Effect.TARGET_BLOCK) {
                if (el.getTargetPlayer() == Effect.TARGET_YOU) {
                    List<List<Integer>> blocks = myField.getRawBlockState();
                    if (el.getTargetQuantity() == Effect.TARGET_ALL) {
                        for (List<Integer> block : blocks) {
                            el.setPlayer(player);
                            addEffect(block, el);
                        }
                    } else {
                        for (List<Integer> block : blocks) {
                            int num = block.get(0);
                            for (int i = 0; i < xMy.length; i++) {
                                if (myField.getG()[xMy[i]][yMy[i]] == num) {
                                    el.setPlayer(player);
                                    addEffect(block, el);
                                    break;
                                }
                            }
                        }
                    }
                } else {
                    List<List<Integer>> blocks = opponentsField.getRawBlockState();
                    if (el.getTargetQuantity() == Effect.TARGET_ALL) {
                        for (List<Integer> block : blocks) {
                            el.setPlayer(player);
                            addEffect(block, el);
                        }
                    } else {
                        for (List<Integer> block : blocks) {
                            int num = block.get(0);
                            for (int i = 0; i < xOpponent.length; i++) {
                                if (opponentsField.getG()[xOpponent[i]][yOpponent[i]] == num) {
                                    el.setPlayer(player);
                                    addEffect(block, el);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static void recalculateSkills(Game game) {
        int current = game.getCurrentTurn();
        recalculateCharactersSkills(game.getFirstCharacters(), current);
        recalculateCharactersSkills(game.getSecondCharacters(), current);
        recalculateBlocksSkills(game.getFirstField().getRawBlockState(), current);
        recalculateBlocksSkills(game.getSecondField().getRawBlockState(), current);
    }

    private static void recalculateCharactersSkills(Character[] characters, int current) {
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

    private static void recalculateBlocksSkills(List<List<Integer>> blocks, int current) {
        for (List<Integer> block : blocks) {
            for (int i = 2; i < block.size(); i++) {
                int valuable = Effect.VALUABLE.get(block.get(i));
                int player = block.get(i + 1);
                if (player != current) {
                    int turns = block.get(i + 3);
                    if (turns != 0) {
                        block.set(i + 3, turns - 1);
                        if (turns == 1) {
                            block.remove(i);
                            block.remove(i);
                            block.remove(i);
                            block.remove(i);
                            if (valuable == Effect.VALUABLE_TRUE) block.remove(i);
                            i--;
                            continue;
                        }
                    }
                }
                if (valuable == Effect.VALUABLE_TRUE) i += 4;
                else i += 3;
            }
        }
    }

    public static void addEffect(List<Integer> list, Effect effect) {
        list.add(effect.getId());
        list.add(effect.getPlayer());
        if (effect.getTimes() != null) list.add(effect.getTimes());
        else list.add(0);
        if (effect.getTurns() != null) list.add(effect.getTurns());
        else list.add(0);
        if (effect.getValue() != null) list.add(effect.getValue());
    }

}
