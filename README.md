# Villager Group Discounts (VGD)

This is a [Paper](https://papermc.io/) plugin designed to make the discounts that villagers offer available to a (user defined) group of players (instead of only to the player that cured the zombie villager).

## Table of contents
  - [Install](#install)
  - [Usage](#usage)
  - [Issues](#issues)

## Install

To install the plugin, all you need to do is place the `VillagerGroupDiscount-X.X.jar` into your `plugins` folder. VGD uses an H2 database. You cannot change what database you use currently.

## Usage

* Firstly a player must create a group by executing the `/vgd group create "Your group name here"` command.
* Once the group has been created, the owner of the group can invite other players with the `/vgd group invite "The name of the player you want to invite"` command. The player you want to invite will then receive the invite in his chat which he can accept or decline.

**:warning: Important: the plugin keeps track of all the villagers that get cured. Even when the player isn't in a team. Once a player decides to join a team, all his previously cured villagers will also be affected so that the whole group will have the discount.**

**:exclamation: Do note that villagers that were cured before the plugin was installed will not have been registered, and thus will not be offering the discount to the whole group. I am working on a system to manually register cured villagers, but this feature hasn't been added to the plugin yet.**

If a player decides to leave a group, all the villagers that he cured will no longer offer discounts to the whole group.

Also, zombie villagers can still be cured multiple times as per the [Minecraft wiki](https://minecraft.fandom.com/wiki/Zombie_Villager#Curing).

## Issues

Feel free to open an issue on Github when you encounter bugs :sweat_smile:!

