package fertdt.helpers;

import com.google.gson.Gson;
import fertdt.entities.Character;
import fertdt.entities.*;

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
        Gson gson = new Gson();
        if (isNormalSkill) effects = character.getNormalSkill().getEffects();
        else effects = character.getSpecialSkill().getEffects();
        for (Effect el : effects) {
            if (el.getTargetEntity() == Effect.TARGET_BLOCK) {
                if (el.getTargetPlayer() == Effect.TARGET_YOU) {
                    Block[] blocks = myField.getBlocks();
                    if (el.getTargetQuantity() == Effect.TARGET_ALL) {
                        for (Block block : blocks) {
                            el.setPlayer(player);
                            block.getEffects().add(gson.fromJson(gson.toJson(el), Effect.class));
                        }
                    } else {
                        for (Block block : blocks) {
                            int num = block.getId();
                            for (int i = 0; i < xMy.length; i++) {
                                if (myField.getG()[xMy[i]][yMy[i]] == num) {
                                    el.setPlayer(player);
                                    block.getEffects().add(gson.fromJson(gson.toJson(el), Effect.class));
                                    break;
                                }
                            }
                        }
                    }
                } else {
                    Block[] blocks = opponentsField.getBlocks();
                    if (el.getTargetQuantity() == Effect.TARGET_ALL) {
                        for (Block block : blocks) {
                            el.setPlayer(player);
                            block.getEffects().add(gson.fromJson(gson.toJson(el), Effect.class));
                        }
                    } else {
                        for (Block block : blocks) {
                            int num = block.getId();
                            for (int i = 0; i < xOpponent.length; i++) {
                                if (opponentsField.getG()[xOpponent[i]][yOpponent[i]] == num) {
                                    el.setPlayer(player);
                                    block.getEffects().add(gson.fromJson(gson.toJson(el), Effect.class));
                                    break;
                                }
                            }
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

    public static int damageWithEffects(int defaultDamage, Character character, Block block) {
        List<Effect> characterEffects = character.getEffects(), blockEffects = block.getEffects();
        int valueChange = 0, percentageChange = 0;
        for (int i = 0; i < characterEffects.size(); i++) {
            Effect effect = characterEffects.get(i);
            if (effect.getTimes() != null) effect.setTimes(effect.getTimes() - 1);
            if (effect.getId() == 1) valueChange += effect.getValue();
            else if (effect.getId() == 2) percentageChange += effect.getValue();
            else if (effect.getId() == 3) valueChange -= effect.getValue();
            else if (effect.getId() == 4) percentageChange -= effect.getValue();
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
            else if (effect.getTimes() != null) effect.setTimes(effect.getTimes() + 1);
            if (effect.getTimes() != null && effect.getTimes() == 0) {
                blockEffects.remove(i);
                i--;
            }
        }
        int damage = defaultDamage * (100 + percentageChange) / 100 + valueChange;
        if (damage < 0) damage = 0;
        return damage;
    }
}
