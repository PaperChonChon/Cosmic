/*
	This file is part of the OdinMS Maple Story Server
    Copyright (C) 2008 Patrick Huy <patrick.huy@frz.cc>
		       Matthias Butz <matze@odinms.de>
		       Jan Christian Meyer <vimes@odinms.de>

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as
    published by the Free Software Foundation version 3 as published by
    the Free Software Foundation. You may not use, modify or distribute
    this program under any other version of the GNU Affero General Public
    License.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
/* 	
	NPC Name: 		Big Headward
        Map(s): 		Victoria Road : Henesys Hair Salon (100000104)
	Description: 		Random haircut

        GMS-like revised by Ronan -- contents found thanks to Mitsune (GamerBewbs), Waltzing, AyumiLove
*/

var status = 0;

var hair_main = Array();

var mhair_r1 = Array(30007, 30027, 30037, 30047, 30057, 30067, 30107, 30117, 30127, 30137, 30147, 30157, 30167, 30177, 30187, 30197, 30207, 30217, 30227, 30237, 30247, 30257, 30267, 30277, 30287, 30297, 30307, 30317, 30327, 30337, 30347, 30357, 30367, 30377, 30387, 30407, 30417, 30427, 30437, 30447, 30457, 30467, 30477, 30487, 30497, 30517, 30527, 30537, 30547, 30557, 30567, 30577, 30587, 30597, 30607, 30617, 30627, 30637, 30647, 30657, 30667, 30677, 30687, 30697, 30707, 30717, 30727, 30737, 30747, 30757, 30767, 30777, 30787, 30797, 30807, 30817, 30827, 30837, 30847, 30857, 30867, 30877, 30887, 30897, 30907, 30917, 30927, 30937, 30947, 30957, 30967, 30977, 30987, 30997, 31007, 31017, 31027, 31037, 31047, 31057, 31067, 31077, 31087, 31097, 31107, 31117, 31127, 31137, 31147, 31157, 31167, 31177, 31187, 31197, 31207, 31217, 31227, 31237, 31247, 31257, 31267, 31277, 31287, 31297, 31307, 31317, 31327);

var hairnew = Array();

function pushIfItemExists(array, itemid) {
    if ((itemid = cm.getCosmeticItem(itemid)) != -1 && !cm.isCosmeticEquipped(itemid)) {
        array.push(itemid);
    }
}

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode < 1) {  // disposing issue with stylishs found thanks to Vcoc
        cm.dispose();
    } else {
        if (mode == 1) {
            status++;
        } else {
            status--;
        }

        if (status == 0) {
            cm.sendSimple("Hi, I'm #p1012117#, the most charming and stylish stylist around. If you're looking for the best looking hairdos around, look no further!\r\n\#L0##i5150040##t5150040##l\r\n\#L1##i5150044##t5150044##l");
        } else if (status == 1) {
            if (selection == 0) {
                beauty = 1;
                cm.sendYesNo("If you use this REGULAR coupon, your hair may transform into a random new look...do you still want to do it using #b#t5150040##k, I will do it anyways for you. But don't forget, it will be random!");
            } else {
                beauty = 2;

                hairnew = Array();
                if (cm.getPlayer().getGender() == 0 || cm.getPlayer().getGender() == 1) {
                    for (var i = 0; i < mhair_r1.length; i++) {
                        //console.log("here1: "+parseInt(cm.getPlayer().getHair() % 10))
                        pushIfItemExists(hairnew, mhair_r1[i]);
                    }
                } else {
                        console.log("Wrong gender for hair - 9999999 NPC")
                }

                cm.sendStyle("Using the SPECIAL coupon you can choose the style your hair will become. Pick the style that best provides you delight...", hairnew);
            }
        } else if (status == 2) {
            if (beauty == 1) {
                if (cm.haveItem(5150040) == true) {
                    hairnew = Array();
                    if (cm.getPlayer().getGender() == 0 || cm.getPlayer().getGender() == 1) {
                        for (var i = 0; i < mhair_r1.length; i++) {
                            console.log("here2: "+parseInt(cm.getPlayer().getHair() % 10))
                            pushIfItemExists(hairnew, mhair_r1[i]);
                        }
                    } else {
                        console.log("Wrong gender for hair - 9999999 NPC")
                    }

                    cm.gainItem(5150040, -1);
                    cm.setHair(hairnew[Math.floor(Math.random() * hairnew.length)]);
                    cm.sendOk("Enjoy your new and improved hairstyle!");
                } else {
                    cm.sendOk("Hmmm...it looks like you don't have our designated coupon...I'm afraid I can't give you a haircut without it. I'm sorry...");
                }
            } else if (beauty == 2) {
                if (cm.haveItem(5150044) == true) {
                    cm.gainItem(5150044, -1);
                    cm.setHair(hairnew[selection]);
                    cm.sendOk("Enjoy your new and improved hairstyle!");
                } else {
                    cm.sendOk("Hmmm...it looks like you don't have our designated coupon...I'm afraid I can't give you a haircut without it. I'm sorry...");
                }
            }

            cm.dispose();
        }
    }
}
